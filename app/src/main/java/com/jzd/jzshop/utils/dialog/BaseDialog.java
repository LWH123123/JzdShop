package com.jzd.jzshop.utils.dialog;

import android.app.Dialog;
import android.content.Context;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


/**
 * @author LXB
 * @description:  公用弹窗基类
 * @date :2019/12/11 16:59
 */
public class BaseDialog extends Dialog {

    private int res;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId,int layoutResId) {
        super(context, themeResId);
        this.res = layoutResId;
        setContentView(res);
        setCanceledOnTouchOutside(false);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
