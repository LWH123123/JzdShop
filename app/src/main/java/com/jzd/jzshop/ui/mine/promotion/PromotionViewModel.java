package com.jzd.jzshop.ui.mine.promotion;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentPromotionBinding;
import com.jzd.jzshop.entity.TuiguangEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.home.invite_friends.InviteFriendsActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.mine.promotion.awarddetail.AwardDetailFragment;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;


public class PromotionViewModel extends ToolbarViewModel<Repository> {

    private FragmentPromotionBinding binding;
    public ObservableField<TuiguangEntity.ResultBean> entity = new ObservableField<>();

    public PromotionViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void initToolbar() {
        setRightIconVisible(View.GONE);
        setTitleText("推广中心");
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void setBinding(FragmentPromotionBinding binding) {
        this.binding = binding;
    }

    public void requestwork() {
        addSubscribe(model.postMyTui(model.getUserToken()), new ParseDisposableTokenErrorObserver<TuiguangEntity>() {
            @Override
            public void onResult(TuiguangEntity o) {
                super.onResult(o);
                dismissDialog();
                entity.set(o.getResult());
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }
        });
    }

    public BindingCommand onCliclorderListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(AwardDetailFragment.class.getCanonicalName());
        }
    });
    //推广中心
    public BindingCommand romotionCenterOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(InviteFriendsActivity.class);
        }
    });
}
