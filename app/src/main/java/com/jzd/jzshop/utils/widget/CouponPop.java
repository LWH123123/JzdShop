package com.jzd.jzshop.utils.widget;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jzd.jzshop.BR;
import com.jzd.jzshop.R;
import com.jzd.jzshop.databinding.PoplayoutCoupon2Binding;
import com.jzd.jzshop.ui.base.CouponItemViewModel;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.KLog;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 优惠券弹窗
 */

public class CouponPop extends PopupWindow {
    public static final String TOKEN_COUPONPOP_DISMISS = "token_couponpop_dismiss";
    public ObservableList items;
    //    public ItemBinding itemBinding;
    public ItemBinding<CouponItemViewModel> itemBinding = ItemBinding.of(BR.couponIVM, R.layout.pop_item_coupon_2);
    private PoplayoutCoupon2Binding couponBinding;

    public CouponPop(Activity context, ObservableList items) {
        super(context);
        this.items = items;
        initview(context);
        //注册一个空消息监听
        //参数1：接受人（上下文）
        //参数2：定义的token
        //参数3：执行的回调监听
        Messenger.getDefault().register(this, TOKEN_COUPONPOP_DISMISS, new BindingAction() {
            @Override
            public void call() {
            }
        });

    }

    public CouponPop(Activity context, ObservableList items, ItemBinding itemBinding) {
        super(context);
        initview(context);
        this.items = items;
        this.itemBinding = itemBinding;
    }

    private void initview(final Activity context) {
        setContentView(initCouponLayout(context));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击外部消失
        setOutsideTouchable(true);
        //设置可以点击
        setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        setAnimationStyle(R.style.myPopwindowAnimStyle);
        backgroundalpha(context, 0.5f);//0.0-1.0
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundalpha(context, 1f);
                CouponPop.this.items = null;
                CouponPop.this.itemBinding = null;
            }
        });
    }

    private View initCouponLayout(Activity context) {
        couponBinding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.poplayout_coupon_2, null, false);
        couponBinding.setCouponVM(this);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        couponBinding.pcClose.setOnClickListener(listener);
        couponBinding.pcOkBt.setOnClickListener(listener);
        return couponBinding.getRoot();
    }


    public void setNoData(){
        if(couponBinding==null){
            return;
        }
        couponBinding.pcCouponRecycler.setVisibility(View.GONE);
        couponBinding.tvNodata.setVisibility(View.VISIBLE);
        couponBinding.textView87.setVisibility(View.GONE);
        KLog.a("暂时没有优惠券!");

    }


    public ObservableList getItems() {
        return items;
    }

    public void setItems(ObservableList items) {
        this.items = items;
    }


    public ItemBinding getItemBinding() {
        return itemBinding;
    }

    public void setItemBinding(ItemBinding itemBinding) {
        this.itemBinding = itemBinding;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgalpha
     */
    public void backgroundalpha(Activity context, float bgalpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgalpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

}