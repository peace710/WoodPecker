package me.peace.pecker

import android.app.Application
import me.peace.engine.WoodPecker.Companion.woodPecker

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val stringArray = resources.getStringArray(R.array.keys)
        val keys = ArrayList<String>()
        stringArray.forEach {
            keys.add(it)
        }
        woodPecker().with(keys).jump(false).count(10).fly(this)
    }
}