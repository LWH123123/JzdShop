package com.jzd.jzshop.ui.home.invite_friends;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityInviteFriendsBinding;
import com.jzd.jzshop.entity.PosterNewEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LXB
 * @description:
 * @date :2020/1/9 12:20
 */
public class InviteFriendsViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = InviteFriendsViewModel.class.getSimpleName();
    private Context context;
    private ActivityInviteFriendsBinding binding;


    //绑定数据
    public ObservableField<String> inviteCode = new ObservableField<>();
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<List<String>> xbannerLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent btnPicClick = new SingleLiveEvent<>();
        public SingleLiveEvent btnshareWXClick = new SingleLiveEvent<>();

    }

    public InviteFriendsViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setBinding(Context context, ActivityInviteFriendsBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
        setToolbarBgColor(Color.parseColor("#FE5D53"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void requestData() {
        isShowDialog(false);
        String userToken = model.getUserToken();
        addSubscribe(model.postPosterData(userToken), new ParseDisposableTokenErrorObserver<PosterNewEntity>() {
            @Override
            public void onResult(PosterNewEntity posterNewEntity) {
                super.onResult(posterNewEntity);
                Log.d(TAG, "onSuccess:---->>>");
                PosterNewEntity.ResultBean result = posterNewEntity.getResult();
                if (result != null) {
                   if (!TextUtils.isEmpty(String.valueOf(result.getInvit_id()))){
                       inviteCode.set(String.valueOf(result.getInvit_id()));
                   }else {}
                }
                if (posterNewEntity != null) {
                    ArrayList<String> bannerData = new ArrayList<>();
                    bannerData.clear();

                    if (result.getPoster() != null && result.getPoster().size() > 0) {
                        for (String url: result.getPoster()) {
                            bannerData.add(url);
                        }
                    }
                    if (bannerData.size() != 0) {
                        uc.xbannerLiveData.setValue(bannerData);
                    }
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError:---->>>" + e.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }
        });

    }

    //复制
    public BindingCommand copyInviteClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() { // 全局复制粘贴板
            if(inviteCode.get()==null){
                return;
            }
            String text = inviteCode.get().toString();
            if (text != null && !TextUtils.isEmpty(text)) {
                SystemUtils.copyTextClipboard(context,text);
                ToastUtils.showShort(R.string.invite_code_msg_succ);
                return;
            }else {
                ToastUtils.showShort(R.string.invite_code_msg_tip);
                return;
            }
        }
    });


    public BindingCommand savePicClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.btnPicClick.call();

        }
    });

    public BindingCommand shareWeinXinClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.btnshareWXClick.call();

        }
    });

}
