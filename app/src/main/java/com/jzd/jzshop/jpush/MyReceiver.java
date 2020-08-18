package com.jzd.jzshop.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.entity.OrderToPayEntityH5;
import com.jzd.jzshop.ui.home.news.HomePageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";
	private NotificationManager mNotificationManager;
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			this.mContext = context;
			if (null == mNotificationManager) {
				mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			}
			Bundle bundle = intent.getExtras();
			Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG,"[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

				//todo 打开自定义的Activity,暂时跳主页
				Intent i = new Intent(context, HomePageActivity.class);
				i.putExtras(bundle);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Logger.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();
					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.get(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//JPushInterface.EXTRA_EXTRA
//		if (HomePageActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			Intent msgIntent = new Intent(HomePageActivity.MESSAGE_RECEIVED_ACTION);
//			KLog.json("<EXTRA_EXTRA> ========服务器推送下来的附加字段:",bundle.getString(JPushInterface.EXTRA_EXTRA));
//			showInspectorRecordNotification(bundle);		// 显示自定义通知

			msgIntent.putExtra(HomePageActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(HomePageActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
	}


	private void showInspectorRecordNotification(Bundle bundle) {
//		String title = bundle.getString(JPushInterface.EXTRA_TITLE);//JPushInterface.EXTRA_TITLE
//		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE); //JPushInterface.EXTRA_MESSAGE
//		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//JPushInterface.EXTRA_EXTRA
//
//		RemoteViews customView = new RemoteViews(mContext.getPackageName(), R.layout.notification_view_custom);
//		customView.setTextViewText(R.id.title, title);
//		customView.setTextViewText(R.id.text, message);
//		customView.setTextViewText(R.id.time, "");
//
//		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
//		mBuilder.setContent(customView)
//				.setContentIntent(getDefalutIntent(PendingIntent.FLAG_UPDATE_CURRENT))
//				.setWhen(System.currentTimeMillis())
//				.setTicker("")
//				.setPriority(Notification.PRIORITY_DEFAULT)
//				.setOngoing(false)
//				.setSmallIcon(R.mipmap.logo);
//		Notification notify = mBuilder.build();
//		notify.contentView = customView;
//		notify.flags |= Notification.FLAG_AUTO_CANCEL; // 点击通知后通知栏消失
//		// 通知id需要唯一，要不然会覆盖前一条通知
//		int notifyId = (int) System.currentTimeMillis();
//		NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//		mNotificationManager.notify(notifyId, notify);
	}

	/**
	 * 构造一个打开通知的Intent
	 *
	 * @param flags
	 * @return
	 */
//	private PendingIntent getDefalutIntent(int flags) {
//		Intent transferIntent = new Intent(mContext, HomePageActivity.class);
//		transferIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		transferIntent.putExtra(HomePageActivity.KEY_FORM_TYPE, "pushType");
//		// 第二个参数不能写死，可以写一个随机数或者是时间毫秒数 保证唯一
//		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, (int)(Math.random() * 100), transferIntent, flags);
//		return pendingIntent;
//	}
}
