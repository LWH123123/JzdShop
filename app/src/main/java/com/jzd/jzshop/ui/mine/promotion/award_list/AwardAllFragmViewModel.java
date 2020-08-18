package com.jzd.jzshop.ui.mine.promotion.award_list;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentAwardListPageBinding;
import com.jzd.jzshop.entity.AwardListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lxb on 2020/2/11.
 * 邮箱：2072301410@qq.com
 * TIP：
 */
public class AwardAllFragmViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = AwardAllFragmViewModel.class.getSimpleName();
    private Context mContext;
    private FragmentAwardListPageBinding binding;

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<AwardListEntity.ResultBean> mLiveData = new SingleLiveEvent<>();
    }

    public AwardAllFragmViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(FragmentActivity activity, FragmentAwardListPageBinding binding) {
        this.mContext = activity;
        this.binding = binding;
    }

    /**
     * 获取 君子权记录 all
     */
    public void requestData(int status) {
        isShowDialog(false);
        addSubscribe(model.postAwardList(model.getUserToken(),status), new ParseDisposableTokenErrorObserver<AwardListEntity>() {
            @Override
            public void onResult(AwardListEntity awardListEntity) {
                super.onResult(awardListEntity);
                Log.d(TAG, "onSuccess:---->>>");
                if (awardListEntity != null) {
                    if (awardListEntity.getResult() != null) {
                        uc.mLiveData.setValue(awardListEntity.getResult());
                    }
                }
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
                startActivity(LoginVertifyActivity.class);
            }

            @Override
            public void onError(String errarMessage) {
                super.onError(errarMessage);
                dismissDialog();
            }
        });

    }
}
