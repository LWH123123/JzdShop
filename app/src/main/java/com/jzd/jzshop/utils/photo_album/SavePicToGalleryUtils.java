package com.jzd.jzshop.utils.photo_album;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author LXB
 * @description: android 把图片 视频 保存到相册
 * tip : 保存图片到相册
 * 方案一：发送广播通知系统更新相册（相册更新很慢，部分手机无法更新）
 * 方案二：MediaScanner  查阅链接：https://www.xp.cn/b.php/81293.html
 * 方案三：仿照 微信保存图片到系统相册
 * @date :2020/3/25 12:20
 */
public class SavePicToGalleryUtils {

    private static final String TAG = "SavePicToGalleryUtils";

    /**
     * 把图片 保存到相册
     *
     * @param context
     * @param file
     * @param fileName
     */
    public static void saveToSystemGallery(Context context, File file, String fileName) {
        //方案一：发送广播通知系统更新相册
//        sendBroadcastInsertImage(context, file, fileName);

        //方案三：仿照 微信保存图片到系统相册
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getImageContentValues(context, file, System.currentTimeMillis());
        localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        Uri localUri = Uri.fromFile(file);
        localIntent.setData(localUri);
        context.sendBroadcast(localIntent);
    }

    /**
     * 发送广播通知系统更新相册
     *
     * @param context
     * @param file
     * @param fileName
     */
    private static void sendBroadcastInsertImage(Context context, File file, String fileName) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName + ".jpg", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
    }


    /**
     * 把视频文件 保存到相册
     * @param context
     * @param file
     * @param fileName
     */
    public static void saveVideoToSystemGallery(Context context, File file, String fileName) {
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        localIntent.setData(localUri);
        context.sendBroadcast(localIntent);
    }


    public static ContentValues getImageContentValues(Context context, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "image/jpeg");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("orientation", Integer.valueOf(0));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }

    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }
}
