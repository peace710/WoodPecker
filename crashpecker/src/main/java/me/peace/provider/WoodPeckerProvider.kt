package me.peace.provider


import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import me.peace.communication.ProcessConstant
import me.peace.communication.ProcessCoordinator

class WoodPeckerProvider : ContentProvider() {
    private var coordinator: ProcessCoordinator? = null
    override fun onCreate(): Boolean {
        coordinator = ProcessCoordinator()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        if (ProcessConstant.METHOD_SAVE_SP == method) {
            val context = context
            coordinator!!.saveSharedPreferences(context, arg, extras)
        } else if (ProcessConstant.METHOD_LOAD_SP == method) {
            val context = context
            return coordinator!!.loadSharedPreferences(context, arg, extras)
        }
        return null
    }
}