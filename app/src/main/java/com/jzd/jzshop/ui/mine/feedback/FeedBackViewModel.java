package com.jzd.jzshop.ui.mine.feedback;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityFeedbackBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.byteam.superadapter.OnItemClickListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by lxb on 2020/2/10.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class FeedBackViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = FeedBackViewModel.class.getSimpleName();
    private Context context;
    private ActivityFeedbackBinding mBinding;
    private List<String> dataList;
    private FeedBackQuestAdapter feedBackQuestAdapter;
    private GridImageAdapter adapter;
    private int reasonIndex = 0;//处理选中

    public ObservableField<String> etPhone = new ObservableField<>("");
    public ObservableField<String> etContent = new ObservableField<>("");

    public UIChangeObservable uc = new UIChangeObservable();
    private String questName;

    public class UIChangeObservable {
        public SingleLiveEvent submitLiveData = new SingleLiveEvent<>();
    }

    public FeedBackViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context mContext, ActivityFeedbackBinding binding, GridImageAdapter adapter) {
        this.context = mContext;
        this.mBinding = binding;
        this.adapter = adapter;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }


    /**
     * 设置 反馈问题分类数据
     *
     * @param dataList
     */
    public void setQuestClassData(final List<String> dataList) {
        this.dataList = dataList;
        feedBackQuestAdapter = new FeedBackQuestAdapter(context, dataList, R.layout.item_rv_feedback_quest);
        mBinding.rvQuestClassific.setAdapter(feedBackQuestAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        mBinding.rvQuestClassific.setLayoutManager(gridLayoutManager);
        feedBackQuestAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {//类型
                feedBackQuestAdapter.setSelectedPosition(position);     //选中，设置背景颜色
                questName = dataList.get(position);
            }
        });

    }

    public void requestData() {

    }

    //选择原因
    public BindingCommand onClikcReason = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2020/2/13 模拟数据
            List<String> data_reason = Arrays.asList(context.getResources().getStringArray(R.array.assets_record_tab_txt));
            MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                    .title("选择原因")
                    .positiveText(null)
                    .items(data_reason)
                    .itemsCallbackSingleChoice(reasonIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            Log.d(TAG, " 意见反馈原因 onSelection:" + text + "which:" + which);
                            reasonIndex = which;
                            mBinding.tvReason.setText(text);
                            return false;
                        }
                    });
            builder.titleGravity(GravityEnum.CENTER);
            builder.widgetColor(context.getResources().getColor(R.color.colorPrimary));
            builder.build().show();
        }
    });

    public BindingCommand onClikcSubmit = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String phone = etPhone.get();
            String content = etContent.get();
//            String reason = mBinding.tvReason.getText().toString().trim();
           if (TextUtils.isEmpty(phone)) {
               ToastUtils.showShort(R.string.importmobile);
               return;
            } else{
               boolean mobileNO = SystemUtils.isMobileNO(phone);
               if(!mobileNO) {
                   ToastUtils.showLong("请填写正确的手机号");
                   return;
               }
           }
//           if (TextUtils.isEmpty(content)) {
//               ToastUtils.showShort("请输入您的问题");
//               return;
//            } else { }
            postSubmit(questName, phone, content, adapter);
        }
    });


    /**
     * 提交意见反馈
     *
     * @param questName
     * @param phone
     * @param content
     * @param adapter
     */
    private void postSubmit(String questName, String phone, String content,/* String reason,*/ GridImageAdapter adapter) {
        Map<String, String> params = new HashMap<>();
        List<LocalMedias> list = adapter.getList();
        HashMap<String, String> otherParams = new HashMap<>();
        otherParams.put("name", questName);
        otherParams.put("mobile", phone);
        otherParams.put("remark", content);
//        otherParams.put("reason", reason);
        otherParams.put("imageaddimage", adapter.httpImg());
        String postBodyString = ParameterToJsonUtils.createJsonString(model.getUserToken(), AppApplication.myDeviceID,
                otherParams);
        String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
        String sign = ParameterToJsonUtils.encrypSign(postBodyString);
        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);

        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                LocalMedias localMedias = list.get(i);
                if (localMedias.getFile() != null) {
                    postFormBuilder.addFile("image_" + (i + 1), localMedias.getFile().getName(), localMedias.getFile());
                }
            }
        }
        postFormBuilder
                .addHeader("Content-Type", "multipart/form-data;")
                .url(RetrofitClient.baseUrl + Constants.URL.feedback_submit)
                .params(params)
                .build()
                .execute(new FeedBackViewModel.MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
            Log.d(TAG, "loading...:");
        }

        @Override
        public void onAfter(int id) {
            Log.d(TAG, "Sample-okHttp:");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.d(TAG, "onResponse：complete" + response);
            ToastUtils.showShort("提交成功！");
            uc.submitLiveData.setValue(response);
            startContainerActivity(FeedBackSuccessFragment.class.getCanonicalName());
            finish();
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Log.d(TAG, "onError():" + e.getMessage());
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
