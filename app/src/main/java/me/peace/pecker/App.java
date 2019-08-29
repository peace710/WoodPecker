package me.peace.pecker;

import android.app.Application;

import me.peace.engine.WoodPecker;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WoodPecker.instance().with(getResources().getStringArray(R.array.keys)).jump(false).count(10).fly(this);
    }
}
