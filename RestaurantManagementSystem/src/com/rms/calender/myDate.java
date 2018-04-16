package com.rms.calender;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class myDate {
	
	public  String compute(String str){
		StringBuilder strb = new StringBuilder();
		System.out.println(str);
		if(str.length()>7){
		String year=str.substring(0,4);
		String week=str.substring(6);
		System.out.println(year+" "+week);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, Integer.parseInt(year));
		c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		strb.append(sdf.format(c.getTime())).append(",");
		
		c.roll(Calendar.DAY_OF_WEEK, 6);
		strb.append(sdf.format(c.getTime()));
		}
		return strb.toString();
	}
	
	public String getMaxDay(String str){
		String res="";
		if(str.length() == 7)
		{
			String[] arr=str.split("-");
			int year = Integer.parseInt(arr[0]);
			int month = Integer.parseInt(arr[1])-1;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			res=Integer.toString(lastDay);
		}
		return res;
	}
	public static void main(String[] args){
		myDate md=new myDate();
		System.out.println(md.getMaxDay("2000-01"));
	}
}