package ye.fang.tool;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeDemo {
	
	
	
	public static String get_Time(){
		
		final Calendar c = Calendar.getInstance();
		  c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		  String mYear = String.valueOf(c.get(Calendar.YEAR));//获取当前年份
		  String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
		  String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		  String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
		  String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
		  String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒
		 return mYear+"/"+mMonth+"/"+mDay+"/"+mHour+"/"+mMinute+"/"+mSecond;
	}
	
}
