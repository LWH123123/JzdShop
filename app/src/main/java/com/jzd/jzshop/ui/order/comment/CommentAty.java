package com.jzd.jzshop.ui.order.comment;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCommentBinding;
import com.jzd.jzshop.entity.CommentEntity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.byteam.superadapter.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * 编辑评价
 */
public class CommentAty extends BaseActivity<ActivityCommentBinding, CommentViewModel> {
    private String order_id, is_evaluation;
    private CommentEntity.ResultBean resultList;
    private String wholeSheetTxt;
    private int index = -1;
    private GridImageAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_comment;
    }

    @Override
    public int initVariableId() {
        return BR.commonVM;
    }

    @Override
    public CommentViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CommentViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        requestpermission(this);
        viewModel.initToolBar(getResources().getString(R.string.evaluaion));
        viewModel.setBinding(mContext, binding, CommentAty.this);
        viewModel.requestData(order_id);

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

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer<CommentEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CommentEntity.ResultBean resultBean) {
                resultList = resultBean;
                List<CommentEntity.ResultBean.DataBean> data = resultBean.getData();
                if (data != null && data.size() > 0) {
                    binding.recyView.setVisibility(View.VISIBLE);
                    initRecycleView(data);
                } else {
                    binding.recyView.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initRecycleView(List<CommentEntity.ResultBean.DataBean> data) {
        binding.recyView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        binding.recyView.addItemDecoration(divider);
        CommontAdapter adapter = new CommontAdapter(mContext, viewModel, is_evaluation, data, R.layout.item_edit_commont);
        binding.recyView.setAdapter(adapter);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.item_commont_head_view, null);
        initHeadData(headView, resultList);
        View footView = LayoutInflater.from(mContext).inflate(R.layout.item_commont_foot_view, null);
        initFootData(footView);
        adapter.addHeaderView(headView);
        adapter.addFooterView(footView);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                Log.d("onItemClick", "" + position);
            }
        });
        binding.recyView.scrollToPosition(0);
        adapter.notifyDataSetChanged();


    }

    private void initHeadData(View headView, CommentEntity.ResultBean resultList) {
        TextView tv_title = headView.findViewById(R.id.tv_title);
        if (resultList != null) {
            if (!TextUtils.isEmpty(resultList.getMerch_name())) {
                tv_title.setText(resultList.getMerch_name());
            }
        }
    }

    private void initFootData(View footView) {
        final ConstraintLayout constrlOne = footView.findViewById(R.id.constrlOne);
        if (is_evaluation.equals("0")) { //评价 有星级评价
            constrlOne.setVisibility(View.VISIBLE);
        } else if (is_evaluation.equals("1")) {
            constrlOne.setVisibility(View.GONE);
        } else {
        }
        final SimpleRatingBar ratingbar = footView.findViewById(R.id.ratingbar);
        final TextView tv_score_txt = footView.findViewById(R.id.tv_score_txt);
        ImageView iv_close = footView.findViewById(R.id.iv_close);
        SystemUtils.clearRating(mContext, ratingbar, tv_score_txt); //清空星级评价
        final EditText et_commont = footView.findViewById(R.id.et_commont);
        viewModel.setParamsValue(wholeSheetTxt);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingbar.setRating(0f);
            }
        });
        ratingbar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                Log.d(TAG, " whole  onRatingChanged:---->>>" + "ratin=" + rating + ",fromUser=" + fromUser);
                viewModel.setParamsValueStarClass(rating);
                int ratingLevel = (int) rating;
                SystemUtils.setRatingBarLevel(mContext, ratingLevel, tv_score_txt);
            }
        });
        binding.tvSubmitCommont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //提交评价
//            if (!TextUtils.isEmpty(String.valueOf(wholeSheetRating))){
                wholeSheetTxt = et_commont.getText().toString().trim();
                if (!TextUtils.isEmpty(wholeSheetTxt)) {
                    viewModel.submitCommentData(order_id, wholeSheetTxt);
                } else {
                    ToastUtils.showShort(R.string.empty_wholeSheetTxt);
                    return;
                }
//            }else {
//                ToastUtils.showShort(R.string.empty_wholeSheetRating);
//                return;
//            }
            }
        });


        //多图上传
        RecyclerView upload = footView.findViewById(R.id.iv_upload);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(CommentAty.this,
                4, GridLayoutManager.VERTICAL, false);
        upload.setLayoutManager(manager);
        PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(CommentAty.this, 5, PictureConfig.MULTIPLE);
        adapter = new GridImageAdapter(upload.getContext(), pictureSelectorUtils.onAddPicClickListener);
        adapter.setList(viewModel.picture);
        upload.setAdapter(adapter);
        adapter.setSelectMax(5);
        viewModel.setAdapter(adapter);

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getString(Constants.ORDER_ID);
            is_evaluation = bundle.getString(Constants.IS_EVALUATION);
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