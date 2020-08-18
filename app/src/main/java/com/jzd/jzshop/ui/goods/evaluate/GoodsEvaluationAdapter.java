package com.jzd.jzshop.ui.goods.evaluate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.GoodsEvaluationEntity;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.ui.goods.evaluate.child.AppendCommentChildAdapter;
import com.jzd.jzshop.ui.goods.evaluate.child.AppendReplyChildAdapter;
import com.jzd.jzshop.ui.goods.evaluate.child.CommentChildAdapter;
import com.jzd.jzshop.ui.goods.evaluate.child.ReplyChildAdapter;
import com.jzd.jzshop.ui.home.AppIdentityJumpUtils;
import com.jzd.jzshop.utils.widget.ZQImageViewRoundOval;

import org.byteam.superadapter.OnItemClickListener;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/5/25 17:03
 */
public class GoodsEvaluationAdapter extends SuperAdapter<GoodsEvaluationEntity.ResultBean.DataBean> {
    private Context mContext;
    private List<GoodsEvaluationEntity.ResultBean.DataBean> data;

    public GoodsEvaluationAdapter(Context context, List<GoodsEvaluationEntity.ResultBean.DataBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext = context;
        this.data = items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, GoodsEvaluationEntity.ResultBean.DataBean item) {
        holder.setIsRecyclable(false);
        holder.setText(R.id.tv_name, item.getNickname());
        holder.setText(R.id.tv_time, item.getTime());
        holder.setText(R.id.tv_spec_title, item.getSpec_title());
        ZQImageViewRoundOval logo = holder.findViewById(R.id.iv_headimg);
        logo.setType(ZQImageViewRoundOval.TYPE_CIRCLE);
        logo.setRoundRadius(9);//矩形凹行大小
        Glide.with(mContext).load(item.getHeadimg()).into(logo);
        //用户评论
        if (item.getContent() != null && !TextUtils.isEmpty(item.getContent())) {
            holder.setText(R.id.tv_content, item.getContent());
            RecyclerView recy_comment_pics = holder.findViewById(R.id.recy_comment_pics);
            if (item.getImages() != null && item.getImages().size() > 0) {
                recy_comment_pics.setVisibility(View.VISIBLE);
                initCommentRecycle(recy_comment_pics, item.getImages());
            } else {//评论图片为0
                recy_comment_pics.setVisibility(View.GONE);
            }
        } else {
            holder.findViewById(R.id.tv_content).setVisibility(View.GONE);
        }
        //商家回复
        if (item.getReply_content() != null && !TextUtils.isEmpty(item.getReply_content())) {
            SpannableString spannableString = new SpannableString("商家回复: ".concat(item.getReply_content()));
            StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan_B,0,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.setText(R.id.tv_reply_content, spannableString);
            RecyclerView recy_reply_images = holder.findViewById(R.id.recy_reply_images);
            if (item.getImages() != null && item.getReply_images().size() > 0) {
                recy_reply_images.setVisibility(View.VISIBLE);
                initReplyRecycle(recy_reply_images, item.getReply_images());
            } else {//商家回复为0
                recy_reply_images.setVisibility(View.GONE);
            }
        } else {
            holder.findViewById(R.id.ll_reply_content).setVisibility(View.GONE);
            holder.findViewById(R.id.tv_reply_content).setVisibility(View.GONE);
        }
        //用户追评
        View view_line = holder.findViewById(R.id.view_line);
        TextView tv_append_content_tip = holder.findViewById(R.id.tv_append_content_tip);
        if (item.getAppend_content() != null && !TextUtils.isEmpty(item.getAppend_content())) {
            view_line.setVisibility(View.VISIBLE);
            tv_append_content_tip.setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_append_content, item.getAppend_content());   //用户追评
            RecyclerView recy_append_images = holder.findViewById(R.id.recy_append_images);
            if (item.getAppend_images() != null && item.getAppend_images().size() > 0) {
                recy_append_images.setVisibility(View.VISIBLE);
                initAppendCommentRecycle(recy_append_images, item.getAppend_images());
            } else {//用户追评为0
                recy_append_images.setVisibility(View.GONE);
            }
        } else {
            view_line.setVisibility(View.GONE);
            tv_append_content_tip.setVisibility(View.GONE);
            holder.findViewById(R.id.tv_append_content).setVisibility(View.GONE);
        }
        //回复 用户追评
        if (item.getAppend_reply_content() != null && !TextUtils.isEmpty(item.getAppend_reply_content())) {
            SpannableString spannableString = new SpannableString("商家回复: ".concat(item.getAppend_reply_content()));
            StyleSpan styleSpan_B  = new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan_B,0,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.setText(R.id.tv_append_reply_content,spannableString);
            RecyclerView recy_append_reply_images = holder.findViewById(R.id.recy_append_reply_images);
            if (item.getAppend_reply_images() != null && item.getAppend_reply_images().size() > 0) {
                recy_append_reply_images.setVisibility(View.VISIBLE);
                initAppendReplyRecycle(recy_append_reply_images, item.getAppend_reply_images());
            } else {//回复 用户追评为0
                recy_append_reply_images.setVisibility(View.GONE);
            }
        } else {
            holder.findViewById(R.id.ll_append_reply_content).setVisibility(View.GONE);
            holder.findViewById(R.id.tv_append_reply_content).setVisibility(View.GONE);
        }

    }

    /**
     * set 用户评论 图片 数据集
     *
     * @param recyclerView
     * @param images
     */
    private void initCommentRecycle(RecyclerView recyclerView, List<String> images) {
        List<BannEntity> list = new ArrayList<>();
        for (String image : images) {
            list.add(new BannEntity(image,""));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        CommentChildAdapter adapter = new CommentChildAdapter(mContext, images, R.layout.item_good_evalua_comment_child);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                AppIdentityJumpUtils.previewLargePicGoods((GoodsEvaluationAty)mContext,list,position);
            }
        });
    }

    /**
     * set 商家回复 图片 数据集
     *
     * @param recyclerView
     * @param images
     */
    private void initReplyRecycle(RecyclerView recyclerView, List<String> images) {
        List<BannEntity> list = new ArrayList<>();
        for (String image : images) {
            list.add(new BannEntity(image,""));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        ReplyChildAdapter adapter = new ReplyChildAdapter(mContext, images, R.layout.item_good_evalua_append_reply);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                AppIdentityJumpUtils.previewLargePicGoods((GoodsEvaluationAty)mContext,list,position);
            }
        });
    }


    /**
     * set 用户追评 图片 数据集
     *
     * @param recyclerView
     * @param images
     */
    private void initAppendCommentRecycle(RecyclerView recyclerView, List<String> images) {
        List<BannEntity> list = new ArrayList<>();
        for (String image : images) {
            list.add(new BannEntity(image,""));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        AppendCommentChildAdapter adapter = new AppendCommentChildAdapter(mContext, images, R.layout.item_good_evalua_comment_child);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                AppIdentityJumpUtils.previewLargePicGoods((GoodsEvaluationAty)mContext,list,position);
            }
        });
    }
    /**
     * set 回复 用户追评 图片 数据集
     *
     * @param recyclerView
     * @param images
     */
    private void initAppendReplyRecycle(RecyclerView recyclerView, List<String> images) {
        List<BannEntity> list = new ArrayList<>();
        for (String image : images) {
            list.add(new BannEntity(image,""));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        AppendReplyChildAdapter adapter = new AppendReplyChildAdapter(mContext, images, R.layout.item_good_evalua_append_reply);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                AppIdentityJumpUtils.previewLargePicGoods((GoodsEvaluationAty)mContext,list,position);
            }
        });
    }

}
