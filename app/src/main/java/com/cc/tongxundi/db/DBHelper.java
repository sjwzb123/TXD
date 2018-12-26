package com.cc.tongxundi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cc.tongxundi.greendao.DaoMaster;
import com.cc.tongxundi.greendao.DaoSession;
import com.cc.tongxundi.greendao.UserBeanDao;

import org.greenrobot.greendao.database.Database;

public class DBHelper {
    private DaoMaster.DevOpenHelper mHelper;
    private static final String DB_NAME = "txd-db";
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static DBHelper instance = new DBHelper();

    private DBHelper() {

    }

    public static DBHelper getInstance() {
        return instance;
    }

    public void setDatabase(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null) {
            @Override
            public void onCreate(Database db) {
                super.onCreate(db);
            }

            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                super.onUpgrade(db, oldVersion, newVersion);
            }
        };
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public UserBeanDao getUserDao() {
        return mDaoSession.getUserBeanDao();
    }

}

