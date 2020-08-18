package com.jzd.jzshop.ui.mine.creditsign;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppViewModelFactory;
import com.jzd.jzshop.databinding.ActivityTimeSelectAtyBinding;

import java.lang.reflect.Field;
import java.util.Calendar;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class TimeSelectAty extends BaseActivity<ActivityTimeSelectAtyBinding,TimeSelectViewModel> {

    private DatePicker datePicker;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_time_select_aty;
    }

    @Override
    public int initVariableId() {
        return BR.timeselectVM;
    }

    @Override
    public TimeSelectViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(TimeSelectViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.setContext(this);
        viewModel.initToolBar("选择时间");
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        binding.tvTime.setText(year+"年"+month+"月");
        DatePicker datePicker= binding.tpSelect;
        if(datePicker!=null){
            ((ViewGroup)((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
//            ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            });
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.selectdate.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                int year = binding.tpSelect.getYear();
                int month = binding.tpSelect.getMonth();
                Intent intent = new Intent();
                intent.putExtra("YEAR",year);
                intent.putExtra("MONTH",month+1);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
