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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.Key;
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
        enableDescendantFocusabilityWhenKeyEvent(event);
        return super.dispatchKeyEvent(event);
    }

    private void enableDescendantFocusability(){
        if (recyclerView != null){
            if (recyclerView.getDescendantFocusability() == RecyclerView.FOCUS_BLOCK_DESCENDANTS){
                recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            }
        }
    }

    private void enableDescendantFocusabilityWhenKeyEvent(KeyEvent event){
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    if (recyclerView.getDescendantFocusability() == RecyclerView.FOCUS_BLOCK_DESCENDANTS) {
                        recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                        View child = recyclerView.getChildAt(0);
                        if (child != null){
                            child.setFocusable(true);
                            child.requestFocus();
                        }
                    }
                }
            }
        }
    }
}
