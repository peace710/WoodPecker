package me.peace.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Process
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.peace.communication.ProcessDispatcher
import me.peace.constant.Constant
import me.peace.crashpecker.R
import me.peace.ui.WoodPeckerActivity
import me.peace.utils.AsyncTaskDelegate
import me.peace.utils.CrashUtils
import me.peace.utils.LogUtils
import me.peace.utils.Utils
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

class WoodPeckerActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var list: ArrayList<String>? = ArrayList()
    private var keys: ArrayList<String>? = ArrayList()
    private var adapter: TraceAdapter? = null
    private var appName: String? = ""
    private var crashTime: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_info)
        LogUtils.e(TAG, "WoodPeckerActivity onCreate")
        initView()
        fetchCrashInfo()
    }

    private fun fetchFromFile() {
        try {
            AsyncTaskDelegate.execute(CrashFetchTask(this), *arrayOf())
        } catch (e: IOException) {
            e.printStackTrace()
            toast(R.string.fetch_crash_info_error)
        }
    }

    private fun initActionBar() {
        if (!TextUtils.isEmpty(appName)) {
            title = appName
        }
    }

    private fun initView() {
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager
    }

    private fun initRecyclerView() {
        LogUtils.e(TAG, "WoodPeckerActivity initRecyclerView")
        adapter = TraceAdapter(list, keys)
        recyclerView!!.adapter = adapter
        recyclerView!!.viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    private fun fetchCrashInfo() {
        val intent = intent
        if (intent.hasExtra(Constant.KEY_WITH_TRACE)) {
            fetchCrashInfoFromIntent(intent)
            addCrashInfo()
            loadUiView()
        } else {
            fetchFromFile()
        }
    }

    private fun fetchCrashInfoFromFile() {
        fetchCrashInfoFromFile(CrashUtils.getCrashFile(this))
        addCrashInfo()
    }

    private fun loadUiView() {
        initActionBar()
        initRecyclerView()
        noProblemTip()
    }

    private fun addCrashInfo() {
        if (list != null && list!!.size > 0) {
            if (!TextUtils.isEmpty(appName) && !TextUtils.isEmpty(crashTime)) {
                val format = getString(R.string.cause_description)
                list!!.add(0, String.format(format, appName, crashTime))
            }
        }
    }

    private fun fetchCrashInfoFromIntent(intent: Intent?) {
        if (intent != null) {
            list = intent.getStringArrayListExtra(Constant.KEY_TRACES)
            keys = intent.getStringArrayListExtra(Constant.KEY_HIGH_LIGHT)
            crashTime = intent.getStringExtra(Constant.KEY_CRASH_TIME)
            appName = intent.getStringExtra(Constant.KEY_APP_NAME)
            LogUtils.e(TAG, "WoodPeckerActivity fetchCrashInfoFromIntent")
        }
    }

    private fun fetchCrashInfoFromFile(file: File?) {
        if (file != null) {
            list = CrashUtils.read(file)
            keys = loadConfigKey()
            crashTime = loadCrashTime(file.name)
            appName = loadApplicationName()
        }
    }


    private fun loadConfigKey(): ArrayList<String> {
        val dispatcher = ProcessDispatcher()
        val str = dispatcher.loadSpString(
            this, Constant.WOOD_PECKER_SP, Constant.KEY_HIGH_LIGHT,
            ""
        )
        return Utils.string2List(str)
    }

    private fun loadCrashTime(fileName: String): String {
        try {
            val str = fileName.substring(0, fileName.indexOf("."))
            val strs = str.split("-").toTypedArray()
            return strs[1] + "." + strs[2] + "." + strs[3] + " " + strs[4] + ":" + strs[5] + ":" + strs[6]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun loadApplicationName(): String {
        val dispatcher = ProcessDispatcher()
        return dispatcher.loadSpString(
            this, Constant.WOOD_PECKER_SP, Constant.KEY_APP_NAME,
            ""
        )
    }

    private fun toast(@StringRes id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_LONG).show()
    }

    private fun noProblemTip() {
        if (list != null && list!!.size == 0) {
            toast(R.string.app_no_problem)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        enableDescendantFocusability()
        return super.dispatchTouchEvent(ev)
    }

    private fun enableDescendantFocusability() {
        if (recyclerView != null) {
            if (recyclerView!!.descendantFocusability == RecyclerView.FOCUS_BLOCK_DESCENDANTS) {
                recyclerView!!.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            }
        }
    }

    private fun enableDescendantFocusabilityTv() {
        LogUtils.e(TAG, "isPhone = " + Utils.isPhone(this))
        if (!Utils.isPhone(this)) {
            if (recyclerView != null && recyclerView!!.childCount > 0) {
                if (recyclerView!!.descendantFocusability == RecyclerView.FOCUS_BLOCK_DESCENDANTS) {
                    recyclerView!!.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
                    val child = recyclerView!!.getChildAt(0)
                    if (child != null) {
                        child.isFocusable = true
                        child.requestFocus()
                    }
                }
            }
        }
    }

    private val listener: OnGlobalLayoutListener = object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            enableDescendantFocusabilityTv()
            if (recyclerView != null) {
                recyclerView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    }

    internal class CrashFetchTask(activity: WoodPeckerActivity?) :
        AsyncTask<String?, Void?, Void?>() {
        var weakReference: WeakReference<WoodPeckerActivity?>? = WeakReference(activity)
        override fun doInBackground(vararg params: String?): Void? {
            if (weakReference != null && weakReference!!.get() != null) {
                weakReference!!.get()!!.fetchCrashInfoFromFile()
            }
            return null
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            if (weakReference != null && weakReference!!.get() != null) {
                weakReference!!.get()!!.loadUiView()
            }
        }

    }

    companion object {
        private val TAG = WoodPeckerActivity::class.java.simpleName
    }
}