package com.jzd.jzshop.ui.mine.setting.perfectdata.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzd.jzshop.R;

import java.util.List;

/**
 * @author LWH
 * @description:
 * @date :2019/12/20 10:43
 */
public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.MyAdapter> {

    private List<String> aducation;

    public EducationAdapter(List<String> aducation) {
        this.aducation = aducation;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_education, viewGroup,false);
        return new MyAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter myAdapter, final int i) {
        String s = aducation.get(i);
        myAdapter.education.setText(s);
        myAdapter.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onitemClick!=null){
                    onitemClick.OnitemClick(i);
                }
            }
        });


    }



    @Override
    public int getItemCount() {
        return aducation.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView education;
        LinearLayout item;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            education=itemView.findViewById(R.id.education);
            item =itemView.findViewById(R.id.item);
        }
    }


    public interface OnitemClick {
        void OnitemClick(int position);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }
}
