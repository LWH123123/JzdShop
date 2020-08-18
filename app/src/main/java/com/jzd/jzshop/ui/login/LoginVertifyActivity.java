package com.jzd.jzshop.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityHomeBinding;
import com.jzd.jzshop.databinding.ActivityLoginVertifyBinding;
import com.jzd.jzshop.ui.home.HomeActivity;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.mine.MyActivity;
import com.jzd.jzshop.utils.BigBackgroundPicFitUtils;
import com.jzd.jzshop.utils.Constants;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.systembar.StatusBarUtil;

/**
 * @author LXB
 * @description:
 * @date :2019/12/2 13:56
 */
public class LoginVertifyActivity extends BaseActivity<ActivityLoginVertifyBinding, LoginVertifyViewModel> {

    private String sign;
    private TimeCount timeCount;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login_vertify;
    }
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public LoginVertifyViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,factory).get(LoginVertifyViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //添加 activity 进出动画
            getWindow().setEnterTransition(new Slide().setDuration(1000));
            getWindow().setExitTransition(new Slide().setDuration(1000));
        }
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            sign = extras.getString(Constants.CAR_LOGIN);
            viewModel.setSign(sign);
        }
        viewModel.setBinding(binding,this);
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        StatusBarUtil.setStatusBarDarkTheme(this,false);
//        BigBackgroundPicFitUtils.scaleImage(this,binding.ivBg, R.mipmap.bg_login); //大图适配方法有问题，后期处理bug,采用此方法
        binding.btnMakeSure.setCardBackgroundColor(getResources().getColor(R.color.lightpink));
        binding.btnMakeSure.getBackground().setAlpha(178);
        binding.afoPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String trim = binding.afoPhone.getText().toString().trim();
                if(trim.length()<=11){
                    binding.btnMakeSure.setCardBackgroundColor(getResources().getColor(R.color.lightpink));
                    binding.btnMakeSure.setClickable(false);
                }else {
                    binding.btnMakeSure.setClickable(true);
                    binding.btnMakeSure.setCardBackgroundColor(getResources().getColor(R.color.login_button));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = binding.afoPhone.getText().toString().trim();
                 if(trim.length()==11){
                     binding.btnMakeSure.setClickable(true);
                     binding.btnMakeSure.setCardBackgroundColor(getResources().getColor(R.color.login_button));
                 }

            }
        });
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.getvertify.observe(LoginVertifyActivity.this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                timeCount = new TimeCount(binding.btnVerifyCode);
                binding.btnVerifyCode.setClickable(false);
                timeCount.start();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() { //返回
            @Override
            public void onClick(View v) {
                if(sign!=null&&!TextUtils.isEmpty(sign)){
                    startActivity(HomePageActivity.class);
                    return;
                }
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(sign!=null&&!TextUtils.isEmpty(sign)){
            startActivity(HomePageActivity.class);
            return;
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
        if(timeCount!=null){
            timeCount.cancel();
        }
    }

    public class  TimeCount extends CountDownTimer{
        TextView text;
        public TimeCount(TextView text) {
            super(60000, 1000);
            this.text = text;
        }
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            text.setTextColor(getResources().getColor(R.color.level1text));
            text.setText("剩余"+millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            text.setText("获取验证码");
            text.setClickable(true);
            if(text.getText().toString().length()>=4){
                text.setTextColor(getResources().getColor(R.color.white));
            }

        }
    }


}
