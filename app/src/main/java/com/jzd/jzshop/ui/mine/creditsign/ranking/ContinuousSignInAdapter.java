package com.jzd.jzshop.ui.mine.creditsign.ranking;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AssetsRecordEntity;
import com.jzd.jzshop.entity.SignRankingEntity;
import com.shehuan.niv.NiceImageView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author LXB
 * @description:
 * @date :2020/4/7 10:39
 */
public class ContinuousSignInAdapter extends SuperAdapter<SignRankingEntity.ResultBean.DaatBean> {
    private Context mContext;
    private List<SignRankingEntity.ResultBean.DaatBean> dataList;
    private TextView tv_state, tv_result_state;
    private boolean isHide = true;
    private ContinuousSignInViewModel viewModel;

    public ContinuousSignInAdapter(Context context, ContinuousSignInViewModel viewModel,
                                   List<SignRankingEntity.ResultBean.DaatBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.dataList = items;
        mContext = context;
        this.viewModel = viewModel;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SignRankingEntity.ResultBean.DaatBean item) {
        NiceImageView iv_sigin_avater = holder.findViewById(R.id.iv_sigin_avater);
        Glide.with(mContext).load(item.getAvatar()).into(iv_sigin_avater);
        holder.setText(R.id.tv_sign_name, item.getNickname());
        holder.setText(R.id.tv_sign_state, "当前连续签到 ".concat(item.getDay()) + " 天");
        TextView tv_signNum = holder.findViewById(R.id.tv_signNum);
        ImageView iv_sigin_num = holder.findViewById(R.id.iv_sigin_num);
        if (layoutPosition >= 0 && layoutPosition < 10){
            tv_signNum.setVisibility(View.VISIBLE);
            tv_signNum.setText(String.valueOf(layoutPosition+1));
            if (layoutPosition ==0){
                tv_signNum.setVisibility(View.GONE);
                iv_sigin_num.setVisibility(View.VISIBLE);
                iv_sigin_num.setBackgroundResource(R.mipmap.ic_first);
            }else if (layoutPosition == 1){
                tv_signNum.setVisibility(View.GONE);
                iv_sigin_num.setVisibility(View.VISIBLE);
                iv_sigin_num.setBackgroundResource(R.mipmap.ic_second);
            }else if (layoutPosition ==2){
                tv_signNum.setVisibility(View.GONE);
                iv_sigin_num.setVisibility(View.VISIBLE);
                iv_sigin_num.setBackgroundResource(R.mipmap.ic_thired);
            }else {
                iv_sigin_num.setVisibility(View.INVISIBLE);
            }
        }else {
            iv_sigin_num.setVisibility(View.GONE);
            tv_signNum.setVisibility(View.GONE);
            tv_signNum.setText(String.valueOf(layoutPosition));
        }
    }
}
