package com.jzd.jzshop.ui.order.aftersale;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityFillExpressBillBinding;
import com.jzd.jzshop.entity.OrderRefundProgressEntity;
import com.jzd.jzshop.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description: 填写快递单号/ 修改快递单号
 * @date :2020/1/17 16:53
 */
public class FillExpressBillAty extends BaseActivity<ActivityFillExpressBillBinding, FillExpressBillViewModel> implements View.OnClickListener {

    private OrderRefundProgressEntity.ResultBean.ExpressInfoBean expressInfoBean;
    private String order_id, refund_id;
    private List<OrderRefundProgressEntity.ResultBean.ExpressDataBean> expressData;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_fill_express_bill;
    }

    @Override
    public int initVariableId() {
        return BR.fillExpressBillVM;
    }

    @Override
    public FillExpressBillViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(FillExpressBillViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        getIntentData();
        viewModel.initToolBar(getResources().getString(R.string.apply_after_sale));
        viewModel.setBinding(mContext, binding);
        viewModel.requestData(expressInfoBean, order_id, refund_id,expressData);

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            expressInfoBean = (OrderRefundProgressEntity.ResultBean.ExpressInfoBean) bundle.getSerializable(Constants.BUNDLE_KEY_EXPRESS_INFO);
            expressData = (List<OrderRefundProgressEntity.ResultBean.ExpressDataBean>) bundle.getSerializable(Constants.BUNDLE_KEY_EXPRESS_DATA);
            order_id = bundle.getString(Constants.ORDER_ID);
            refund_id = bundle.getString(Constants.REFUND_ID);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.mLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Messenger.getDefault().send("submit_express", FillExpressBillViewModel.TOKEN_VIEWMODEL_SUBMIT_EXPRESS_REFRESH);
                finish();
            }
        });

        binding.tvReturnGoodsAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_returnGoodsAddress: //选填快递公司
                if (expressData != null && expressData.size() > 0) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < expressData.size(); i++) {
                        list.add(expressData.get(i).getExpress_name());
                    }
                    mShowDialog("选择快递公司", list, binding.tvReturnGoodsAddress);
                } else {
                }

                break;
            default:
                break;
        }

    }

    /**
     *  选择快递公司 Dialog
     * @param way
     * @param list
     * @param textView
     */
    private void mShowDialog(final String way, List<String> list, final TextView textView) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title(way)
                .items(list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Log.d(TAG,way +"onSelection:" + text + "position:" + position);
                        textView.setText(text);
                        dialog.dismiss();
                    }
                })
                .negativeText("取消");
        builder.build().show();
    }
}
