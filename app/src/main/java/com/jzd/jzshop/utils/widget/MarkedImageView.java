package com.jzd.jzshop.utils.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * @author LWH
 * @description:
 * @date :2020/1/14 17:56
 */
    @SuppressLint("AppCompatCustomView")
    public class MarkedImageView extends ImageView {
        private Paint mCirclePanit;
        private Paint mTextPanit;
        private int mMessageNumber;
        private boolean mIsHideMessageMark = false;

        private Context mContext;
        private int mPaddingPx;
        private float mMarkRadius;
        private int mTextSize;
        private int color=Color.WHITE;

        public MarkedImageView(Context context) {
            this(context, null);
        }

        public MarkedImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MarkedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.mContext = context;
            mCirclePanit = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCirclePanit.setColor(Color.parseColor("#FF4400"));/*#FF4400*/
            mTextPanit = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPanit.setColor(color);
            mTextPanit.setTextAlign(Paint.Align.CENTER);
            mPaddingPx = DensityUtil.dp2px(8);
            mMarkRadius = DensityUtil.dp2px(8);
            mTextSize = DensityUtil.dp2px(10);
            mTextPanit.setTextSize(mTextSize);
        }

        public void setTextColor(int color){
            this.color=color;
            invalidate();
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!mIsHideMessageMark) {
                //8 is checkable padding 8dp should turns to px
                if (mMessageNumber > 0 && mMessageNumber < 100) {
                    canvas.drawCircle(getMeasuredWidth() - mMarkRadius, mMarkRadius + mPaddingPx, mMarkRadius, mCirclePanit);
                    canvas.drawText(mMessageNumber + "", getMeasuredWidth() - mMarkRadius, mMarkRadius + mTextSize / 3 + mPaddingPx, mTextPanit);
                } else if (mMessageNumber > 99) {
                    RectF rectF = new RectF(getMeasuredWidth() - 2 * mMarkRadius, mPaddingPx, getMeasuredWidth() + (2 / 3) * mMarkRadius, 2 * mMarkRadius + mPaddingPx);
                    canvas.drawRoundRect(rectF, mMarkRadius, mMarkRadius, mCirclePanit);
                    canvas.drawText("99+", getMeasuredWidth() - mMarkRadius, mMarkRadius + mTextSize / 3 + mPaddingPx, mTextPanit);
                }
            }
        }

        public void setMessageNumber(int messageNumber) {
            mMessageNumber = messageNumber;
            invalidate();
        }

        public void setIsHideMessageMark(boolean isHideMessageMark) {
            mIsHideMessageMark = isHideMessageMark;
            invalidate();
        }
    }
