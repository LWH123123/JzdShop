package com.jzd.jzshop.ui;

import android.app.Application;
import android.support.annotation.NonNull;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityMoreProductJumpLinkBinding;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * @author LXB
 * @description:
 * @date :2019/12/10 11:00
 */
public class MoreProductJumpLinkViewModel extends ToolbarViewModel<Repository> {
    ActivityMoreProductJumpLinkBinding binding;
    private String url, title;

    public void setBinding(ActivityMoreProductJumpLinkBinding binding, String url, String title) {
        this.binding = binding;
        this.title = title;
        this.url = url;
    }

    public MoreProductJumpLinkViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void initToolbar(String title) {
        setTitleText(title);
    }

    //查看订单详情
    public BindingCommand onCLickSee = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setBackOnClick();
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BUNDLE_KEY_URL, url);
//            startContainerActivity(BaseWebviewFragment.class.getCanonicalName(), bundle);
        }
    });

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
