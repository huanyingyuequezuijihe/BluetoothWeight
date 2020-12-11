package com.zt.bw.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.zt.bw.bean.GoodsBean;

/**
 * Created by lifujun on 2018/9/11.
 */
@Database(entities = {GoodsBean.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract GoodsDao goodsDao();
}

