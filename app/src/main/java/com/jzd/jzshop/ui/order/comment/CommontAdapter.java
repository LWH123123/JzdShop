package com.jzd.jzshop.ui.order.comment;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.CommentEntity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.jzd.jzshop.utils.SystemUtils;
import com.luck.picture.lib.config.PictureConfig;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/1/16 14:24
 */
public class CommontAdapter extends SuperAdapter<CommentEntity.ResultBean.DataBean> {
    private static final String TAG = CommontAdapter.class.getSimpleName();
    private Context mContext;
    private CommentViewModel viewModel;
    private List<CommentEntity.ResultBean.DataBean> dataList;
    private boolean isHide = true;
    private String is_evaluation;

    public CommontAdapter(Context context, CommentViewModel viewModel, String is_evaluation, List<CommentEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.viewModel = viewModel;
        this.is_evaluation = is_evaluation;
        this.dataList = items;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, final int layoutPosition, CommentEntity.ResultBean.DataBean item) {
        holder.setText(R.id.tv_goodname, item.getTitle());
        if (item.getSpec_title() != null) {
            holder.setText(R.id.tv_goodspec, item.getSpec_title());
        }
        holder.setText(R.id.tv_money, item.getPrice());
        ImageView iv_good = holder.findViewById(R.id.iv_good);
        Glide.with(mContext).load(item.getThumb()).into(iv_good);
        holder.setText(R.id.tv_number, "x".concat(String.valueOf(item.getTotal())));
        ConstraintLayout consts = holder.findViewById(R.id.constlHideCommont);
        consts.setVisibility(View.GONE);
        //hide
        final RelativeLayout rl_hide = holder.findViewById(R.id.rl_hide);
        consts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide) {
                    isHide = false;
//                    Animation animBottomOut = AnimationUtils.loadAnimation(mContext,R.anim.outdowntoup);
//                    animBottomOut.setDuration(240);
                    rl_hide.setVisibility(View.VISIBLE);
//                    rl_hide.startAnimation(animBottomOut);
                    holder.findViewById(R.id.iv_HideArrows).setBackgroundResource(R.mipmap.up);
                    String id = dataList.get(layoutPosition - 1).getId();
                    toComment(holder, id);
                } else {
                    isHide = true;
//                    Animation animBottomOut = AnimationUtils.loadAnimation(mContext,R.anim.inuptodown);
//                    animBottomOut.setDuration(240);
                    rl_hide.setVisibility(View.GONE);
//                    rl_hide.startAnimation(animBottomOut);
                    holder.findViewById(R.id.iv_HideArrows).setBackgroundResource(R.mipmap.down);
                }
            }
        });


    }

    /**
     * 单个商品的评分
     *
     * @param holder
     */
    private void toComment(SuperViewHolder holder, final String position) {
        final ConstraintLayout constrlOne = holder.findViewById(R.id.constrlOne);
        if (is_evaluation.equals("0")) { //评价 有星级评价
            constrlOne.setVisibility(View.VISIBLE);
        } else if (is_evaluation.equals("1")) {
            constrlOne.setVisibility(View.GONE);
        } else {
        }
        final TextView tv_score_txt = holder.findViewById(R.id.tv_score_txt);
        ImageView iv_close = holder.findViewById(R.id.iv_close);
        final EditText et_commont = holder.findViewById(R.id.et_commont);

        //多图上传
        RecyclerView iv_upload = holder.findViewById(R.id.iv_upload);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(iv_upload.getContext(),
                4, GridLayoutManager.VERTICAL, false);
        iv_upload.setLayoutManager(manager);
        PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(viewModel.commentAty, 5, PictureConfig.CHOOSE_REQUEST);
        GridImageAdapter gridImageAdapter = new GridImageAdapter(iv_upload.getContext(), pictureSelectorUtils.onAddPicClickListener);
        KLog.i("我的位置是=======>>>>>>>:", position);
        viewModel.setGridImageAdapter(position, gridImageAdapter);
        gridImageAdapter.setSelectMax(5);
        iv_upload.setAdapter(gridImageAdapter);
        gridImageAdapter.notifyDataSetChanged();
        viewModel.notifyRecycle(position);


        et_commont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = et_commont.getText().toString().trim();
                viewModel.content.put("content_" + position, trim);
            }
        });

        //星级评分
        final SimpleRatingBar ratiang = holder.findViewById(R.id.ratingbar);
        SystemUtils.clearRating(mContext, ratiang, tv_score_txt); //清空星级评价
        ratiang.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                KLog.a("我选择的星级评分:", "   ===>>>>>>" + rating);
                viewModel.setParamsValueSingleStarClass(rating);
                int ratingLevel = (int) rating;
                SystemUtils.setRatingBarLevel(mContext, ratingLevel, tv_score_txt);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratiang.setRating(0f);
            }
        });


    }
}
