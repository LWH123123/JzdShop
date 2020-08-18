package com.jzd.jzshop.ui.goods;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.jzd.jzshop.entity.CommentsEntity;
import com.jzd.jzshop.entity.GoodsEntity;

import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class GoodsCommentsItemViewModel extends ItemViewModel<GoodsViewModel> {
    public ObservableField<GoodsEntity.ResultBean.CommentBean> entity=new ObservableField<>();
    private String gid;

    public GoodsCommentsItemViewModel(@NonNull GoodsViewModel viewModel, GoodsEntity.ResultBean.CommentBean bean, String gid) {
        super(viewModel);
        this.gid =gid;
        entity.set(bean);
    }

    public BindingCommand onClick=new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            // TODO: 2019/12/25  跳转评论页
        }
    });
}
