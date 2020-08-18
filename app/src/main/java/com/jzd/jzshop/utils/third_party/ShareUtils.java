package com.jzd.jzshop.utils.third_party;

import android.content.Context;
import android.graphics.Bitmap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @author LXB
 * @description: shareSdk 分享工具类
 * @date :2019/12/6 16:27
 */
public class ShareUtils {
    public static void shareImageBitmap(Context context, String shareTitle, String shareText, Bitmap shareBitmap, String shareUrl) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(shareTitle);
        oks.setTitleUrl(shareUrl);
        oks.setText(shareText);
        oks.setImageData(shareBitmap);
        oks.setUrl(shareUrl);
        oks.show(context);
    }

    public static void shareImageUrl(Context context, String shareTitle, String shareText, String shareImage, String shareUrl) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(shareTitle);
        oks.setTitleUrl(shareUrl);
        oks.setText(shareText);
        oks.setImageUrl(shareImage);
        oks.setUrl(shareUrl);
        oks.show(context);
    }

    public static void shareWeixinImageUrl(Context context, String shareTitle, String shareText, String shareImage, String shareUrl) {
        OnekeyShare oks = new OnekeyShare();
        oks.setPlatform(Wechat.NAME);
        oks.disableSSOWhenAuthorize();
        oks.setTitle(shareTitle);
        oks.setTitleUrl(shareUrl);
        oks.setText(shareText);
        oks.setImageUrl(shareImage);
        oks.setUrl(shareUrl);
        oks.show(context);
    }

    /**
     * 从APP 分享小程序到微信好友会话、朋友圈或添加到微信收藏
     *
     * @param context
     * @param shareTitle
     * @param shareText
     * @param shareImage
     * @param shareUrl
     * @param userName
     * @param path
     */
    public static void shareWxMiniprogram(Context context, String shareTitle, final String shareText, final String shareImage, final String shareUrl,
                                          final String userName, final String path) {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(shareTitle);
        oks.setTitleUrl(shareUrl);
        oks.setText(shareText);
        oks.setImageUrl(shareImage);
        oks.setUrl(shareUrl);
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams shareParams) {
                /**
                 * 凡是标明得参数都不能为空
                 */
                if (platform.getName().equals(Wechat.NAME)) {
                    shareParams.setShareType(Platform.SHARE_WXMINIPROGRAM);
                    shareParams.setWxUserName(userName); //小程序原始id
                    shareParams.setWxPath(path);//小程序分享页面路径
                    shareParams.setImageUrl(shareImage);
                    shareParams.setText(shareText);
                    shareParams.setUrl(shareUrl);
                    //数字，分享微信小程序时，表示小程序的开发状态，取值范围：0-正式，1-开发，2-体验，仅在微信中使用
                    shareParams.setWxMiniProgramType(0);
                }
            }
        });
        oks.show(context);
    }
}
