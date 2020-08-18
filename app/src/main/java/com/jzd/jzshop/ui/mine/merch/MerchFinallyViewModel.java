package com.jzd.jzshop.ui.mine.merch;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentMerchFinallyBinding;
import com.jzd.jzshop.entity.EventFile;
import com.jzd.jzshop.entity.MerchEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.EncryptInterceptor;
import com.jzd.jzshop.utils.ParameterToJsonUtils;
import com.jzd.jzshop.utils.net_utils.RetrofitClient;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LWH
 * @description:
 * @date :2020/1/3 11:32
 */
public class MerchFinallyViewModel extends ToolbarViewModel<Repository> {


    public MerchFinallyViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    private MerchEntity.ResultBean parcelable;
    private List<MerchEntity.ResultBean.FieldsBean> parcelableArray;


    public void setData(MerchEntity.ResultBean parcelable, List<MerchEntity.ResultBean.FieldsBean> parcelableArray) {
        this.parcelable = parcelable;
        this.parcelableArray = parcelableArray;
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void setToolbar() {
        setTitleText("商家入驻");
    }


    private List<EventFile.FileBean> fileBeans;

    public void setPic(List<EventFile.FileBean> fileBeans) {
        this.fileBeans = fileBeans;
    }

    private FragmentMerchFinallyBinding binding;
private MerchFinallyFragment fragments;
    public void setBinding(FragmentMerchFinallyBinding binding,MerchFinallyFragment fragment) {
        this.binding = binding;
        this.fragments=fragment;
    }

    public BindingCommand onClickListerenApply = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            appllyData();
        }
    });

    //协议内容查看
    public BindingCommand onApplyContentClick =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            seeApplyContent();
        }
    });

    private void seeApplyContent() {
        if(TextUtils.isEmpty(parcelable.getApplytitle())){
            return;
        }
        Spanned spanned = Html.fromHtml(parcelable.getApplycontent());
        MessageDialog.showMessageDialog(fragments.getContext(), parcelable.getApplytitle(), spanned.toString(),
                new MessageDialog.MessageDialogClick() {
                    @Override
                    public void onSureClickListener() {
                    }
                });

    }


    private void appllyData() {
        String username = binding.username.getMessage();
        String word = binding.word.getMessage();
        String reword = binding.reword.getMessage();
        String recid = binding.recid.getMessage();
        boolean checked = binding.checkBox2.isChecked();
        if(!checked){
            ToastUtils.showLong("请阅读入驻申请协议。");
            return;
        }
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(word) && !TextUtils.isEmpty(reword)) {
            if (!word.equals(reword)) {
                ToastUtils.showLong("俩次密码不一致,请重新输入");
                binding.word.setText("");
                binding.reword.setText("");
                return;
            }
        } else {
            return;
        }

        KLog.a("我的图片集合", parcelableArray.size());
        HashMap<String, String> shopinfo = new HashMap<>();
        shopinfo.put("realname", parcelable.getRealname());//联系人
        shopinfo.put("mobile", parcelable.getMobile());//手机号
        shopinfo.put("merchname", parcelable.getMerchname());//商户名称
        shopinfo.put("salecate", parcelable.getSalecate());//主营项目
        shopinfo.put("desc", parcelable.getDesc());//介绍
        shopinfo.put("uname", username);//账号
        shopinfo.put("upass", word);//密码
        shopinfo.put("rec_id",recid);
        String jsonString = ParameterToJsonUtils.createJsonString(model.getUserToken(), AppApplication.myDeviceID, shopinfo);
        String data = ParameterToJsonUtils.encrypReqData(jsonString);
        String sign = ParameterToJsonUtils.encrypSign(jsonString);
        HashMap<String, String> parmes = new HashMap<>();
        parmes.put(EncryptInterceptor.KEY_REQ_DATA, data);
        parmes.put(EncryptInterceptor.KEY_SIGN, sign);

        PostFormBuilder postFormBuilder = OkHttpUtils.post();


      /*  for (MerchEntity.ResultBean.FieldsBean fieldsBean : parcelableArray) {
            if (fieldsBean.getFile() != null) {
                KLog.a("我上传的图片", fieldsBean.getTp_name());
                postFormBuilder.addFile(fieldsBean.getTp_key(), fieldsBean.getFile().getName(), fieldsBean.getFile());
            }
        }*/
        for (EventFile.FileBean fileBean : fileBeans) {
            postFormBuilder.addFile(fileBean.getKey(), fileBean.getFile().getName(), fileBean.getFile());
        }
        KLog.a("最后图片集合为", fileBeans.size());
        postFormBuilder.
                addHeader("Content-Type", "multipart/form-data;")
                .url(RetrofitClient.baseUrl + Constants.URL.submitStoreInfo)
                .params(parmes)
                .build()
                .execute(new MySubmitResult());
    }


    public class MySubmitResult extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);

        }

        @Override
        public void onError(Call call, Exception e, int id) {
            KLog.a("响应结果",e.getMessage());

        }

        @Override
        public void onResponse(String response, int id) {
            startContainerActivity(MerchSuccessFragment.class.getCanonicalName());
        }
    }


}
