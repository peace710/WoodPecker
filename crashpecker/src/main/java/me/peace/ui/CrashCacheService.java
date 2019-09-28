package me.peace.ui;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import me.peace.communication.ProcessDispatcher;
import me.peace.constant.Constant;
import me.peace.utils.CrashUtils;
import me.peace.utils.LogUtils;
import me.peace.utils.Utils;

public class CrashCacheService extends IntentService {

    private static final String NAME = CrashCacheService.class.getSimpleName();
    private static final String TAG = CrashCacheService.class.getSimpleName();

    public CrashCacheService() {
        super(NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG,"service onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtils.e(TAG,"service onStart");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtils.e(TAG,"service onHandleIntent");
        String appName = intent.getStringExtra(Constant.KEY_APP_NAME);
        String crashDate = intent.getStringExtra(Constant.KEY_CRASH_DATE);
        int crashRecordCount = intent.getIntExtra(Constant.KEY_CRASH_RECORD_COUNT,
            Constant.DEFAULT_MAX_SAVE_FILE_COUNT);
        ArrayList<String> keys = intent.getStringArrayListExtra(Constant.KEY_HIGH_LIGHT);
        Throwable throwable = (Throwable) intent.getSerializableExtra(Constant.KEY_THROWABLE);

        ProcessDispatcher dispatcher = new ProcessDispatcher();
        dispatcher.saveSpString(this, Constant.WOOD_PECKER_SP,
            Constant.KEY_HIGH_LIGHT, Utils.list2String(keys));
        dispatcher.saveSpString(this,Constant.WOOD_PECKER_SP,Constant.KEY_APP_NAME, appName);
        CrashUtils.save(this,throwable,crashDate,crashRecordCount);
        stopSelf();
        LogUtils.e(TAG,"service onHandleIntent end");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG,"service onDestroy");
    }
}
