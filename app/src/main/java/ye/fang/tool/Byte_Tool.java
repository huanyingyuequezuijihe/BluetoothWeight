package ye.fang.tool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Byte_Tool {
    Context context;


    public Byte_Tool(Context c) {
        this.context = c;

    }

    /*******十六进制的校验*****/
    public static boolean jiaoyan(String s) {
        boolean jy = false;
        byte b1[] = hexString2Bytes(s.substring(0, s.length() - 2));
        byte b2[] = new byte[1];
        b2[0] = getXor(b1);

        String s1 = toHexString1(b2);
        if (s1.equals(s.substring(s.length() - 2, s.length()))) {
            jy = true;
        }
        return jy;
    }


    /*********十六进制的校验*****/
    public static byte[] jiaoyan(byte[] s) {

        //byte b1[]=hexString2Bytes(s.substring(0, s.length()-2));
        byte b2[] = new byte[1];
        b2[0] = getXor(s);
        return b2;
    }


    public static byte[] int2byte(int num) {
        byte b10[] = intToButeArray(num);
        //System.out.println("十六进制串："+toHexString1(b10));
        byte b12[] = new byte[3];
        //for(int a=b10.length-3;a<b10.length;a++){
        for (int a = 0; a < 3; a++) {
            b12[a] = b10[a + b10.length - 3];
        }

        return b12;
    }
    public static byte[] int2byte(int num,int bit) {
        byte b10[] = intToButeArray(num);
        //System.out.println("十六进制串："+toHexString1(b10));
        byte b12[] = new byte[bit];
        //for(int a=b10.length-3;a<b10.length;a++){
        for (int a = 0; a < bit; a++) {
            b12[a] = b10[a + b10.length - bit];
        }

        return b12;
    }


    /**
     * 整数转换成字节数组 关键技术：ByteArrayOutputStream和DataOutputStream
     *
     * @param i 需要转换整数
     * @return
     */
    public static byte[] intToButeArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }


    /**
     * 数组转成十六进制字符串,和下面的函数想法的关系
     *
     * @return HexString
     */
    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    /**
     * @param data1
     * @param data2
     * @return data1 与 data2拼接的结果
     */
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    public static byte getXor(byte[] datas) {
        byte temp = datas[0];
        for (int i = 1; i < datas.length; i++) {
            temp ^= datas[i];
        }
        return temp;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }


    public static byte[] inttobyte(int num) {
        byte a[] = new byte[1];
        a[0] = intToByteArray(num)[3];
        return a;
    }

    /*********心跳*****/
    public static byte[] heart() {
        byte order[] = {(byte) 0xFF, (byte) 0x04, (byte) 0xa3,};
        byte a[] = new byte[1];
        a[0] = getXor(order);
        byte b[] = addBytes(order, a);
        return b;

    }
    /*********蓝牙称的文本下载更新*****/
    public static byte[] Paper_Text01(String text1, int index) {
        byte order[] = {(byte) 0xFF, (byte) 0xc3, (byte) 0xc6,};
        //byte[]b1=new byte[1];
        //b1[0]=0x01;
        //byte[] b1=intToBytes(index);
        byte a[] = new byte[1];
        a[0] = intToByteArray(index)[3];
        System.out.println("文本参数第：  " + toHexString1(a) + "准备更新了");

        byte b2[] = addBytes(order, a);
//        System.out.println("b2 长度是 " + b2.length + "");
        byte[] text1_b = chinesetoShiliu(text1, 190);
        byte b3[] = addBytes(b2, text1_b);
//        System.out.println("b3 长度是 " + b3.length + "");
        byte b9[] = new byte[1];
        b9[0] = getXor(b3);
//        System.out.println("校验和 " + toHexString1(b9) + "");

        byte b10[] = addBytes(b3, b9);

        return b10;

    }
    /*********蓝牙称修改系统参数*****/
    public static byte[] sysParam(int index, int value) {
        byte order[] = {(byte) 0xFF, (byte) 0x0A, (byte) 0xCA,};
        byte a[] = int2byte(index,2);
        byte b[] = intToByteArray(value);
        System.out.println("文本参数第：  " + toHexString1(a) + "准备更新了");

        byte b2[] = addBytes(order, a);
        byte b3[] = addBytes(b2, b);
        byte b9[] = new byte[1];
        b9[0] = getXor(b3);
        byte b10[] = addBytes(b3, b9);
        return b10;

    }


    public static byte[] ticket(String address, String title, String market_name, String shop_name,
                                    String tangwei_num, String weiyi_01, String weiyi_02, String weiyi_03) {
        byte zhiling[] = {(byte) 0xFF, (byte) 0x01,(byte)0xA3, (byte) 0xb0};
        byte[] address_b = chinesetoShiliu(address, 190);
        byte[] title_b = chinesetoShiliu(title, 32);
        byte[] market_name_b = chinesetoShiliu(market_name, 32);
        byte[] shop_name_b = chinesetoShiliu(shop_name, 16);
        byte[] tangwei_num_b = chinesetoShiliu(tangwei_num, 32);
        byte[] weiyi_01_b = chinesetoShiliu(weiyi_01, 32);
        byte[] weiyi_02_b = chinesetoShiliu(weiyi_02, 32);
        byte[] weiyi_03_b = chinesetoShiliu(weiyi_03, 32);
        byte b1[] = addBytes(zhiling, address_b);
        byte b2[] = addBytes(b1, title_b);
        byte b3[] = addBytes(b2, market_name_b);
        byte b4[] = addBytes(b3, shop_name_b);
        byte b5[] = addBytes(b4, tangwei_num_b);
        byte b6[] = addBytes(b5, weiyi_01_b);
        byte b7[] = addBytes(b6, weiyi_02_b);
        byte b8[] = addBytes(b7, weiyi_03_b);
        byte b9[] = new byte[1];
        b9[0] = getXor(b8);
        byte b10[] = addBytes(b8, b9);
        System.out.println("小票长度是 " + b10.length + "");
        return b10;

    }


    public static byte[] chinesetoShiliu(String zhongwen, int length) {
        byte[] b = new byte[length];
        if(!TextUtils.isEmpty(zhongwen)){
            byte b7[] = null;
            try {
                b7 = zhongwen.getBytes("GBK");
                int count = b7.length;
                if (length < b7.length) {
                    count =  length;
                }
                for (int a = 0; a < count; a++) {
                    b[a] = b7[a];
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    /*******N个商品的下发*****/
    public static byte[] good_massege(ArrayList<byte[]> b) {
        byte ONE_CODE_EIGHT_NUM[] = {(byte) 0xFF, (byte) 0xF5, (byte) 0xB2};
        int index = b.size();
        byte b1[] = {(byte) index};
        byte b2[] = Byte_Tool.addBytes(ONE_CODE_EIGHT_NUM, b1);
        byte[] insert = new byte[240];
        for (int a = 0; a < b.size(); a++) {
            for (int a1 = 0; a1 < b.get(a).length; a1++) {
                insert[a1 + a * 48] = b.get(a)[a1];
            }
        }
        byte[] b4 = Byte_Tool.addBytes(b2, insert);
        byte b5[] = new byte[1];
        b5[0] = Byte_Tool.getXor(b4);
        //System.out.println("校验和 "+ Shiliu_Detail.toHexString1(b5)+"");
        byte[] b6 = Byte_Tool.addBytes(b4, b5);
        return b6;
    }
    public static byte[] good_massege2(byte[] data) {
        byte ONE_CODE_EIGHT_NUM[] = {(byte) 0xFF, (byte) 0xF5, (byte) 0xB2};
        byte[] insert = new byte[240];
        byte b1[] = {(byte) 0x01};
        byte b2[] = Byte_Tool.addBytes(ONE_CODE_EIGHT_NUM, b1);
        for (int a1 = 0; a1 < data.length; a1++) {
            insert[a1 ] = data[a1];
        }
        byte[] b4 = Byte_Tool.addBytes(b2, insert);
        byte b5[] = new byte[1];
        b5[0] = Byte_Tool.getXor(b4);
        //System.out.println("校验和 "+ Shiliu_Detail.toHexString1(b5)+"");
        byte[] b6 = Byte_Tool.addBytes(b4, b5);
        return b6;
    }


    /********每个商品信息的十六进制格式*****/
    public static byte[] goods(String code, String cargo_code, String index_code, String unit, double prize, String name) {
        byte b1[] = Byte_Tool.int2byte(Integer.parseInt(code));//3个字节 编号
        byte b12[] = Byte_Tool.int2byte(Integer.parseInt(cargo_code));//3个字节 货号
        byte b15[] = Byte_Tool.addBytes(b1, b12);
        byte b13[] = index_code.getBytes();
        byte b14[] = new byte[18];//18个字节 索引码
        for (int a = 0; a < b13.length; a++) {
            b14[a] = b13[a];
        }
        byte b16[] = Byte_Tool.addBytes(b15, b14);
        int i = (int) (prize * 100);//(new Double(prize*100)).intValue();
//        byte b2[] = Byte_Tool.hexString2Bytes(Byte_Tool.numToHex16(i));
        byte b2[] = Byte_Tool.int2byte(i);//3个字节 价格
        byte b17[] = Byte_Tool.addBytes(b16, b2);
        byte b4[] = null;
        try {
            byte nameByte[] = Byte_Tool.showBytes(name, "GBK");
            b4 = new byte[20];
            System.arraycopy(nameByte,0,b4,0,nameByte.length>20?20:nameByte.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte b6[] = Byte_Tool.addBytes(b17, b4);
        byte b8[] = {(byte) Integer.parseInt(unit)};
        byte b9[] = Byte_Tool.addBytes(b6, b8);
        return b9;
    }


    /*******N个商品的下发*****/
    public static byte[] goodMassegeLK(ArrayList<byte[]> b) {
        byte ONE_CODE_EIGHT_NUM[] = {(byte) 0xFF, (byte) 0xF5, (byte) 0x20};
        int index = 0;
        index = b.size();
        byte b1[] = {(byte) index};
        byte b2[] = Byte_Tool.addBytes(ONE_CODE_EIGHT_NUM, b1);
        byte[] insert = new byte[240];
        for (int a = 0; a < b.size(); a++) {
            for (int a1 = 0; a1 < b.get(a).length; a1++) {
                insert[a1 + a * 48] = b.get(a)[a1];
            }
        }
        byte[] b4 = Byte_Tool.addBytes(b2, insert);
        byte b5[] = new byte[1];
        b5[0] = Byte_Tool.getXor(b4);
        //System.out.println("校验和 "+ Shiliu_Detail.toHexString1(b5)+"");

        byte[] b6 = Byte_Tool.addBytes(b4, b5);
//System.out.println(""+Shiliu_Detail.toHexString1(b6)+"  b6 长度："+b6.length);
//connectThread.sendbyte(b6);
//b.clear();	
        return b6;
    }


    /********每个商品信息的十六进制格式*****/
    public static byte[] goodsLK(int code, Double prize, String name) {//String unit,

//byte b1[]=int2byte(code);
        byte b1[] = hexString2Bytes(numToHex8(code));
//System.out.println("十六进制串："+Shiliu_Detail.toHexString1(b1)+"b1 长度："+b1.length);
//byte b10[]=IntToButeArray.intToButeArray(cargo_code);
////System.out.println("十六进制串："+Shiliu_Detail.toHexString1(b10));
//byte b12[]=new byte[3];
////for(int a=b10.length-3;a<b10.length;a++){
//for(int a=0;a<3;a++){
//b12[a]=b10[a+b10.length-3];
//}
//byte b15[]=Byte_Tool.addBytes(b1, b12);

//byte b13[]=index_code.getBytes(); 
//byte b14[]=new byte[18];
//for(int a=0;a<b13.length;a++){
////b14[18-b13.length+a]=b13[a];
//b14[a]=b13[a];
//}
//byte b16[]=Byte_Tool.addBytes(b15, b14);
//System.out.println("十六进制串:"+Shiliu_Detail.toHexString1(b16)+"b16 长度："+b16.length);
        int i = (int) (prize * 100);//(new Double(prize*100)).intValue();
//System.out.println("插入称的价格是:"+i);
        byte b2[] = Byte_Tool.hexString2Bytes(Byte_Tool.numToHex16(i));
        byte b4[] = null;
//byte b3[]=Shiliu_Detail.addBytes(b1, b2);
        byte b7[] = null;
        byte b17[] = Byte_Tool.addBytes(b1, b2);
//System.out.println(""+Shiliu_Detail.toHexString1(b17)+"b17 长度："+b17.length);
        try {
            b7 = new byte[Byte_Tool.showBytes(name, "GBK").length];
            for (int a = 0; a < Byte_Tool.showBytes(name, "GBK").length; a++) {
                b7[a] = Byte_Tool.showBytes(name, "GBK")[a];
            }
            b4 = new byte[20 - Byte_Tool.showBytes(name, "GBK").length];
            for (int a = 0; a < b4.length; a++) {
                b4[a] = 0x00;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte b5[] = Byte_Tool.addBytes(b17, b7);
//System.out.println("b5"+Shiliu_Detail.toHexString1(b5)+"b5 长度："+b5.length);
        byte b6[] = Byte_Tool.addBytes(b5, b4);
//System.out.println("b6"+Shiliu_Detail.toHexString1(b6)+"b6 长度："+b6.length);
//byte b8[]={(byte)Integer.parseInt(unit)};
////System.out.println("b8"+Shiliu_Detail.toHexString1(b8)+"b8 长度："+b8.length);
//byte b9[]=Byte_Tool.addBytes(b6, b8);	
//System.out.println(""+Shiliu_Detail.toHexString1(b9)+"b9 长度："+b9.length);
//System.out.println("b9 length   "+b9.length);
        return b6;
    }


    /******
     * auto yefang
     * 开称
     * **/
    public static byte[] open_balance() {
        byte a[] = {(byte) 0xFF, (byte) 0x08, (byte) 0xA0, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,};//
        byte b6[] = new byte[1];
        b6[0] = getXor(a);

        byte b7[] = addBytes(a, b6);


        return b7;
    }


    //中文转16进制
    public static byte[] showBytes(String str, String charset) throws IOException {
        byte[] bb = new byte[str.getBytes(charset).length];
        int a = 0;
        for (byte b : str.getBytes(charset)) {
            bb[a] = b;
            a++;
        }
        a = 0;
        return bb;
    }


    /******
     * auto yefang
     * 设置时间
     * **/
    public static byte[] setTime(String year, String mouth, String day, String hour, String minute, String second) {
        //Log.i("Integer.toHexString(Integer.parseInt(year) ",numToHex16(Integer.parseInt(year)).substring(2,6));
        byte b0[] = new byte[]{(byte) 0xff, (byte) 0x0b, (byte) 0xc4};
        //byte b0[]=new byte[]{(byte)0xff,(byte)0x0b,(byte)0x32};
        byte b_year[] = hexString2Bytes(numToHex16(Integer.parseInt(year)).substring(2, 6));//;0x07,(byte)0xe2
        byte bb[] = addBytes(b0, b_year);
        byte b_mouth[] = hexString2Bytes(numToHex16(Integer.parseInt(mouth)).substring(4, 6));
        byte b1[] = addBytes(bb, b_mouth);
        byte b_day[] = hexString2Bytes(numToHex16(Integer.parseInt(day)).substring(4, 6));

        byte b2[] = addBytes(b1, b_day);
        byte b_hour[] = hexString2Bytes(numToHex16(Integer.parseInt(hour)).substring(4, 6));
        byte b3[] = addBytes(b2, b_hour);
        byte b_minute[] = hexString2Bytes(numToHex16(Integer.parseInt(minute)).substring(4, 6));
        byte b4[] = addBytes(b3, b_minute);
        byte b_second[] = hexString2Bytes(numToHex16(Integer.parseInt(second)).substring(4, 6));
        byte b5[] = addBytes(b4, b_second);

        byte b6[] = new byte[1];
        b6[0] = getXor(b5);

        byte b7[] = addBytes(b5, b6);
        //String hex = Integer.toHexString(Integer.parseInt(year));
        for (int i1 = 0; i1 < b7.length; i1++) {
            //Log.i("b_year： "+i1+" ", b7[i1]+"");
        }
        return b7;
    }


    /******
     * auto yefang
     * 设置时间
     * **/
    public static byte[] SetTimeLK(String year, String mouth, String day, String hour, String minute, String second) {
//Log.i("Integer.toHexString(Integer.parseInt(year) ",numToHex16(Integer.parseInt(year)).substring(2,6));
        byte b0[] = new byte[]{(byte) 0xff, (byte) 0x0b, (byte) 0x32};
//byte b0[]=new byte[]{(byte)0xff,(byte)0x0b,(byte)0x32}; 
        byte b_year[] = hexString2Bytes(numToHex16(Integer.parseInt(year)).substring(2, 6));//;0x07,(byte)0xe2
        byte bb[] = addBytes(b0, b_year);
        byte b_mouth[] = hexString2Bytes(numToHex16(Integer.parseInt(mouth)).substring(4, 6));
        byte b1[] = addBytes(bb, b_mouth);
        byte b_day[] = hexString2Bytes(numToHex16(Integer.parseInt(day)).substring(4, 6));

        byte b2[] = addBytes(b1, b_day);
        byte b_hour[] = hexString2Bytes(numToHex16(Integer.parseInt(hour)).substring(4, 6));
        byte b3[] = addBytes(b2, b_hour);
        byte b_minute[] = hexString2Bytes(numToHex16(Integer.parseInt(minute)).substring(4, 6));
        byte b4[] = addBytes(b3, b_minute);
        byte b_second[] = hexString2Bytes(numToHex16(Integer.parseInt(second)).substring(4, 6));
        byte b5[] = addBytes(b4, b_second);

        byte b6[] = new byte[1];
        b6[0] = getXor(b5);

        byte b7[] = addBytes(b5, b6);
//String hex = Integer.toHexString(Integer.parseInt(year));
        for (int i1 = 0; i1 < b7.length; i1++) {
//Log.i("b_year： "+i1+" ", b7[i1]+"");
        }
        return b7;
    }


    //使用1字节就可以表示b
    public static String numToHex8(int b) {
        return String.format("%02x", b);//2表示需要两个16进行数
    }

    //需要使用3字节表示b
    public static String numToHex16(int b) {
        return String.format("%06x", b);
    }

    //需要使用4字节表示b
    public static String numToHex32(int b) {
        return String.format("%08x", b);
    }


    /******
     *会员支付情况
     * 下发批次
     * **/
    public static byte[] cancel(String s, String pay) {
        System.out.println("s   " + s + "");
        System.out.println("pay   " + pay + "");
        byte b1[] = {(byte) 0xFF, (byte) 0x15, (byte) 0xb9};
        byte b_m[] = hexString2Bytes(s);

        byte b2[] = addBytes(b1, b_m);

        byte b_p[] = hexString2Bytes(pay);

        byte b3[] = addBytes(b2, b_p);

        byte b5[] = new byte[1];
        b5[0] = getXor(b3);//0x4d;//

        byte b7[] = addBytes(b3, b5);

        return b7;
    }


    /******
     * auto yefang
     * 下发批次
     * **/
    public static byte[] pichi(String[] b) {
        byte pichi[] = new byte[213];

        for (int a1 = 0; a1 < b.length; a1++) {
            pichi[a1] = 0x00;
        }
        pichi[0] = (byte) 0xff;
        pichi[1] = (byte) 0xd6;
        pichi[2] = (byte) 0xc0;

        byte b1[] = new byte[b.length * 7];
        for (int a1 = 0; a1 < b.length; a1++) {
            for (int i = 0; i < b[a1].length() / 2; i++) {
                String s = b[a1].substring(i * 2, i * 2 + 2);
                Log.i("s" + a1 + " : ", s);
                b1[a1 * 7 + i] = (byte) Integer.parseInt(s);
                pichi[a1 * 7 + i + 3] = (byte) Integer.parseInt(s);
                Log.i("a1*7+i ", a1 * 7 + i + "");
                //如果我吧图片的16进制转成字符串，然后按上面的方法不是可以搞到 byte 和byte【】吗？
                //System.out.println(plu.charAt(i));
            }
        }
        for (int a1 = 0; a1 < pichi.length; a1++) {
            Log.i(a1 + " : ", pichi[a1] + "");
        }
        byte b5[] = new byte[1];
        b5[0] = getXor(pichi);

        byte b7[] = addBytes(pichi, b5);
        return b7;
    }


    /******
     * auto yefang
     * 会员登入
     * **/

    public static byte[] Merber(String merber_card, String name, double balance, int jifen) {
        //byte pichi[]=new byte[213];
        byte MERBER_ONE_CODE_EIGHT_NUM[] = {(byte) 0xff, (byte) 0x24, (byte) 0xa9};
        byte b_m[] = hexString2Bytes(merber_card);
        byte b1[] = addBytes(MERBER_ONE_CODE_EIGHT_NUM, b_m);
        byte b4[] = null;


        //	byte b71[] ={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        byte b7[] = null;

        try {
            b7 = new byte[showBytes(name, "GBK").length];
            for (int a = 0; a < showBytes(name, "GBK").length; a++) {
                b7[a] = showBytes(name, "GBK")[a];
            }

            b4 = new byte[8 - showBytes(name, "GBK").length];
            for (int a = 0; a < b4.length; a++) {
                b4[a] = 0x00;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte b2[] = addBytes(b1, b7);

        byte b3[] = addBytes(b2, b4);

        int i = (new Double(balance * 100)).intValue();
        byte b5[] = hexString2Bytes(numToHex16(i));    //double 转byte数组
        byte b6[] = addBytes(b3, b5);
        String s = "";

        byte b8[] = hexString2Bytes(numToHex16(jifen));    //int 转byte数组


        byte[] b14 = new byte[4 - b8.length];
        for (int a = 0; a < b14.length; a++) {
            b14[a] = 0x00;
        }
        byte b15[] = addBytes(b14, b8);

        byte b9[] = addBytes(b6, b15);
        byte b10[] = {0x0a};
        byte b11[] = addBytes(b9, b10);

//		s=Shiliu_Detail.yihuo(Shiliu_Detail.byte2hex(b11)+"");
//		System.out.println("校验和 "+ s+"");
//		byte[] b12=Shiliu_Detail.hexString2Bytes(s);

        byte b12[] = new byte[1];
        b12[0] = getXor(b11);


        byte b13[] = addBytes(b11, b12);
        for (int a = 0; a < b13.length; a++) {
            Log.i(a + " : ", b13[a] + "");
        }
        return b13;
    }


    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    /******
     * auto yefang
     *发送编码打印出纸   01-03 21:20:05.672: I/System.out(17099): str ff10b6190103212005000034000088fa
     *                                                                 190103210750000034000086
     * **/
    public static byte[] print_code(String b) {
        byte ONE_CODE_THREE_NUM[] = {(byte) 0xFF, (byte) 0x10, (byte) 0xb6};
        byte weiyima[] = new byte[b.length() / 2];
        weiyima = hexString2Bytes(b);
        byte b2[] = addBytes(ONE_CODE_THREE_NUM, weiyima);
//		String s="";
//		s=Shiliu_Detail.yihuo(Shiliu_Detail.byte2hex(b2)+"");
//		if(s.length()==1){
//		s="0"+s;
//		}	
//		System.out.println("送编码，马上打印。 "+s+"");
//		byte[] b6=Shiliu_Detail.hexString2Bytes(s);	
//		byte b12[]=new byte[1];
//		b12[0]=Shiliu_Detail.getXor(b2);
//		
        byte b12[] = {getXor(b2)};//异或校验和
        byte b7[] = addBytes(b2, b12);
//		    for(int a=0;a<b7.length;a++)	
//		    {
//			System.out.println("a"+a+"  ;  "+b7[a]);
//			}
//		    String str= new String (b7);
        System.out.println("str " + toHexString(b7));
        return b7;
    }


    public static String toHex(String str) {
        String hexString = "0123456789ABCDEF";
        byte[] bytes = str.getBytes();
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            hex.append(hexString.charAt((bytes[i] & 0xf0) >> 4));  // 作用同 n / 16
            hex.append(hexString.charAt((bytes[i] & 0x0f) >> 0));  // 作用同 n
            hex.append(' ');  //中间用空格隔开
        }
        return hex.toString();
    }


    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     *
     * @param src String
     * @return byte[]
     **/
    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[8];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < 8; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);

            Log.i("ret[i]" + i + "   ", ret[i] + "");
        }
        return ret;
    }


    /**
     * 将两个ASCII字符合成一个字节；
     * 如："EF"--> 0xEF
     *
     * @param src0 byte
     * @param src1 byte
     * @return byte
     **/
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }


    /*
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }

    }


    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static long[] card2int(String s) {

        Long l = Long.parseLong(s, 16);

        System.out.println("l ... " + l);

        long az[] = new long[2];

        int lg = (l + "").length();

        System.out.println("lg ... " + lg);
        if (lg < 10) {

            az[0] = 0;
            az[1] = l;
        } else {

            az[0] = Long.parseLong((l + "").substring(0, lg - 9));
            az[1] = Integer.parseInt((l + "").substring(lg - 9, lg));
        }

        for (int i = 0; i < 2; i++) {
            System.out.println("az" + i + "  :  " + az[i]);
        }

        return az;

    }

    public String gotWYM(boolean update, int minus) {
        String num = "1";

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String s = new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());

        String weiyima = s.substring(2, s.length()) + Double_Number.Get_Six_String(2) + Double_Number.Get_Six_String(1);//
        System.out.println("唯一码 -------------------  " + weiyima);
        num = Integer.parseInt(num) + 1 + "";

        return weiyima;
    }

    //將16進制字符串轉換為10進制數字
    public static int decodeHEX(String hexs){
        BigInteger bigint=new BigInteger(hexs, 16);
        int numb=bigint.intValue();
        return numb;
    }
    //將16進制字符串轉換為10進制數字
    public static String decodeHEX2Price(String hexs){
        BigDecimal bigDecimal=new BigDecimal(decodeHEX(hexs));
        return bigDecimal.divide(new BigDecimal("100")).setScale(2).toPlainString();
    }

}




