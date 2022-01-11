package com.addon

import me.peace.utils.LogUtils.e

object AddonUtils {
    fun div(a: Double, b: Double): Double {
        return a / b
    }

    fun div(a: Int, b: Int): Double {
        return (a / b).toDouble()
    }

    fun stack() {
        val stackTraceElements = Thread.currentThread().stackTrace
        for (element in stackTraceElements) {
            e(
                "stack",
                element.className + "," + element.methodName + "," + element.lineNumber + "," + element.fileName
            )
        }
    }
}