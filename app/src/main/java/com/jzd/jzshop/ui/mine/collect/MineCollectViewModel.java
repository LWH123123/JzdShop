package com.jzd.jzshop.ui.mine.collect;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.databinding.FragmentMineCollectBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.base.BaseModel;

public class MineCollectViewModel extends ToolbarViewModel {
    public MineCollectViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }
   private FragmentMineCollectBinding binding;

    public void initToolbar(){
        setRightIconVisible(View.GONE);
        setTitleText("我的收藏");

    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();

    }


    public void setBing(FragmentMineCollectBinding binding) {
        this.binding=binding;
    }
}
