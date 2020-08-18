package com.jzd.jzshop.ui.home.creditsstore.comment;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCreditCommentBinding;
import com.jzd.jzshop.entity.CreditCommentEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LXB
 * @description:
 * @date :2020/5/15 9:39
 */
public class CreditCommentViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CreditCommentViewModel.class.getSimpleName();
    public Context context;
    private ActivityCreditCommentBinding activityCreditCommentBinding;
    //-----------------评价----------start-------
    public CreditCommentActivity commentAty;
    private String log_id, wholeSheetTxt;
    private float wholeSheetRating;
    private GridImageAdapter gridImageAdapter;
    public ObservableField<String> position = new ObservableField<>();
    private GridImageAdapter adapter;
    //-----------------评价-----------end------
    public ObservableField<CreditCommentEntity> comment = new ObservableField<>();
    public HashMap<String, List<LocalMedias>> updater = new HashMap<>();//商品的图片
    public HashMap<String, String> content = new HashMap<>();//商品的评论
    public ObservableList<LocalMedias> picture = new ObservableArrayList<>();
    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<CreditCommentEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent submitEvaluationLiveData = new SingleLiveEvent<>();
    }

    public void setBinding(Context context, ActivityCreditCommentBinding binding, CreditCommentActivity creditCommentActivity) {
        this.context = context;
        this.activityCreditCommentBinding = binding;
        this.commentAty = creditCommentActivity;
    }

    public CreditCommentViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
    }


    public void setParamsValue(String wholeSheetTxt) {
        this.wholeSheetTxt = wholeSheetTxt;
    }

    public void setParamsValueStarClass(float wholeSheetRating) {
        this.wholeSheetRating = wholeSheetRating;
    }

    public void notifyRecycle(String positi) {
        if (updater.size() != 0 && updater.get(positi) != null) {
            gridImageAdapter.setList(updater.get(positi));
            gridImageAdapter.notifyDataSetChanged();
        }
    }

    public void setGridImageAdapter(String position, GridImageAdapter gridImageAdapter) {
        this.gridImageAdapter = gridImageAdapter;
        this.position.set(position);
    }

    public void setAdapter(GridImageAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 显示评价
     *
     * @param log_id
     * @param merch_id
     */
    public void requestData(String log_id, String merch_id) {
        this.log_id = log_id;
        isShowDialog(true);
        addSubscribe(model.postShowCreditComment(model.getUserToken(), log_id, merch_id), new ParseDisposableTokenErrorObserver<CreditCommentEntity>() {
            @Override
            public void onResult(CreditCommentEntity creditCommentEntity) {
                super.onResult(creditCommentEntity);
                comment.set(creditCommentEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (creditCommentEntity != null) {
                    CreditCommentEntity.ResultBean result = creditCommentEntity.getResult();
                    if (result != null) {
                        uc.mLiveData.setValue(result);
                    }
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }
        });
    }

    /**
     * 评价提交
     *
     * @param log_id
     */
    public void submitCommentData(String log_id, String wholeSheetTxt) {
        Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
        HashMap<String, String> parames = new HashMap<>();
        parames.put("log_id", log_id);
        parames.put("level", String.valueOf(wholeSheetRating));//评价的评分
        parames.put("content", wholeSheetTxt); //单评价内容
        parames.put("merch_id", "");

        if (content.size() != 0) {
            for (HashMap.Entry<String, String> entry : content.entrySet()) {
                String key = entry.getKey();
                String value1 = entry.getValue();
                parames.put(key, value1);
            }
        }

        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        //总评价的图片
        List<LocalMedias> list = adapter.getList();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                LocalMedias localMedias = list.get(i);
                if (localMedias.getFile() != null) {
                    /*所有的商品的总评价*/
                    KLog.a("商品的总评价=====>>>>>>    " + localMedias.getFile().getPath());
                    postFormBuilder.addFile("image_0" + "_" + i, localMedias.getFile().getName(), localMedias.getFile());
                }
            }
        }
        String jsonString = ParameterToJsonUtils.createJsonString(model.getUserToken(), AppApplication.myDeviceID, parames);
        String data = ParameterToJsonUtils.encrypReqData(jsonString);
        String sign = ParameterToJsonUtils.encrypSign(jsonString);
        HashMap<String, String> parmes = new HashMap<>();
        parmes.put(EncryptInterceptor.KEY_REQ_DATA, data);
        parmes.put(EncryptInterceptor.KEY_SIGN, sign);
//        File file = new File("imagePath");
//        OkHttpUtils.post()
//                .addFile("avatar", "file.getName()", file)
        postFormBuilder
                .addHeader("Content-Type", "multipart/form-data;")
                .url(RetrofitClient.baseUrl + Constants.URL.submit_comment_credit)
                .params(parmes)
                .headers(headers)
                .build()
                .execute(new MyStringCallback());
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
            uc.submitEvaluationLiveData.setValue(response);
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
