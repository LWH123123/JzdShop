package com.jzd.jzshop.chat;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.chat.model.PopGoodsSpecItemViewModelChat;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActiivtyOpenChatBinding;
import com.jzd.jzshop.entity.GoodsEntity;
import com.jzd.jzshop.entity.GoodsSpecEntity;
import com.jzd.jzshop.entity.RechargeRecordEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * @author LXB
 * @description:
 * @date :2020/4/21 18:16
 */
public class OpenChatViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = OpenChatViewModel.class.getSimpleName();

    private Context context;
    private ActiivtyOpenChatBinding mBinding;
    //分页加载字段-------------
    public int pagesize = 10; //默认每页页数
    private SmartRefreshLayout mRefreshLayout;
    private List<RechargeRecordEntity.ResultBean> totalList = new ArrayList();
    // ====================添加购物车====================
    public ItemBinding<PopGoodsSpecItemViewModelChat> pg_spec_ib = ItemBinding.of(BR.specIVM, R.layout.pop_item_spec_chat);
    //属性
    public ObservableList<PopGoodsSpecItemViewModelChat> pg_spec_ob = new ObservableArrayList<>();
    public ObservableField<GoodsEntity.ResultBean> entity = new ObservableField<>();
    public ObservableField<GoodsSpecEntity.ResultBean> specEntity = new ObservableField<>();
    //存放规格组数据
    public HashMap<String, String> spec = new HashMap<>();
    //规格ID的组合（将规格ID按降序的形式拼接成的字符串，多个之间用“_”拼接）
    public ArrayList<String> specs_id = new ArrayList<>();

    public ObservableInt myTotal = new ObservableInt(1);
    public ObservableField<Integer> badgenumber = new ObservableField<>();
    private ObservableField<String> gids = new ObservableField<>();
    protected String gid, openFlag, chat_id, show_price;
    // ====================添加购物车====================
    private String mOpenFlag;


    public UIChangeObservable uc = new UIChangeObservable();

    public void setFlag(String openFlag) {
        this.mOpenFlag = openFlag;
    }

    public class UIChangeObservable {
        public SingleLiveEvent<GoodsEntity.ResultBean> mGoodsLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<String> showSpecPopwindow = new SingleLiveEvent<>();
        public SingleLiveEvent<String> closePopwindow = new SingleLiveEvent<>();
    }

    public OpenChatViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public String getToken() {
        return model.getUserToken();
    }

    public void setGid(String gid) {
        this.gid = gid;
        KLog.a("商品详情得gid:", gid);
        this.gids.set(gid);
        int shopCarnumber = model.getShopCarnumber();
        this.badgenumber.set(shopCarnumber);
    }

    public void setBinding(Context mContext, ActiivtyOpenChatBinding binding) {
        this.context = mContext;
        this.mBinding = binding;
    }

    @Override
    protected void initToolBar(String text) {
        super.initToolBar(text);
        if (!TextUtils.isEmpty(mOpenFlag)){
            if (mOpenFlag.equals("jpush") || mOpenFlag.equals("message_center") || mOpenFlag.equals("order_detail")) {
                setRightIconVisible(View.GONE);
            } else {//商品详情页 也隐掉
//                setRightIconVisible(View.VISIBLE);
                setRightIconVisible(View.GONE);
            }
        }
    }

    /**
     * 获取商品详情页数据 （ 直接传递过了的 商品详情页 实体无法序列化，所有直接请求接口获取）
     *
     * @param gids
     */
    public void requestGoodsDetails(String gids) {
        isShowDialog(false);
        addSubscribe(model.postGoods(model.getUserToken(), gids, null,null),
                new ParseDisposableObserver<GoodsEntity>() {
                    @Override
                    public void onResult(GoodsEntity result) {
                        dismissDialog();
                        String name = result.getResult().getShop_info().getName();
                        if(!TextUtils.isEmpty(name)){
                            setTitleText(name);
                        }

                        result.getResult().setPrice(result.getResult().getShow_price());
                        result.getResult().setTotal("");
                        entity.set(result.getResult());
                        requestGoodsSpce(gid);
                        uc.mGoodsLiveData.setValue(result.getResult());
                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                    }
                });
    }


    public void requestGoodsSpce(String gid) {
        KLog.a("规格", gid);
        isShowDialog(false);
        addSubscribe(model.postGoodsSpce(model.getUserToken(), gid), new ParseDisposableTokenErrorObserver<GoodsSpecEntity>() {
            @Override
            public void onResult(GoodsSpecEntity entitys) {
                dismissDialog();
                List<GoodsSpecEntity.ResultBean.ListBean> list = entitys.getResult().getList();
                for (int i = 0; i <list.size() ; i++) {
                    list.get(i).setType(String.valueOf(i));
                }
                specEntity.set(entitys.getResult());
                setDefault();
                for (GoodsSpecEntity.ResultBean.ListBean listBean : entitys.getResult().getList()) {
                    pg_spec_ob.add(new PopGoodsSpecItemViewModelChat(OpenChatViewModel.this, listBean));
                }
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
            }
        });
    }

    public void setDefault() {
        if (specEntity.get() != null) {
            entity.get().setPrice(specEntity.get().getDefault_price());
            entity.get().setTotal(specEntity.get().getTotal());
            entity.get().setUrl(specEntity.get().getThumb());
            entity.notifyChange();
        }
    }

    //获取选择的规格组数据
    public void setSpecData(String title, String lid) {
        boolean b = spec.containsValue(lid);
        if (b) {
            spec.remove(title);
            setDefault();
            return;
        }
        spec.put(title, lid);
        KLog.a("map的长度为"+spec.size());
    }


    //弹窗中加入购物车
    public BindingCommand onClickPopAddShoppingCar = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (specEntity.get() != null && specEntity.get().getList() != null && specEntity.get().getList().size() != spec.size()) {
                ToastUtils.showLong("请选择商品规格!");
                return;
            }
            if(entity.get().getTotal().equals("0")){
                return;
            }
            requestAddShoppingcar();
        }
    });

    //++
    public BindingCommand onClickAdd = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            int total = Integer.parseInt(entity.get().getTotal());
            if(total==0){
                return;
            }
            if ((myTotal.get() + 1) <= total) {
                myTotal.set(myTotal.get() + 1);
            } else {
                ToastUtils.showShort("超出库存数量");
            }
        }
    });


    //--
    public BindingCommand onClickSubtrect = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            int total = Integer.parseInt(entity.get().getTotal());
            if(total==0){
                return;
            }
            if ((myTotal.get() - 1) >= 1) {
                myTotal.set(myTotal.get() - 1);
            }
        }
    });

    //弹窗中立即购买
    public BindingCommand onClickPopBuynow = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (specEntity.get() != null && specEntity.get().getList() != null && specEntity.get().getList().size() != spec.size()) {
                ToastUtils.showLong("请选择商品规格!");
                return;
            }
            if(entity.get().getTotal().equals("0")){
                return;
            }
            if (!TextUtils.isEmpty(model.getUserToken())) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.TYPE, 0);
                model.saveShopID(gid);
                model.saveOptionID(getOptionID());
                model.saveGoodsNumber(String.valueOf(myTotal.get()));
                model.saveGiftID(getGiftID());
                model.saveOpenFlag(openFlag);//保存打开标识
                startActivity(FirmOrderActivity.class, bundle);
                uc.closePopwindow.call();
            } else {
                startActivity(LoginVertifyActivity.class);
                return;
            }
        }
    });

    /**
     * 获取赠品id
     *
     * @return
     */
    public String getGiftID() {
        for (GoodsEntity.ResultBean.GiftInfoBean bean : entity.get().getGift_info()) {
            return bean.getGift_id();
        }
        return null;
    }


    //请求添加到购物车
    public void requestAddShoppingcar() {
        String optionID = getOptionID();
        if (optionID != null) {
            KLog.a("我的APP规格", optionID);
        }
        showDialog();
        addSubscribe(model.postAddShoppingCart(model.getUserToken(), gid, getOptionID(), myTotal.get() + ""), new ParseDisposableTokenErrorObserver() {
            @Override
            public void onResult(Object o) {
                dismissDialog();
                ToastUtils.showLong("商品已加入购物车");
                //更新购物车角标数字
                /*try {
                    JSONObject js = new JSONObject(o.toString());
                    JSONObject result = js.optJSONObject("result");
                    String total = result.optString("total");
                    KLog.a("我添加的数量我添加的数量", total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                uc.closePopwindow.call();
                int shopCarnumber = model.getShopCarnumber();
                shopCarnumber += myTotal.get();
                badgenumber.set(shopCarnumber);
                badgenumber.notifyChange();
                model.saveShopCarnumber(shopCarnumber);

            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                KLog.a("错误信息", errarMessage);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
            }
        });
    }


    public void updateShop() {
        if (spec.size() == pg_spec_ob.size()) {
            specs_id.clear();
            for (HashMap.Entry<String, String> entry : spec.entrySet()) {
                List<GoodsSpecEntity.ResultBean.ListBean> list = specEntity.get().getList();
                for (GoodsSpecEntity.ResultBean.ListBean listBean : list) {
                    if (listBean.getType().equals(entry.getKey())) {
                        specs_id.add(entry.getValue());
                    }
                }
            }
        } else {
            return;
        }
        String optionID = getOptionID();
        if (optionID == null) {
            return;
        }
        List<GoodsSpecEntity.ResultBean.DataBean> data = specEntity.get().getData();
        for (GoodsSpecEntity.ResultBean.DataBean datum : data) {
            if (optionID.equals(datum.getOptionid())) {
                entity.get().setTotal(datum.getTotal());
                entity.get().setPrice(datum.getPrice());
            }
        }
        List<GoodsSpecEntity.ResultBean.ListBean> list = specEntity.get().getList();
        List<GoodsSpecEntity.ResultBean.ListBean.ItemsBean> items = list.get(0).getItems();
        String s = spec.get(list.get(0).getType());
        for (GoodsSpecEntity.ResultBean.ListBean.ItemsBean item : items) {
            if (item.getLid().equals(s)) {
                if (item.getThumb() != null && !TextUtils.isEmpty(item.getThumb())) {
                    entity.get().setUrl(item.getThumb());
                }
            }
        }
        entity.notifyChange();
    }

    /**
     * 获取规格id
     *
     * @return
     */
    StringBuffer sb = null;

    public String getOptionID() {
        sb = new StringBuffer();
        //降序 specs_id.descendingSet()
        sb.replace(0, sb.length(), "");
        List<String> data = updateData(specs_id);
        for (String id : data) {
            sb.append(id + "_");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        for (GoodsSpecEntity.ResultBean.DataBean bean : specEntity.get().getData()) {
            if (bean.getSpecs_id().equals(sb.toString())) {
                sb.replace(0, sb.length(), "");
                return bean.getOptionid();
            }
        }
        sb.delete(0, sb.length());
        sb.replace(0, sb.length(), "");
        return null;
    }

    @Override
    protected void rightIconOnClick() {
        super.rightIconOnClick();
        if (entity.get()!=null){
            GoodsEntity.ResultBean.ShopInfoBean shop_info = entity.get().getShop_info();
            if (shop_info != null) {
                String id = shop_info.getId();
                String type = shop_info.getType();
                String name = shop_info.getName();
                Bundle bundle = new Bundle();
                if (!TextUtils.isEmpty(type)) {
                    if (type.equals("mall")) {//商城
                        startActivity(HomePageActivity.class);
                    } else if (type.equals("merch")) {//商户
                        bundle.putString(Constants.MINE_SHOP, id);
                        bundle.putString("title", name);
                        startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
                    } else if (type.equals("shop")) {//小店
                        bundle.putString(Constants.MINE_SMALL_STORE, id);
                        startActivity(MineShopHomeAty.class);
                    }
                }
            } else {
                Log.d(TAG, "shop_info == null");
            }
        }
    }


    /**
     * 由于选择的规格id 是由小到大的顺序进行排列的
     * 所有要把id进行排序
     * */
    public List<String> updateData(List<String> option){
        ArrayList<String> data = new ArrayList<>();
        ArrayList<Integer> datas = new ArrayList<>();
        for (String datum : option) {
            int i = Integer.parseInt(datum);
            datas.add(i);
        }
        Collections.sort(datas);
        Collections.reverse(datas);
        for (Integer integer : datas) {
            data.add(String.valueOf(integer));
        }
        return data;
    }


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
