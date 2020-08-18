package com.jzd.jzshop.ui.base.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;


import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Create Author：goldze
 * Create Date：2019/01/03
 * Description： 对应include标题的ToolbarViewModel
 * Toolbar的封装方式有很多种，具体封装需根据项目实际业务和习惯来编写
 * 所有例子仅做参考,业务多种多样,可能我这里写的例子和你的需求不同，理解如何使用才最重要。
 */

public class ToolbarViewModel<M extends BaseModel> extends BaseViewModel<M> {

    public ObservableInt toolbarBgColor = new ObservableInt();
    public ObservableInt ivBackIsVisible = new ObservableInt(View.VISIBLE);
    public ObservableInt ivBackWhiteIsVisible = new ObservableInt(View.GONE);
    //标题文字
    public ObservableField<String> titleText = new ObservableField<>("");
    public ObservableInt titleTextColor = new ObservableInt(Color.parseColor("#000000"));
    //右边文字
    public ObservableField<String> rightText = new ObservableField<>("");
    public ObservableInt  rightTextColor = new ObservableInt(Color.parseColor("#000000"));
    //右边文字的观察者
    public ObservableInt rightTextVisibleObservable = new ObservableInt(View.GONE);
    //右边图标的观察者
    public ObservableInt rightIconVisibleObservable = new ObservableInt(View.GONE);
//    public ObservableInt resRightImgId = new ObservableInt(R.mipmap.add_address);
    //兼容databinding，去泛型化
    public ToolbarViewModel toolbarViewModel;

    public ToolbarViewModel(@NonNull Application application) {
        this(application, null);
    }

    public ToolbarViewModel(@NonNull Application application, M model) {
        super(application, model);
        this.model = model;
        toolbarViewModel = this;
    }

    /**
     *  抽取到父类，子类实现无需每个vm 都写一遍
     * @param text
     */
    protected void initToolBar(String text){
        setTitleText(text);
    }


    /**
     *  设置状态栏背颜色
     * @param colorId
     */
    public void setToolbarBgColor(int colorId){
        toolbarBgColor.set(colorId);
        toolbarBgColor.notifyChange();
    }


    /**
     * 设置标题
     *
     * @param text 标题文字
     */
    public void setTitleText(String text) {
        titleText.set(text);
    }

    /**
     *  黑色返回键
     * @param visibility
     */
    public void setIvBackIsVisible(int visibility) {
        ivBackIsVisible.set(visibility);
    }

    /**
     *  白色返回键
     * @param visibility
     */
    public void setIvBackWhiteIsVisible(int visibility) {
        ivBackWhiteIsVisible.set(visibility);
    }

    /**
     * 设置标题颜色
     * @param colorId
     */
    public void setTitleTextColor(int colorId){
        titleTextColor.set(colorId);
    }

    /**
     * 设置右边文字
     *
     * @param text 右边文字
     */
    public void setRightText(String text) {
        rightText.set(text);
    }


    /**
     * 设置标题颜色
     * @param colorId
     */
    public void setRightTextColor(int colorId){
        rightTextColor.set(colorId);
    }

    /**
     * 设置右边文字的显示和隐藏
     *
     * @param visibility
     */
    public void setRightTextVisible(int visibility) {
        rightTextVisibleObservable.set(visibility);
    }

    /**
     * 设置右边图标的显示和隐藏
     *
     * @param visibility
     */
    public void setRightIconVisible(int visibility) {
        rightIconVisibleObservable.set(visibility);
    }

    /**
     * 设置右边图标
     *
     * @param resId 右边图标
     */

//    public void setResRightImg(int resId) {
//        resRightImgId.set(resId);
//    }

    /**
     * 返回按钮的点击事件
     */
    public final BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            setBackOnClick();
            if(backOnClick==null) {
                finish();
            }
        }
    });

    public BindingCommand rightTextOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rightTextOnClick();
        }
    });

    public BindingCommand rightIconOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rightIconOnClick();
        }
    });

    protected void setBackOnClick(){
    }

    /*public BindingCommand popOnClickGoToHome = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(HomePageActivity.class);
        }
    });
    public BindingCommand popOnClickGoToShoppingCar = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ShoppingCarActivity.class);
        }
    });
    public BindingCommand popOnClickGoToMy = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(MyActivity.class);
        }
    });
    public BindingCommand popOnClickGoToCategory = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(CategoryActivity.class);
        }
    });*/

    /**
     * 右边文字的点击事件，子类可重写
     */
    protected void rightTextOnClick() {
    }

    /**
     * 右边图标的点击事件，子类可重写
     */
    protected void rightIconOnClick() {
    }

    /**
     * [TODO]
     * 应该在重新定义绑定时间 拿到布局文件的引用进而拿到content 然后去做ui的处理
     * 不要在这里传入activity的引用 因为viewmodel的生命周期比activity长 容易出现内存泄露
     *
     * @param anchor
     */

  /*  public void initPopwindowLayout(View anchor) {
        //加载弹出框的布局
        View contentView=  LayoutInflater.from(anchor.getContext()).inflate(R.layout.layout_toolbar_pop, null);
        LayoutToolbarPopBinding binding = DataBindingUtil.bind(contentView);
        binding.setToolbarPoPVM(this);
        PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.myPopwindowAnimStyle);

//        if (Build.VERSION.SDK_INT < 24) {
//            //nexus5 不显示
//            if (Build.MODEL.equals("Nexus 5"))
//                popupWindow.showAtLocation(anchor, Gravity.TOP, 0, 0);
//            else
//                popupWindow.showAsDropDown(anchor);
//        } else {
//            popupWindow.showAtLocation(anchor, Gravity.TOP|Gravity.RIGHT, 20, 200);
//        }
        popupWindow.showAsDropDown(anchor);
    }*/

}
