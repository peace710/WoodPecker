package me.peace.pecker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.addon.AddonActivity
import com.addon.AddonUtils
import me.peace.pecker.MainActivity
import me.peace.utils.LogUtils
import me.peace.utils.LogUtils.i

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (findViewById<View>(R.id.ssr) as Button).setOnClickListener {
            i(TAG, "button is click")
            i(TAG, "5 / 2 = " + AddonUtils.div(5.0, 2.0))
            i(TAG, "5 / 0 = " + AddonUtils.div(5.0, 0.0))
            i(TAG,"5 / 0 = "+ AddonUtils.div(5,0));
//                AddonUtils.stack();
//            startActivity(Intent(this@MainActivity, AddonActivity::class.java))
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}