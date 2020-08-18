package com.jzd.jzshop.ui.home.creditsstore.express;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityCreditShopExpressAtyBinding;
import com.jzd.jzshop.entity.CreditExpressEntity;
import com.jzd.jzshop.ui.home.creditsstore.PartakeRecordViewModel;
import com.jzd.jzshop.utils.Constants;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 积分商城  物流详情
 */
public class CreditShopExpressAty extends BaseActivity<ActivityCreditShopExpressAtyBinding,CreditShopExpressViewModel> {
    private String log_id;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_credit_shop_express_aty;
    }

    @Override
    public int initVariableId() {
        return BR.creditshopexpressVM;
    }

    @Override
    public CreditShopExpressViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(CreditShopExpressViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setTitleText("物流信息");
        getIntentData();
        viewModel.requestWork(log_id,null);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            log_id = bundle.getString(Constants.GOODS_KEY_GID);
            Log.d(TAG, "log_id====:" + log_id);
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.updatedata.observe(this, new Observer<CreditExpressEntity.ResultBean>() {
            @Override
            public void onChanged(@Nullable CreditExpressEntity.ResultBean resultBean) {

                int statusstr = resultBean.getStatus();
                switch (statusstr){
                    case 0://
                        binding.tvStatus.setVisibility(View.INVISIBLE);
                        break;
                    case 1://备货
                        binding.tvStatus.setText("备货中");
                        break;
                    case 2:
                        binding.tvStatus.setText("配送中");
                        break;
                    case 3:
                        binding.tvStatus.setText("已签收");
                        break;
                }
                if(!TextUtils.isEmpty(resultBean.getExpress_com())){
                binding.tvProfile.setText("快递公司:"+resultBean.getExpress_com());
                }
                if(!TextUtils.isEmpty(resultBean.getExpress_sn())){
                binding.tvFormcard.setText("快递单号:"+resultBean.getExpress_sn());
                }
                if (resultBean.getData().size() != 0) {
                binding.recycle.setVisibility(View.VISIBLE);
                }
                List<CreditExpressEntity.ResultBean.DataBean> data = resultBean.getData();
                binding.recycle.setLayoutManager(new LinearLayoutManager(CreditShopExpressAty.this));
                CreditExpressAdapter creditExpressAdapter = new CreditExpressAdapter();
                creditExpressAdapter.setList(data,CreditShopExpressAty.this);
                binding.recycle.setAdapter(creditExpressAdapter);
            }
        });

    }
}
