package ye.fang.tool;

import java.io.IOException;

import android.util.Log;

public class Shiliu_Detail {

	
	static public int ShiliutoInt(String s){
		int a =0;
		a=Integer.parseInt( s.substring(0, 2))*65536+Integer.valueOf(s.substring(2, 4), 16)*256+Integer.valueOf(s.substring(4, 6), 16);  
		return a;	
	}

	
	/***
	*
	 * 16进制字符串转字节数组
	 */
	 public static byte[] hexString2Bytes(String hex) {

	     if ((hex == null) || (hex.equals(""))){
	         return null;
	     }
	     else if (hex.length()%2 != 0){
	         return null;
	     }
	     else{
	         hex = hex.toUpperCase();
	         int len = hex.length()/2;
	         byte[] b = new byte[len];
	         char[] hc = hex.toCharArray();
	         for (int i=0; i<len; i++){
	             int p=2*i;
	             b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
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

	
	
	
	public static String convertStringToHex(String str){
		//把字符串转换成char数组
		    char[] chars = str.toCharArray();
		//新建一个字符串缓存类
		    StringBuffer hex = new StringBuffer();
		//循环每一个char
		    for(int i = 0; i < chars.length; i++){
		//把每一个char都转换成16进制的，然后添加到字符串缓存对象中
		        hex.append(Integer.toHexString((int)chars[i]));
		    }
		//最后返回字符串就是16进制的字符串
		    return hex.toString();
		}

	//使用1字节就可以表示b
	public static String numToHex8(int b) {
	        return String.format("%02x", b);//2表示需要两个16进行数
	    }
	//需要使用2字节表示b
	public static String numToHex16(int b) {
		
		
		System.out.println(String.format("%06x", b));
	        return String.format("%06x", b);
	    }
	//需要使用4字节表示b
	public static String numToHex32(int b) {
	        return String.format("%08x", b);
	    }

	
	/**
	 * 
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
	
	public static byte getXor(byte[] data){
	       byte temp =  (byte) 0;
	       for(int i=1;i<data.length;i++){
	          temp^=data[i];
	       }
	       return temp;
	}
	
	
	


	
	public static byte[] intToBytes(int n){ 
		String s = String.valueOf(n); 
		return s.getBytes(); 
		} 

	
	
	 /**
	  * int转byte数组
	  * @param bytes
	  * @return
	  */
	 public static byte[]IntToByte(int num){
	 	byte[]bytes=new byte[4];
	 	bytes[0]=(byte) ((num>>24)&0xff);
	 	bytes[1]=(byte) ((num>>16)&0xff);
	 	bytes[2]=(byte) ((num>>8)&0xff);
	 	bytes[3]=(byte) (num&0xff);
	 	return bytes;
	 }
	 
	 
	 /**
	  * byte数组转成字符串
	  */
	 public static String btye2Str(byte[] data) {
	     String str = new String(data);
	     return str;
	 }

	 /**
	  * 将byte数组化为十六进制串
	  */

	 public static final StringBuilder byte2hex(byte[] data) {
	     StringBuilder stringBuilder = new StringBuilder(data.length);
	     for (byte byteChar : data) {
	         stringBuilder.append(String.format("%02X ", byteChar).trim());
	     }
	     return stringBuilder;
	 }
	 
	 
	 

	 /**
	  * 汉字转byte数组
	  */
public static byte[]  showBytes(String str, String charset) throws IOException {
	
byte[]bb=new byte[str.getBytes(charset).length];
int a=0;
for (byte b : str.getBytes(charset)){
        //System.out.printf("0x%x ", b);
       // System.out.println(b+"");
        bb[a]=b;
    	a++;
    }
    a=0;
   // System.out.println();
return bb;
}


	 
	 
	 
		public static String yihuo(String content) {
			content = change(content);
			String[] b = content.split(" ");
			int a = 0;
			for (int i = 0; i < b.length; i++) {
				a = a ^ Integer.parseInt(b[i], 16);
			}
			if(a<10){
				StringBuffer sb = new StringBuffer();
				sb.append("0");
				sb.append(a);
				return sb.toString();
			}
			return Integer.toHexString(a);
			
			

		}
		

	public static String change(String content) {
		String str = "";
		for (int i = 0; i < content.length(); i++) {
			if (i % 2 == 0) {
				str += " " + content.substring(i, i + 1);
			} else {
				str += content.substring(i, i + 1);
			}
		}
		return str.trim();
	} 
	 
	 
	 
	 
	 
	 

	 /**
	  * 将byte数组转化成浮点数（4个字节带小数的浮点数）
	  */
	 public static float byte2int_Float(byte b[]) {
	     int bits = b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16
	             | (b[0] & 0xff) << 24;

	     int sign = ((bits & 0x80000000) == 0) ? 1 : -1;
	     int exponent = ((bits & 0x7f800000) >> 23);
	     int mantissa = (bits & 0x007fffff);

	     mantissa |= 0x00800000;
	     // Calculate the result:
	     float f = (float) (sign * mantissa * Math.pow(2, exponent - 150));

	     return f;
	 }

	 /**
	  * 将十六进制串转化为byte数组
	  */
	 public static final byte[] hex2byte(String hex) throws IllegalArgumentException {
	     if (hex.length() % 2 != 0) {
	         throw new IllegalArgumentException();
	     }
	     char[] arr = hex.toCharArray();
	     byte[] b = new byte[hex.length() / 2];
	     for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
	         String swap = "" + arr[i++] + arr[i];
	         int byteint = Integer.parseInt(swap, 16) & 0xFF;
	         b[j] = new Integer(byteint).byteValue();
	     }
	     return b;
	 }

	 /**
	  * 将十六进制串转换为二进制  
	  * */
	 public static byte[] parseHexStr2Byte(String hexStr) {
	     if (hexStr.length() < 1) {
	         return null;
	     }
	     byte[] result = new byte[hexStr.length() / 2];
	     for (int i = 0; i < hexStr.length() / 2; i++) {
	         int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
	         int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
	         result[i] = (byte) (high * 16 + low);
	     }
	     return result;
	 }
	 /**

	  * 将字符串转成ASCII值
	  */
	 public static String strToASCII(String data) {
	     String requestStr = "";
	     for (int i = 0; i < data.length(); i++) {
	         char a = data.charAt(i);
	         int aInt = (int) a;
	         requestStr = requestStr + integerToHexString(aInt);
	     }
	     return requestStr;
	 }

	 
	 

	 /**
	  * 将十进制整数转为十六进制数，并补位
	  * */
	 public static String integerToHexString(int s) {
	     String ss = Integer.toHexString(s);
	     if (ss.length() % 2 != 0) {
	         ss = "0" + ss;//0F格式
	     }
	     return ss.toUpperCase();
	 }

	 /**
	  * 将二进制转换成十六进制串  
	  * */
	 public static String parseByte2HexStr(byte buf[]) {
	     StringBuffer sb = new StringBuffer();
	     for (int i = 0; i < buf.length; i++) {
	         String hex = Integer.toHexString(buf[i] & 0xFF);
	         if (hex.length() == 1) {
	             hex = '0' + hex;
	         }
	         sb.append(hex.toUpperCase());
	     }
	     return sb.toString();
	 }

	 
	 public static long[] card2int(String s){
	
	   Long l=Long.parseLong(s, 16);
	   //System.out.println("l ... "+l);
		 
		 long az[]=new long[2];
		 
		 int lg=(l+"").length();
	
		// System.out.println("lg ... "+lg);
		 if(lg<9&lg>-1){
			 
			az[0]=0; 
			az[1]=l; 
		 }
		 else{
			
			 az[0]=Long.parseLong((l+"").substring(0,lg-9));
			 az[1]=Integer.parseInt((l+"").substring(lg-9,lg));
		 }
		 
		  for(int i=0;i<2;i++){
			 System.out.println("az"+i+"  :  "+az[i]);
		 }
	
		return az;
		 
	 }
	 

}
