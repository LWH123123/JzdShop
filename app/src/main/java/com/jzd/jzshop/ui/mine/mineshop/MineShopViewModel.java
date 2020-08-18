package com.jzd.jzshop.ui.mine.mineshop;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMineShopBinding;
import com.jzd.jzshop.entity.MineShopEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.mineshop.regist.MineShopRegiestFragment;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author lwh
 * @description :
 * @date 2020/3/20.
 */
public class MineShopViewModel extends ToolbarViewModel<Repository> {


    private static final String TAG = "MineShopViewModel提交小店信息";
    public MineShopViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private ActivityMineShopBinding bin;

    private String sty_type;
    public void setBinding(ActivityMineShopBinding binding,String sty_type) {
        this.bin=binding;
        this.sty_type=sty_type;
    }


    private MineShopSterAdapter baseadpater;
    private MineShopPersonAdapter personAdapter;
    public void setadpater(MineShopSterAdapter baseadpater,MineShopPersonAdapter personAdapter) {
        this.baseadpater = baseadpater;
        this.personAdapter=personAdapter;
    }

    public SingleLiveEvent back =new SingleLiveEvent();
    public SingleLiveEvent<MineShopEntity.ResultBean.DataBean> datalist=new SingleLiveEvent<>();
    private ObservableField<MineShopEntity.ResultBean> mineshopList =new ObservableField<>();

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }




    //申请小店(展示)
    public void requestApplywork(){
        isShowDialog(false);
        addSubscribe(model.postMineShop(model.getUserToken()),new ParseDisposableTokenErrorObserver<BaseResponse>(){
            @Override
            public void onResult(BaseResponse o) {
                super.onResult(o);
                dismissDialog();
                String s = new Gson().toJson(o.getResult());
                MineShopEntity.ResultBean resultBean = new ParseDataMineshop().parseData("{\"result\": " + s + "}");
                int status = resultBean.getStatus();
                if(status==400){
                    ToastUtils.showLong("小店审核中!");
                    startContainerActivity(MineShopRegiestFragment.class.getCanonicalName());
                    finish();
                    return;
                }
                if(status==300){
                    startActivity(MineShopHomeAty.class);
                    finish();
                    return;
                }
                if(resultBean.getStatus()==200){
                    if(!TextUtils.isEmpty(resultBean.getData().getRefuse())) {
                        //小店申请被驳回
                        ToastUtils.showLong("小店被驳回,驳回理由为:" + resultBean.getData().getRefuse());
                    }
                }
                //如果当小店状态为100时也是申请小店页面
                datalist.setValue(resultBean.getData());
                mineshopList.set(resultBean);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
                // 测试环境小店未开启
                 finish();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }
        });

    }





    //设置小店(展示)
    public void requestSetWork(){
//        isShowDialog(false);
        addSubscribe(model.postMineSetShop(model.getUserToken()),new ParseDisposableTokenErrorObserver<BaseResponse>(){
            @Override
            public void onResult(BaseResponse o) {
                super.onResult(o);
                dismissDialog();
                String s = new Gson().toJson(o.getResult());
                MineShopEntity.ResultBean resultBean = new ParseDataMineshop().parseData("{\"result\": " + s + "}");
                KLog.a("我的数据"+resultBean.getStatus()+"======>>>>>"+resultBean.getData().getBase().size());
                datalist.setValue(resultBean.getData());
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                KLog.a(errarMessage);
                dismissDialog();
                finish();
            }


            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

        });



    }


    //提交申请
    public BindingCommand onClickSubmitApplyListeren =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          HashMap<String,String> base= baseadpater.hashMap;
          HashMap<String,String> personal= personAdapter.hashMap;
            for (Map.Entry<String,String> entry : base.entrySet()) {
                KLog.a("我的小店==>>"+entry.getKey()+"     "+entry.getValue());
            }
        /* HashMap<String,GridImageAdapter> gridImageAdapters= baseadpater.map;
            for (Map.Entry<String,GridImageAdapter> entry : gridImageAdapters.entrySet()) {
                KLog.a("我的小店==>>"+entry.getKey()+"     "+entry.getValue().locaImgFile().get(0).getPath());
            }*/
            if(!baseadpater.mustInport()||!personAdapter.mustInport()){
                ToastUtils.showLong("请填写必填项");
                return;
            }
            //基本信息   个人信息  文本数据
            HashMap<String, String> map = new HashMap<>();
            map.putAll(base);
            map.putAll(personal);
            // 图片上传
           HashMap<String,GridImageAdapter> baseadapter= baseadpater.map;
           HashMap<String,GridImageAdapter> personadpter= personAdapter.map;
            PostFormBuilder postFormBuilder = OkHttpUtils.post();
            boolean b = addAdpterData(map, baseadapter,postFormBuilder);
            boolean b1 = addAdpterData(map, personadpter,postFormBuilder);
            if(!b||!b1){
                ToastUtils.showLong("请上传有关信息");
                return;
            }
            HashMap<String, String> hashmap = new HashMap<>();
            String postBodyString = ParameterToJsonUtils.createJsonString(model.getUserToken(), AppApplication.myDeviceID,
                    map);
            String reqData = ParameterToJsonUtils.encrypReqData(postBodyString);
            String sign = ParameterToJsonUtils.encrypSign(postBodyString);
            hashmap.put(EncryptInterceptor.KEY_REQ_DATA, reqData);
            hashmap.put(EncryptInterceptor.KEY_SIGN, sign);
            if(sty_type!=null&&!TextUtils.isEmpty(sty_type)){
                KLog.a("这是设置小店接口UI");
                postFormBuilder.url(RetrofitClient.baseUrl + Constants.URL.mineshopset_submit);
            }else {
                KLog.a("这是申请小店接口UI");
                postFormBuilder.url(RetrofitClient.baseUrl + Constants.URL.mineShop_submit);
            }
            postFormBuilder
                    .addHeader("Content-Type", "multipart/form-data;")
                    .params(hashmap)
                    .build()
                    .execute(new MineShopViewModel.MyStringCallback());

        }
    });


    public boolean addAdpterData(HashMap<String,String> hashMap,HashMap<String,GridImageAdapter> adapterHashMap,PostFormBuilder postFormBuilder){
        if(adapterHashMap==null&&adapterHashMap.size()==0){
            return true;
        }

        for (Map.Entry<String,GridImageAdapter> entry : adapterHashMap.entrySet()) {
            GridImageAdapter adapter= entry.getValue();
            if(!adapter.isMust()){
                return false;
            }
            if(adapter!=null){
                if(!TextUtils.isEmpty(adapter.httpImg())){
                    hashMap.put(entry.getKey()+"addimage",adapter.httpImg());
                }
                List<File> files = adapter.locaImgFile();
                if(files!=null&&files.size()!=0) {
                    for (int i = 0; i < files.size(); i++) {
                        postFormBuilder.addFile(entry.getKey() + "_" + i, files.get(i).getName(), files.get(i));
                    }
                }
            }
        }
        return true;
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
//            ToastUtils.showLong("申请已经成功提交!");
            if(sty_type==null) {//申请小店
            ToastUtils.showLong("申请已成功提交");
                startContainerActivity(MineShopRegiestFragment.class.getCanonicalName());
                finish();
            }else {//设置小店
                back.call();
            }
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
}
