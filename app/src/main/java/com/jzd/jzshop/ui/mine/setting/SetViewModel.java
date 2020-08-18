package com.jzd.jzshop.ui.mine.setting;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentSetBinding;
import com.jzd.jzshop.ui.mine.setting.aboutapp.AboutappFragment;
import com.jzd.jzshop.ui.mine.setting.perfectdata.complete.CompleteAty;
import com.jzd.jzshop.utils.DataCleanManager;
import com.jzd.jzshop.utils.VersionUtils;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.jzd.jzshop.utils.notification.NotificationsUtils;
import com.slodonapp.ywj_release.wxapi.WechatInfoSpHelper;
import com.umeng.analytics.MobclickAgent;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class SetViewModel extends BaseViewModel<Repository> {

    private FragmentSetBinding binding;
    private SetFragment context;
    public ObservableField<String> version = new ObservableField<>("V " + VersionUtils.getVersionName());

    public SetViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public void setBinding(FragmentSetBinding binding, SetFragment context) {
        this.binding = binding;
        this.context = context;
    }

    public BindingCommand onClickBackListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finish();
        }
    });

    public BindingCommand onClickExitListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            MessageDialog.showCancelAndSureDialog(context.getContext(), "确认要退出登录吗？", "",
                    R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                        @Override
                        public void onCancelClickListener() {
                        }

                        @Override
                        public void onSureClickListener() {
                            SPUtils.getInstance().remove(WechatInfoSpHelper.WECHAT_SP_USER_KEY);
                            WechatInfoSpHelper.clearWxSp();
                            SPUtils.getInstance().clear();
                            model.saveUserToken("");

                            MobclickAgent.onProfileSignOff();  //账号退出时，调用之后不再发送账号相关内容。
                            String userToken = model.getUserToken();
                            finish();
                        }
                    });
        }

    });

    public BindingCommand onClickPerfectListeren = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            startContainerActivity(PerFectDataFragment.class.getCanonicalName());
            startActivity(CompleteAty.class);
        }
    });
    public BindingCommand onAboutAppClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startContainerActivity(AboutappFragment.class.getCanonicalName());
        }
    });

    //清理在使用时保存在SDCard中的数据
    public BindingCommand onClearBufferClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            final String cacheSize = getCacheSize();
            if (cacheSize.equals("0.0Byte")) {
                ToastUtils.showLong("已清理完毕");
                return;
            }
            MessageDialog.showCancelAndSureDialog(context.getContext(), "确认要清除数据吗？", "",
                    R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                        @Override
                        public void onCancelClickListener() {
                        }

                        @Override
                        public void onSureClickListener() {
                            cleanCache();
                            ToastUtils.showLong("清理了" + cacheSize);
                        }
                    });

        }
    });

    //是否开启消息通知
    public BindingCommand onClickOpenMessage = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            boolean isEnabled = NotificationsUtils.isNotificationEnabled(context.getContext());
            if (isEnabled){
                binding.tvMessage.setText(R.string.message_state_already_opened);
                NotificationsUtils.requestNotify(context.getContext());
            }else {
                binding.tvMessage.setText(R.string.message_state_not_opened);
                NotificationsUtils.requestNotify(context.getContext());
            }
        }
    });


    private String getCacheSize() {
        String str = "";
        try {
            str = DataCleanManager.getTotalCacheSize(context.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    //清空缓存
    private void cleanCache() {
        DataCleanManager.clearAllCache(context.getContext());
    }


}
