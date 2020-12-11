package com.spinner.litz;

import java.util.ArrayList;
import java.util.List;

import ye.fang.tool.Byte_Tool;

/**
 * Created by lifujun on 2020/3/16.
 */

public class GoodsInfo {
    public int bianhao;//编号  3字节
    public int huohao;//货号  3字节
    public int weight;//重量  3字节
    public String price;//单价  3字节
    public String total;//总价  3字节
    public int pici;//批次  4字节
    public int unit ;//单位  1字节
    public String name;//菜名
    public String unitName;//单位名
    public static List<GoodsInfo> parse(String data){
//        if(TextUtils.isEmpty(data) || data.length() != 1620){
//            return null;
//        }
        int count = Byte_Tool.decodeHEX(data.substring(16,18));
        List<GoodsInfo> list = new ArrayList<>(count);
        for(int i = 0;i<count;i++){
            GoodsInfo info = new GoodsInfo();
            String goodStr = data.substring(18+i*40,58+i*40);
            info.huohao = Byte_Tool.decodeHEX(goodStr.substring(0,6));
            info.bianhao = Byte_Tool.decodeHEX(goodStr.substring(6,12));
            info.weight = Byte_Tool.decodeHEX(goodStr.substring(12,18));
            info.price = Byte_Tool.decodeHEX2Price(goodStr.substring(18,24));
            info.total = Byte_Tool.decodeHEX2Price(goodStr.substring(24,30));
            info.pici = Byte_Tool.decodeHEX(goodStr.substring(30,38));
            info.unit = Byte_Tool.decodeHEX(goodStr.substring(38,40));
            list.add(info);
        }
        return list;
    }
    @Override
    public String toString() {
        return "no：" + bianhao +
//                ", 货号：" + huohao +
                ", weight：" + weight +
                ", price：" + price +
                ", total：" + total;
    }
}
