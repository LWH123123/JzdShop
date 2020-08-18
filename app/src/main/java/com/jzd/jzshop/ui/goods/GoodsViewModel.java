package com.jzd.jzshop.ui.goods;

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

import com.jzd.jzshop.R;
import com.jzd.jzshop.app.AppApplication;
import com.jzd.jzshop.chat.OpenChatActivity;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.ActivityGoodsBinding;
import com.jzd.jzshop.entity.BannEntity;
import com.jzd.jzshop.entity.CouponEntity;
import com.jzd.jzshop.entity.GoodsEntity;
import com.jzd.jzshop.entity.GoodsSpecEntity;
import com.jzd.jzshop.entity.ShopPriceEntity;
import com.jzd.jzshop.entity.SpinnerItemData;
import com.jzd.jzshop.ui.base.CouponItemViewModel;
import com.jzd.jzshop.ui.goods.evaluate.GoodsEvaluationAty;
import com.jzd.jzshop.ui.home.news.HomePageActivity;
import com.jzd.jzshop.ui.login.LoginVertifyActivity;
import com.jzd.jzshop.ui.merch_alliance.ShopFragment;
import com.jzd.jzshop.ui.mine.mineshop.shophome.MineShopHomeAty;
import com.jzd.jzshop.ui.mine.news.MyPageActivity;
import com.jzd.jzshop.ui.order.firmorder.FirmOrderActivity;
import com.jzd.jzshop.ui.shoppingcar.ShoppingCarActivity;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.net_utils.ParseDisposableTokenErrorObserver;
import com.jzd.jzshop.utils.third_party.ShareUtils;
import com.slodonapp.ywj_release.wxapi.WXEntryActivity;
import com.slodonapp.ywj_release.wxapi.WXUserInfo;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class GoodsViewModel extends BaseViewModel<Repository> {
    private static final String TAG = GoodsViewModel.class.getSimpleName();
    private ActivityGoodsBinding binding;

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    //优惠券
    public ObservableList<CouponItemViewModel> pg_coupon_ob = new ObservableArrayList<>();
    //属性
    public ObservableList<PopGoodsSpecItemViewModel> pg_spec_ob = new ObservableArrayList<>();
    public ItemBinding<PopGoodsSpecItemViewModel> pg_spec_ib = ItemBinding.of(com.jzd.jzshop.BR.specIVM, R.layout.pop_item_spec);
    //参数
    public ObservableList<PopGoodsParameterItemViewModel> pg_param_ob = new ObservableArrayList<>();
    public ItemBinding<PopGoodsParameterItemViewModel> pg_param_ib = ItemBinding.of(com.jzd.jzshop.BR.parameterIVM, R.layout.pop_item_parameter);
    //评论
    public ObservableList<GoodsCommentsItemViewModel> pg_comments_ob = new ObservableArrayList<>();
    public ItemBinding<GoodsCommentsItemViewModel> pg_comments_ib = ItemBinding.of(com.jzd.jzshop.BR.commentsIVM, R.layout.item_goods_comments);

    public ObservableField<GoodsEntity.ResultBean> entity = new ObservableField<>();
    public ObservableField<GoodsSpecEntity.ResultBean> specEntity = new ObservableField<>();
    public ObservableInt myTotal = new ObservableInt(0);
    public ObservableField<ShopPriceEntity> price = new ObservableField<>();
    public ObservableField<Integer> badgenumber = new ObservableField<>();
    public GoodsEntity.ResultBean.ShareinfoBean shareinfoBean;
    public ObservableField<Integer> love = new ObservableField<>();
    //当前商品的单位 显示活动时可能会用到
    public String unit = "";
    protected String gid, openFlag, chat_id,name, show_price, shopid, goodid;
    private ObservableField<String> gids = new ObservableField<>();
    /**
     * 规格ID的组合（将规格ID按降序的形式拼接成的字符串，多个之间用“_”拼接）
     */
    public ArrayList<String> specs_id = new ArrayList<>();
    private final Context mContext;
    //存放规格组数据
    public HashMap<String, String> spec = new HashMap<>();
    private GoodsEntity.ResultBean goodsInfo;

    public class UIChangeObservable {
        //更新xbanner数据
        public SingleLiveEvent<List<BannEntity>> xbannerRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent<String> webviewRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent<String> showCouponPopwindow = new SingleLiveEvent<>();
        public SingleLiveEvent<String> showSpecPopwindow = new SingleLiveEvent<>();
        public SingleLiveEvent<String> showParamPopwindow = new SingleLiveEvent<>();
        public SingleLiveEvent<String> updateParamText = new SingleLiveEvent<>();
        public SingleLiveEvent<String> updateSpecText = new SingleLiveEvent<>();
        public SingleLiveEvent backTop = new SingleLiveEvent<>();
        public SingleLiveEvent<List<GoodsEntity.ResultBean.CouponDataBean>> updateCouponText = new SingleLiveEvent<>();
        public SingleLiveEvent<List<GoodsEntity.ResultBean.ActivityBean>> updateActivityText = new SingleLiveEvent<>();
        public SingleLiveEvent<String> closePopwindow = new SingleLiveEvent<>();
        public SingleLiveEvent<Integer> lovegood = new SingleLiveEvent<>();
        public SingleLiveEvent network = new SingleLiveEvent();
        public SingleLiveEvent qqservice = new SingleLiveEvent();
    }

    public GoodsViewModel(@NonNull Application application, Repository model) {
        super(application, model);
        mContext = application.getApplicationContext();
    }

    public void setGid(String gid, String openFlag, String shopid, String goodid) {
        this.gid = gid;
        KLog.a("商品详情得gid:", gid);
        this.gids.set(gid);
        this.openFlag = openFlag;
        this.shopid = shopid;
        this.goodid = goodid;
        int shopCarnumber = model.getShopCarnumber();
        this.badgenumber.set(shopCarnumber);
    }

    public void setBinding(ActivityGoodsBinding binding) {
        this.binding = binding;
    }

    //网络没连接  重新加载
    public BindingCommand onReRequestwordClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KLog.i("商品ID====", gids.get());
            requestNetWork(gids.get());
        }
    });

    public void requestNetWork(String gids) {
        //获取详情数据 "TjGc4jqekL3z"
        KLog.a("我的商品id：", gids);
        addSubscribe(model.postGoods(model.getUserToken(), gids, goodid, shopid),
                new ParseDisposableObserver<GoodsEntity>() {
                    @Override
                    public void onResult(GoodsEntity result) {
                        dismissDialog();
                        uc.network.call();
                        result.getResult().setPrice(result.getResult().getShow_price());
                        result.getResult().setTotal("");
                        entity.set(result.getResult());
                        KLog.i("店铺logo店铺logo", entity.get().getShop_info().getName());
                        uc.lovegood.setValue(result.getResult().getIs_favorite());
                        //展示置頂的评价内容
                        showComments();
                        //============banner
                        List<BannEntity> bannEntities = new ArrayList<>();
                        for (String img : result.getResult().getThumb()) {
                            bannEntities.add(new BannEntity(img));
                        }
                        uc.xbannerRefreshing.setValue(bannEntities);
                        /**********优惠券********/
                        for (final GoodsEntity.ResultBean.CouponDataBean couponDataBean : result.getResult().getCoupon_data()) {
                            final CouponEntity couponEntity = CouponEntity.conversion(couponDataBean);
                            final CouponItemViewModel cimodel = new CouponItemViewModel(GoodsViewModel.this, couponEntity);
                            pg_coupon_ob.add(cimodel);
                            cimodel.setOnclick(new BindingCommand(new BindingAction() {
                                @Override
                                public void call() {
                                    //领取优惠券
                                    if (couponEntity.getCanget() == 1) {
                                        requestGetGoupon(couponEntity.getCoupon_id(), couponEntity);
                                        cimodel.entity.notifyChange();
                                    } else {
                                        ToastUtils.showLong("恭喜， 抢到了");
                                    }
                                }
                            }));
                        }
                        uc.updateCouponText.setValue(result.getResult().getCoupon_data());

                        /**********活动********/
                        unit = result.getResult().getUnit();
                        uc.updateActivityText.setValue(result.getResult().getActivity());
                        /**======商品spce**/
                        StringBuffer spec_sb = new StringBuffer();
                        for (Object title : result.getResult().getSpec_titles()) {
                            spec_sb.append(title + ";");
                        }
                        uc.updateSpecText.setValue(spec_sb.toString());
                        /**=====商品参数数据**/
                        StringBuffer param_sb = new StringBuffer();
                        for (GoodsEntity.ResultBean.ParamsBean paramsBean : result.getResult().getParams()) {
                            PopGoodsParameterItemViewModel model =
                                    new PopGoodsParameterItemViewModel(
                                            GoodsViewModel.this,
                                            new SpinnerItemData(paramsBean.getTitle(), paramsBean.getValue()));
                            param_sb.append(paramsBean.getTitle() + ";");
                            pg_param_ob.add(model);
                        }
                        //设置默认显示参数
                        uc.updateParamText.setValue(param_sb.toString());
                        //=============大图详情
                        uc.webviewRefreshing.setValue(result.getResult().getContent());
                        //分享信息
                        shareinfoBean = result.getResult().getShareinfo();
                        // TODO: 2019/11/29  从h5 跳转详情，需要更新下gid ,再使用,其他跳转是否需要，暂时未处理。。。
                        //============封装 针对聊天 的商品详情页 实体=============
                        gid = result.getResult().getGid();
                        chat_id = result.getResult().getChat_id();
                        name = result.getResult().getShop_info().getName();
                        show_price = result.getResult().getShow_price();
//                        GoodsEntity.ResultBean goodsInfo = result.getResult();
//                        SPUtils.getInstance().putSerializableEntity(Constants.SP.GOODS_INFO,goodsInfo);
                        //============封装 针对聊天 的商品详情页 实体=============
                        //请求评论数据
//                        requestGoodsComments(gid);
                        //请求规格数据
                        requestGoodsSpce(gid);
                    }

                    @Override
                    public void onError(String errarMessage) {
                        dismissDialog();
                    }
                });
    }

    /**
     * 评论
     *
     * @param
     */
    public void showComments() {
        List<GoodsEntity.ResultBean.CommentBean> comment = entity.get().getComment();
        if (comment == null || comment.size() == 0) {
            return;
        }
        for (GoodsEntity.ResultBean.CommentBean commentBean : comment) {
            GoodsCommentsItemViewModel model = new GoodsCommentsItemViewModel(GoodsViewModel.this, commentBean, gid);
            pg_comments_ob.add(model);
        }
    }

    private void setDefault() {
        if (specEntity.get() != null) {
            entity.get().setPrice(specEntity.get().getDefault_price());
            entity.get().setTotal(specEntity.get().getTotal());
            entity.get().setUrl(specEntity.get().getThumb());
            entity.notifyChange();
        }
    }

    /**
     * 商品规格
     *
     * @param gid
     */
    public void requestGoodsSpce(String gid) {
        KLog.a("规格", gid);
        isShowDialog(false);
        addSubscribe(model.postGoodsSpce(model.getUserToken(), gid), new ParseDisposableTokenErrorObserver<GoodsSpecEntity>() {
            @Override
            public void onResult(GoodsSpecEntity entitys) {
                dismissDialog();
                List<GoodsSpecEntity.ResultBean.ListBean> list = entitys.getResult().getList();
                for (int i = 0; i <list.size() ; i++) {
                    list.get(i).setType(""+i);
                }
                specEntity.set(entitys.getResult());
                setDefault();
                for (GoodsSpecEntity.ResultBean.ListBean listBean : entitys.getResult().getList()) {
                    pg_spec_ob.add(new PopGoodsSpecItemViewModel(GoodsViewModel.this, listBean));
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

    //领取优惠券的接口
    public void requestGetGoupon(String coupon_id, final CouponEntity couponEntity) {
        KLog.a("领取优惠券");
        isShowDialog(false);
        addSubscribe(model.postGetGoupon(model.getUserToken(), coupon_id), new ParseDisposableTokenErrorObserver<JSONObject>() {
            @Override
            public void onResult(JSONObject o) {
                dismissDialog();
                synchronized (couponEntity) {  //java.lang.IllegalMonitorStateException: object not locked by thread before notify()
                    couponEntity.setCanget(0);
                    couponEntity.notify();
                }
                CouponItemViewModel coupon = null;
                for (int i = 0; i < pg_coupon_ob.size(); i++) {
                    CouponItemViewModel couponItemViewModel = pg_coupon_ob.get(i);
                    coupon = couponItemViewModel;
                    coupon.entity.notifyChange();
                }
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                ToastUtils.showShort(errarMessage);
            }

            @Override
            protected void onTokenError() {
                super.onTokenError();
                startActivity(LoginVertifyActivity.class);
            }
        });
    }


    /**
     * 将选择的商品规格标题和id存入HashMap中
     * 并判断此id是否已经存在
     */
    public void setSpecData(String title, String lid) {
        boolean b = spec.containsValue(lid);
        if (b) {
            spec.remove(title);
            setDefault();
            return;
        }
        spec.put(title, lid);
    }


    //请求添加到购物车
    public void requestAddShoppingcar() {
        String optionID = getOptionID();
        if (optionID != null) {
            KLog.a("我的APP规格", optionID);
        }
        showDialog();
        addSubscribe(model.postAddShoppingCart(model.getUserToken(), gid, getOptionID(), myTotal.get()==0?"1":myTotal.get()+""), new ParseDisposableTokenErrorObserver() {
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


    //切换商品的收藏效果
    private void setgoodlove(String token, String gid, final int isfavorite) {
        isShowDialog(false);
        addSubscribe(model.postLoveGood(token, gid, isfavorite), new ParseDisposableObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse o) {
                dismissDialog();
                uc.lovegood.setValue(isfavorite);
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });


    }


    //购物车入口
    public BindingCommand onClickGotoShoppingCar = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!TextUtils.isEmpty(model.getUserToken())) {
                startActivity(ShoppingCarActivity.class);
            } else {
                startActivity(LoginVertifyActivity.class);
            }
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
            if ((myTotal.get() - 1) >= 0) {
                myTotal.set(myTotal.get() - 1);
            }
        }
    });


    //分享
    public BindingCommand onClickShare = new BindingCommand<>(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            if (shareinfoBean != null) {
                ShareUtils.shareImageUrl(mContext, shareinfoBean.getTitle(), shareinfoBean.getDesc(), shareinfoBean.getImgUrl(), shareinfoBean.getLink());
            } else {
                Log.d(TAG, "onClickShare:" + shareinfoBean.toString());
            }
        }
    });


    //back
    public BindingCommand onClickBack = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onBackPressed();
        }
    });


    //跳转到评论列表
    public BindingCommand onClickCommentAll = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.GOODS_KEY_GID, gid);
                bundle.putString(Constants.GOODS_KEY_TYPE, "all");
                startActivity(GoodsEvaluationAty.class, bundle);
            }
        }
    });


    //跳转到商铺
    public BindingCommand onClickShop = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String id = entity.get().getShop_info().getId();
            Bundle bundle = new Bundle();
            String type = entity.get().getShop_info().getType();
            if (!TextUtils.isEmpty(type)) {
                if (type.equals("mall")) {//商城
                    startActivity(HomePageActivity.class);
                } else if (type.equals("merch")) {//商户
                    bundle.putString(Constants.MINE_SHOP, id);
                    bundle.putString("title", entity.get().getShop_info().getName());
                    startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
                } else if (type.equals("shop")) {//小店
                    bundle.putString(Constants.MINE_SMALL_STORE, id);
                    startActivity(MineShopHomeAty.class);
                }
            }
        }
    });


    //打开优惠券列表
    public BindingCommand onClickCoupon = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            uc.showCouponPopwindow.setValue(Constants.GOODS_KEY_COUPON);
        }
    });


    //打开属性弹窗
    public BindingCommand onClickSpec = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            if (pg_spec_ob.size() == 0) {
                return;
            }
            uc.showSpecPopwindow.setValue(Constants.GOODS_KEY_SPEC);
        }
    });


    //打开参数弹窗
    public BindingCommand onClickShowParam = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
         //参数窗口不需要判断登录
            if (pg_param_ob.size() == 0) {
                return;
            }
            uc.showParamPopwindow.setValue(Constants.GOODS_KEY_PARAM);
        }
    });

    //回到顶部
    public BindingCommand onClickBackTop = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.backTop.call();
        }
    });
    //去店铺
    public BindingCommand onClickGotoShop = new BindingCommand(new BindingAction() {

        @Override
        public void call() {
            String id = entity.get().getShop_info().getId();
            Bundle bundle = new Bundle();
            String type = entity.get().getShop_info().getType();
            if (!TextUtils.isEmpty(type)) {
                if (type.equals("mall")) {//商城
                    startActivity(HomePageActivity.class);
                } else if (type.equals("merch")) {//商户
                    bundle.putString(Constants.MINE_SHOP, id);
                    bundle.putString("title", entity.get().getShop_info().getName());
                    startContainerActivity(ShopFragment.class.getCanonicalName(), bundle);
                } else if (type.equals("shop")) {//小店
                    bundle.putString(Constants.MINE_SMALL_STORE, id);
                    startActivity(MineShopHomeAty.class);
                }
            }
        }
    });
    //收藏
    public BindingCommand onClickCollect = new BindingCommand(new BindingAction() {

        @Override
        public void call() {
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            if (entity.get() == null) {
                return;
            }
            if (uc.lovegood.getValue() == 0) {
                setgoodlove(model.getUserToken(), gid, 1);
            } else {
                setgoodlove(model.getUserToken(), gid, 0);
            }
        }
    });
    //客服
    public BindingCommand onClickGotoQQService = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            uc.qqservice.call();
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            //打开先先关闭，防止已经打开，打开多个会话页面，聊天数据会话错乱
            AppManager.getAppManager().finishActivity(OpenChatActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putString(Constants.GOODS_KEY_USER_TOKEN, model.getUserToken());
//            bundle.putString(Constants.GOODS_KEY_GOODS_PRICE, show_price);
//            bundle.putSerializable(Constants.GOODS_KEY_GOODS_INFO, shareinfoBean);
            bundle.putString(Constants.GOODS_KEY_GID, gid);
            bundle.putString(Constants.GOODS_KEY_CHAT_ID, chat_id);
            bundle.putString(Constants.GOODS_KEY_CHAT_NAME, name);
            bundle.putString(Constants.GOODS_KEY_CHAT_LTYPE, "member");
            bundle.putString(Constants.SP.ACTIVITY_OPEN_FLAG, "goods");
            startActivity(OpenChatActivity.class, bundle);
        }
    });


    //立即购买
    public BindingCommand onClickBuynow = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (entity.get() == null) {
                return;
            }
            specs_id.clear();
            spec.clear();
            myTotal.set(1);
            setDefault();
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
//            startContainerActivity(NetWorkFragment.class.getCanonicalName());

            uc.showSpecPopwindow.setValue(Constants.GOODS_KEY_BUYNOW);
        }
    });
    //购物车
    public BindingCommand onClickAddShoppingCar = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (entity.get() == null) {
                return;
            }
            myTotal.set(1);
            specs_id.clear();
            spec.clear();
            setDefault();
            if (TextUtils.isEmpty(model.getUserToken())) {
                startActivity(LoginVertifyActivity.class);
                return;
            }
            if (entity.get() == null || entity.get().getAdd_cart() == 0) {
                ToastUtils.showShort("此商品不能加入购物车");
                return;
            }
//            startActivity(ShoppingCarActivity.class);
            if (entity.get().getSpec_titles() != null && entity.get().getSpec_titles().size() != 0) {
                uc.showSpecPopwindow.setValue(Constants.GOODS_KEY_SHOPPING_CAR);
            } else {
                requestAddShoppingcar();
            }
        }
    });


    /**
     * 当选择好一个完整的商品ID时
     * 显示该商品的库存和图标
     */
    public void updateShop() {
//        myTotal.set(1);
        if (spec.size() == pg_spec_ob.size()) {//假如规格已经选择好
            specs_id.clear();
            for (HashMap.Entry<String, String> entry : spec.entrySet()) {
                List<GoodsSpecEntity.ResultBean.ListBean> list = specEntity.get().getList();
                for (GoodsSpecEntity.ResultBean.ListBean listBean : list) {
                    if (listBean.getType().equals(entry.getKey())) {
                        specs_id.add(entry.getValue());//将选择好的规格ID存放起来
                    }
                }
            }
        } else {
            return;
        }
        String optionID = getOptionID();//根据寻找的规格组ID  来匹配真正实用的规格ID
        if (optionID == null) {
            return;
        }
        List<GoodsSpecEntity.ResultBean.DataBean> data = specEntity.get().getData();
        for (GoodsSpecEntity.ResultBean.DataBean datum : data) {
            if (optionID.equals(datum.getOptionid())) {
                entity.get().setTotal(datum.getTotal());//设置选择好的规格的  库存和  价格
                entity.get().setPrice(datum.getPrice());
            }
        }
        List<GoodsSpecEntity.ResultBean.ListBean> list = specEntity.get().getList();
        List<GoodsSpecEntity.ResultBean.ListBean.ItemsBean> items = list.get(0).getItems();

        String s = spec.get(list.get(0).getType());
        for (GoodsSpecEntity.ResultBean.ListBean.ItemsBean item : items) {
            if (item.getLid().equals(s)) {
                if (item.getThumb() != null && !TextUtils.isEmpty(item.getThumb())) {
                    entity.get().setUrl(item.getThumb());//设置选择的规格的图片
                }
            }
        }
        entity.notifyChange();
    }

    //弹窗中立即购买
    public BindingCommand onClickPopBuynow = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            if (specEntity.get() != null && specEntity.get().getList() != null && specEntity.get().getList().size() != spec.size()) {
                ToastUtils.showLong("请选择商品规格!");
                return;
            }
            String total = entity.get().getTotal();
            if(total.equals("0")){
//                ToastUtils.showLong("库存不足!");
                return;
            }

            if (!TextUtils.isEmpty(model.getUserToken())) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.TYPE, 0);
                model.saveShopID(gid);
                model.saveOptionID(getOptionID());
                model.saveGoodsNumber(myTotal.get()==0?"1":String.valueOf(myTotal.get()));
                model.saveGiftID(getGiftID());
                model.saveOpenFlag(openFlag);//保存打开标识
                //---------------友盟自定义 购买 事件-------------
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("shopName", entity.get().getTitle());
                map.put("shopPrice", entity.get().getShow_price());
                map.put("gid", gid);
                MobclickAgent.onEvent(mContext, "buy_in_now_id", map);
                //---------------友盟自定义 购买 事件-------------
                startActivity(FirmOrderActivity.class, bundle);
                uc.closePopwindow.call();
            } else {
                startActivity(LoginVertifyActivity.class);
                return;
            }

        }
    });


    //弹窗中加入购物车
    public BindingCommand onClickPopAddShoppingCar = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (specEntity.get() != null && specEntity.get().getList() != null && specEntity.get().getList().size() != spec.size()) {
                ToastUtils.showLong("请选择商品规格!");
                return;
            }
            String total = entity.get().getTotal();
            if(total.equals("0")){
//                ToastUtils.showLong("库存不足!");
                return;
            }
            requestAddShoppingcar();
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

    /**
     * 获取规格id
     *
     * @return
     */
    StringBuffer sb = null;

    /**
     * 需要将选择的商品规格id 升序+"_"组成字符串
     * 并且匹配好规格详细数据集合中的Option ID
     */
    public String getOptionID() {
        sb = new StringBuffer();
        //降序 specs_id.descendingSet()
        sb.replace(0, sb.length(), "");
        List<String> data = updateData(specs_id);
        for (String id : data) {
            sb.append(id + "_");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            KLog.a("规格id===>>>" + sb.toString());
            if (specEntity.get() != null) {
                for (GoodsSpecEntity.ResultBean.DataBean bean : specEntity.get().getData()) {
                    if (bean.getSpecs_id().equals(sb.toString())) {
                        sb.replace(0, sb.length(), "");
                        return bean.getOptionid();
                    }
                }
                sb.delete(0, sb.length());
            }
        }
        return null;
    }

    /**
     * 由于选择的规格id 是由大到小的顺序进行排列的
     * 所有要把id进行排序
     */
    public List<String> updateData(List<String> option) {
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


    /**
     * 单独集成微信分享
     */
    private void mWXShare() {
        WXEntryActivity.loginWeixin(AppApplication.getInstance(), AppApplication.sApi, new WXEntryActivity.ApiCallback<WXUserInfo>() {
            @Override
            public void onSuccess(WXUserInfo info) {
                if (shareinfoBean != null)
                    WXEntryActivity.ShareURL(shareinfoBean.getLink(), shareinfoBean.getTitle(), shareinfoBean.getDesc());
                else {
                    ToastUtils.showShort("请先登录");
                    startActivity(MyPageActivity.class);
                }
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                ToastUtils.showShort(errorMsg);
                KLog.d(errorMsg);
            }

            @Override
            public void onFailure(IOException e) {
                ToastUtils.showShort(e.getMessage());
                KLog.d(e.getMessage());
            }

            @Override
            public void onPayError(String res) {
                KLog.a(res);
            }
        });
    }
}