package com.cc.tongxundi;

import android.app.Application;

import com.cc.tongxundi.db.DBHelper;

public class TXDApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.getInstance().setDatabase(getApplicationContext());
    }
}
