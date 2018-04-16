 package com.rms.util;
 
 import java.io.PrintStream;
 import java.text.ParsePosition;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 
 public class DateUtil
 {
   public static Date getDateFromString(String strDate)
     throws Exception
   {
     int length = strDate.length();
     if (length == 6) {
       return getDateFromString(strDate, "yyMMdd");
     }
     if (length == 8) {
       return getDateFromString(strDate, "yyyyMMdd");
     }
     if (length == 10) {
       return getDateFromString(strDate, "yyyy-MM-dd");
     }
     return getDateFromString(strDate, "yy-MM-dd");
   }
   
   public static Date getDateFromString(String strDate, String format)
     throws Exception
   {
     try
     {
       SimpleDateFormat formatter = new SimpleDateFormat(format);
       
       ParsePosition pos = new ParsePosition(0);
       
       return formatter.parse(strDate, pos);
     }
     catch (Exception ex)
     {
       throw new Exception(ex.getMessage());
     }
   }
   
   public static String getFormat2FromStd(String date, String format)
     throws Exception
   {
     return getFormat2FromFormat1(date, "yyyyMMdd", format);
   }
   
   public static String getFormat2FromFormat1(String date, String format1, String format2)
     throws Exception
   {
     return getStringFromDate(getDateFromString(date, format1), format2);
   }
   
   public static String getStringFromDate(Date dt)
     throws Exception
   {
     return getStringFromDate(dt, "yyyyMMdd");
   }
   
   public static String getStringFromDate(Date dt, String format)
     throws Exception
   {
     try
     {
       SimpleDateFormat formatter = new SimpleDateFormat(format);
       
       return formatter.format(dt);
     }
     catch (Exception ex)
     {
       throw new Exception(ex.getMessage());
     }
   }
   
   public static int diffDate(Date dt1, Date dt2)
   {
     long date1 = dt1.getTime();
     
     long date2 = dt2.getTime();
     if (date1 > date2) {
       return (int)((date1 - date2) / 86400000.0D + 0.5D);
     }
     return (int)((date2 - date1) / 86400000.0D + 0.5D);
   }
   
   public static int diffDate(String dt1, String dt2)
     throws Exception
   {
     return diffDate(dt1, dt2, "yyyyMMdd");
   }
   
   public static int diffDate(String dt1, String dt2, String format)
     throws Exception
   {
     return diffDate(getDateFromString(dt1, format), getDateFromString(dt2, format));
   }
   
   public static Date addDaysToDate(Date dt1, int days)
   {
     Calendar cale = Calendar.getInstance();
     cale.setTime(dt1);
     cale.add(5, days);
     
     return cale.getTime();
   }
   
   public static Date addDaysToDate(String dt1, int days)
     throws Exception
   {
     return addDaysToDate(dt1, "yyyyMMdd", days);
   }
   
   public static Date addDaysToDate(String dt1, String format, int days)
     throws Exception
   {
     return addDaysToDate(getDateFromString(dt1, format), days);
   }
   
   public static String getCurrentDate()
     throws Exception
   {
     return getCurrentDateTime("yyyyMMdd");
   }
   
   public static String getCurrentTime()
     throws Exception
   {
     return getCurrentDateTime("HHmmss");
   }
   
   public static String getCurrentDateTime()
     throws Exception
   {
     return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
   }
   

   
   public static String getCurrentDateTime(String format)
     throws Exception
   {
     return getStringFromDate(new Date(System.currentTimeMillis()), format);
   }
   
   public static String getTimeFromLongstr(String longtime)
   {
	   Calendar cal = Calendar.getInstance();
	   String outdate="";
	     //cal.setTimeInMillis(1444636607320L);
	     cal.setTimeInMillis(Long.parseLong(longtime));
	     try {
	    	 outdate=getStringFromDate( cal.getTime(), "yyyy-MM-dd HH:mm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return outdate;
   }
   
   public static void main(String[] args)
     throws Exception
   {
     Date nowTime = new Date();
     Calendar cal = Calendar.getInstance();
     //cal.setTimeInMillis(1444636607320L);
     cal.setTimeInMillis(1497440280000L);
     
     System.out.println("北京时间�?" + cal.getTime());
     cal.add(10, -8);
     System.out.println("格林威治时间�?" + cal.getTime());
   }
 }


/* Location:           C:\Users\zhangtonghu\Desktop\sqlcheck\
 * Qualified Name:     com.ccb.sqlcheck.util.DateUtil
 * JD-Core Version:    0.7.0.1
 */