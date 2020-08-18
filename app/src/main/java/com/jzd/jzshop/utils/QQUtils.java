package com.jzd.jzshop.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.jzd.jzshop.test.QQWebViewActivity;

/**
 * @author LXB
 * @description: app内创建QQ临时会话, 类似客服功能
 * @date :2019/12/31 16:40
 *
 *  1、企业QQ
 *  if (QQUtil.installedApp(this, "com.tencent.mobileqq") || QQUtil.installedApp(this, "com.tencent.tim")) {
 *     // 此处url填写自己公司企点地址
 *     QQUtil.openEnterpriseQQ(this, "http://q.url.cn/xxx?_type=wpa&qidian=true");
 *    }
 *
 *  2、个人QQ
 *  if (QQUtil.installedApp(this, "com.tencent.mobileqq") || QQUtil.installedApp(this, "com.tencent.tim")) {
 *     QQUtil.openPersonalQQ(this,"qq号码");
 *    }
 */
public class QQUtils {

    /**
     * 检查是否安装了app
     *
     * @param context     上下文对象
     * @param packageName app包名
     * @return true 已安装 false 未安装
     */
    public static boolean installedApp(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        return null == packageInfo ? false : true;
    }

    /**
     * 打开企业qq，创建临时会话
     *
     * @param context   上下文对象
     * @param qidianUrl 企点url 例如：http://q.url.cn/xxxx?_type=wpa&qidian=true
     */
    public static void openEnterpriseQQ(Context context, String qidianUrl) {
        Intent intent = new Intent(context, QQWebViewActivity.class);
        intent.putExtra("url", qidianUrl);
        context.startActivity(intent);
    }

    /**
     * 打开个人qq，创建临时会话
     *
     * @param context 上下文对象
     * @param qq      qq号码，该qq号码必须开通QQ推广，否则不能创建临时会话 @url http://shang.qq.com/v3/index.html (开通方式，点击推广工具-> 登录 -> 立即免费开通)
     */
    public static void openPersonalQQ(Context context, String qq) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+ qq));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch (ActivityNotFoundException a){
            a.getMessage();
        }
    }
}
