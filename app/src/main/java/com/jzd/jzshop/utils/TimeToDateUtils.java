package com.jzd.jzshop.utils;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author lwh
 * @description : 时间戳 转日期
 * @date 2020/5/18.
 */
public class TimeToDateUtils {




    /*
     * 将时间戳转换为时间
     */
    public static String stampToTime(long stamp) {
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(stamp * 1000);
        time = simpleDateFormat.format(date);
        return time;
    }






    /*
     * 将时间转换为时间戳
     */
    public String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    //判断当前时间是否在指定时间的前后
    public static boolean isAfter(long stamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(stamp * 1000);
        simpleDateFormat.format(date);
        if(getNowTime().after(date)){
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowTime() {
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH)+1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);

//        Date date = new Date(year,month,day,hour,minute,second);
        Date date1 = new Date();
        return date1;
    }


    //获取当前时间的   2020-05-30 14:26:00格式
    public static String getNowDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }


    /*
    * 计算时间差
    * */
    public static long getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timer=0;
        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分"+s+"秒";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

            long l = timeToSecond(hour1, min1, s);
            timer=l;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timer;
    }


    /*
     *将时间转为秒数
     * */
    public static long timeToSecond(long hour,long min,long second){
        long hours=0;
        long mins=0;
        long s=0;
        if(hour!=0){
            hours= hour*3600;
        }
        if(min!=0){
            mins=min*60;
        }
        s=hours+mins+second;
        return s;
    }














//1、根据时间字符串获取毫秒数
    private long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
        }
        return returnMillis;
    }


    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date_str,String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            long a = sdf.parse(date_str).getTime() / 1000;
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

}
