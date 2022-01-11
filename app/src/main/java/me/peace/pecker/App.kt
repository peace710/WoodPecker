package me.peace.pecker

import android.app.Application
import me.peace.engine.WoodPecker.Companion.instance

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val stringArray = resources.getStringArray(R.array.keys)
        val keys = ArrayList<String>()
        stringArray.forEach {
            keys.add(it)
        }
        instance().with(keys).jump(false).count(10).fly(this)
    }
}