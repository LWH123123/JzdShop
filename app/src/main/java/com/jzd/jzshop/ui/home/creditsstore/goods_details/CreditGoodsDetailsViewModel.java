package com.jzd.jzshop.ui.home.creditsstore.goods_details;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityGoodsDetailsBinding;
import com.jzd.jzshop.entity.BaseShopSpecEntity;
import com.jzd.jzshop.entity.CreditDetailsLogsEntity;
import com.jzd.jzshop.entity.CreditGoodsDetailsEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.home.creditsstore.confirm_order.CreditConfirmOrderActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.dialog.BaseGoodsSpecDoalog;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;

import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description :
 * @date 2020/5/12.
 */
public class CreditGoodsDetailsViewModel extends ToolbarViewModel<Repository> {

    public ObservableField<CreditGoodsDetailsEntity.ResultBean> result = new ObservableField<>();

    public UIChangeObservable uc = new UIChangeObservable();

    private int page=0;
    private String gid;
    private ActivityGoodsDetailsBinding binding;
    private CreditGoodsDetailsActivity goodsDetailsActivity;

    public void setBinding(ActivityGoodsDetailsBinding binding, CreditGoodsDetailsActivity goodsDetailsActivity) {
        this.binding = binding;
        this.goodsDetailsActivity = goodsDetailsActivity;
    }

    public class UIChangeObservable {
        public SingleLiveEvent<CreditGoodsDetailsEntity.ResultBean> binddata = new SingleLiveEvent<>();//商品数据
        public SingleLiveEvent<CreditDetailsLogsEntity.ResultBean> logsdata = new SingleLiveEvent<>();//参与记录
    }

    public CreditGoodsDetailsViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    @Override
    public void setTitleText(String text) {
        super.setTitleText(text);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    /*积分商城 详情 接口*/
    public void requestWork(String gid) {
        this.gid=gid;
        //token 不是必传项
        KLog.a("我请求的商品gid为==>>>" + gid);
        addSubscribe(model.postCreditDetail(model.getUserToken(), gid, null), new ParseDisposableObserver<CreditGoodsDetailsEntity>() {
            @Override
            public void onResult(CreditGoodsDetailsEntity o) {
                dismissDialog();
                binding.idRl.setVisibility(View.VISIBLE);
                List<BaseShopSpecEntity> specs_list = o.getResult().getSpecs_list();
                for (int i = 0; i <specs_list.size() ; i++) {
                    specs_list.get(i).setType(String.valueOf(i));
                }
                result.set(o.getResult());
                uc.binddata.setValue(o.getResult());

            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                KLog.a("商品详情错误信息===>>>" + errarMessage);

            }
        });
    }

    /*积分商城 详情 参与记录*/
    public void partlogs(String gid) {
        this.gid=gid;
        page++;
        addSubscribe(model.postCreditDetailsLogs(gid, page, 10), new ParseDisposableObserver<CreditDetailsLogsEntity>() {
            @Override
            public void onResult(CreditDetailsLogsEntity o) {
                dismissDialog();
                CreditDetailsLogsEntity.ResultBean result = o.getResult();
                uc.logsdata.setValue(result);
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();

            }
        });


    }


    //加载更多
    public BindingCommand onClickLoadMore =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
          partlogs(gid);
        }
    });


    //立即兑换
    public BindingCommand onClicklNewConvert = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(result.get().getSpecs_list().size()!=0) {
                showDialogSpec();
            }else {
                /*String money = result.get().getMoney();
                String membermoney = result.get().getMember().getMoney();
                if(!TextUtils.isEmpty(money)&&!TextUtils.isEmpty(membermoney)){
                    double mon = Double.parseDouble(money);
                    double memmon = Double.parseDouble(membermoney);
                    if(mon>memmon){
                        ToastUtils.showLong("余额不足");
                        return;
                    }
                }*/
                String credit = result.get().getCredit();
                int points = result.get().getMember().getPoints();
                if(!TextUtils.isEmpty(credit)){
                    double cre = Double.parseDouble(credit);
                    if(cre>points){
                        ToastUtils.showLong("积分不足");
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, result.get().getGid());
                bundle.putString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE, "");
                startActivity(CreditConfirmOrderActivity.class, bundle);
            }
        }
    });

    private void showDialogSpec() {
        BaseGoodsSpecDoalog.showGoodsSpec(goodsDetailsActivity, result.get(), new BaseGoodsSpecDoalog.DialogClick() {
            @Override
            public void onCancelClickListener() {

            }

            @Override
            public void onSureClickListener(List<CreditGoodsDetailsEntity.ResultBean.SpecsDataBean> optionID) {
                //集合中只有一位元素
                KLog.a("选择的规格ID为=====>>>>"+optionID);
//                  ToastUtils.showLong("选择的规格ID为"+integer);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, result.get().getGid());
                if(optionID==null||optionID.size()==0){
                bundle.putString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE, "");
                }else {
                    bundle.putString(Constants.GOODS_KEY_CREDIT_GOODS_TYPE, String.valueOf(optionID.get(0).getOptionid()));
                }
                startActivity(CreditConfirmOrderActivity.class, bundle);
            }
        });
    }


}
