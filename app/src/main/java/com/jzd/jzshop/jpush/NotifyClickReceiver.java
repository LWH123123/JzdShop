package com.jzd.jzshop.jpush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.jzd.jzshop.chat.OpenChatActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.message.MessageMerchListActivity;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailAty;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description: jpush 通知栏接收点击事件的广播
 * @date :2020/3/24 16:30
 */
public class NotifyClickReceiver extends BroadcastReceiver {
    private static final String TAG = NotifyClickReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(TAG, "[NotifyClickReceiver] onReceive - " + intent.getAction());
        String action = intent.getAction();
        String content = intent.getStringExtra("content");
        String param2 = intent.getStringExtra("param2");
        int type = intent.getIntExtra("type", -1);
        Log.d(TAG, "onReceive　type:" + type);
        Gson gson = new Gson();
        JpushBean.Param param = gson.fromJson(param2, JpushBean.Param.class);
        switch (type) {
            case Constants.NOTIFY_MSG_TYPE_ORDER_PAYED: // 会员消息   skip 订单详情
            case Constants.NOTIFY_MSG_TYPE_ORDER_REFUND:
            case Constants.NOTIFY_MSG_TYPE_ORDER_SEND:
            case Constants.NOTIFY_MSG_TYPE_ORDER_END:
            case Constants.NOTIFY_MSG_TYPE_ORDER_CLOSE:
            case Constants.NOTIFY_MSG_TYPE_REFUND_NOSEND:
            case Constants.NOTIFY_MSG_TYPE_RREFUND_SEND:
            case Constants.NOTIFY_MSG_TYPE_REFUND_ENDS:
            case Constants.NOTIFY_MSG_TYPE_REFUND_REFUSE:
                if (param != null) {
                    String order_id = param.getOrder_id();
                    Log.d(TAG, "NotifyClickReceiver　order_id－－－－＞＞:" + order_id);
                    Intent orderDetail = new Intent(context, OrderDetailAty.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ORDER_ID, param.getOrder_id());
                    orderDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    orderDetail.putExtras(bundle);
                    context.startActivity(orderDetail);
                }
                break;
            case Constants.NOTIFY_MSG_TYPE_MERCH_ORDER_SEND: //商户消息
            case Constants.NOTIFY_MSG_TYPE_MERCH_REFUND:
            case Constants.NOTIFY_MSG_TYPE_MERCH_ORDER_END:
            case Constants.NOTIFY_MSG_TYPE_MERCH_REFUND_SEND:
                String jpush_type ="";
                if (param != null) {
                    String order_id = param.getOrder_id();
                    String refund_id = param.getRefund_id();
                    Log.d(TAG, "NotifyClickReceiver　order_id－－－－＞＞:" + order_id
                            + "\n refund_id－－－－＞＞:" +refund_id + "\n type－－－－＞＞:" +type);
                    Intent intent1 = new Intent(context, MessageMerchListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ORDER_ID, order_id);
                    bundle.putString(Constants.REFUND_ID, refund_id);
                    if (type == 9){
                        jpush_type = "app:merch.order.send";
                    }else if (type == 10){
                        jpush_type = "app:merch.refund";
                    }else if (type == 11){
                        jpush_type = "app:merch.order.end";
                    }else if (type == 12){
                        jpush_type = "app:merch.refund.send";
                    }else {
                    }
                    bundle.putString(Constants.BUNDLE_KEY_JPUSH_MESSAGE_TYPE, jpush_type);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtras(bundle);
                    context.startActivity(intent1);
                }
                break;
            case Constants.NOTIFY_MSG_TYPE_CHAT_MESSAGE: //客服对话 消息
                if (param != null) {
                    String jpush_type_chat ="app:chat.message";
                    String receive_name = param.getReceive_name();
                    String ltype = param.getLtype();
                    String receive_id = param.getReceive_id();
                    Log.d(TAG, "NotifyClickReceiver　ltype－－－－＞＞:" + ltype
                            + "\n receive_id－－－－＞＞:" +receive_id + "\n type－－－－＞＞:" +type);

                    //打开先先关闭，防止已经打开，打开多个会话页面，聊天数据会话错乱
                    AppManager.getAppManager().finishActivity(OpenChatActivity.class);

                    Intent intentChat = new Intent(context, OpenChatActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.GOODS_KEY_GID, "");
                    bundle.putString(Constants.GOODS_KEY_CHAT_ID, receive_id);
                    bundle.putString(Constants.GOODS_KEY_CHAT_NAME, receive_name);
                    bundle.putString(Constants.GOODS_KEY_CHAT_LTYPE, ltype);
                    bundle.putString(Constants.SP.ACTIVITY_OPEN_FLAG, "jpush");
                    intentChat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentChat.putExtras(bundle);
                    context.startActivity(intentChat);
                }
                break;
            default:
                Intent home = new Intent(context, HomePageActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(home);
                break;
        }
    }

}
