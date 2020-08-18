package com.jzd.jzshop.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jzd.jzshop.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.annotations.NonNull;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by ${lxb} on 2019/8/30.
 * tip : 常用工具类
 */
public class SystemUtils {
    private static final String TAG = "SystemUtils";

    /**
     * 解析出url参数中的键值对（android 获取url中的参数）
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }


    /**
     * 电话格式校验
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(14[0-9])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 密码格式校验
     *
     * @param pass
     * @return
     */
    public static boolean passWordVerify(String pass) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]{6,12}$");
//      Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,12}$");
        return p.matcher(pass).matches();
    }


    /**
     * 邮箱格式校验
     *
     * @param mailAddress
     * @return
     */
    public static boolean mailAddressVerify(String mailAddress) {
        String emailExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(emailExp);
        return p.matcher(mailAddress).matches();
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Android SearchView的自定义
     * 参考连接https://www.jianshu.com/p/f1fe616d630d
     *
     * @param context
     * @param searchView
     */
    public static void customeStyle(Context context, SearchView searchView) {
        //取消搜索下划线
//        View underline =searchView.findViewById(R.id.search_plate);
//        underline.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        //修改键入的文字字体大小、颜色和hint的字体颜色
        final EditText editText = (EditText) searchView.findViewById(R.id.search_src_text);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.px16sp));
        editText.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        editText.setHintTextColor(ContextCompat.getColor(context, R.color.textColorHint));
        editText.setGravity(Gravity.CENTER_VERTICAL);
        //自定义searchView布局最右侧”❌“号图标
//        ImageView closeViewIcon = (ImageView)searchView.findViewById(R.id.search_close_btn);
//        closeViewIcon.setImageDrawable(ContextCompat
//                .getDrawable(context,R.drawable.ic_search_close));
    }


    /**
     * 获取APP 版本号
     */
    public static int getAppVersionCode(Context ctx) {
        int versionCode = 0;
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        return versionCode;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return context.getString(R.string.version_unknown);
        }
    }

    /**
     * 应用内跳转QQ客服
     * 替换成 推广的qq
     *
     * @param context
     */
    public static void toQQServer(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.tencent.mobileqq", PackageManager.GET_UNINSTALLED_PACKAGES);
            if (info != null) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=1966770975")));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            ToastUtils.showShort("本机未安装QQ应用");
        }
    }

    /**
     * 复制文本  到粘贴板
     *
     * @param context
     * @param content
     */
    public static void copyTextClipboard(Context context, String content) {
        ClipboardManager clipboardmanager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // Creates a new text clip to put on the
        ClipData clip = ClipData.newPlainText("Label", content);
        clipboardmanager.setPrimaryClip(clip);
    }

    /**
     * 从粘贴板  粘贴文本
     *
     * @param context
     */
    public static String pasteTextClipboard(Context context) {
        ClipboardManager mClipboardManager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!mClipboardManager.hasPrimaryClip()) return "";
        //获取 ClipDescription
        ClipDescription clipDescription = mClipboardManager.getPrimaryClipDescription();
        //获取 lable
        String lable = clipDescription.getLabel().toString();
        ClipData clipData = mClipboardManager.getPrimaryClip();

        ClipData.Item item = clipData.getItemAt(0);
        //获取text
        String text = item.getText().toString();
        return text;
    }

    /**
     * 星级评分
     *
     * @param context
     * @param ratingLevel
     * @param textView
     */
    public static void setRatingBarLevel(Context context, int ratingLevel, TextView textView) {
        if (ratingLevel == 0) {
            textView.setText(R.string.no_score);
            textView.setTextColor(context.getResources().getColor(R.color.textColor));
        } else if (ratingLevel == 1) {
            textView.setText(R.string.no_good_score);
            textView.setTextColor(context.getResources().getColor(R.color.textColor));
        } else if (ratingLevel == 2) {
            textView.setText(R.string.normal_score);
            textView.setTextColor(context.getResources().getColor(R.color.color_dialog_btn));
        } else if (ratingLevel == 3) {
            textView.setText(R.string.good_score);
            textView.setTextColor(context.getResources().getColor(R.color.picture_color_20c064));
        } else if (ratingLevel == 4) {
            textView.setText(R.string.satisfied_score);
            textView.setTextColor(context.getResources().getColor(R.color.levelorange));
        } else {
            textView.setText(R.string.very_good_score);
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * 清空星级评价
     *
     * @param context
     * @param ratingbar
     * @param tv_score_txt
     */
    public static void clearRating(Context context, SimpleRatingBar ratingbar, TextView tv_score_txt) {
        ratingbar.setRating(0f);
        tv_score_txt.setText(R.string.no_score);
        tv_score_txt.setTextColor(context.getResources().getColor(R.color.textColor));
        ratingbar.invalidate();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        Date date = new Date();// 创建一个时间对象，获取到当前的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
        String CurrentTime = sdf.format(date);
        return CurrentTime;
    }

    /**
     * 输入手机设备系统参数
     */
    public static void showSystemParameter(Context ctx) {
        String TAG = "系统参数：";
        Log.e(TAG, "手机厂商：" + SystemUtils.getDeviceBrand());
        Log.e(TAG, "手机型号：" + SystemUtils.getSystemModel());
        Log.e(TAG, "手机当前系统语言：" + SystemUtils.getSystemLanguage());
        Log.e(TAG, "Android系统版本号：" + SystemUtils.getSystemVersion());
        Log.e(TAG, "手机IMEI：" + SystemUtils.getIMEI(ctx));
    }

    //---------------------------------------------------------获取设备系统参数-----------------------------------------------------------------

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 添加 activity 进出转场动画
     *
     * @param activity
     * @param context
     * @param clz
     */
    public static void openActivityByAnimation(Activity activity, Context context, Class<?> clz) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(new Intent(context, clz),
                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    }

    /**
     * 用于判断是否快速点击(防止view 多次重复点击)
     */
    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 隐藏软键盘(有输入框)
     *
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context,
                                        @NonNull EditText mEditText) {
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 打开 软键盘(有输入框)
     *
     * @param activity
     * @param mEditText
     */
    public static void showSoftKeyboard(@NonNull Activity activity,
                                        @NonNull EditText mEditText) {
        activity.getWindow().getDecorView().post(() -> {
            InputMethodManager inputManager =
                    (InputMethodManager) activity.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(mEditText, 0);
        });
    }


    /**
     * 聊天时间格式化显示  (时间戳格式转换)
     * <p>
     * 在同一年的显示规则：
     * 如果是当天显示格式为 HH:mm 例：14:45
     * 如果是昨天,显示格式为 昨天 HH:mm 例：昨天 13:12
     * 如果是在同一周 显示格式为 周一 HH:mm 例：周一14:05
     * 如果不是同一周则显示格式为 M月d日 早上或者其它 HH:mm 例： 2月5日 早上10:10
     * <p>
     * 不在同一年的显示规则：
     * 显示格式为 yyyy年M月d日 晚上或者其它 HH:mm 例：2016年2月5日 晚上18:05
     * <p>
     * 运行结果：
     * System.out.println("当前时间："+new SimpleDateFormat("yyyy/M/d HH:mm:ss").format(System.currentTimeMillis()));
     * 当前时间：2017/2/9 14:36:36
     * 2016/2/1 05:05:00  显示为：2016年2月5日 晚上18:05
     * 2017/2/1 05:05:00  显示为：2月2日 凌晨05:05
     * 2017/2/4 12:05:00  显示为：2月4日 中午12:05
     * 2017/2/5 13:12:00  显示为：2月5日 早上10:10
     * 2017/2/5 13:12:00  显示为：2月5日 下午13:12
     * 2017/2/6 14:05:00  显示为：周一14:05
     */

    private static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String chatTimeDisplay(long timesamp) {
        String result = "";
        Calendar todayCalendar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            todayCalendar = Calendar.getInstance();
            Calendar otherCalendar = Calendar.getInstance();
            otherCalendar.setTimeInMillis(timesamp);

            String timeFormat = "M月d日 HH:mm";
            String yearTimeFormat = "yyyy年M月d日 HH:mm";
            String am_pm = "";
            int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 0 && hour < 6) {
                am_pm = "凌晨";
            } else if (hour >= 6 && hour < 12) {
                am_pm = "早上";
            } else if (hour == 12) {
                am_pm = "中午";
            } else if (hour > 12 && hour < 18) {
                am_pm = "下午";
            } else if (hour >= 18) {
                am_pm = "晚上";
            }
            timeFormat = "M月d日 " + am_pm + "HH:mm";
            yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";

            boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
            if (yearTemp) {
                int todayMonth = todayCalendar.get(Calendar.MONTH);
                int otherMonth = otherCalendar.get(Calendar.MONTH);
                if (todayMonth == otherMonth) {//表示是同一个月
                    int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                    switch (temp) {
                        case 0:
                            result = getHourAndMin(timesamp);
                            break;
                        case 1:
                            result = "昨天 " + getHourAndMin(timesamp);
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                            int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                            if (dayOfMonth == todayOfMonth) {//表示是同一周
                                int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                                if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                    result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin(timesamp);
                                } else {
                                    result = getTime(timesamp, timeFormat);
                                }
                            } else {
                                result = getTime(timesamp, timeFormat);
                            }
                            break;
                        default:
                            result = getTime(timesamp, timeFormat);
                            break;
                    }
                } else {
                    result = getTime(timesamp, timeFormat);
                }
            } else {
                result = getYearTime(timesamp, yearTimeFormat);
            }
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     *
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }

    /**
     * list pic  预加载动画
     *
     * @param mContext
     * @param thumb
     * @param shopIcon
     */
    public static void setPicTransition(Context mContext, String thumb, ImageView shopIcon) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);
        Glide.with(mContext)
                .load(thumb)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(shopIcon);
    }


}
