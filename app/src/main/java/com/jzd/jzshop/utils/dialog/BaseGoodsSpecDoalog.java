package com.jzd.jzshop.utils.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.BaseShopSpecEntity;
import com.jzd.jzshop.entity.CreditGoodsDetailsEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description : 公用的商品规格弹窗
 * @date 2020/5/14.
 */
public class BaseGoodsSpecDoalog  {

    //价格
    private static TextView price;
    //库存
    private static TextView total;
    //选择好的规格信息
    private static ArrayList<CreditGoodsDetailsEntity.ResultBean.SpecsDataBean> dataBeans;

    /*商品规格*/
    public static void showGoodsSpec(Activity context, CreditGoodsDetailsEntity.ResultBean data, final DialogClick dialogClick){

        dataBeans = new ArrayList<>();
        if(dataBeans.size()!=0){
            dataBeans.clear();
        }
        final Dialog dialog = new BaseDialog(context, R.style.SpecDialog, R.layout.dialog_goods_spec);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        if(window!=null) {
            window.setWindowAnimations(R.style.myPopwindowAnimStyle);
            window.setGravity(Gravity.BOTTOM);
        }
        ImageView thume = dialog.findViewById(R.id.iv_thume);
        price = dialog.findViewById(R.id.tv_price);
        total = dialog.findViewById(R.id.tv_total);
        TextView addcar = dialog.findViewById(R.id.tv_addcar);
        TextView nowbuy = dialog.findViewById(R.id.tv_nowbuy);
        ImageButton close = dialog.findViewById(R.id.ib_close);
        RecyclerView re_view = dialog.findViewById(R.id.ps_spec_recycler);
        List<BaseShopSpecEntity> specs_list = data.getSpecs_list();
        if (specs_list != null && specs_list.size() >0) {
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
                    if(dataBeans.size()!=0) {
                        dataBeans.clear();
                    }
                    price.setText(data.getCredit()+"积分");
                    total.setText("库存"+data.getTotal()+"件");
                    Glide.with(context).load(data.getThumb()).into(thume);
                }
            });
        }
        nowbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = data.getMember().getMoney();//用户金额
                int credit = data.getMember().getPoints();//用户积分
                String money2 = data.getMoney();//兑换所需金额
                String credit2 = data.getCredit();//兑换所需积分


                if(data.getSpecs_list().size()==0){
                    if(data.getTotal()==0){
                        ToastUtils.showLong("库存不足!");
                    }
                    double mymoney = Double.parseDouble(money);
                    double youmoney = Double.parseDouble(money2);
                    double youcredit = Double.parseDouble(credit2);
                    if(mymoney<youmoney){
                        ToastUtils.showLong("余额不足!");
                        return;
                    }
                    if(credit<youcredit){
                        ToastUtils.showLong("积分不足!");
                        return;
                    }
                    dialog.cancel();
                    dialogClick.onSureClickListener(dataBeans);
                    return;
                }
                if(data.getSpecs_data()==null||data.getSpecs_data().size()==0){
                    return;
                }
                if(dataBeans==null||dataBeans.size()==0){
                    ToastUtils.showLong("请选择相应的规格");
                    return;
                }
                CreditGoodsDetailsEntity.ResultBean.SpecsDataBean specsDataBean = dataBeans.get(0);
                if(specsDataBean.getTotal().equals("0")){
                    ToastUtils.showLong("库存不足!");
                    return;
                }
                if(!TextUtils.isEmpty(money)){//假如 积分和金额 不足时不允许通过
                    double mymoney = Double.parseDouble(money);
                    String money1 = specsDataBean.getMoney();
                    double youmoney = Double.parseDouble(money1);
                    int credit1 = specsDataBean.getCredit();
                    KLog.a("兑换需要的积分为=="+credit1+"    需要的金额为==="+youmoney);
                    if(credit<credit1){
                        ToastUtils.showLong("积分不足!");
                        return;
                    }
                  /*  if(mymoney<youmoney){
                        ToastUtils.showLong("金额不足");
                        return;
                    }*/
                }
                dialog.cancel();
                dialogClick.onSureClickListener(dataBeans);
            }
        });

        nowbuy.setText("立即兑换");
        addcar.setVisibility(View.GONE);
        Glide.with(context).load(data.getThumb()).into(thume);
        price.setText(data.getCredit()+"积分");
        total.setText("库存"+data.getTotal()+"件");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialogClick.onCancelClickListener();
            }
        });
        dialog.show();

        //dialog 默认显示宽度不会铺满 需要做处理
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    //获取真实的optionID
    private static void  getOptionid(HashMap<String, Integer> hashMap, CreditGoodsDetailsEntity.ResultBean data) {
        if(dataBeans.size()!=0){
            dataBeans.clear();
        }
        ArrayList<Integer> datavalue = new ArrayList<>();
        for (HashMap.Entry<String, Integer> entry : hashMap.entrySet()){
            datavalue.add(entry.getValue());
        }
        Collections.sort(datavalue);
        Collections.reverse(datavalue);
        StringBuffer sb = new StringBuffer();
        for (Integer value:datavalue) {
            sb.append(value+"_");
        }
        sb.deleteCharAt(sb.length()-1);
        KLog.a("组合的optionID"+sb.toString());
        List<CreditGoodsDetailsEntity.ResultBean.SpecsDataBean> specs_data = data.getSpecs_data();
        for (int i = 0; i <specs_data.size() ; i++) {
            CreditGoodsDetailsEntity.ResultBean.SpecsDataBean specsDataBean = specs_data.get(i);
            String specs_id = specsDataBean.getSpecs_id();
            if(specs_id.equals(sb.toString())){
                KLog.a("获取的规格id为"+specsDataBean.getOptionid());
                dataBeans.add(specsDataBean);
                String pricetext="¥"+specsDataBean.getMoney()+"+"+specsDataBean.getCredit()+"积分";
                price.setText(pricetext);
                total.setText("库存: "+specsDataBean.getTotal()+"件");
            }
        }
    }


    public interface DialogClick {
        void onCancelClickListener();

        void onSureClickListener(List<CreditGoodsDetailsEntity.ResultBean.SpecsDataBean> option);
    }

    public interface MessageDialogClick {
        void onSureClickListener();
    }


}
