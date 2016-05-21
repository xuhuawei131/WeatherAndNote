package com.example.vongvia.weatherandcourse.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.vongvia.weatherandcourse.MyApplication;

import java.util.List;

import test.greenDAO.bean.Duty;
import test.greenDAO.dao.DaoSession;
import test.greenDAO.dao.DutyDao;

/**
 * Created by castl on 2016/5/20.
 */
public class DbServices {
    private static final String TAG = DbServices.class.getSimpleName();
    private static DbServices instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private DutyDao dutyDao;


    private DbServices() {
    }

    /**
     * 采用单例模式
     *
     * @param context 上下文
     * @return dbservice
     */
    public static DbServices getInstance(Context context) {
        if (instance == null) {
            instance = new DbServices();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = MyApplication.getDaoSession(context);
            instance.dutyDao = instance.mDaoSession.getDutyDao();
        }
        return instance;
    }

    /**
     * 根据任务id,取出任务信息
     *
     * @param id 任务id
     * @return 任务实例
     */
    public Duty loadDuty(long id) {
        if (!TextUtils.isEmpty(id + "")) {
            return dutyDao.load(id);
        }
        return null;
    }

    /**
     * 取出所有数据
     *
     * @return 所有数据信息
     */
    public List<Duty> loadAllNote() {
        return dutyDao.loadAll();
    }

    /**
     * 生成按id倒排序的列表
     *
     * @return 倒排数据
     */
    public List<Duty> loadAllNoteByOrder() {
        return dutyDao.queryBuilder().orderDesc(DutyDao.Properties.Id).list();
    }

    /**
     * 根据查询条件,返回数据列表
     *
     * @param where 条件
     * @return 数据列表
     */
    public List<Duty> queryNote(String where) {

        return dutyDao.queryBuilder().where(DutyDao.Properties.Type.eq(where))
                .orderDesc(DutyDao.Properties.Id)
                .build().list();
    }


    /**
     * 插入或修改信息
     *
     * @param
     * @return 插件或修改的事件
     */
    public long saveNote(Duty duty) {
        return dutyDao.insertOrReplace(duty);
    }


    /**
     * 批量插入或修改
     *
     * @param list 事件列表
     */
    public void saveNoteLists(final List<Duty> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        dutyDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    Duty user = list.get(i);
                    dutyDao.insertOrReplace(user);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote() {
        dutyDao.deleteAll();
    }

    /**
     * 根据id,删除数据
     *
     * @param id 事件id
     */
    public void deleteNote(long id) {
        dutyDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    /**
     * 根据实例类,删除信息
     *
     * @param duty
     */
    public void deleteNote(Duty duty) {
        dutyDao.delete(duty);
    }
}