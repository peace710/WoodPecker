package me.peace.utils

import android.content.Context
import android.util.Log
import me.peace.constant.Constant
import java.io.*

object CrashUtils {
    fun save(
        context: Context, throwable: Throwable,
        date: String, crashRecordCount: Int
    ): Boolean {
        val fileName = "Crash-$date.log"
        val crashDir = context.cacheDir.path + Constant.CRASH_DIR
        val crashPath = crashDir + fileName
        val file = File(crashPath)
        if (file.exists()) {
            file.delete()
        } else {
            if (!createFile(file, crashRecordCount)) {
                return false
            }
        }
        return write(file, throwable)
    }

    private fun createFile(file: File?, crashRecordCount: Int): Boolean {
        if (file != null) {
            try {
                val dir = File(file.parent)
                dir.mkdirs()
                val childFiles = listFiles(dir)
                if (childFiles != null) {
                    val count = childFiles.size - crashRecordCount
                    for (i in 0..count) {
                        val firstModifiedFile = findFirstModifiedFile(dir)
                        firstModifiedFile?.delete()
                    }
                }
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    private fun write(file: File, throwable: Throwable): Boolean {
        var throwable = throwable
        val writer: PrintWriter
        try {
            writer = PrintWriter(file)
            val cause = throwable.cause
            throwable = cause ?: throwable
            throwable.printStackTrace(writer)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun findLastModifiedFile(file: File): File? {
        var lastModifiedFile: File? = null
        var timestamp: Long = -1
        for (f in listFiles(file)) {
            val lastModified = f!!.lastModified()
            if (lastModified > timestamp) {
                timestamp = lastModified
                lastModifiedFile = f
            }
        }
        return lastModifiedFile
    }

    private fun findFirstModifiedFile(file: File): File? {
        var firstModifiedFile: File? = null
        var timestamp = Long.MAX_VALUE
        for (f in listFiles(file)) {
            val lastModified = f!!.lastModified()
            if (lastModified < timestamp) {
                timestamp = lastModified
                firstModifiedFile = f
            }
        }
        return firstModifiedFile
    }

    fun getCrashFile(context: Context): File? {
        val crashDir = context.cacheDir.path + Constant.CRASH_DIR
        val file = File(crashDir)
        if (file.exists()) {
            val lastModifiedFile = findLastModifiedFile(file)
            if (lastModifiedFile != null) {
                return lastModifiedFile
            }
        }
        return null
    }

    fun read(file: File?): ArrayList<String> {
        file?.reader().use{
            return it?.readLines() as ArrayList<String>
        }

    }

    private fun listFiles(file: File?): Array<File?> {
        if (file == null) {
            return arrayOfNulls(0)
        }
        return if (!file.isDirectory) {
            arrayOfNulls(0)
        } else file.listFiles(FilenameFilter { dir, name -> Utils.contains(name) })
    }
}