package me.goldze.mvvmhabit.binding.viewadapter.image;


import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import me.goldze.mvvmhabit.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes","isCircle","isRounded"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes,boolean isCircle,boolean isRounded) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            RequestBuilder<Drawable> rb=Glide.with(imageView.getContext()).load(url).transition(withCrossFade());
            if(placeholderRes==0) {
                placeholderRes=R.drawable.placeholder;
            }
            rb.apply( RequestOptions.placeholderOf(placeholderRes));
            if(isCircle){
                RequestOptions circleRO = RequestOptions.circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                        .skipMemoryCache(true);//不做内存缓存
                rb.apply(circleRO);
            }
            if(isRounded){
                //设置图片圆角角度
                RoundedCorners roundedCorners= new RoundedCorners(10);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//                RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);
                rb.apply(options);
            }
            rb.into(imageView);
        }
    }
}

