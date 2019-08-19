package me.peace.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.Arrays;

import me.peace.communication.ProcessDispatcher;
import me.peace.constant.Constant;
import me.peace.crashpecker.R;
import me.peace.utils.CrashUtils;
import me.peace.utils.LogUtils;
import me.peace.utils.Utils;

public class WoodPeckerActivity extends AppCompatActivity {
    private static final String TAG = WoodPeckerActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private TraceAdapter adapter;
    private String appInfo;
    private boolean withTrace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info);
        obtainIntent();
        initView();
        loadLocalTrace();
    }

    private void setAppInfo(){
        if (!TextUtils.isEmpty(appInfo)){
            setTitle(appInfo);
        }
    }

    private void initView(){
        setAppInfo();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TraceAdapter(list,keys);
        recyclerView.setAdapter(adapter);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    private void obtainIntent(){
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.KEY_WITH_TRACE)) {
            withTrace = true;
            list = intent.getStringArrayListExtra(Constant.KEY_TRACES);
            keys = intent.getStringArrayListExtra(Constant.KEY_HIGH_LIGHT);
            appInfo = intent.getStringExtra(Constant.KEY_APP_INFO);
        }
    }

    private void loadLocalTrace(){
        if (!withTrace) {
            String trace = CrashUtils.read(this);
            Log.e(TAG,"withTrace = " + trace);
            String[] traces = trace.split("\n");
            ArrayList<String> list = new ArrayList<>(Arrays.asList(traces));
            if (list != null && list.size() > 0) {
                loadHighLightKeys();
                loadAppInfo();
                setAppInfo();
                this.list = list;
                adapter = new TraceAdapter(this.list,this.keys);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void loadHighLightKeys(){
        ProcessDispatcher dispatcher = new ProcessDispatcher();
        String str = dispatcher.loadSpString(this,Constant.WOOD_PECKER_SP,Constant.KEY_HIGH_LIGHT,
            "");
        keys = Utils.string2List(str);
    }

    private void loadAppInfo(){
        ProcessDispatcher dispatcher = new ProcessDispatcher();
        appInfo = dispatcher.loadSpString(this,Constant.WOOD_PECKER_SP,Constant.KEY_APP_INFO,
            "");
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

    private void enableDescendantFocusability(){
        if (recyclerView != null){
            if (recyclerView.getDescendantFocusability() == RecyclerView.FOCUS_BLOCK_DESCENDANTS){
                recyclerView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            }
        }
    }

    private void enableDescendantFocusabilityTv(){
        LogUtils.e(TAG,"isPhone = " + Utils.isPhone(this));
        if (!Utils.isPhone(this)){
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

    private ViewTreeObserver.OnGlobalLayoutListener listener  = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            enableDescendantFocusabilityTv();
            if (recyclerView != null){
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    };
}
