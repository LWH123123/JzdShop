package com.jzd.jzshop.ui.category;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.entity.AppUpdateEntity;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.CategoryEntity;
import com.jzd.jzshop.entity.HomeEntity;
import com.jzd.jzshop.entity.MessageCenterEntity;
import com.jzd.jzshop.ui.home.news.HomePageViewModel;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CategoryViewModel extends BaseViewModel<Repository> {
    private static final String TAG = CategoryViewModel.class.getSimpleName();
    public ObservableList<CategoryLeftItemViewModel> ac_category_ob = new ObservableArrayList<>();
    public ItemBinding<CategoryLeftItemViewModel> ac_category_ib = ItemBinding.of(com.jzd.jzshop.BR.categoryLeftIVM, R.layout.item_category_left);

    public ObservableList<CategoryRightItemViewModel> ac_container_ob = new ObservableArrayList<>();
    public ItemBinding<CategoryRightItemViewModel> ac_container_ib = ItemBinding.of(com.jzd.jzshop.BR.rightIVM, R.layout.item_category_right);

    public int pagesize = 10; //默认每页页数
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        public SingleLiveEvent<MessageCenterEntity.ResultBean> mMessageLiveData = new SingleLiveEvent<>();

    }

    public CategoryViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }


    public String getUserToken() {
        return model.getUserToken();
    }

    public void requestNetWork() {
        isShowDialog(false);
        addSubscribe(model.postCategoryData(), new ParseDisposableObserver<CategoryEntity>() {
            @Override
            public void onResult(CategoryEntity entity) {
                dismissDialog();
                //更新数据
                if(entity!=null&&entity.getResult()!=null) {
                    updateData(entity.getResult());
                    ArrayList<CategoryEntity.ResultBean> objects = new ArrayList<>();
                    objects.addAll(entity.getResult());
                    SPUtils.getInstance().putSerializableEntity(Constants.SP.CACHE_CATEGORY_DATA,objects);
                }

            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });
    }


    /**
     * 首次刷新数据
     * */
    public void updateData(List<CategoryEntity.ResultBean> entity){
        if(entity.size()==0){
            return;
        }
        ac_category_ob.clear();
        setContainerData(entity.get(0));
        for (CategoryEntity.ResultBean resultBean : entity) {
            CategoryLeftItemViewModel itemModel = new CategoryLeftItemViewModel(CategoryViewModel.this, resultBean);
            ac_category_ob.add(itemModel);
        }
    }



    /**
     * 设置子分类数据
     *
     * @param bean
     */
    protected void setContainerData(CategoryEntity.ResultBean bean) {
        ac_container_ob.clear();
        for (CategoryEntity.ResultBean.ChildrenBean childrenBean : bean.getChildren()) {
            CategoryRightItemViewModel itemModel = new CategoryRightItemViewModel(CategoryViewModel.this, childrenBean);
            ac_container_ob.add(itemModel);
        }
    }


    /**
     *  获取 消息总数
     * @param page
     */
    public void requestMessageData(final int page) {
        addSubscribe(model.postMessageData(model.getUserToken(), page, pagesize), new ParseDisposableTokenErrorObserver<MessageCenterEntity>() {
            @Override
            public void onResult(MessageCenterEntity messageCenterEntity) {
                super.onResult(messageCenterEntity);
                Log.d(TAG, "requestMessageData onSuccess:---->>>");
                if (messageCenterEntity != null) {
                    if (messageCenterEntity.getResult() != null) {
                        uc.mMessageLiveData.setValue(messageCenterEntity.getResult());
                    }
                }
                dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                Log.d(TAG, "onError:---->>>" + throwable.getMessage());
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                dismissDialog();
            }
        });
    }




}
