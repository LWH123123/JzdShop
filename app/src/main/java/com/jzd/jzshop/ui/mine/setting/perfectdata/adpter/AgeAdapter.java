package com.jzd.jzshop.ui.mine.setting.perfectdata.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.MessageEntity;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author LWH
 * @description:
 * @date :2019/12/19 17:53
 */
public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.MyAdapter> {

    private List<MessageEntity> age;


    public AgeAdapter(List<MessageEntity> age) {
        this.age = age;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_age, null);
        return new MyAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter myAdapter, final int i) {
        final MessageEntity messageEntity = age.get(i);
        myAdapter.chage.setText(messageEntity.getAge());
        myAdapter.chage.setChecked(messageEntity.getSelect() != 0);
        myAdapter.chage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onitemClick!=null){
                    setAgeData(i);
                    onitemClick.OnitemClick(i);
                }
            }
        });
    }

    public void setAgeData(int position){
        for (int i = 0; i <age.size() ; i++) {
            MessageEntity messageEntity = age.get(i);
            messageEntity.setSelect(0);
            if(i==position){
                messageEntity.setSelect(1);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return age.size();
    }



    public void setList(int position){
        MessageEntity messageEntity = age.get(position);
        String ages = messageEntity.getAge();
        for (MessageEntity entity : age) {
            entity.setSelect(0);


        }

    }


    public class MyAdapter extends RecyclerView.ViewHolder {
        CheckBox chage;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            chage = itemView.findViewById(R.id.ch_age);
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
