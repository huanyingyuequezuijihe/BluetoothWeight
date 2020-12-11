package ye.fang.tool;

import android.util.Log;

public class StringtoShiliu {
	
	
	public static String toHex(String str) {  
	    String hexString="0123456789ABCDEF";  
	    byte[] bytes=str.getBytes();  
	    StringBuilder hex=new StringBuilder(bytes.length * 2);  
	    for(int i=0;i<bytes.length;i++) {  
	        hex.append(hexString.charAt((bytes[i] & 0xf0) >> 4));  // 浣滅敤鍚? n / 16   
	        hex.append(hexString.charAt((bytes[i] & 0x0f) >> 0));  // 浣滅敤鍚? n    
	        hex.append(' ');  //涓棿鐢ㄧ┖鏍奸殧寮?  
	    }  
	    return hex.toString();  
	}  
	
	
	
	
	/** 
	    * 灏嗘寚瀹氬瓧绗︿覆src锛屼互姣忎袱涓瓧绗﹀垎鍓茶浆鎹负16杩涘埗褰㈠紡 
	    * 濡傦細"2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9} 
	    * @param src String 
	    * @return byte[] 
	    **/ 
	 public static byte[] HexString2Bytes(String src)  { 
	  	byte[] ret = new byte[8]; 
	     	byte[] tmp = src.getBytes(); 
	     	for(int i=0; i<8; i++)  {  
	      		ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]); 
	      		
	      		Log.i("ret[i]"+i+"   ", ret[i]+"");
	     	} 
	     	return ret; 
	 } 

	 
	 /** 
	    * 灏嗕袱涓狝SCII瀛楃鍚堟垚涓?涓瓧鑺傦紱 
	    * 濡傦細"EF"--> 0xEF 
	    * @param src0 byte 
	    * @param src1 byte 
	    * @return byte 
	    **/ 
	 public static byte uniteBytes(byte src0, byte src1)  { 
	  	byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue(); 
	      	_b0 = (byte)(_b0 << 4); 
	     	byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue(); 
	     	byte ret = (byte)(_b0 ^ _b1); 
	     	return ret; 
	 }
	 
	 
	 /*String杞琤yte鏁扮粍*/
	 public static byte[] Stringtobytes(String s) {
	     byte[] present = {};
	     if (Integer.parseInt(s) >= 16) {
	         present = hexString2Bytes(Integer.toHexString(Integer.parseInt(s)));
	     }else if(Integer.parseInt(s) == 0){
	         present = new byte[]{0x00};
	     }else if(Integer.parseInt(s) == 1){
	         present = new byte[]{0x01};
	     }else if(Integer.parseInt(s) == 2){
	         present = new byte[]{0x02};
	     }else if(Integer.parseInt(s) == 3){
	         present = new byte[]{0x03};
	     }else if(Integer.parseInt(s) == 4){
	         present = new byte[]{0x04};
	     }else if(Integer.parseInt(s) == 5){
	         present = new byte[]{0x05};
	     }else if(Integer.parseInt(s) == 6){
	         present = new byte[]{0x06};
	     }else if(Integer.parseInt(s) == 7){
	         present = new byte[]{0x07};
	     }else if(Integer.parseInt(s) == 8){
	         present = new byte[]{0x08};
	     }else if(Integer.parseInt(s) == 9){
	         present = new byte[]{0x09};
	     }else if(Integer.parseInt(s) == 10){
	         present = new byte[]{0x0a};
	     }else if(Integer.parseInt(s) == 11){
	         present = new byte[]{0x0b};
	     }else if(Integer.parseInt(s) == 12){
	         present = new byte[]{0x0c};
	     }else if(Integer.parseInt(s) == 13){
	         present = new byte[]{0x0d};
	     }else if(Integer.parseInt(s) == 14){
	         present = new byte[]{0x0e};
	     }else if(Integer.parseInt(s) == 15){
	         present = new byte[]{0x0f};
	     }

	     return present;
	 }
	 /*
	  * 16杩涘埗瀛楃涓茶浆瀛楄妭鏁扮粍
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

	  }/*
	   * 瀛楃杞崲涓哄瓧鑺?
	   */
	  private static byte charToByte(char c) {
	      return (byte) "0123456789ABCDEF".indexOf(c);
	  }
}
