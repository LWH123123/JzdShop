package com.jzd.jzshop.utils.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.BaseShopSpecEntity;
import com.jzd.jzshop.entity.CreditsStoreEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.androidannotations.annotations.Click;

import java.util.HashMap;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author lwh
 * @description :
 * @date 2020/5/13.
 */
public class BaseSpecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseShopSpecEntity> speclist;

    public HashMap<String,Integer> hash =new HashMap<>();

    private Context context;
    public BaseSpecAdapter(Context context, List<BaseShopSpecEntity> speclist) {
        this.speclist = speclist;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_dialog_spec, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (i >= speclist.size())return;
        BaseShopSpecEntity baseShopSpecEntity = speclist.get(i);
        ViewHolder view= (ViewHolder) viewHolder;
        view.title.setText(baseShopSpecEntity.getTitle());
        view.flow.setAdapter(new TagAdapter<BaseShopSpecEntity.ItemsBean>(baseShopSpecEntity.getItems()) {
            @Override
            public View getView(FlowLayout parent, int position, BaseShopSpecEntity.ItemsBean o) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.base_item_spec_goods, null);
                ImageView iv_icon = inflate.findViewById(R.id.iv_icon);
                TextView textView = inflate.findViewById(R.id.tv_text);
                CardView cdicon = inflate.findViewById(R.id.cd_icon);
                if(!TextUtils.isEmpty(o.getThumb())) {
                    Glide.with(context).load(o.getThumb()).into(iv_icon);
                }else {
                    cdicon.setVisibility(View.GONE);
                }
                textView.setText(o.getTitle());
                return inflate.getRootView();
            }
            @Override
            public void onSelected(int flowPosition, View view) {
                super.onSelected(flowPosition, view);
                hash.put(speclist.get(i).getType(),speclist.get(i).getItems().get(flowPosition).getLid());
                if(onitemSelectClick!=null){
                    onitemSelectClick.OnitemClick(hash,speclist.get(i).getItems().get(flowPosition));
                }
                ((CardView)view).setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                view.setBackgroundResource(R.drawable.boder_red_textview);
                TextView textView = view.findViewById(R.id.tv_text);
                textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorred));
            }

            @Override
            public void unSelected(int flowPosition, View view) {
                super.unSelected(flowPosition, view);
                if(hash.containsValue(speclist.get(i).getItems().get(flowPosition).getLid())){
                    hash.remove(speclist.get(i).getTitle());
                }
                if(onitemUnSelectClick!=null){
                    onitemUnSelectClick.OnitemUnSelectClick(speclist.get(i).getItems().get(flowPosition));
                }
                ((CardView)view).setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.spec_text_color));
                view.setBackgroundResource(R.drawable.boder_gray_textview);
                TextView textView = view.findViewById(R.id.tv_text);
                textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.textColor));
//                    specBinding.pisFlow.getAdapter().notifyDataChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return speclist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
          TextView title;
        com.jzd.jzshop.utils.widget.FlowLayout flow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             title = itemView.findViewById(R.id.tv_title);
             flow = itemView.findViewById(R.id.tf_flow);
        }
    }

    //选择规格接口回调
    public interface OnitemSelectClick {
        void OnitemClick(HashMap<String,Integer> hashMap, BaseShopSpecEntity.ItemsBean itemsean);
    }

    private OnitemSelectClick onitemSelectClick;

    public void setOnitemSelectClick(OnitemSelectClick listeren) {
        this.onitemSelectClick = listeren;
    }
    //取消选择接口回调
    public interface OnitemUnSelectClick {
        void OnitemUnSelectClick( BaseShopSpecEntity.ItemsBean itemsean);
    }

    private OnitemUnSelectClick onitemUnSelectClick;

    public void setOnitemUnSelectClick(OnitemUnSelectClick listeren) {
        this.onitemUnSelectClick = listeren;
    }



}
