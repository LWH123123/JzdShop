package com.jzd.jzshop.ui.mine.creditsign;

import android.content.Context;
import android.text.TextUtils;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.RechargeRecordEntity;
import com.jzd.jzshop.entity.SignRecordEntity;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * @author lwh
 * @description :
 * @date 2020/3/24.
 */
public class SignRecordAdapter extends SuperAdapter<SignRecordEntity.ResultBean.DaatBean>{
    private Context mContext;
    private List<SignRecordEntity.ResultBean.DaatBean> dataList;


    public SignRecordAdapter(Context context, List<SignRecordEntity.ResultBean.DaatBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.mContext=context;
        this.dataList=items;
    }


    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SignRecordEntity.ResultBean.DaatBean item) {
        holder.setText(R.id.tv_name,item.getText());
        holder.setText(R.id.tv_time,item.getDate());
        if (!TextUtils.isEmpty(item.getPoints())){
            holder.setText(R.id.tv_sign,"+".concat(item.getPoints()));
        }
        if (item.getType() == 0){//日常签到
            holder.setText(R.id.tv_signType,mContext.getResources().getString(R.string.text_signed_type_one));
            holder.setBackgroundColor(R.id.tv_signType,mContext.getResources().getColor(R.color.green));
        }else if (item.getType() ==1){//连续签到奖励
            holder.setText(R.id.tv_signType,mContext.getResources().getString(R.string.text_signed_type_two));
            holder.setBackgroundColor(R.id.tv_signType,mContext.getResources().getColor(R.color.levelyellow));
        }else if (item.getType() == 2){//总签到奖励
            holder.setText(R.id.tv_signType,mContext.getResources().getString(R.string.text_signed_type_three));
            holder.setBackgroundColor(R.id.tv_signType,mContext.getResources().getColor(R.color.color_signed_all));
        }else {}
    }
}
