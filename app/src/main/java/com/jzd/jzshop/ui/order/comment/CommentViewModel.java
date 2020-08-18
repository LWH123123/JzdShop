package com.jzd.jzshop.ui.order.comment;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityCommentBinding;
import com.jzd.jzshop.entity.CommentEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LWH
 * @description:
 * @date :2020/1/16 9:52
 */
public class CommentViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CommentViewModel.class.getSimpleName();
    public Context context;
    private ActivityCommentBinding fragmentAfterSaleBinding;
    private String order_id, wholeSheetTxt, singleGoodsTxt;
    private float wholeSheetRating, singleStarClass;
    public CommentAty commentAty;

    private GridImageAdapter gridImageAdapter;
    public ObservableField<String> position = new ObservableField<>();

    public void setGridImageAdapter(String position, GridImageAdapter gridImageAdapter) {
        this.gridImageAdapter = gridImageAdapter;
        this.position.set(position);
    }

    public ObservableField<CommentEntity> comment = new ObservableField<>();
    public HashMap<String, List<LocalMedias>> updater = new HashMap<>();//商品的图片
    public HashMap<String, String> content = new HashMap<>();//商品的评论
    public ObservableList<LocalMedias> picture = new ObservableArrayList<>();

    public void notifyRecycle(String positi) {
        if (updater.size() != 0 && updater.get(positi) != null) {
            gridImageAdapter.setList(updater.get(positi));
            gridImageAdapter.notifyDataSetChanged();
        }
    }

    //绑定数据
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<CommentEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent submitEvaluationLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent deleteOrderLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent confirmReceiptLiveData = new SingleLiveEvent<>();

    }

    public CommentViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private GridImageAdapter adapter;

    public void setBinding(Context context, ActivityCommentBinding binding, CommentAty commentAty) {
        this.context = context;
        this.fragmentAfterSaleBinding = binding;
        this.commentAty = commentAty;
    }

    public void setAdapter(GridImageAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);

    }

    public void setParamsValue(String wholeSheetTxt) {
        this.wholeSheetTxt = wholeSheetTxt;
    }

    public void setParamsSingleGoodsTxt(String singleGoodsTxt) {
        this.singleGoodsTxt = singleGoodsTxt;
    }

    public void setParamsValueStarClass(float wholeSheetRating) {
        this.wholeSheetRating = wholeSheetRating;
    }

    public void setParamsValueSingleStarClass(float singleStarClass) {
        this.singleStarClass = singleStarClass;
    }

    /**
     * 显示评价
     *
     * @param order_id
     */
    public void requestData(String order_id) {
        this.order_id = order_id;
        isShowDialog(true);
        addSubscribe(model.postShowCommentData(model.getUserToken(), order_id), new ParseDisposableTokenErrorObserver<CommentEntity>() {
            @Override
            public void onResult(CommentEntity commentEntity) {
                super.onResult(commentEntity);
                comment.set(commentEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (commentEntity != null) {
                    CommentEntity.ResultBean result = commentEntity.getResult();
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
     * @param order_id
     */
    public void submitCommentData(String order_id, String wholeSheetTxt) {
        /*if(wholeSheetRating>-0.000001&&wholeSheetRating<0.000001){
            ToastUtils.showLong("请选择星级评分!");
            return;
        }*/

            Map<String, String> headers = new HashMap<>();
        headers.put("log-header", "I am the log request header.");
       /* Map<String, String> params = new HashMap<>();
        String reqData = ParameterToJsonUtils.encrypReqData(model.getUserToken(), AppApplication.myDeviceID);
        String sign = ParameterToJsonUtils.encrypSign(model.getUserToken(), AppApplication.myDeviceID);
        params.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
        params.put(EncryptInterceptor.KEY_SIGN, sign);*/
        HashMap<String, String> parames = new HashMap<>();
        parames.put("order_id", order_id);
            parames.put("level_0", String.valueOf(wholeSheetRating));//总单评价的评分
        parames.put("content_0", wholeSheetTxt); //总单评价内容
        CommentEntity.ResultBean value = uc.mLiveData.getValue();
       /* if (value != null) {
            if (value.getData() != null && value.getData().size() > 0) {
                for (int i = 0; i < value.getData().size(); i++) {
                    String levelId = value.getData().get(i).getId(); //单个商品的评分
                    parames.put("level_" + levelId, String.valueOf(singleStarClass));
                    parames.put("content_" + levelId, singleGoodsTxt); //单个商品的评价内容
                }
            }
        }*/

        if (content.size() != 0) {
            for (HashMap.Entry<String, String> entry : content.entrySet()) {
                String key = entry.getKey();
                String value1 = entry.getValue();
                parames.put(key, value1);
            }
        }

        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        //商品的图片
        for (HashMap.Entry<String, List<LocalMedias>> entry : updater.entrySet()) {
            String key = entry.getKey();
            List<LocalMedias> value1 = entry.getValue();
            for (int i = 0; i < value1.size(); i++) {
                LocalMedias localMedias = value1.get(i);
                if (localMedias.getFile() != null) {
                    KLog.a("我上传的图片为:=====>>>>>>    " + key, "     图片路劲为:========>>>>>>>   " + localMedias.getFile().getPath());
                    postFormBuilder.addFile("image_" + key + "_" + (i + 1), localMedias.getFile().getName(), localMedias.getFile());
                }
            }
        }
        //总评价的图片
        List<LocalMedias> list = adapter.getList();
        if(list!=null&&list.size()!=0) {
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
                .url(RetrofitClient.baseUrl + Constants.URL.submit_comment)
                .params(parmes)
//                .headers(headers)
                .build()
                .execute(new MyStringCallback());
    }

    //提交评价
    public BindingCommand submitCommontOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
        }
    });


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
            ToastUtils.showShort("评价已提交!");
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
