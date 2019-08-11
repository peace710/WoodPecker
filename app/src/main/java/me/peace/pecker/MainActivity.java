package me.peace.pecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.addon.AddonActivity;
import com.addon.AddonUtils;

import me.peace.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.ssr)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG,"button is click");
                LogUtils.i(TAG,"5 / 2 = "+ AddonUtils.div(5.0,2));
                LogUtils.i(TAG,"5 / 0 = "+ AddonUtils.div(5.0,0));
//                LogUtils.i(TAG,"5 / 0 = "+ AddonUtils.div(5,0));
                startActivity(new Intent(MainActivity.this, AddonActivity.class));
            }
        });
    }
}
