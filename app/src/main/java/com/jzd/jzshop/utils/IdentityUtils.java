package com.jzd.jzshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
* 效验身份证号
* */
public class IdentityUtils {
    //    位权值数组
    private static byte[] Wi = new byte[17];
    //    身份证前部分字符数
    private static final byte fPart = 6;
    //    身份证算法求模关键值
    private static final byte fMod = 11;
    //    旧身份证长度
    private static final byte oldIDLen = 15;
    //    新身份证长度
    private static final byte newIDLen = 18;
    //    新身份证年份标志
    private static final String yearFlag = "19";
    //    校验码串
    private static final String CheckCode = "10X98765432";
    //    最小的行政区划码
    private static final int minCode = 150000;
    //    最大的行政区划码
    private static final int maxCode = 700000;
//    旧身份证号码
//    private String oldIDCard="";
//    新身份证号码
//    private String newIDCard="";
//    地区及编码


    //private String Area[][2] =
    private static void setWiBuffer() {
        for (int i = 0; i < Wi.length; i++) {
            int k = (int) Math.pow(2, (Wi.length - i));
            Wi[i] = (byte) (k % fMod);
        }
    }


    //判断串长度的合法性
    private static boolean checkLength(final String idCard, boolean newIDFlag) {
        boolean right = (idCard.length() == oldIDLen) || (idCard.length() == newIDLen);
        newIDFlag = false;
        if (right) {
            newIDFlag = (idCard.length() == newIDLen);
        }
        return right;
    }



    //获取时间串
    private static String getIDDate(final String idCard, boolean newIDFlag) {
        String dateStr = "";
        if (newIDFlag)
            dateStr = idCard.substring(fPart, fPart + 8);
        else
            dateStr = yearFlag + idCard.substring(fPart, fPart + 6);
        return dateStr;
    }


    //判断时间合法性
    private static boolean checkDate(final String dateSource) {
        String dateStr = dateSource.substring(0, 4) + "-" + dateSource.substring(4, 6) + "-" + dateSource.substring(6, 8);
        System.out.println(dateStr);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dateStr);
            return (date != null);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    //获取新身份证的最后一位:检验位
    private static String getCheckFlag(String idCard) {
        int sum = 0;
        //进行加权求和
        for (int i = 0; i < 17; i++) {
            sum += Integer.parseInt(idCard.substring(i, i + 1)) * Wi[i];
        }
        //取模运算，得到模值
        byte iCode = (byte) (sum % fMod);
        return CheckCode.substring(iCode, iCode + 1);
    }

    //判断身份证号码的合法性
    public static boolean checkIDCard(final String idCard) {
        //初始化方法
        IdentityUtils.setWiBuffer();

        int length = idCard.length();
        boolean isNew;
        if (length == oldIDLen) isNew = false;
        else if (length == newIDLen) isNew = true;
        else return false;

        //String message = "";
        if (!checkLength(idCard, isNew)) {
            //message = "ID长度异常";
            return false;
        }
        String idDate = getIDDate(idCard, isNew);
        if (!checkDate(idDate)) {
            //message = "ID时间异常";
            return false;
        }
        if (isNew) {
            String checkFlag = getCheckFlag(idCard);
            String theFlag = idCard.substring(idCard.length() - 1, idCard.length());
            if (!checkFlag.equals(theFlag)) {
                //message = "新身份证校验位异常";
                return false;
            }
        }
        return true;
    }

}
