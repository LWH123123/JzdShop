package com.jzd.jzshop.chat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.chat.im.ChatMessage;
import com.jzd.jzshop.chat.im.JWebSocketClient;
import com.jzd.jzshop.chat.im.JWebSocketClientService;
import com.jzd.jzshop.chat.model.ChatHistoryEntity;
import com.jzd.jzshop.chat.model.ChatHistoryModel;
import com.jzd.jzshop.chat.model.ChatMessageEntiity;
import com.jzd.jzshop.chat.model.ChatMessageResponse;
import com.jzd.jzshop.chat.model.PopGoodsSpecAdapterChat;
import com.jzd.jzshop.chat.model.UploadPicModel;
import com.jzd.jzshop.chat.model.UploadPicResponse;
import com.jzd.jzshop.databinding.ActiivtyOpenChatBinding;
import com.jzd.jzshop.databinding.PoplayoutSpecChatBinding;
import com.jzd.jzshop.entity.GoodsEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.ui.mine.couponcenter.CouponCenterFragment;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.order.mineorder.MineOrderFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.net_utils.RSASignature;
import com.jzd.jzshop.utils.net_utils.RSAUtils2;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.jzd.jzshop.utils.photo_album.FileUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.byteam.superadapter.OnItemClickListener;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LXB
 * @description: 客服对话
 * @date :2020/4/21 18:08
 */
public class OpenChatActivity extends BaseActivity<ActiivtyOpenChatBinding, OpenChatViewModel> implements View.OnClickListener, OnItemClickListener,
        ChatMessageRecyAdapter.OnItemClickListener, OnRefreshListener {
    private static final String TAG = OpenChatActivity.class.getSimpleName();
    // 拍照相处选图-------------start
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private Bitmap bitmap;
    // 拍照相处选图-------------end
    //商品加入购物车-------------------start-------------------
    public int page = 1;   //分页页码
    private int pagesize = 15;   //每页页码数
    private String msg_id = "";   //通过消息id 查询分页
    PopupWindow popupWindow;
    private String gid,name,chat_id,ltype, price;
    private GoodsEntity.ResultBean.ShareinfoBean shareinfoBean;
    private GoodsEntity.ResultBean.ShopInfoBean shop_info;
    //商品加入购物车-------------------end-------------------
    private EditText et_content;
    private ConstraintLayout cons_top_shop, constrl_disconnection;
    private RecyclerView rv_hot_keyword;
    private Button btn_send;
    private ImageView iv_upload, iv_emoji, iv_thumb, iv_camera, iv_album;
    private TextView tv_reconnect, tv_add_shopcar, tv_buy_shop, tv_sendshop_link, tv_shop_title, tv_price, tv_camera, tv_album;
    private RelativeLayout rl_upload;
    private LinearLayout lin_bottom;
    //----------------im chat--------------start----
    private boolean isLogin = false; //是否调用登录连接
    private JWebSocketClient client;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;
    private ChatMessageRecyAdapter chatMessageRecyAdapter;
    private List<ChatMessageResponse> chatMessageList = new ArrayList<>();//消息列表
    private List<ChatMessageResponse> totalList = new ArrayList();  //list 状态数据，分页加载更多 数据显示有问题，通过 Stack 来转载数据处理
    //    private Stack<ChatMessageResponse> totalList = new Stack<>();
    private ChatMessageReceiver chatMessageReceiver;
    private List<String> listTitle;
    private int indext = 0;
    //----------------im chat--------------end----
    private boolean isShowUpload = false;
    private ChatHotKeyAdapter chatHotKeyAdapter;
    private String openFlag;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "服务与活动成功绑定");
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
            client = jWebSClientService.client;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "服务与活动成功断开");
        }
    };

    private class ChatMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Log.e(TAG, "onReceive() 接收到的客服消息：\n" + message);
            Gson gson = new Gson();
            ChatMessageEntiity chatMessageEntiity = gson.fromJson(message, ChatMessageEntiity.class);
            if (chatMessageEntiity != null) {
                int code = chatMessageEntiity.getCode();
                if (code == 200) {
                    if (chatMessageEntiity.getResData() != null) {
                        ChatMessageEntiity.ResDataBean.ResultBean result = chatMessageEntiity.getResData().getResult();
                        if (!TextUtils.isEmpty(result.getType())) {
                            if (result.getType().equals("login")) { //type （login，send，back，delete,close）
                                Log.e(TAG, "websocket连接状态======>>>>连接已成功");
                                isLogin = true;
                                constrl_disconnection.setVisibility(View.GONE);
                            } else if (result.getType().equals("close")) {//多终端登录会话，会强制断开链接
                                Log.e(TAG, "websocket连接状态======>>>>会话链接已断开，请点击或退出重连！");
                                isLogin = false;
                                ToastUtils.showShort("会话连接已断开，请稍等或退出重连哟！");
                                constrl_disconnection.setVisibility(View.VISIBLE);
                                client.close();
                                try { //确保 websocket 连接已关闭
                                    client.closeBlocking();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return;
                            } else if (result.getType().equals("send")) {//是 send 消息才解析数据
                                if (result.getData() != null) {
                                    ChatMessageEntiity.ResDataBean.ResultBean.DataBean data = result.getData();
                                    if (data != null) {
                                        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
                                        chatMessageResponse.setIs_self(data.getIs_self());
                                        chatMessageResponse.setAvatar(data.getAvatar());
                                        chatMessageResponse.setNickname(data.getNickname());
                                        chatMessageResponse.setMsg_id(data.getMsg_id());
                                        chatMessageResponse.setMsg_type(data.getMsg_type());
                                        chatMessageResponse.setMsg(data.getMsg());
                                        chatMessageResponse.setMsg_time(data.getMsg_time());
                                        chatMessageResponse.setRead(data.getRead());
                                        chatMessageResponse.setStatus(data.getStatus());
//                                        chatMessageResponse.setSendMsg_type("send");
                                        chatMessageList.add(chatMessageResponse);
                                        totalList.clear();
                                        totalList.addAll(chatMessageList);
                                        initChatMsgListView(totalList);
                                    }
                                }
                            } else if (result.getType().equals("back")) {//消息撤回
                                Log.d(TAG, "消息撤回======>>>>");
                                if (result.getData() != null) {
                                    ChatMessageEntiity.ResDataBean.ResultBean.DataBean data = result.getData();
                                    if (data != null) {
                                        int index = getIndex(data);
                                        if (index != -1) {
                                            totalList.get(index).setStatus(1);
                                            initChatMsgListView(totalList);
                                            chatMessageRecyAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }

                            } else if (result.getType().equals("delete")) {//消息删除
                                Log.d(TAG, "消息删除======>>>>");
                                if (result.getData() != null) {
                                    ChatMessageEntiity.ResDataBean.ResultBean.DataBean data = result.getData();
                                    if (data != null) {
                                        int index = getIndex(data);
                                        if (index != -1) {
                                            totalList.remove(index);
                                            initChatMsgListView(totalList);
                                            chatMessageRecyAdapter.notifyDataSetChanged();
                                        }
                                        ToastUtils.showShort("消息已删除");
                                        return;
                                    }
                                }
                            } else {
                                Log.e(TAG, "websocket连接状态======>>>>连接异常");
                            }
                        } else {
                            Log.e(TAG, "websocket连接状态======>>>>连接异常");
                        }
                    }
                } else { // code = 0 消息异常  code= -1  token无效
                    isLogin = false;
                    if (code == -1) {
                        ToastUtils.showShort("抱歉，您与服务器会话连接已断开，请退出重连！");
                        constrl_disconnection.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        ToastUtils.showShort(chatMessageEntiity.getMsg());
                        return;
                    }
                }
            } else {
                ToastUtils.showShort("消息发送异常！！");
                return;
            }
        }
    }

    private int getIndex(ChatMessageEntiity.ResDataBean.ResultBean.DataBean data) {
        String msg_id = data.getMsg_id();
        int index = -1;
        for (int i = 0; i < totalList.size(); i++) {
            if (totalList.get(i).getMsg_id().equals(msg_id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.actiivty_open_chat;
    }

    @Override
    public int initVariableId() {
        return BR.openChatVM;
    }

    @Override
    public OpenChatViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(OpenChatViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.setFlag(openFlag);
        viewModel.initToolBar(getResources().getString(R.string.client_chat));
        viewModel.setBinding(mContext, binding);

        startJWebSClientService();  //开启服务
        bindService();    //绑定服务
        doRegisterReceiver();   //注册广播
        checkNotification(mContext);   //检测通知是否开启

        initView();
        initClickEvent();
        initMallRefresh(); //配置刷新
        if (openFlag != null) {
            if (openFlag.equals("jpush")) {
                cons_top_shop.setVisibility(View.GONE);
                viewModel.initToolBar(name);
            } else if (openFlag.equals("message_center")) {
                cons_top_shop.setVisibility(View.GONE);
                viewModel.initToolBar(name);
            } else if (openFlag.equals("goods")) {
                cons_top_shop.setVisibility(View.VISIBLE);
                if (shop_info!=null){
                    viewModel.initToolBar(shop_info.getName());
                }else {
                }
            } else {
                cons_top_shop.setVisibility(View.GONE);
            }
        }
        requestChatHistoryData(viewModel.getToken(), ltype,chat_id, page, msg_id,pagesize);        //获取历史咨询记录

        setHotKeywordsData();    //处理热词
        //========通讯前必须创建会话链接==========
        new Handler().postDelayed(new Runnable() {  //开启服务才能通过，延迟下等service 创建完成，才能会话
            @Override
            public void run() {
                if (client != null && client.isOpen()) {
                    //已登录连接，无需重复登录（ 切记！！！！重复登录会 返回 close 断开连接类型，被迫下线  ）
                    if (isLogin) {
                    } else {
                        mIsLogin();
                    }
                } else {
                    Log.e(TAG, "client isOpen() ------>>> 连接已断开，请稍等或重启App哟");
                    return;
                }
            }
        }, 500);
    }


    /**
     * 获取历史咨询记录
     *
     * @param token
     * @param receive_id
     * @param page
     * @param pagesize
     */
    private void requestChatHistoryData(String token, String ltype,String receive_id, int page, String msg_id,int pagesize) {
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        Map<String, String> params = new HashMap<>();

        HashMap<String, String> otherParams = new HashMap<>();
        otherParams.put("receive_id", receive_id);
        otherParams.put("ltype", ltype);
        otherParams.put("page", String.valueOf(page));
        Log.d(TAG, "page------->>" + page);
        otherParams.put("msg_id",msg_id);
        Log.d(TAG, "msg_id------->>" + msg_id);
        otherParams.put("pagesize", String.valueOf(pagesize));
        String postBodyString = ParameterToJsonUtils.createJsonString(token, AppApplication.myDeviceID, otherParams);
        String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
        String sign = ParameterToJsonUtils.encrypSign(postBodyString);

        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);
        OkHttpUtils.post()
                .url(RetrofitClient.baseUrl + Constants.URL.chat_history)
                .params(params)
                .headers(headers)
                .build()
                .execute(new MyStringCallback(binding.refreshLayout));
    }

    public class MyStringCallback extends StringCallback {
        private SmartRefreshLayout smartRefreshLayout;

        public MyStringCallback(SmartRefreshLayout refresh) {
            this.smartRefreshLayout = refresh;
        }

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
            smartRefreshLayout.finishRefresh();
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "获取咨询历史数据 onResponse：complete" + response);
            Gson gson = new Gson();
            ChatHistoryModel chatHistoryModel = gson.fromJson(response, ChatHistoryModel.class);
            if (chatHistoryModel != null) {
                int code = chatHistoryModel.getCode();
                if (code == 200) {
                    String resJson = new String(RSAUtils2.decode(chatHistoryModel.getResData()));
                    if (StringUtils.isEmpty(resJson)) {
                        ToastUtils.showShort("解密异常");
                        return;
                    }
                    boolean sign = RSASignature.doCheck(resJson, chatHistoryModel.getSign());
                    if (!sign) {
                        ToastUtils.showShort("签名异常");
                        return;
                    }
                    KLog.d(TAG, "获取咨询历史 解密后数据：" + resJson);
                    //==========解密后真正需要的数据实体=======start======
                    ChatHistoryEntity chatHistoryEntity = new Gson().fromJson(resJson, ChatHistoryEntity.class);
                    if (chatHistoryEntity != null) {
                        if (chatHistoryEntity.getResult() != null) {
                            if (chatHistoryEntity.getResult().getData() != null) {
                                List<ChatHistoryEntity.ResultBean.DataBean> data = chatHistoryEntity.getResult().getData();
                                if (data.size() > 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
                                        chatMessageResponse.setIs_self(data.get(i).getIs_self());
                                        chatMessageResponse.setAvatar(data.get(i).getAvatar());
                                        chatMessageResponse.setNickname(data.get(i).getNickname());
                                        chatMessageResponse.setMsg_id(data.get(i).getMsg_id());
                                        chatMessageResponse.setMsg_type(data.get(i).getMsg_type());
                                        chatMessageResponse.setMsg(data.get(i).getMsg());
                                        chatMessageResponse.setMsg_time(data.get(i).getTime());
                                        chatMessageResponse.setRead(data.get(i).getRead());
                                        chatMessageResponse.setStatus(data.get(i).getStatus());
                                        chatMessageList.add(chatMessageResponse);
                                    }
//                                    initChatMsgListView(chatMessageList);
                                    if (page == 1) {
                                        totalList.clear();
                                        totalList.addAll(chatMessageList);
                                        initChatMsgListView(totalList);
                                    } else {
                                        if (page > 1) {
                                            totalList.clear();
                                            totalList.addAll(chatMessageList);
                                            //根据 msg_id 从小到大排序显示
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                totalList.sort((x, y) -> Double.compare(Double.parseDouble(x.getMsg_id()),
                                                        Double.parseDouble(y.getMsg_id())));
                                            }
                                            ArrayList newList = mRemoveDuplicates(totalList);
                                            initChatMsgListView(newList);

                                        }
                                    }
                                } else {
                                    if (page == 1) {
                                        mBackResultNull(data, page);
                                    } else {
                                        ToastUtils.showShort("没有更多数据啦！");
                                        return;
                                    }
                                }
                            } else {
                                ToastUtils.showShort("获取聊天消息异常");
                                return;
                            }
                        }
                    }
                    smartRefreshLayout.finishRefresh();
                    //==========解密后真正需要的数据实体=========end======
                } else {
                    ToastUtils.showShort(chatHistoryModel.getMsg());
                    smartRefreshLayout.finishRefresh();
                    return;
                }
            }

        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
            page--;
            smartRefreshLayout.finishRefresh();
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            smartRefreshLayout.finishRefresh();
        }
    }


    /**
     *  去除重复的自定义对象
     * @param totalList
     */
    private ArrayList mRemoveDuplicates(List<ChatMessageResponse> totalList) {
        List list = new ArrayList();
        Iterator it = totalList.iterator();
        while (it.hasNext()) {
            Object obj = (Object) it.next();
            if(!list.contains(obj)){  //不包含就添加
                list.add(obj);
            }
        }
        return (ArrayList) list;
    }

    /**
     * 接口返回数据为null  或者 空[] 处理
     *
     * @param data
     * @param page
     */
    private void mBackResultNull(List<ChatHistoryEntity.ResultBean.DataBean> data, int page) {
        if (page == 1 && data.size() == 0) {
            ToastUtils.showShort(R.string.chat_empty_text);
        }
    }


    /**
     * 发送 热词关键字
     */
    private void setHotKeywordsData() {
//        if (openFlag.equals("jpush") || openFlag.equals("message_center")){
//            listTitle = Arrays.asList(getResources().getStringArray(R.array.chat_keywords_txt_no_goods));
//        }else {
//            listTitle = Arrays.asList(getResources().getStringArray(R.array.chat_keywords_txt));
//        }
        listTitle = Arrays.asList(getResources().getStringArray(R.array.chat_keywords_txt_no_goods));
        chatHotKeyAdapter = new ChatHotKeyAdapter(mContext, listTitle, R.layout.item_rv_chat_hot_keywords);
        rv_hot_keyword.setAdapter(chatHotKeyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_hot_keyword.setLayoutManager(linearLayoutManager);
        chatHotKeyAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        constrl_disconnection = binding.getRoot().findViewById(R.id.constrl_disconnection);
        tv_reconnect = binding.getRoot().findViewById(R.id.tv_reconnect);

        rv_hot_keyword = binding.getRoot().findViewById(R.id.rv_hot_keyword);
        cons_top_shop = binding.getRoot().findViewById(R.id.cons_top_shop);
        iv_thumb = binding.getRoot().findViewById(R.id.iv_thumb);
        tv_shop_title = binding.getRoot().findViewById(R.id.tv_shop_title);
        tv_price = binding.getRoot().findViewById(R.id.tv_price);
        tv_add_shopcar = binding.getRoot().findViewById(R.id.tv_add_shopcar);
        tv_buy_shop = binding.getRoot().findViewById(R.id.tv_buy_shop);
        tv_sendshop_link = binding.getRoot().findViewById(R.id.tv_sendshop_link);


        btn_send = binding.getRoot().findViewById(R.id.btn_send);
        iv_upload = binding.getRoot().findViewById(R.id.iv_upload);
        et_content = binding.getRoot().findViewById(R.id.et_content);

        rl_upload = binding.getRoot().findViewById(R.id.rl_upload);
        iv_emoji = findViewById(R.id.iv_emoji);

        iv_camera = binding.getRoot().findViewById(R.id.iv_camera);
        tv_camera = binding.getRoot().findViewById(R.id.tv_camera);
        iv_album = binding.getRoot().findViewById(R.id.iv_album);
        tv_album = binding.getRoot().findViewById(R.id.tv_album);

        lin_bottom = binding.getRoot().findViewById(R.id.lin_bottom);

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        openFlag = bundle.getString(Constants.SP.ACTIVITY_OPEN_FLAG);
        if (openFlag != null) {
            if (openFlag.equals("jpush")) {
                name = bundle.getString(Constants.GOODS_KEY_CHAT_NAME);
                chat_id = bundle.getString(Constants.GOODS_KEY_CHAT_ID);
                ltype = bundle.getString(Constants.GOODS_KEY_CHAT_LTYPE);
            } else if (openFlag.equals("message_center")) {
                name = bundle.getString(Constants.GOODS_KEY_CHAT_NAME);
                chat_id = bundle.getString(Constants.GOODS_KEY_CHAT_ID);
                ltype = bundle.getString(Constants.GOODS_KEY_CHAT_LTYPE);
            } else if (openFlag.equals("goods")) {
                name = bundle.getString(Constants.GOODS_KEY_CHAT_NAME);
                gid = bundle.getString(Constants.GOODS_KEY_GID);
                Log.d(TAG, "商品id:===>>>" + gid);
                chat_id = bundle.getString(Constants.GOODS_KEY_CHAT_ID);
                ltype = bundle.getString(Constants.GOODS_KEY_CHAT_LTYPE);
                if (!TextUtils.isEmpty(gid)) {
                    viewModel.requestGoodsDetails(gid);  //请求商品详情页数据
//                  viewModel.requestGoodsSpce(gid);      //请求规格数据
                    viewModel.setGid(gid);
                } else {
                }
            }else if (openFlag.equals("order_detail")){
                name = bundle.getString(Constants.GOODS_KEY_CHAT_NAME);
                chat_id = bundle.getString(Constants.GOODS_KEY_CHAT_ID);
                ltype = bundle.getString(Constants.GOODS_KEY_CHAT_LTYPE);
            }else {
            }
            Log.d(TAG, " 消息所有者ID:===>>>" + chat_id);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mGoodsLiveData.observe(this, new Observer<GoodsEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable GoodsEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    shareinfoBean = resultBean.getShareinfo();
                    shop_info = resultBean.getShop_info();
                    if (shareinfoBean == null) return;
                    Log.d(TAG, "shareinfoBean:===>>>" + shareinfoBean.toString());
                    price = resultBean.getShow_price();
                    Glide.with(mContext).load(shareinfoBean.getImgUrl()).into(iv_thumb);
                    tv_shop_title.setText(shareinfoBean.getTitle());
                    tv_price.setText("¥" + price);
                }
            }
        });

        viewModel.uc.showSpecPopwindow.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String type) {
                showPopwindow(type);
            }
        });

        viewModel.uc.closePopwindow.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (popupWindow != null && popupWindow.isShowing())
                    popupWindow.dismiss();
            }
        });

    }

    private void initChatMsgListView(List<ChatMessageResponse> chatMessageList) {
        /*listview*/
//        adapter_chatMessage = new ChatMessageAdapter(mContext, chatMessageList);
//        listView.setAdapter(adapter_chatMessage);
//        listView.setSelection(chatMessageList.size());
        /*recycleview*/
        chatMessageRecyAdapter = new ChatMessageRecyAdapter(mContext,
                chatMessageList);
        binding.rvc.setAdapter(chatMessageRecyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvc.setLayoutManager(linearLayoutManager);
        if (page == 1) {
            binding.rvc.scrollToPosition(chatMessageRecyAdapter.getItemCount() - 1);
        }
        chatMessageRecyAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "您点击了" + position + "行");
        int msg_type = totalList.get(position).getMsg_type();
        if (msg_type == 2) { //发送商品 点击事件
            if (!TextUtils.isEmpty(gid)) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, gid);
                startActivity(GoodsActivity.class, bundle);
            }
        } else {
        }

    }

    @Override
    public void onLongClick(int position) {
        Log.d(TAG, "您长按了" + position + "行");
        showChatMessageDialogManager(position);
    }


    /**
     * 聊天消息管理 弹窗
     *
     * @param position
     */
    private void showChatMessageDialogManager(int position) {
        try{
            NiceDialog.init()
                    .setLayoutId(R.layout.dialog_chat_message_manager)
                    .setConvertListener(new ViewConvertListener() {
                        @Override
                        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                            setListener(holder, dialog, position);
                        }
                    })
//                .setGravity(Gravity.BOTTOM)
                    .setWidth(200)
                    .setDimAmount(0.5f)//调节灰色背景透明度[0-1]，默认0.5f
                    .setShowBottom(false)
                    .show(getSupportFragmentManager());
        }catch (IllegalStateException ignore){
            ignore.printStackTrace();
        }
    }

    private void setListener(ViewHolder holder, BaseNiceDialog dialog, int position) {
        //删除
        holder.setOnClickListener(R.id.tv_msg_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.showCancelAndSureDialog(mContext, "提示", "确定要删除吗？",
                        R.color.textColor, R.color.textColor,
                        new MessageDialog.DialogClick() {
                            @Override
                            public void onSureClickListener() {
                                String msg_id = totalList.get(position).getMsg_id();
                                if (!TextUtils.isEmpty(msg_id)) {
                                    mDeleteChatTxtMessage(msg_id);
                                } else {
                                    Log.e(TAG, " delet  msg_id:----->>" + msg_id);
                                }
                            }

                            @Override
                            public void onCancelClickListener() {
                            }
                        });
                dialog.dismiss();
            }
        });
        //撤回
        int isMeSend = totalList.get(position).getIs_self();
        if (isMeSend == 1) { //消息是自己 才能撤回
            holder.getView(R.id.tv_msg_back).setVisibility(View.VISIBLE);
            holder.setOnClickListener(R.id.tv_msg_back, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg_id = totalList.get(position).getMsg_id();
                    if (!TextUtils.isEmpty(msg_id)) {
                        mBackChatTxtMessage(msg_id);
                    } else {
                        Log.e(TAG, " back msg_id:----->>" + msg_id);

                    }
                    dialog.dismiss();
                }
            });
        } else {
            holder.getView(R.id.tv_msg_back).setVisibility(View.GONE);
        }

    }


    /**
     * 首次登录，需要判断 websocket 是否正常建立消息连接
     */
    private void mIsLogin() {
        ChatMessage ChatMessage = new ChatMessage();
        mCommonParams(ChatMessage);

        ChatMessage.setType("login");
        ChatMessage.setLtype(ltype);
        ChatMessage.setReceive_id(chat_id);
        Gson gson = new Gson();
        String message = gson.toJson(ChatMessage);
        Log.e(TAG, "websocket login－－－－－＞＞" + message);
        jWebSClientService.sendMsg(message);
    }


    /**
     * 启动服务（websocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(mContext, JWebSocketClientService.class);
//        startService(intent);
        //Android 8.0 的应用尝试在不允许其创建后台服务的情况下使用 startService() 函数
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(mContext, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 动态注册广播
     */
    private void doRegisterReceiver() {
        chatMessageReceiver = new ChatMessageReceiver();
        IntentFilter filter = new IntentFilter("com.xch.servicecallback.content");
        registerReceiver(chatMessageReceiver, filter);
    }


    private void initClickEvent() {
        tv_reconnect.setOnClickListener(this);
        tv_add_shopcar.setOnClickListener(this);
        tv_buy_shop.setOnClickListener(this);
        tv_sendshop_link.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        iv_upload.setOnClickListener(this); //上传
        iv_camera.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        iv_album.setOnClickListener(this);
        tv_album.setOnClickListener(this);
        iv_emoji.setOnClickListener(this); //表情
        et_content.setOnClickListener(this);
        et_content.addTextChangedListener(new TextWatcher() {     //监听输入框的变化
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_content.getText().toString().length() > 0) {
                    btn_send.setVisibility(View.VISIBLE);
                } else {
                    btn_send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //点击输入框外部 隐藏软键盘
//        binding.rvc.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                SystemUtils.hideSoftKeyboard(mContext, et_content);
//                rl_upload.setVisibility(View.GONE);
//                return true;
//            }
//        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reconnect: //点击重连
                startJWebSClientService();  //开启服务
                bindService();    //绑定服务
                doRegisterReceiver();   //注册广播
                checkNotification(mContext);   //检测通知是否开启
                new Handler().postDelayed(new Runnable() {  //开启服务才能通过，延迟下等service 创建完成，才能会话
                    @Override
                    public void run() {
                        if (client != null && client.isOpen()) {
                            if (isLogin) {
                            } else {
                                mIsLogin();
                            }
                        } else {
                            Log.e(TAG,"client isOpen() ------>>> 连接已断开，请稍等或重启App哟");
                            return;
                        }
                    }
                }, 500);
                break;
            case R.id.tv_add_shopcar: //加入购物车
                mAddShopCar();
                break;
            case R.id.tv_buy_shop: //立即购买
                //隐藏软键盘
                SystemUtils.hideSoftKeyboard(mContext, et_content);
                mBuyNow();
                break;
            case R.id.tv_sendshop_link: //发送商品链接
                if (client != null && client.isOpen()) {
                    if (isLogin) {
                        int msgType = 2;
                        mSendSChatShopMessage(msgType, gid);
                    } else {
                        mIsLogin();
                        int msgType = 2;
                        mSendSChatShopMessage(msgType, gid);
                    }
                } else {
                    Log.e(TAG, "client isOpen() ------>>> 连接已断开，请稍等或重启App哟");
                }
                break;
            case R.id.iv_emoji: //表情
                ToastUtils.showShort(R.string.in_development);
                break;
            case R.id.btn_send: //发送文本消息
                String content = et_content.getText().toString();
                if (content.length() <= 0) {
                    ToastUtils.showShort("消息不能为空哟");
                    return;
                }
                if (client != null && client.isOpen()) {
                    if (isLogin) {
                        int msgType = 0;
                        mSendChatTxtMessage(msgType, content);
                    } else {
                        mIsLogin();
                        int msgType = 0;
                        mSendChatTxtMessage(msgType, content);
                    }
                } else {
                    Log.e(TAG, "client isOpen() ------>>> 连接已断开，请稍等或重启App哟");
                }
                break;
            case R.id.et_content:
                rl_upload.setVisibility(View.GONE);
                SystemUtils.showSoftKeyboard(OpenChatActivity.this, et_content);
                break;
            case R.id.iv_upload: // + 上传
                if (!isShowUpload) {
                    isShowUpload = true;
                    rl_upload.setVisibility(View.VISIBLE);
                    //隐藏软键盘
                    SystemUtils.hideSoftKeyboard(mContext, et_content);
                    rv_hot_keyword.setVisibility(View.GONE);
                } else {
                    isShowUpload = false;
                    rl_upload.setVisibility(View.GONE);
//                    rv_hot_keyword.setVisibility(View.VISIBLE);
                    rv_hot_keyword.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_camera: //相机
            case R.id.tv_camera:
                mTakePicture();
                break;
            case R.id.iv_album: //相册
            case R.id.tv_album:
                mPhotoAlbumSelection();
                break;
            default:
                break;
        }

    }

    /**
     * 立即购买
     */
    private void mBuyNow() {
        if (viewModel.entity.get() == null) {
            return;
        }
        viewModel.specs_id.clear();
        viewModel.spec.clear();
        viewModel.myTotal.set(1);
        viewModel.setDefault();
        if (TextUtils.isEmpty(viewModel.getToken())) {
            startActivity(LoginVertifyActivity.class);
            return;
        }
//        viewModel.uc.showSpecPopwindow.setValue(Constants.GOODS_KEY_BUYNOW);
        showPopwindow(Constants.GOODS_KEY_BUYNOW);
    }

    /**
     * 加入购物车
     */
    private void mAddShopCar() {
        if (viewModel.entity.get() == null) {
            return;
        }
        viewModel.myTotal.set(1);
        viewModel.specs_id.clear();
        viewModel.spec.clear();
        viewModel.setDefault();
        if (TextUtils.isEmpty(viewModel.getToken())) {
            startActivity(new Intent(mContext, LoginVertifyActivity.class));
            return;
        }
        if (viewModel.entity.get() != null) {
            if (viewModel.entity.get().getAdd_cart() == 0) {
                ToastUtils.showShort("此商品不能加入购物车");
                return;
            }
            if (viewModel.entity.get().getSpec_titles() != null & viewModel.entity.get().getSpec_titles().size() > 0) {
                showPopwindow(Constants.GOODS_KEY_SHOPPING_CAR);
            } else {//规格集合 参数为空
                Log.d(TAG, "商品 规格集合 为空！");
                viewModel.requestAddShoppingcar();
            }
        } else {
            Log.e(TAG, "商品加入购物车出错！");
        }
    }


    public void showPopwindow(String type) {
        View root = initPopwindowLayout(type);
//        marginHeight(root);
//        popupWindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screen[1]*.85));
        popupWindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.myPopwindowAnimStyle);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
//                ((Activity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
//        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        if (Build.VERSION.SDK_INT < 24) {
            //nexus5 不显示
            if (Build.MODEL.equals("Nexus 5") || Build.VERSION.SDK_INT == 23) {
                popupWindow.showAtLocation(lin_bottom, Gravity.BOTTOM, 0, 0);
            } else {
                popupWindow.showAsDropDown(lin_bottom);
            }
        } else {
            popupWindow.showAtLocation(lin_bottom, Gravity.BOTTOM, 0, 0);
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
//        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //不添加这个偶尔会出现 背景重叠的问题
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    /**
     * 显示popupWindow
     */
    public View initPopwindowLayout(String type) {
        //加载弹出框的布局
        if (type.equals(Constants.GOODS_KEY_SPEC) ||
                type.equals(Constants.GOODS_KEY_BUYNOW) ||
                type.equals(Constants.GOODS_KEY_SHOPPING_CAR)) {
            return initSpecLayout(type);
        }
        return null;
    }


    //规格
    private View initSpecLayout(String type) {
        final PoplayoutSpecChatBinding popBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.poplayout_spec_chat, null, false);
        popBinding.setSpecVM(viewModel);
        popBinding.setSpecEntity(viewModel.specEntity.get());
        PopGoodsSpecAdapterChat adapter = new PopGoodsSpecAdapterChat();
        popBinding.setAdapter(adapter);
        adapter.setOnitemClick(new PopGoodsSpecAdapterChat.OnitemClick() {
            @Override
            public void OnitemClick() {
                viewModel.updateShop();
            }
        });

        popBinding.pgClosePopwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        /***========================页面逻辑处理===*/
        if (type.equals(Constants.GOODS_KEY_SPEC)) {
            popBinding.psAddShoppingcar.setVisibility(View.VISIBLE);
            popBinding.psBuynow.setVisibility(View.VISIBLE);
        } else if (type.equals(Constants.GOODS_KEY_BUYNOW)) {
            popBinding.psAddShoppingcar.setVisibility(View.GONE);
            popBinding.psBuynow.setText("确定");
        } else if (type.equals(Constants.GOODS_KEY_SHOPPING_CAR)) {
            popBinding.psAddShoppingcar.setVisibility(View.VISIBLE);
            popBinding.psAddShoppingcar.setText("确定");
            popBinding.psAddShoppingcar.setBackgroundColor(getResources().getColor(R.color.colorred));
            popBinding.psBuynow.setVisibility(View.GONE);
        }
        /***========================页面逻辑处理===*/
        return popBinding.getRoot();
    }


    /**
     * 向 websocket 发送 商品消息
     *
     * @param msgType
     * @param gid
     */
    private void mSendSChatShopMessage(int msgType, String gid) {
        ChatMessage ChatMessage = new ChatMessage();
        // 公共 入参
        mCommonParams(ChatMessage);
        //send 入参
        ChatMessage.setType("send");
        ChatMessage.setLtype(ltype);
        ChatMessage.setReceive_id(chat_id);
        ChatMessage.setMsg_type(msgType);
        ChatMessage.setMsg(gid);

        Gson gson = new Gson();
        String message = gson.toJson(ChatMessage);
        Log.e(TAG, "－－－－－＞＞" + message);
        jWebSClientService.sendMsg(message);
        et_content.setText("");

    }

    /**
     * 相册选图
     */
    private void mPhotoAlbumSelection() {
        choiceImageFromGallery();
    }

    /**
     * 拍照
     */
    private void mTakePicture() {
        requestPermissions();
    }


    /**
     * 向 websocket 发送 图片消息
     *
     * @param msgType
     * @param picLink
     */
    private void mSendChatPicMessage(int msgType, String picLink) {
        ChatMessage ChatMessage = new ChatMessage();
        // 公共 入参
        mCommonParams(ChatMessage);
        //send 入参
        ChatMessage.setType("send");
        ChatMessage.setLtype(ltype);
        ChatMessage.setReceive_id(chat_id);
        ChatMessage.setMsg_type(msgType);
        ChatMessage.setMsg(picLink);

        Gson gson = new Gson();
        String message = gson.toJson(ChatMessage);
        Log.e(TAG, "mSendChatPicMessage－－－－－＞＞" + message);
        jWebSClientService.sendMsg(message);
        et_content.setText("");
    }


    /**
     * 向 websocket 发送 文本消息
     *
     * @param msgType
     * @param content
     */
    private void mSendChatTxtMessage(int msgType, String content) {
        ChatMessage ChatMessage = new ChatMessage();
        // 公共 入参
        mCommonParams(ChatMessage);
        //send 入参
        ChatMessage.setType("send");
        ChatMessage.setLtype(ltype);
        ChatMessage.setReceive_id(chat_id);
        ChatMessage.setMsg_type(msgType); //发送文本
        ChatMessage.setMsg(content);

        Gson gson = new Gson();
        String message = gson.toJson(ChatMessage);
        Log.e(TAG, "mSendChatTxtMessage－－－－－＞＞" + message);
        jWebSClientService.sendMsg(message);
        //暂时将发送的消息加入消息列表，实际以发送成功为准（也就是服务器返回你发的消息时）
//                    ChatMessage chatMessage=new ChatMessage();
//                    chatMessage.setContent(content);
//                    chatMessage.setIsMeSend(1);
//                    chatMessage.setIsRead(1);
//                    chatMessage.setTime(System.currentTimeMillis()+"");
//                    chatMessageList.add(chatMessage);
//                    initChatMsgListView();
        et_content.setText("");
    }

    /**
     * 向 websocket 发送 删除消息
     *
     * @param msg_id
     */
    private void mDeleteChatTxtMessage(String msg_id) {
        ChatMessage ChatMessage = new ChatMessage();
        // 公共 入参
        mCommonParams(ChatMessage);
        //send 入参
        ChatMessage.setType("delete");
        ChatMessage.setMsg_id(msg_id);

        Gson gson = new Gson();
        String message = gson.toJson(ChatMessage);
        Log.e(TAG, "mDeleteChatTxtMessage－－－－－＞＞" + message);
        jWebSClientService.sendMsg(message);
    }

    private void mCommonParams(ChatMessage chatMessage) {
        chatMessage.setUser_token(viewModel.getToken());
        chatMessage.setGuid(AppApplication.myDeviceID);
        chatMessage.setOs("android");
        chatMessage.setVersion("1.0");
    }

    /**
     * 向 websocket 发送 撤回消息
     *
     * @param msg_id
     */
    private void mBackChatTxtMessage(String msg_id) {
        ChatMessage ChatMessage = new ChatMessage();
        // 公共 入参
        mCommonParams(ChatMessage);
        //send 入参
        ChatMessage.setType("back");
        ChatMessage.setMsg_id(msg_id);

        Gson gson = new Gson();
        String message = gson.toJson(ChatMessage);
        Log.e(TAG, "mBackChatTxtMessage－－－－－＞＞" + message);
        jWebSClientService.sendMsg(message);
    }


    @Override
    public void onItemClick(View itemView, int viewType, int position) {
        String userToken = viewModel.getToken();
        if (TextUtils.isEmpty(userToken)) {
            viewModel.startActivity(LoginVertifyActivity.class);
        } else {
            Bundle bundle = new Bundle();
            if (position == 0) { //优惠券
                startContainerActivity(CouponCenterFragment.class.getCanonicalName());
            } else if (position == 1) { //订单查询
                bundle.putInt(Constants.MyOrder, 0);
                startContainerActivity(MineOrderFragment.class.getCanonicalName(), bundle);
            } else if (position == 2) { //店铺上新
                if (shop_info != null) {
                    String id = shop_info.getId();
                    String type = shop_info.getType();
                    String name = shop_info.getName();
                    if (!TextUtils.isEmpty(type)) {
                        if (type.equals("mall")) {//商城
                            startActivity(HomePageActivity.class);
                        } else if (type.equals("merch")) {//商户
                            bundle.putString(Constants.MINE_SHOP, id);
                            bundle.putString("title", name);
                            startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
                        } else if (type.equals("shop")) {//小店
                            bundle.putString(Constants.MINE_SMALL_STORE, id);
                            startActivity(MineShopHomeAty.class);
                        }
                    }
                } else {
                    Log.d(TAG, "shop_info == null");
                }

            } else {
            }
        }

    }


    @Override
    protected void onDestroy() {
        if (chatMessageReceiver != null) {   //取消广播接收器
            unregisterReceiver(chatMessageReceiver);
        }
        unbindService(serviceConnection);
        closeConnect();   //断开websocket通信 服务
        super.onDestroy();
    }


    /**
     * 检测是否开启通知
     *
     * @param context
     */
    private void checkNotification(final Context context) {
        if (!isNotificationEnabled(context)) {
            new AlertDialog.Builder(context).setTitle("温馨提示")
                    .setMessage("你还未开启系统通知，将影响消息的接收，要去开启吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setNotification(context);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
    }

    /**
     * 如果没有开启通知，跳转至设置界面
     *
     * @param context
     */
    private void setNotification(Context context) {
        Intent localIntent = new Intent();
        //直接跳转到应用通知设置的代码：
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            localIntent.putExtra("app_package", context.getPackageName());
            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
        } else {
            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(localIntent);
    }

    /**
     * 获取通知权限,监测是否开启了系统通知
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (!TextUtils.isEmpty(viewModel.getToken())) {
            page++;
            if (chatMessageList!=null && chatMessageList.size() > 0){
                String msg_id = totalList.get(0).getMsg_id();
                requestChatHistoryData(viewModel.getToken(),ltype, chat_id, page, msg_id,pagesize);        //获取历史咨询记录
            }
        } else {
            Log.e(TAG,"userToken：" + viewModel.getToken());
            ToastUtils.showShort("获取聊天信息异常！");
            return;
        }
    }


    private void initMallRefresh() {
        binding.mallRefreshHeader.setEnableLastTime(true);                 //时间显示
        binding.mallRefreshHeader.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshHeader.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshHeader.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.mallRefreshFooter.setTextSizeTitle(13);                    //设置标题文字大小（sp单位）
        binding.mallRefreshFooter.setDrawableSize(16);                     //同时设置箭头和图片的大小（dp单位）
        binding.mallRefreshFooter.setDrawableMarginRight(18);              //设置图片和箭头和文字的间距（dp单位）

        binding.refreshLayout.setHeaderHeight(68);          //设置header高度
        binding.refreshLayout.setFooterHeight(68);          //设置footer高度

        binding.refreshLayout.setEnableRefresh(true);
        binding.refreshLayout.setEnableAutoLoadmore(false);  //开启自动加载功能
        binding.refreshLayout.setOnRefreshListener(this);
//        binding.refreshLayout.setOnLoadmoreListener()
        binding.refreshLayout.setEnableLoadmore(false);
    }


    /**
     * 相册选图/拍照
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    if (data != null) {
                        startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    }
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    //-----------------------------------------拍照/相册选图---------------------------------------------------

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(this, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(this, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1);
            } else {
                takePicture();
            }
        } else {
            takePicture();
        }
    }


    private void choiceImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 10003)) {
                choiceImage();
            }
        } else {
            choiceImage();
        }
    }

    /**
     * 相册选图
     */
    public void choiceImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private boolean checkPermission(String permission, int requestCode) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
            flag = true;
        else
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        return flag;
    }

    /**
     * 拍照
     */
    public void takePicture() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(this, "com.jzd.jzshop.img.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }


    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        String imageName = System.currentTimeMillis() + ".jpg";
        File imageFile = getPicFile(imageName);
        tempUri = Uri.fromFile(imageFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    private File getPicFile(String fileName) {
        File picDir = new File(getExternalFilesDir(null), "pics");
        if (!picDir.exists()) {
            picDir.mkdir();
        }
        return new File(picDir, fileName);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            Log.d("TAG", "setImageToView:" + photo);
//            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
//            imgUserIcon.setImageBitmap(photo);
//            uploadPic(photo);
//        }
        String imagePath = FileUtils.getFilePathByUri(this, tempUri);
        try {
            uploadPic(imagePath);
        } catch (Exception e) {
        }
    }

    private void uploadPic(String imagePath) {
        this.bitmap = bitmap;
        if (imagePath != null) {
            File file = new File(imagePath);
            // TODO: 2020/4/26
//            Glide.with(OpenChatNewActivity.this).load(file).into(iv_avatar);
//            iv_avatar.setImageBitmap(bitmap);
            uploadPic(file, imagePath);
            Log.d("TAG", "imagePath:" + imagePath);
        }
    }

    /**
     * 发送图片，需先上传服务器，将图片链接 通过 websocket 发送
     *
     * @param file
     * @param imagePath
     */
    public void uploadPic(File file, String imagePath) {
        if (!file.exists()) {
            ToastUtils.showShort("文件不存在，请重新选择图片");
            return;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        Map<String, String> params = new HashMap<>();
        String reqData = ParameterToJsonUtils.encrypReqData(viewModel.getToken(), AppApplication.myDeviceID);
        String sign = ParameterToJsonUtils.encrypSign(viewModel.getToken(), AppApplication.myDeviceID);
        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);
        OkHttpUtils.post()
                .addFile("files", file.getName(), file)
                .url(RetrofitClient.baseUrl + Constants.URL.updatePic)
                .params(params)
                .headers(headers)
                .build()
                .execute(new UploadPicCallback(binding.refreshLayout));
    }

    public class UploadPicCallback extends StringCallback {
        private SmartRefreshLayout smartRefreshLayout;

        public UploadPicCallback(SmartRefreshLayout refresh) {
            this.smartRefreshLayout = refresh;
        }

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
            smartRefreshLayout.finishRefresh();
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "uploadPic  websocket 图片上传成功");
            Log.d(TAG, "onResponse：complete" + response);
            Gson gson = new Gson();
            UploadPicModel uploadPicModel = gson.fromJson(response, UploadPicModel.class);
            if (uploadPicModel != null) {
                int code = uploadPicModel.getCode();
                if (code == 200) {
                    String resJson = new String(RSAUtils2.decode(uploadPicModel.getResData()));
                    if (StringUtils.isEmpty(resJson)) {
                        ToastUtils.showShort("解密异常");
                        return;
                    }
                    boolean sign = RSASignature.doCheck(resJson, uploadPicModel.getSign());
                    if (!sign) {
                        ToastUtils.showShort("签名异常");
                        return;
                    }
                    KLog.d(TAG, "websocket 发送的图片链接 解密后数据：" + resJson);
                    //==========解密后真正需要的数据实体=======start======
                    UploadPicResponse uploadPicResponse = new Gson().fromJson(resJson, UploadPicResponse.class);
                    if (uploadPicResponse != null) {
                        KLog.d(TAG, "websocket 发送的图片链接----->>>：" + resJson);
                        if (uploadPicResponse.getResult() != null) {
                            if (!TextUtils.isEmpty(uploadPicResponse.getResult().getFile())) {
                                if (client != null && client.isOpen()) {
                                    if (isLogin) {
                                        String picLink = uploadPicResponse.getResult().getFile();
                                        int msgType = 1;
                                        mSendChatPicMessage(msgType, picLink);
                                    } else {
                                        mIsLogin();
                                        String picLink = uploadPicResponse.getResult().getFile();
                                        int msgType = 1;
                                        mSendChatPicMessage(msgType, picLink);
                                    }
                                } else {
                                    ToastUtils.showShort("连接已断开，请稍等或重启App哟");
                                    return;
                                }
                            } else {
                                ToastUtils.showShort("上传图片异常");
                                return;
                            }
                        }
                    }
                    smartRefreshLayout.finishRefresh();
                    //==========解密后真正需要的数据实体=========end======
                } else {
                    ToastUtils.showShort(uploadPicModel.getMsg());
                    smartRefreshLayout.finishRefresh();
                    return;
                }
            }
            smartRefreshLayout.finishRefresh();

        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
            smartRefreshLayout.finishRefresh();
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            smartRefreshLayout.finishRefresh();
        }
    }


    /**
     * 断开连接
     */
    private void closeConnect() {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
