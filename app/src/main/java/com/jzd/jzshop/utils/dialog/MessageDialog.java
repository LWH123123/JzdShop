package com.jzd.jzshop.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.BaseShopSpecEntity;
import com.jzd.jzshop.entity.CreditGoodsDetailsEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description: 消失提示/确认 弹窗
 * @date :2019/12/11 17:04
 */
public class MessageDialog {
    /**
     * 显示错误信息、提示信息的dialog（不带标题）
     *
     * @param mContext
     * @param message
     */
    public static void showMessageDialog(Context mContext, String title, String message, final MessageDialogClick messageDialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_prompt, null);
        builder.setView(view);
        builder.setCancelable(false);
        TextView tvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        tvPrompt.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        final AlertDialog dialog = builder.create();
        tvPrompt.setText(message);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                messageDialogClick.onSureClickListener();
            }
        });
        dialog.show();
    }

    /**
     *  警示弹窗
     * @param mContext
     * @param title
     * @param message
     * @param messageDialogClick
     */
    public static void showWarningDialog(Context mContext, String title, String message, final MessageDialogClick messageDialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_warning, null);
        builder.setView(view);
        builder.setCancelable(false);
        TextView tvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        tvPrompt.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        final AlertDialog dialog = builder.create();
        tvPrompt.setText(message);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                messageDialogClick.onSureClickListener();
            }
        });
        dialog.show();
    }


    public static void showConfirmAndCancelDialog(Context context, String title, String content, final DialogClick dialogClick) {
        showCancelAndSureDialog(context, title, content, 0, 0, dialogClick);
    }

    /**
     * 取消、确定的dialog
     *
     * @param context
     * @param title
     * @param content
     * @param cancelColor
     * @param sureColor
     * @param dialogClick
     */
    public static void showCancelAndSureDialog(Context context, String title, String content, int cancelColor, int sureColor, final DialogClick dialogClick) {
        final Dialog dialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_confirm_cancel);
        dialog.setCancelable(false);
        TextView dialogTitle = dialog.findViewById(R.id.tv_dialog_title);
        TextView dialogContent = dialog.findViewById(R.id.tv_dialog_content);
        dialogContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        final TextView dialogCancel = dialog.findViewById(R.id.tv_dialog_cancel);
        TextView dialogSure = dialog.findViewById(R.id.tv_dialog_sure);

        if (TextUtils.isEmpty(title)) {
            dialogTitle.setVisibility(View.GONE);
        } else {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            dialogContent.setVisibility(View.GONE);
        } else {
            dialogContent.setVisibility(View.VISIBLE);
            dialogContent.setText(content);
        }
        dialogCancel.setText("取消");
//        dialogCancel.setTypeface(Typeface.DEFAULT);
        if (cancelColor == 0) {
            dialogCancel.setTextColor(context.getResources().getColor(R.color.level2text));
        } else {
            dialogCancel.setTextColor(context.getResources().getColor(R.color.level2text));
        }
        dialogSure.setText("确认");
//        dialogSure.setTypeface(Typeface.DEFAULT);
        if (sureColor == 0) {
            dialogSure.setTextColor(context.getResources().getColor(R.color.colorred));
        } else {
            dialogSure.setTextColor(context.getResources().getColor(R.color.colorred));
        }

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialogClick.onCancelClickListener();
            }
        });

        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialogClick.onSureClickListener();
            }
        });
        dialog.show();
    }


    //积分签到 点击签到的弹窗
    public static void showSignClickDialog(Context context, String message, final DialogClick dialogClick) {
        final Dialog dialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_sign);
        dialog.setCancelable(false);
        TextView tvmessage = dialog.findViewById(R.id.tv_message);
        TextView draw = dialog.findViewById(R.id.tv_draw);
        tvmessage.setText(message);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialogClick.onSureClickListener();
            }
        });

        ImageView close = dialog.findViewById(R.id.iv_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    //积分商城  确认订单  是否需要兑换弹窗
    public static void showIsgetShop(Context context,String message,final DialogClick dialogClick){
        final Dialog dialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_isget_shop);
        dialog.setCancelable(false);
        TextView tvmessage = dialog.findViewById(R.id.tv_message);
        TextView sure = dialog.findViewById(R.id.tv_dialog_sure);
        tvmessage.setText(message);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialogClick.onSureClickListener();
            }
        });

        TextView cancel = dialog.findViewById(R.id.tv_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }






/*
    */
/*商品规格*//*

    public static void showGoodsSpec(Activity context, CreditGoodsDetailsEntity.ResultBean data, final DialogClick dialogClick){
        final Dialog dialog = new BaseDialog(context, R.style.SpecDialog, R.layout.dialog_goods_spec);
        dialog.setCancelable(false);
        ImageView thume = dialog.findViewById(R.id.iv_thume);
        TextView price = dialog.findViewById(R.id.tv_price);
        TextView total = dialog.findViewById(R.id.tv_total);
        TextView addcar = dialog.findViewById(R.id.tv_addcar);
        TextView nowbuy = dialog.findViewById(R.id.tv_nowbuy);
        ImageButton close = dialog.findViewById(R.id.ib_close);
        RecyclerView re_view = dialog.findViewById(R.id.ps_spec_recycler);
        List<BaseShopSpecEntity> specs_list = data.getSpecs_list();
        re_view.setLayoutManager(new LinearLayoutManager(context));
        BaseSpecAdapter baseSpecAdapter = new BaseSpecAdapter(context, specs_list);
        re_view.setAdapter(baseSpecAdapter);
        baseSpecAdapter.notifyDataSetChanged();
        baseSpecAdapter.setOnitemSelectClick(new BaseSpecAdapter.OnitemSelectClick() {
            @Override
            public void OnitemClick(HashMap<String, Integer> hashMap, BaseShopSpecEntity.ItemsBean itemsBean) {
                if(!TextUtils.isEmpty(itemsBean.getThumb())){
                    Glide.with(context).load(itemsBean.getThumb()).into(thume);
                }
                if(hashMap.size()==specs_list.size()){//规格选择完毕
                    //按照顺序重组商品规格 lid 在全数据规格中查找到对应的真实规格ID
                    getOptionid(hashMap, data);
                }
            }
        });
        baseSpecAdapter.setOnitemUnSelectClick(new BaseSpecAdapter.OnitemUnSelectClick() {
            @Override
            public void OnitemUnSelectClick(BaseShopSpecEntity.ItemsBean itemsean) {
                Glide.with(context).load(data.getThumb()).into(thume);
            }
        });

        nowbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.a("数量为===>>>"+baseSpecAdapter.hash.size());
                if(baseSpecAdapter.hash.size()==specs_list.size()){
                    ToastUtils.showLong("规格已经选择好了");
                }else {
                    ToastUtils.showLong("请选择规格!");
                }
            }
        });

        nowbuy.setText("立即兑换");
        addcar.setVisibility(View.GONE);
        Glide.with(context).load(data.getThumb()).into(thume);
        price.setText(data.getCredit()+"积分");
        total.setText("库存"+String.valueOf(data.getTotal())+"件");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialogClick.onSureClickListener();
            }
        });
        dialog.show();

    }

    //获取真实的optionID
    private static void   getOptionid(HashMap<String, Integer> hashMap, CreditGoodsDetailsEntity.ResultBean data) {
        ArrayList<Integer> datavalue = new ArrayList<>();
        for (HashMap.Entry<String, Integer> entry : hashMap.entrySet()){
            datavalue.add(entry.getValue());
        }
        Collections.sort(datavalue);
        StringBuffer sb = new StringBuffer();
        for (Integer value:datavalue) {
           sb.append(value+"_");
        }
        sb.deleteCharAt(sb.length()-1);
        KLog.a("组合的optionID为"+sb.toString());
        List<CreditGoodsDetailsEntity.ResultBean.SpecsDataBean> specs_data = data.getSpecs_data();
        for (int i = 0; i <specs_data.size() ; i++) {
            CreditGoodsDetailsEntity.ResultBean.SpecsDataBean specsDataBean = specs_data.get(i);
            String specs_id = specsDataBean.getSpecs_id();
            if(specs_id.equals(sb.toString())){
                KLog.a("获取的规格id为"+specsDataBean.getOptionid());
            }
        }
    }
*/


    /**
     * dialog取消、确定的点击事件监听
     */
    public interface DialogClick {
        void onCancelClickListener();

        void onSureClickListener();
    }

    public interface MessageDialogClick {
        void onSureClickListener();
    }

}
