package com.example.vongvia.weatherandcourse;

import android.app.Application;
import android.content.Context;

import test.greenDAO.dao.DaoMaster;
import test.greenDAO.dao.DaoSession;

/**
 * Created by castl on 2016/5/19.
 */
public class MyApplication extends Application {


    private static MyApplication mInstance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null)
            mInstance = this;
    }

    /**
     * 取得DaoMaster
     *
     * @param context 上下文
     * @return DaoMaster
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, "myDb", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context 上下文
     * @return DaoSession
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
