package com.jzd.jzshop.ui.mine.merch;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.databinding.FragmentMerchBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;

import me.goldze.mvvmhabit.base.BaseModel;

@Deprecated
public class MerchViewModel  extends ToolbarViewModel {
    public MerchViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
    private FragmentMerchBinding binding;
    public void initToolbar(){
        setRightIconVisible(View.GONE);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        if(binding.web.canGoBack()){
            binding.web.goBack();
        }else {
            startActivity(MyPageActivity.class);
        }
    }
    public void setbing(FragmentMerchBinding binding) {
        this.binding=binding;
    }
}


