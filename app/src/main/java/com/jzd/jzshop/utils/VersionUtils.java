package com.jzd.jzshop.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jzd.jzshop.app.AppApplication;

/**
 * @author LWH
 * @description:
 * @date :2019/12/11 11:17
 */
public class VersionUtils {
    public static int getVersionCode(){
        PackageInfo info = null;
        try {
            info = AppApplication.getInstance().getPackageManager().getPackageInfo(AppApplication.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException pE) {
            pE.printStackTrace();
        }
        return info.versionCode;
    }
    public static String getVersionName(){
        PackageInfo info=null;
        try {
           info =AppApplication.getInstance().getPackageManager().getPackageInfo(AppApplication.getInstance().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

       return info.versionName;
    }

}
