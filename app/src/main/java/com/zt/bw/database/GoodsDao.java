package com.zt.bw.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zt.bw.bean.GoodsBean;

import java.util.List;

@Dao
public interface GoodsDao {
    @Query("SELECT count(*) FROM goods")
    int getCount();

//    @Query("SELECT * FROM systemlog where date between :startDate and :endDate limit 200")
//    List<SystemLogBean> getDataByTime(long startDate, long endDate);

//    @Query("SELECT * FROM goods order by id desc limit 40 offset :page*40")
//    List<SystemLogBean> getDataByPage(int page);
//    @Query("SELECT * FROM systemlog")
//    List<SystemLogBean> getDataByPage();

//    @Query("SELECT * FROM goods limit 1")
    @Query("SELECT * FROM goods where id = :id")
    GoodsBean findGoodsById(int id);
    @Query("SELECT * FROM goods")
    List<GoodsBean> getAllDoods();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(GoodsBean bean);

//    @Delete
//    int delete(SystemLogBean bean);

    @Query("DELETE FROM goods")
    int clear();
}
