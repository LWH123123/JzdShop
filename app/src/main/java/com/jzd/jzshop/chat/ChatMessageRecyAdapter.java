package com.jzd.jzshop.chat;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jzd.jzshop.R;
import com.jzd.jzshop.chat.model.ChatMessageResponse;
import com.jzd.jzshop.chat.model.ChatMessageShopEntiity;
import com.jzd.jzshop.utils.SystemUtils;
import com.shehuan.niv.NiceImageView;

import java.util.List;


/**
 * @author LXB
 * @description:
 * @date :2020/4/22 11:08
 * tip : https://www.jianshu.com/p/5ce7426dfb4f
 */
public class ChatMessageRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ChatMessageRecyAdapter.class.getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    List<ChatMessageResponse> mChatMessageList;
    LayoutInflater inflater;
    Context context;
    //viewType;
    public static final int TYPE_EMPTY = 0;// 空消息
    public static final int TYPE_RECEIVE = 1;// 接收消息
    public static final int TYPE_SEND = 2; //发送消息
    private String msgId;
    private int indext = -1;
    private String msg_id;
    private ChatMessageResponse dataBean;

    public ChatMessageRecyAdapter(Context context, List<ChatMessageResponse> list) {
        this.mChatMessageList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    /*给item一个唯一标识*/
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemViewType(int position) {
        if (mChatMessageList.size() == 0) {
            return TYPE_EMPTY;
        } else if (mChatMessageList.get(position).getIs_self() == 1) { //自己发送
            return TYPE_SEND;
        } else if (mChatMessageList.get(position).getIs_self() == 0) {//否
            return TYPE_RECEIVE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_empty, viewGroup, false);
            return new EmptyHolder(emptyView);
        } else if (viewType == TYPE_SEND) {
            View sendView = LayoutInflater.from(context).inflate(R.layout.item_chat_send_text, viewGroup, false);
            return new SendHolder(sendView);
        } else {//对方发送
            View receiveView = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_text, viewGroup, false);
            return new ReceiveHolder(receiveView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EmptyHolder) { //显示空item
            EmptyHolder emptyHolder = (EmptyHolder) viewHolder;
            emptyHolder.tv_empty_txt.setText(R.string.chat_empty_text);
        } else if (viewHolder instanceof SendHolder) {//是自己发送
            SendHolder sendHolder = (SendHolder) viewHolder;
            //获取model对象
            ChatMessageResponse dataBean = mChatMessageList.get(i);
            int msg_type = dataBean.getMsg_type();
            if (msg_type == 2) { //当msg_type = 2时，为json串
                Gson gson = new Gson();
                ChatMessageShopEntiity chatMessageShopEntiity = gson.fromJson(dataBean.getMsg(), ChatMessageShopEntiity.class);
                //发送的商品信息商品
                if (chatMessageShopEntiity != null) {
                    sendHolder.ll_push.setVisibility(View.VISIBLE);
                    Glide.with(context).load(chatMessageShopEntiity.getThumb()).into(sendHolder.iv_push_img);
                    sendHolder.tv_menuName.setText(chatMessageShopEntiity.getTitle());
                    sendHolder.tv_pushContent.setText("¥" + chatMessageShopEntiity.getPrice());
                    sendHolder.tvContent.setVisibility(View.GONE);
                    sendHolder.rl_content_img.setVisibility(View.GONE);
                }
            } else if (msg_type == 1) { //当msg_type = 1时，  图片
                String contentLink = dataBean.getMsg();
                sendHolder.rl_content_img.setVisibility(View.VISIBLE);
                Glide.with(context).load(contentLink).into(sendHolder.iv_content_img);
                sendHolder.ll_push.setVisibility(View.GONE);
                sendHolder.tvContent.setVisibility(View.GONE);
            } else {
                String msg = dataBean.getMsg().trim();
                if (!TextUtils.isEmpty(msg)) {
                    sendHolder.tvContent.setVisibility(View.VISIBLE);
                    sendHolder.tvContent.setText(msg);

                }
                sendHolder.ll_push.setVisibility(View.GONE);
                sendHolder.rl_content_img.setVisibility(View.GONE);

                int isMeSend = dataBean.getIs_self();
                int isRead = dataBean.getRead();////是否已读（0未读 1已读）
                //如果是自己发送才显示未读已读
                if (isMeSend == 1) {//是自己
                    if (isRead == 0) {
                        sendHolder.tvIsRead.setText("未读");
                        sendHolder.tvIsRead.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    } else if (isRead == 1) {
                        sendHolder.tvIsRead.setText("已读");
                        sendHolder.tvIsRead.setTextColor(Color.GRAY);
                    } else {
                        sendHolder.tvIsRead.setText("");
                    }
                } else {
                    sendHolder.tvDisplayName.setVisibility(View.VISIBLE);
                    sendHolder.tvDisplayName.setText(dataBean.getNickname());
                }
            }
            //根据model对象给cell的组件赋值
            String time = formatTime(dataBean.getMsg_time());
            sendHolder.tvSendTime.setText(time);
            Glide.with(context).load(dataBean.getAvatar()).into(sendHolder.jmui_avatar_iv);
            //撤回消息显示
            int status = dataBean.getStatus();
            int isMeSend = dataBean.getIs_self();
            if (status == 1) {
                if (isMeSend == 1) {
                    if (msg_type == 2){//商品
                        sendHolder.ll_push.setVisibility(View.GONE);
                        sendHolder.cardview.setVisibility(View.GONE);
                        sendHolder.tvIsRead.setVisibility(View.GONE);
                        sendHolder.tvContent.setVisibility(View.GONE);

                        sendHolder.tv_back_msg.setVisibility(View.VISIBLE);
                        sendHolder.tv_back_msg.setText(context.getResources().getString(R.string.chat_txt_send_backmsg_tip));
                    }else if (msg_type == 1){//图片
                        sendHolder.rl_content_img.setVisibility(View.GONE);
                        sendHolder.cardview.setVisibility(View.GONE);
                        sendHolder.tvIsRead.setVisibility(View.GONE);

                        sendHolder.tv_back_msg.setVisibility(View.VISIBLE);
                        sendHolder.tv_back_msg.setText(context.getResources().getString(R.string.chat_txt_send_backmsg_tip));
                    }else {//文本
                        sendHolder.tv_back_msg.setVisibility(View.VISIBLE);
                        sendHolder.tv_back_msg.setText(context.getResources().getString(R.string.chat_txt_send_backmsg_tip));

                        sendHolder.tvContent.setVisibility(View.GONE);
                        sendHolder.cardview.setVisibility(View.GONE);
                        sendHolder.tvIsRead.setVisibility(View.GONE);
                    }
//                    sendHolder.tv_back_msg.setVisibility(View.VISIBLE);
//                    sendHolder.tv_back_msg.setText(context.getResources().getString(R.string.chat_txt_send_backmsg_tip));
//                    sendHolder.tvContent.setVisibility(View.GONE);
//                    sendHolder.cardview.setVisibility(View.GONE);
//                    sendHolder.tvIsRead.setVisibility(View.GONE);
                } else {
                    sendHolder.tv_back_msg.setVisibility(View.GONE);
                }
            } else {
                sendHolder.tv_back_msg.setVisibility(View.GONE);
                sendHolder.cardview.setVisibility(View.VISIBLE);
            }
            //添加事件回调
            mAddItemCallBack(i, sendHolder);
        } else if (viewHolder instanceof ReceiveHolder) { //不是自己
            ReceiveHolder receiveHolder = (ReceiveHolder) viewHolder;
            //获取model对象
            ChatMessageResponse dataBean = mChatMessageList.get(i);
            int msg_type = dataBean.getMsg_type();
            if (msg_type == 2) { //当msg_type = 2时，为json串  商品
                Gson gson = new Gson();
                ChatMessageShopEntiity chatMessageShopEntiity = gson.fromJson(dataBean.getMsg(), ChatMessageShopEntiity.class);
                //发送的商品信息商品
                if (chatMessageShopEntiity != null) { //发送的商品信息商品
                    receiveHolder.ll_push.setVisibility(View.VISIBLE);
                    Glide.with(context).load(chatMessageShopEntiity.getThumb()).into(receiveHolder.iv_push_img);
                    receiveHolder.tv_menuName.setText(chatMessageShopEntiity.getTitle());
                    receiveHolder.tv_pushContent.setText(chatMessageShopEntiity.getPrice());
                    receiveHolder.tvContent.setVisibility(View.GONE);
                    receiveHolder.rl_content_img.setVisibility(View.GONE);
                }
            } else if (msg_type == 1) { //当msg_type = 1时， 图片
                String contentLink = dataBean.getMsg();
                receiveHolder.rl_content_img.setVisibility(View.VISIBLE);
                Glide.with(context).load(contentLink).into(receiveHolder.iv_content_img);
                receiveHolder.ll_push.setVisibility(View.GONE);
                receiveHolder.tvContent.setVisibility(View.GONE);
            } else {  //当msg_type = 0时，为json串  文本
                // TODO: 2020/4/23      // 纯文本/带有链接的文本
                String msg = dataBean.getMsg().trim();
                final Spanned spanned = Html.fromHtml(msg, null, null);
                Log.d(TAG, "spanned============" + spanned);
                if (!TextUtils.isEmpty(msg)) {
                    receiveHolder.tvContent.setVisibility(View.VISIBLE);
                    //过滤商户发送空格
                    String trim = spanned.toString().trim();
                    receiveHolder.tvContent.setText(trim);
//                    receiveHolder.tvContent.setText(Html.fromHtml(msg));
                }
                receiveHolder.ll_push.setVisibility(View.GONE);
                receiveHolder.rl_content_img.setVisibility(View.GONE);

                int isMeSend = dataBean.getIs_self();
                int isRead = dataBean.getRead();////是否已读（0未读 1已读）
                //如果是自己发送才显示未读已读
                if (isMeSend == 1) {
                    if (isRead == 0) {
                        receiveHolder.tvIsRead.setText("未读");
                        receiveHolder.tvIsRead.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    } else if (isRead == 1) {
                        receiveHolder.tvIsRead.setText("已读");
                        receiveHolder.tvIsRead.setTextColor(Color.GRAY);
                    } else {
                        receiveHolder.tvIsRead.setText("");
                    }
                } else {
                    if (dataBean.getStatus() == 1) {
                        receiveHolder.tvDisplayName.setVisibility(View.GONE);
                    } else {
                        receiveHolder.tvDisplayName.setVisibility(View.VISIBLE);
                        receiveHolder.tvDisplayName.setText(dataBean.getNickname());
                    }
//                    receiveHolder.tvDisplayName.setVisibility(View.VISIBLE);
//                    receiveHolder.tvDisplayName.setText(dataBean.getNickname());
                }
            }
            //根据model对象给cell的组件赋值
            String time = formatTime(dataBean.getMsg_time());
            receiveHolder.tvSendTime.setText(time);
            Glide.with(context).load(dataBean.getAvatar()).into(receiveHolder.jmui_avatar_iv);
            //撤回消息显示
            int status = dataBean.getStatus();
            int isMeSend = dataBean.getIs_self();
            if (status == 1) {
                if (isMeSend == 0) {
                    receiveHolder.tv_back_msg.setVisibility(View.VISIBLE);
                    receiveHolder.tv_back_msg.setText("“" + dataBean.getNickname() + "”" + "撤回了一条消息");
                    receiveHolder.tvContent.setVisibility(View.GONE);
                    receiveHolder.cardview.setVisibility(View.GONE);
                } else {
                    receiveHolder.tv_back_msg.setVisibility(View.GONE);
                }
            } else {
                receiveHolder.tv_back_msg.setVisibility(View.GONE);
            }

            //添加事件回调
            mAddItemCallBack(i, receiveHolder);
        } else {
        }
    }

    @Override
    public int getItemCount() {
        if (mChatMessageList.size() > 0) {
            return mChatMessageList.size();
        } else {//无数据item
            return 1;
        }

    }

    /*有数据的holder*/
    public class SendHolder extends RecyclerView.ViewHolder {
        private LinearLayout lin_rootview_send;
        private NiceImageView jmui_avatar_iv;
        private CardView cardview;
        private TextView tvContent;
        private RelativeLayout rl_content_img;
        private ImageView iv_content_img;
        private TextView tvSendTime;
        private TextView tvDisplayName;
        private TextView tvIsRead;
        //商品信息
        private LinearLayout ll_push;
        private ImageView iv_push_img;
        private TextView tv_menuName;
        private TextView tv_pushContent;
        //撤回消息
        private TextView tv_back_msg;

        public SendHolder(View view) {
            super(view);
            lin_rootview_send = itemView.findViewById(R.id.lin_rootview_send);
            cardview = itemView.findViewById(R.id.cardview);
            jmui_avatar_iv = itemView.findViewById(R.id.jmui_avatar_iv);
            tvContent = itemView.findViewById(R.id.tv_content);
            rl_content_img = itemView.findViewById(R.id.rl_content_img);
            iv_content_img = itemView.findViewById(R.id.iv_content_img);
            tvSendTime = itemView.findViewById(R.id.tv_sendtime);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvIsRead = itemView.findViewById(R.id.tv_isRead);
            //发送的商品信息UI
            ll_push = itemView.findViewById(R.id.ll_push);
            iv_push_img = itemView.findViewById(R.id.iv_push_img);
            tv_menuName = itemView.findViewById(R.id.tv_menuName);
            tv_pushContent = itemView.findViewById(R.id.tv_pushContent);
            tv_back_msg = itemView.findViewById(R.id.tv_back_msg);
        }
    }

    public class ReceiveHolder extends RecyclerView.ViewHolder {
        private LinearLayout lin_rootview_receive;
        private NiceImageView jmui_avatar_iv;
        private CardView cardview;
        private TextView tvContent;
        private RelativeLayout rl_content_img;
        private ImageView iv_content_img;
        private TextView tvSendTime;
        private TextView tvDisplayName;
        private TextView tvIsRead;
        //商品信息
        private LinearLayout ll_push;
        private ImageView iv_push_img;
        private TextView tv_menuName;
        private TextView tv_pushContent;
        //撤回消息
        private TextView tv_back_msg;

        public ReceiveHolder(View itemView) {
            super(itemView);
            lin_rootview_receive = itemView.findViewById(R.id.lin_rootview_receive);
            cardview = itemView.findViewById(R.id.cardview);
            jmui_avatar_iv = itemView.findViewById(R.id.jmui_avatar_iv);
            tvContent = itemView.findViewById(R.id.tv_content);
            rl_content_img = itemView.findViewById(R.id.rl_content_img);
            iv_content_img = itemView.findViewById(R.id.iv_content_img);
            tvSendTime = itemView.findViewById(R.id.tv_sendtime);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvIsRead = itemView.findViewById(R.id.tv_isRead);
            //发送的商品信息UI
            ll_push = itemView.findViewById(R.id.ll_push);
            iv_push_img = itemView.findViewById(R.id.iv_push_img);
            tv_menuName = itemView.findViewById(R.id.tv_menuName);
            tv_pushContent = itemView.findViewById(R.id.tv_pushContent);
            tv_back_msg = itemView.findViewById(R.id.tv_back_msg);
        }
    }

    /*无数据的空item*/
    public class EmptyHolder extends RecyclerView.ViewHolder {
        TextView tv_empty_txt;

        public EmptyHolder(View view) {
            super(view);
            tv_empty_txt = itemView.findViewById(R.id.tv_empty_txt);
        }

    }


    private void mAddItemCallBack(int i, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof SendHolder) {
            SendHolder sendHolder = (SendHolder) viewHolder;
            if (mOnItemClickListener != null) {
                sendHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(i);
                    }
                });
                sendHolder.tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(i);
                        return true;//true 消费掉事件
                    }
                });
                sendHolder.iv_content_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(i);
                    }
                });
                sendHolder.iv_content_img.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(i);
                        return true;
                    }
                });
                sendHolder.ll_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(i);
                    }
                });
                sendHolder.ll_push.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(i);
                        return true;
                    }
                });
            }
        } else {
            ReceiveHolder receiveHolder = (ReceiveHolder) viewHolder;
            if (mOnItemClickListener != null) {
                receiveHolder.tvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(i);
                    }
                });
                receiveHolder.tvContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(i);
                        return true;
                    }
                });
                receiveHolder.iv_content_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(i);
                    }
                });
                receiveHolder.iv_content_img.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(i);
                        return true;
                    }
                });
                receiveHolder.ll_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(i);
                    }
                });
                receiveHolder.ll_push.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(i);
                        return true;
                    }
                });
            }
        }
    }

    /**
     * 本地消息撤回处理
     *
     * @param indext
     * @param msg_id
     */
    public void showBackMsg(int indext, String msg_id) {
        this.indext = indext;
        this.msg_id = msg_id;
        ChatMessageResponse chatMessageResponse = mChatMessageList.get(indext);
        chatMessageResponse.setStatus(1);
//        notifyItemRemoved(indext);
//        //必须调用这行代码
//        notifyItemRangeChanged(indext, mChatMessageList.size());
        notifyDataSetChanged();
    }


    /**
     * 将毫秒数转为 日期格式
     *
     * @param timeMillis
     * @return
     */
    private String formatTime(String timeMillis) {
        if (!TextUtils.isEmpty(timeMillis)) {
            long timeMillisl = Long.parseLong(timeMillis);
            //当前时间：2017/2/9 14:36:36
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //2010-12-08 14:36:36
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d HH:mm:ss");  // 2017/2/9 14:36:36
//            Date date = new Date(timeMillisl * 1000L);
//            return simpleDateFormat.format(date);
            return SystemUtils.chatTimeDisplay(timeMillisl * 1000L);// 聊天时间显示规则
        }
        return "";
    }

    //定义事件回调
    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

//    public void setBackId(String msgId) {
//        this.msgId = msgId;
//        notifyDataSetChanged();
//    }
}
