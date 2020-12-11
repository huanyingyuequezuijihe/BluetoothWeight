package com.zt.bw.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.math.BigDecimal;

@Entity(tableName = "goods")
public class GoodsBean {
    @NonNull
    @PrimaryKey
    public int id;
    public String goodsNo;
    public String goodsName;
    public String unit;
    public String keyword;
    public double price;
}
