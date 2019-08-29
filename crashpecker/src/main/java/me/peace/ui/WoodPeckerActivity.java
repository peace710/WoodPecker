package me.peace.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

import me.peace.communication.ProcessDispatcher;
import me.peace.constant.Constant;
import me.peace.crashpecker.R;
import me.peace.utils.AsyncTaskDelegate;
import me.peace.utils.CrashUtils;
import me.peace.utils.LogUtils;
import me.peace.utils.Utils;

public class WoodPeckerActivity extends AppCompatActivity {
    private static final String TAG = WoodPeckerActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private TraceAdapter adapter;
    private String appName = "";
    private String crashTime = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info);
        LogUtils.e(TAG,"WoodPeckerActivity onCreate");
        initView();
        fetchCrashInfo();
    }

    private void fetchFromFile(){
        try {
            AsyncTaskDelegate.execute(new CrashFetchTask(this),new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
            toast(R.string.fetch_crash_info_error);
        }
    }

    private void initActionBar(){
        if (!TextUtils.isEmpty(appName)){
            setTitle(appName);
        }
    }

    private void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initRecyclerView(){
        LogUtils.e(TAG,"WoodPeckerActivity initRecyclerView");
        adapter = new TraceAdapter(list,keys);
        recyclerView.setAdapter(adapter);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    private void fetchCrashInfo(){
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.KEY_WITH_TRACE)) {
            fetchCrashInfoFromIntent(intent);
            addCrashInfo();
            loadUiView();
        }else{
            fetchFromFile();
        }
    }

    private void fetchCrashInfoFromFile(){
        fetchCrashInfoFromFile(CrashUtils.getCrashFile(this));
        addCrashInfo();
    }

    private void loadUiView(){
        initActionBar();
        initRecyclerView();
        noProblemTip();
    }

    private void addCrashInfo(){
        if (list != null && list.size() > 0){
            if (!TextUtils.isEmpty(appName) && !TextUtils.isEmpty(crashTime)) {
                String format = getString(R.string.cause_description);
                list.add(0,String.format(format, appName, crashTime));
            }
        }
    }

    private void fetchCrashInfoFromIntent(Intent intent){
        if (intent != null) {
            list = intent.getStringArrayListExtra(Constant.KEY_TRACES);
            keys = intent.getStringArrayListExtra(Constant.KEY_HIGH_LIGHT);
            crashTime = intent.getStringExtra(Constant.KEY_CRASH_TIME);
            appName = intent.getStringExtra(Constant.KEY_APP_NAME);
            LogUtils.e(TAG,"WoodPeckerActivity fetchCrashInfoFromIntent");
        }
    }

    private void fetchCrashInfoFromFile(File file){
        if (file != null){
            list = loadLocalTraces(CrashUtils.read(file));
            keys = loadConfigKey();
            crashTime = loadCrashTime(file.getName());
            appName = loadApplicationName();
        }
    }

    private ArrayList<String> loadLocalTraces(String trace){
        if (!TextUtils.isEmpty(trace)){
            String[] traces = trace.split("\n");
            return new ArrayList<>(Arrays.asList(traces));
        }
        return new ArrayList<>();
    }

    private ArrayList<String> loadConfigKey(){
        ProcessDispatcher dispatcher = new ProcessDispatcher();
        String str = dispatcher.loadSpString(this,Constant.WOOD_PECKER_SP,Constant.KEY_HIGH_LIGHT,
            "");
        return Utils.string2List(str);
    }

    private String loadCrashTime(String fileName){
        try {
            String str = fileName.substring(0,fileName.indexOf("."));
            String[] strs = str.split("-");
            return strs[1] + "-" + strs[2] + "-" + strs[3] + " " + strs[4] + ":" + strs[5] + ":" + strs[6];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String loadApplicationName(){
        ProcessDispatcher dispatcher = new ProcessDispatcher();
        return dispatcher.loadSpString(this,Constant.WOOD_PECKER_SP,Constant.KEY_APP_NAME,
            "");
    }

    private void toast(@StringRes int id){
        Toast.makeText(this,id,Toast.LENGTH_LONG).show();
    }

    private void noProblemTip(){
        if (list != null && list.size() == 0){
            toast(R.string.app_no_problem);
        }
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

    static class CrashFetchTask extends AsyncTask<String,Void,Void>{

        WeakReference<WoodPeckerActivity> weakReference;

        public CrashFetchTask(WoodPeckerActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(String... params) {
            if (weakReference != null && weakReference.get() != null){
                weakReference.get().fetchCrashInfoFromFile();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (weakReference != null && weakReference.get() != null){
                weakReference.get().loadUiView();
            }
        }
    }

}
