package com.jzd.jzshop.ui.order.aftersale;

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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityApplyForRefundBinding;
import com.jzd.jzshop.entity.OrderRefundEntity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.ui.order.orderdetail.OrderDetailViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 申请退款/申请售后
 * @date :2020/1/15 17:21
 */
public class ApplyForRefundAty extends BaseActivity<ActivityApplyForRefundBinding, AfterSaleViewModel> implements View.OnClickListener {
    private String order_id, title;
    private OrderRefundEntity.ResultBean resultList;
    private List<OrderRefundEntity.ResultBean.DataRefundTypeBean> data_refund_type;
    private int handleWayIndex = 0;//处理选中
    private int reasonIndex = 0;//处理选中
    private List<String> data_reason;
    private GridImageAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_apply_for_refund;
    }

    @Override
    public int initVariableId() {
        return BR.aftersaleVM;
    }

    @Override
    public AfterSaleViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AfterSaleViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        requestpermission(this);
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(order_id);
        initPicture();
    }
   private ArrayList<LocalMedias> localmedia = new ArrayList<>();

    private void initPicture() {
        RecyclerView upload = binding.ivUpload;
        FullyGridLayoutManager manager = new FullyGridLayoutManager(upload.getContext(),
                4, GridLayoutManager.VERTICAL, false);
        upload.setLayoutManager(manager);
        PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(ApplyForRefundAty.this, 5, Constants.UPLOAD_DOCUMENTS);
        adapter = new GridImageAdapter(upload.getContext(), pictureSelectorUtils.onAddPicClickListener);
        upload.setAdapter(adapter);
        adapter.setSelectMax(5);
        adapter.notifyDataSetChanged();

    }


    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public void requestpermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23) {
            int check1 = ContextCompat.checkSelfPermission(activity, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(activity, permissions[1]);
            if (check1 + check2 != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 1);
            }
        }
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getString(Constants.ORDER_ID);
            title = bundle.getString(Constants.BUNDLE_KEY_TITLE);
            viewModel.initToolBar(title);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<OrderRefundEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable OrderRefundEntity.ResultBean resultBean) {
                resultList = resultBean;
                int status = resultBean.getStatus();
                if (status == 1) {//退款
                    title = getResources().getString(R.string.apply_exitAmount);
                    viewModel.initToolBar(title);
                } else {
                    title = getResources().getString(R.string.apply_after_sale);
                    viewModel.initToolBar(title);
                }
                if (!TextUtils.isEmpty(resultList.getRefund_content())) {
                    binding.constrlRefuseTip.setVisibility(View.VISIBLE);
                } else {
                    binding.constrlRefuseTip.setVisibility(View.GONE);
                }
                //处理方式数据集
                data_refund_type = resultList.getData_refund_type();
                //退款、换货原因数据集
                data_reason = resultList.getData_reason();
                //已申请信息集合
                OrderRefundEntity.ResultBean.RefundInfoBean refund_info = resultList.getRefund_info();
                if (refund_info != null) {
                    binding.tvAmount.setText("¥ " + refund_info.getRefund_price());
                    binding.tvTip.setText("*提示您可退款的最大金额为¥ "+refund_info.getRefund_price());
                    if (data_refund_type != null && data_refund_type.size() > 0) {
                        String name = data_refund_type.get(refund_info.getRefund_type()).getName();
                        binding.tvHandleWay.setText(name);
                    }
                    binding.tvReason.setText(refund_info.getReason());
                    if (!TextUtils.isEmpty(refund_info.getContent())) {
                        binding.tvExplain.setText(refund_info.getContent()); // 说明
                    }
                }

                initApplyPic();


            }
        });

        binding.ivHandleWay.setOnClickListener(this);
        binding.ivReasonWay.setOnClickListener(this);
        binding.tvSubmitApply.setOnClickListener(this);

        viewModel.uc.submitLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("submit_apply", OrderDetailViewModel.TOKEN_VIEWMODEL_SUBMIT_APPLY_REFRESH);
                finish();
            }
        });
        viewModel.uc.modifyLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("modify_apply", AfterSaleViewModel.TOKEN_AFTERSALEVIEWMODEL_MODIFY_APPLY_REFRESH);
                finish();
            }
        });

    }

    private void initApplyPic() {
        List<String> images = resultList.getRefund_info().getImages();
        for (String image : images) {
            LocalMedias localMedias = new LocalMedias();
            localMedias.setUrl(image);
            localmedia.add(localMedias);
        }
        adapter.setList(localmedia);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_handleWay: //处理方式
                if (data_refund_type != null && data_refund_type.size() > 0) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < data_refund_type.size(); i++) {
                        list.add(data_refund_type.get(i).getName());
                    }
                    mShowDialog("处理方式", list, binding.tvHandleWay);
                } else {
                }
                break;
            case R.id.iv_reasonWay://退款退货原因
                if (data_reason != null && data_reason.size() > 0) {
//                    mShowDialog("退款退货原因",data_reason,binding.tvReason);
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                            .title("退款退货原因")
                            .positiveText(null)
                            .items(data_reason)
                            .itemsCallbackSingleChoice(reasonIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    Log.d(TAG, " 退款退货原因 onSelection:" + text + "which:" + which);
                                    reasonIndex = which;
                                    binding.tvReason.setText(text);
                                    return false;
                                }
                            });
                    builder.titleGravity(GravityEnum.CENTER);
                    builder.widgetColor(mContext.getResources().getColor(R.color.colorPrimary));
                    builder.build().show();
                } else {
                }
                break;
            case R.id.tv_submitApply://提交申请
                String reason = binding.tvReason.getText().toString().trim();
                String express = binding.tvExplain.getText().toString().trim();
                //处理方式ID
                if (data_refund_type != null && data_refund_type.size() > 0) {
                    String name = data_refund_type.get(handleWayIndex).getName();
                    int id = data_refund_type.get(handleWayIndex).getId();
                    if (reason != null && express != null) {
                        Log.i(TAG, "refund_type:----->>>" + id);
                        viewModel.submitApplayData(order_id, String.valueOf(id), reason, express, "imgages", adapter);
                    }
                } else {
                }
                break;
        }

    }

    /**
     * 单选框
     *
     * @param way
     * @param list
     * @param tvHandleWay
     */
    private void mShowDialog(final String way, List<String> list, final TextView tvHandleWay) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title(way)
                .positiveText(null)
                .items(list)
                .itemsCallbackSingleChoice(handleWayIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.d(TAG, way + "onSelection:" + text + "which:" + which);
                        handleWayIndex = which;
                        tvHandleWay.setText(text);
                        return false;
                    }
                });
        builder.titleGravity(GravityEnum.CENTER);
        builder.widgetColor(mContext.getResources().getColor(R.color.colorPrimary));
        builder.build().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.UPLOAD_DOCUMENTS:
                    List<LocalMedia> loca = PictureSelector.obtainMultipleResult(data);
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
                        localmedia.add(httpMedia);
                    }
                    adapter.setList(localmedia);
                    adapter.notifyDataSetChanged();

                    break;


            }


        }

    }
}
