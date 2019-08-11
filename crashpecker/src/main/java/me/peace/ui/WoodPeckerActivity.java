package me.peace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import me.peace.constant.Constant;
import me.peace.crashpecker.R;

public class WoodPeckerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private ArrayList<String> keys;
    private String appInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info);
        obtainIntent();
        initView();
    }


    private void initView(){
        if (!TextUtils.isEmpty(appInfo)){
            setTitle(appInfo);
        }
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        TraceAdapter adapter = new TraceAdapter(list,keys);
        recyclerView.setAdapter(adapter);
    }

    private void obtainIntent(){
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra(Constant.KEY_TRACES);
        keys = intent.getStringArrayListExtra(Constant.KEY_HIGH_LIGHT);
        appInfo =intent.getStringExtra(Constant.KEY_APP_INFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        enableDescendantFocusability();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        enableDescendantFocusability();
        return super.dispatchKeyEvent(event);
    }

    private void enableDescendantFocusability(){
        if (recyclerView != null){
            if (recyclerView.getDescendantFocusability() == RecyclerView.FOCUS_BLOCK_DESCENDANTS){
                recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            }
        }
    }
}
