package com.jzd.jzshop.ui.mine.setting.perfectdata.adpter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jzd.jzshop.R;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.SdkVersionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 */
public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
    public static final String TAG = "PictureSelector";
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedias> list = new ArrayList<>();
    private int selectMax = 5;
    private boolean isAndroidQ;
    private int must=0;

    public void setMust(int must) {
        this.must = must;
    }


    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public String key;
    public void setKey(String key){
        this.key=key;
    }
    public interface onAddPicClickListener {
        void onAddPicClick(int maxSelect);
    }

    public GridImageAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        this.isAndroidQ = SdkVersionUtils.checkedAndroid_Q();
    }


    /**
     * 设置最大上传数量
    * */
    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }


    /**
     * 綁定數據
    * */
    public void setList(List<LocalMedias> list) {
        this.list=list;
    }




    /**
     * 获取数据
    * */
    public List<LocalMedias> getLists(){
        return this.list;
    }

    /**
     * 添加数据
    * */
    public void addList(List<LocalMedias> list){
        KLog.a("原有集合的数据"+this.list.size());
        this.list.addAll(list);
    }


    /**
     *判断必传项是否有数据
    * */
    public boolean isMust(){
        if(this.must==1){
            if(list.size()==0){
                return false;
            }
        }
        return true;
    }

    /**
     * 转化为需要上传的String值
    * */
    public String httpImg(){
        StringBuffer sb = new StringBuffer();

        for (LocalMedias localMedias : list) {
            if(localMedias.getUrl()!=null){
                sb.append(localMedias.getUrl()+"@");
            }
        }
        if(sb.length()==0){
            return null;
        }
        sb.replace(sb.length()-1,sb.length(),"");
        return sb.toString();
    }


    /**
     * 获取需要上传的file集合
    * */
    public List<File> locaImgFile(){
        ArrayList<File> locafile = new ArrayList<>();
        for (LocalMedias localMedias : list) {
            if(localMedias.getFile()!=null){
                locafile.add(localMedias.getFile());
            }
        }
        return locafile;
    }


    /**
     * 设置Add图片
     * */
    private int addmipmap=R.mipmap.ic_add_image;
    public void setAddmipmap(int mipmap){
       this.addmipmap=mipmap;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        ImageView mIvDel;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
            mIvDel = view.findViewById(R.id.iv_del);
        }
    }


    @Override
    public int getItemCount() {
        if(list.size()<selectMax){
            return list.size()+1;
        }
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.gv_filter_image,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(addmipmap);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onAddClickListener!=null){
                        onAddClickListener.onAddClick(key);
                    }
                  mOnAddPicClickListener.onAddPicClick(selectMax-list.size());
                }
            });
            viewHolder.mIvDel.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mIvDel.setVisibility(View.VISIBLE);
            viewHolder.mIvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION && list.size() > index) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                    }
                }
            });
            if (list.get(position).getUrl()!=null) {
               Glide.with(viewHolder.mImg.getContext()).load(list.get(position).getUrl()).into(viewHolder.mImg);
            } else {
                final LocalMedias media = list.get(position);
                if (media == null
                        || TextUtils.isEmpty(media.getPath())) {
                    return;
                }
                String path;
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                } else {
                    // 原图
                    path = media.getPath();
                }


                RequestOptions requestOptions1 = new RequestOptions().centerCrop().placeholder(R.color.app_color_f6).diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(viewHolder.itemView.getContext())
                        .load(isAndroidQ && !media.isCut() && !media.isCompressed() ? Uri.parse(path)
                                : path).apply(requestOptions1)
                        .into(viewHolder.mImg);
                //itemView 的点击事件
                if (mItemClickListener != null) {
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int adapterPosition = viewHolder.getAdapterPosition();
                            mItemClickListener.onItemClick(adapterPosition, v, list);
                        }
                    });
                }
            }
        }
    }

    public List<LocalMedias> getList(){
        notifyDataSetChanged();
        if(this.list.size()==0){
            return null;
        }else {
            return this.list;
        }
    }


    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v,List<LocalMedias> media);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }



    protected OnAddClickListener onAddClickListener;

    public interface OnAddClickListener{
        void onAddClick(String key);
    }
    public void setOnAddClickListener(OnAddClickListener listener){
        this.onAddClickListener=listener;
    }



}
