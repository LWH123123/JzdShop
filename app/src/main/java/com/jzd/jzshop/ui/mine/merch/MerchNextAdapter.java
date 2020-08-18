package com.jzd.jzshop.ui.mine.merch;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MerchEntity;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2020/1/7 11:25
 */
public class MerchNextAdapter extends RecyclerView.Adapter<MerchNextAdapter.MyAdapter> {
    private List<MerchEntity.ResultBean.FieldsBean> fieldsBeans;

    public void setList(List<MerchEntity.ResultBean.FieldsBean> fieldsBean) {
        this.fieldsBeans = fieldsBean;
    }

    public List<MerchEntity.ResultBean.FieldsBean> getAdapterList() {
        if (fieldsBeans.size() != 0) {
            return this.fieldsBeans;
        }
        return null;
    }


    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_merch_pic, null);
        return new MyAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter myAdapter, final int i) {
        final MerchEntity.ResultBean.FieldsBean fieldsBean = fieldsBeans.get(i);
        String tp_value = fieldsBean.getTp_value();
        myAdapter.name.setText(fieldsBean.getTp_name());

        if (fieldsBean.getFile() != null && fieldsBean.getFile().exists()) {
            myAdapter.show.setVisibility(View.VISIBLE);
            myAdapter.pic.setVisibility(View.GONE);
            Glide.with(myAdapter.show.getContext()).load(fieldsBean.getFile()).into(myAdapter.show);
        } else {
            if (!TextUtils.isEmpty(tp_value)) {
                myAdapter.show.setVisibility(View.VISIBLE);
                myAdapter.pic.setVisibility(View.GONE);
                Glide.with(myAdapter.show.getContext()).load(tp_value).into(myAdapter.show);
            } else {
                myAdapter.show.setVisibility(View.GONE);
                myAdapter.updatepic.setBackgroundColor(Color.parseColor("#F2F2F2"));
                myAdapter.pic.setImageDrawable(myAdapter.pic.getContext().getResources().getDrawable(R.mipmap.addpic));
            }
        }
        myAdapter.updatepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onitemClick != null) {
                    onitemClick.OnitemClick(i, fieldsBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (fieldsBeans.size() != 0) {
            return fieldsBeans.size();
        }
        return 0;
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView name;
        ImageView pic;
        ConstraintLayout updatepic;
        ImageView show;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            pic = itemView.findViewById(R.id.pic);
            updatepic = itemView.findViewById(R.id.update_pic);
            show = itemView.findViewById(R.id.show);
        }
    }

    public interface OnitemClick {
        void OnitemClick(int position, MerchEntity.ResultBean.FieldsBean fieldsBean);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }


}
