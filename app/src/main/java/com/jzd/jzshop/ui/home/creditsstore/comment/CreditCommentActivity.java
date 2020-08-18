package com.jzd.jzshop.ui.home.creditsstore.comment;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditCommentBinding;
import com.jzd.jzshop.entity.CreditCommentEntity;
import com.jzd.jzshop.ui.home.creditsstore.order_detail.CreditOrderDetailViewModel;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 积分商城 商品评价
 * @date :2020/5/15 9:37
 */
public class CreditCommentActivity extends BaseActivity<ActivityCreditCommentBinding, CreditCommentViewModel> {
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private String merch_id = "";
    private String log_id;
    private CreditCommentEntity.ResultBean resultList;
    private String wholeSheetTxt;
    private int index = -1;
    private GridImageAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_comment;
    }

    @Override
    public int initVariableId() {
        return BR.creditCommentVM;
    }

    @Override
    public CreditCommentViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditCommentViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        requestpermission(this);
        viewModel.initToolBar(getResources().getString(R.string.evaluaion));
        viewModel.setBinding(mContext, binding, CreditCommentActivity.this);
        viewModel.requestData(log_id, merch_id);

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            log_id = bundle.getString(Constants.GOODS_KEY_GID);
            Log.d(TAG, "log_id:====" + log_id);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<CreditCommentEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditCommentEntity.ResultBean resultBean) {
                if (resultBean != null) {
                    setData(resultBean);
                }
            }
        });
        viewModel.uc.submitEvaluationLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {//评价成功
                ToastUtils.showShort("评价已提交!");
                //发送刷新 订单详情页数据
                Messenger.getDefault().send("evaluation", CreditOrderDetailViewModel.TOKEN_VIEWMODEL_EVALUATION_REFRESH);
                finish();
            }
        });
    }

    private void setData(CreditCommentEntity.ResultBean resultBean) {
        binding.tvStoreName.setText(resultBean.getShopname());
        //商品
        Glide.with(mContext).load(resultBean.getThumb()).into(binding.ivGood);
        binding.tvGoodsName.setText(resultBean.getTitle());
        if (!TextUtils.isEmpty(resultBean.getOption())){
            binding.tvGoodspec.setText("规格：" + resultBean.getOption());
        }else {
            binding.tvGoodspec.setText("规格：暂无规格");
        }
        binding.tvCreditNum.setText(String.valueOf(resultBean.getCredit()));
        binding.tvNumber.setText("x" + String.valueOf(resultBean.getNumber()));
        //是评价还是追评
        int status = resultBean.getStatus();        //1：第一次评价 2：第二次评价（追评）
        if (status == 1) {//评价
            binding.constrlOne.setVisibility(View.VISIBLE);
        } else if (status == 2) {//追评
            binding.constrlOne.setVisibility(View.GONE);
        } else {
        }
        //清空星级评价
        SystemUtils.clearRating(mContext, binding.ratingbar, binding.tvScoreTxt);
        viewModel.setParamsValue(wholeSheetTxt);
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ratingbar.setRating(0f);
            }
        });
        binding.ratingbar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                Log.d(TAG, " whole  onRatingChanged:---->>>" + "ratin=" + rating + ",fromUser=" + fromUser);
                viewModel.setParamsValueStarClass(rating);
                int ratingLevel = (int) rating;
                SystemUtils.setRatingBarLevel(mContext, ratingLevel, binding.tvScoreTxt);
            }
        });
        //提交评价
        binding.tvSubmitCommont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //提交评价
//            if (!TextUtils.isEmpty(String.valueOf(wholeSheetRating))){
                wholeSheetTxt = binding.etCommont.getText().toString().trim();
                if (!TextUtils.isEmpty(wholeSheetTxt)) {
                    viewModel.submitCommentData(log_id, wholeSheetTxt);
                } else {
                    ToastUtils.showShort(R.string.credit_empty_wholesheettxt);
                    return;
                }
//            }else {
//                ToastUtils.showShort(R.string.empty_wholeSheetRating);
//                return;
//            }
            }
        });

        //晒图 多图上传
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        binding.ivUpload.setLayoutManager(manager);
        PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(CreditCommentActivity.this, 5, PictureConfig.MULTIPLE);
        adapter = new GridImageAdapter(mContext, pictureSelectorUtils.onAddPicClickListener);
        adapter.setList(viewModel.picture);
        binding.ivUpload.setAdapter(adapter);
        adapter.setSelectMax(5);
        viewModel.setAdapter(adapter);
    }


    public void requestpermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(activity, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(activity, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
//                    if(index!=viewModel.position.get()){
//                        viewModel.imaupdate.clear();
//                        KLog.a("");
//                    }
                    List<LocalMedia> loca = PictureSelector.obtainMultipleResult(data);
                    KLog.d("商品的图片数据", "=====>>>>>>    " + loca.size());
                    List<LocalMedias> localMedias = new ArrayList<>();
                    for (int i = 0; i < loca.size(); i++) {
                        LocalMedias httpMedia = new LocalMedias();
                        httpMedia.setPath(loca.get(i).getPath());
                        httpMedia.setChecked(loca.get(i).isChecked());
                        httpMedia.setCompressPath(loca.get(i).getCompressPath());
                        httpMedia.setCut(loca.get(i).isCut());
                        httpMedia.setCutPath(loca.get(i).getCutPath());
                        httpMedia.setDuration(loca.get(i).getDuration());
                        httpMedia.setPosition(loca.get(i).getPosition());
                        httpMedia.setCompressed(loca.get(i).isCompressed());
                        LocalMedia localMedia = loca.get(i);
                        File file = new File(localMedia.getCutPath());
                        httpMedia.setFile(file);
                        localMedias.add(httpMedia);
                    }
                    viewModel.updater.put(viewModel.position.get(), localMedias);
                    viewModel.notifyRecycle(viewModel.position.get());
                    break;
                case PictureConfig.MULTIPLE:
                    List<LocalMedia> locas = PictureSelector.obtainMultipleResult(data);
                    KLog.a("总的图片", "=====>>>>>>" + locas.size());
                    for (int i = 0; i < locas.size(); i++) {
                        LocalMedias httpMedia = new LocalMedias();
                        httpMedia.setPath(locas.get(i).getPath());
                        httpMedia.setChecked(locas.get(i).isChecked());
                        httpMedia.setCompressPath(locas.get(i).getCompressPath());
                        httpMedia.setCut(locas.get(i).isCut());
                        httpMedia.setCutPath(locas.get(i).getCutPath());
                        httpMedia.setDuration(locas.get(i).getDuration());
                        httpMedia.setPosition(locas.get(i).getPosition());
                        httpMedia.setCompressed(locas.get(i).isCompressed());
                        LocalMedia localMedia = locas.get(i);
                        File file = new File(localMedia.getCutPath());
                        httpMedia.setFile(file);
                        viewModel.picture.add(httpMedia);
                    }
                    adapter.notifyDataSetChanged();
                    KLog.a("总的评价为:=====>>>>>", viewModel.picture);
                    break;
            }
        }
    }
}
