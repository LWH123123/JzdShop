package com.jzd.jzshop.ui.goods.goodslist;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.R;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentGoodsListBinding;
import com.jzd.jzshop.entity.GoodsListEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.goods.GoodsActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;


public class GoodsListViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = GoodsListViewModel.class.getSimpleName();
    public UIChangeObservable uc = new UIChangeObservable();    //封装一个界面发生改变的观察者
    private FragmentGoodsListBinding mBinding;
    private Context mContext;
    private String keywordsSearch;

    public void setBinding(FragmentGoodsListBinding binding, Context context) {
        this.mBinding = binding;
        this.mContext = context;
    }

    public class UIChangeObservable {
        //价格
        public SingleLiveEvent<Boolean> changePriceLableColor = new SingleLiveEvent<>();
        //排序 销量字段更新颜色
        public SingleLiveEvent<Boolean> changeSalesLableColor = new SingleLiveEvent<>();
        //综合
        public SingleLiveEvent<Boolean> changeAllLableColor = new SingleLiveEvent<>();
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
        public SingleLiveEvent<String> showTypeEvent = new SingleLiveEvent<>();

    }

    private boolean isRefreshing;
    private boolean isLoadmore;
    private String currentType=GoodsListItemViewModel.MultiRecyclerTypeList;
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    private String category_id,isnew,ishot,isrecommand,issendfree,keywords,order,by,merch_id;
    //用来记录加载参数  以便刷新的时候保存住对应的数据
    public void setRequestData(String categoryid,String isnew,String ishot,String isrecommand,String issendfree,String keywords,String order,String by,String merch_id){
        this.category_id=categoryid;
        this.isnew=isnew;
        this.ishot=ishot;
        this.isrecommand=isrecommand;
        this.issendfree=issendfree;
        this.keywords=keywords;
        this.order=order;
        this.by=by;
        this.merch_id=merch_id;
    }


    public String getPriceSortBy() {
        return priceSortBy;
    }

    //升序：asc； 降序：desc
    private String priceSortBy=Constants.SORT_ASC;
    private int page=1;
    public String getPage() {
        return page+"";
    }

    private ObservableField<GoodsListEntity.ResultBean> entity=new ObservableField<>();
    public String getNextPage() {
        return (++page)+"";
    }
    public ObservableList<GoodsListItemViewModel> fgl_goodslist_ob = new ObservableArrayList<>();
    public ItemBinding<GoodsListItemViewModel> fgl_goodslist_ib = ItemBinding.of(new OnItemBind<GoodsListItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, GoodsListItemViewModel item) {
            String itemType = (String) item.getItemType();
            if (itemType.equals(GoodsListItemViewModel.MultiRecyclerTypeList)) {
                itemBinding.set(com.jzd.jzshop.BR.glItemVM, R.layout.item_goods_list);
            } else if (itemType.equals(GoodsListItemViewModel.MultiRecyclerTypeGrid)) {
                itemBinding.set(com.jzd.jzshop.BR.glItemVM, R.layout.item_goods_grid);
            }
        }
    });

    public GoodsListViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    /**
     * 初始化Toolbar
     */
    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        setTitleText(text);
        setToolbarBgColor(Color.parseColor("#D90804"));
        setTitleTextColor(Color.parseColor("#FFFFFF"));
        setIvBackIsVisible(View.GONE);
        setIvBackWhiteIsVisible(View.VISIBLE);
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }
    public void requestGoodsList(String keywords) {
        keywordsSearch = keywords;
        requestGoodsList("1", null, null, null, null, keywords, null, null, null,true);
    }

    /**
     *
     * @param page 页数
     * @param isnew 每页的条数
     * @param ishot 热卖
     * @param isrecommand 推荐
     * @param issendfree 包邮
     * @param keywords 关键词
     * @param order 销量/价格
     * @param by 升序/降序
     * @param merch_id 店铺ID
     * @param isrefresh 是否需要打开进度条
     */
    public void requestGoodsList(String page, String isnew, String ishot, String isrecommand, String issendfree, String keywords, String order, String by, String merch_id,boolean isrefresh) {
        isShowDialog(isrefresh);
        addSubscribe(
                model.postGoodsList(page, isnew, ishot, isrecommand, issendfree, keywords, category_id, order, by, merch_id),
                new ParseDisposableObserver<GoodsListEntity>() {
                    @Override
                    public void onResult(GoodsListEntity goodsListEntity) {
                        dismissDialog();
                        //保存加载的参数,刷新时使用
                        setRequestData(category_id,isnew,ishot,isrecommand,issendfree,keywords,order,by,merch_id);
                        if (goodsListEntity != null) {
                            if(isRefreshing){
                                uc.finishRefreshing.call();
                                isRefreshing=false;
                            }
                            if(isLoadmore){
                                uc.finishLoadmore.call();
                                isLoadmore=false;
                            }else{
                                fgl_goodslist_ob.clear();
                            }
                            if (goodsListEntity.getResult()!=null){
                                if (goodsListEntity.getResult().getData()!=null && goodsListEntity.getResult().getData().size() > 0){
                                    for (GoodsListEntity.ResultBean.DataBean bean : goodsListEntity.getResult().getData()) {
                                        GoodsListItemViewModel model = new GoodsListItemViewModel(GoodsListViewModel.this, bean);
                                        model.multiItemType(currentType);
                                        fgl_goodslist_ob.add(model);
                                    }
                                }else {
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                        if(isRefreshing){
                            uc.finishRefreshing.call();
                            isRefreshing=false;
                        }
                        if(isLoadmore){
                            uc.finishLoadmore.call();
                            isLoadmore=false;
                        }
                    }
                });
    }


    public void startGood(String gid,String openflag){
        Bundle bundle = new Bundle();
        bundle.putString("gid", gid);
        bundle.putString(Constants.GOODS_OPEN_FLAG, openflag);
        startActivity(GoodsActivity.class, bundle);
    }


    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.d(TAG,"下拉刷新---->>>");
            page=1;
            isRefreshing=true;
            requestGoodsList("1",isnew,ishot,isrecommand,issendfree,keywords,order,by,merch_id,false);
        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            Log.d(TAG,"上拉加载---->>>");
            isLoadmore=true;
            requestGoodsList(getNextPage(), null, null, null, null, keywordsSearch, null, null, null,false);
        }
    });
    //综合
    public BindingCommand onClickSortAll =new BindingCommand(new BindingAction() {
        @Override
        public void call() {//相当于刷新
            if (keywordsSearch != null && !TextUtils.isEmpty(keywordsSearch)) {
                requestGoodsList("1", null, null, null, null, keywordsSearch, null, priceSortBy, null,false);
                uc.changeAllLableColor.setValue(true);
            }else {
                requestGoodsList("1", null, null, null, null, null, null, priceSortBy, null,false);
                uc.changeAllLableColor.setValue(true);
            }
        }
    });
    //销量
    public BindingCommand onClickSortSales = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mCommon();   //	升序：asc； 降序：desc
        }
    });

    private void mCommon() {
        if (keywordsSearch != null && !TextUtils.isEmpty(keywordsSearch)) {
            requestGoodsList("1", null, null, null, null, keywordsSearch, "salesreal", "desc", null,false);//销量为从高到低
            uc.changeSalesLableColor.setValue(true);
        }else {
            requestGoodsList("1", null, null, null, null, null, "salesreal", "desc", null,false);
            uc.changeSalesLableColor.setValue(true);
        }
    }

    //价格
    public BindingCommand onClickSortPrice =new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //	升序：asc； 降序：desc
            if(TextUtils.equals(priceSortBy, Constants.SORT_ASC)){
            }else{
            }
            if (keywordsSearch != null && !TextUtils.isEmpty(keywordsSearch)){
                priceSortBy=TextUtils.equals(priceSortBy, Constants.SORT_ASC)?Constants.SORT_DESC:Constants.SORT_ASC;
                requestGoodsList("1", null, null, null, null, keywordsSearch, "minprice", priceSortBy, null,false);
                uc.changePriceLableColor.setValue(true);
            }else {
                priceSortBy=TextUtils.equals(priceSortBy, Constants.SORT_ASC)?Constants.SORT_DESC:Constants.SORT_ASC;
                requestGoodsList("1", null, null, null, null, null, "minprice", priceSortBy, null,false);
                uc.changePriceLableColor.setValue(true);
            }
//            mCommon();//点击完价格，再点击销量需要点两次才能起作用
        }
    });
    public BindingCommand onClickShowType = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(fgl_goodslist_ob.size()!=0){
                currentType = ((String)fgl_goodslist_ob.get(0).getItemType()).equals(
                        GoodsListItemViewModel.MultiRecyclerTypeList) ?
                        GoodsListItemViewModel.MultiRecyclerTypeGrid :
                        GoodsListItemViewModel.MultiRecyclerTypeList;
            }
            uc.showTypeEvent.setValue(currentType);
            //循环设置布局类型 导致快速切换时点击无效
            for (GoodsListItemViewModel model : fgl_goodslist_ob) {
                model.multiItemType(currentType);
            }
        }
    });
}
