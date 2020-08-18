package com.jzd.jzshop.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;

/*
* LWH
* 时间选择工具
* */
public class TimeSelectUtils {


    //YYYY年 xx月xx日
    public void  SelectYMDTime(Context context,OnTimeSelectListener onTimeSelectListener){
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);

        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int, mouth_int - 1, day_int);


        TimePickerView build = new TimePickerBuilder(context,onTimeSelectListener)
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTextColorOut(Color.GRAY)//设置没有被选中项的颜色
                .setDate(selectedDate)
                .setSubmitColor(Color.RED)
                .setCancelColor(Color.GRAY)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)//.setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

        build.show();
       /* TimePickerView build = new TimePickerBuilder(context,time())
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTextColorOut(Color.GRAY)//设置没有被选中项的颜色
                .setDate(selectedDate)
                .setSubmitColor(Color.RED)
                .setCancelColor(Color.GRAY)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)//.setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        build.show();*/


    }



    //XX时 XX分
    public void SelectHMTime(Context context,OnTimeSelectListener onTimeSelectListener){

        TimePickerView build = new TimePickerBuilder(context,onTimeSelectListener).setType(new boolean[]{false, false, false, true, true, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("", "", "", "时", "分", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTextColorOut(Color.GRAY)//设置没有被选中项的颜色
                .setSubmitColor(Color.RED)
                .setCancelColor(Color.GRAY)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setDecorView(null)
                .build();
        build.show();




    }






    private String getTime(Date date, int type) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = null;
        if (type == 0) {//TYPE 为1时时间为  几几年 几月  几日
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else if (type == 1) {
            format = new SimpleDateFormat("HH:mm");
        }
        return format.format(date);
    }
}
