package com.jzd.jzshop.ui.mine.setting.perfectdata.adpter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.HabitEntity;

import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author LWH
 * @description:
 * @date :2019/12/20 9:45
 */
public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.MyAdapter> {


    private List<HabitEntity> habitList;

    public HabitAdapter(List<HabitEntity> habitList) {
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_habit_list, null);
        return new MyAdapter(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter myAdapter, final int i) {
        final HabitEntity habitEntity = habitList.get(i);
        String s = habitEntity.getName();
        myAdapter.checkBox.setText(s+"");
        myAdapter.checkBox.setChecked(habitEntity.isStatus());
        myAdapter.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(onitemClick!=null){
                    habitList.get(i).setStatus(isChecked);
                    onitemClick.OnitemClick(i,isChecked);
                }
            }
        });
    }


    public String getData(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <habitList.size() ; i++) {
            HabitEntity habitEntity = habitList.get(i);
            if(habitEntity.isStatus()) {
               sb.append(habitEntity.getName());
               sb.append("@");
            }
        }
        if(!TextUtils.isEmpty(sb.toString())){
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
        }
        return "";
    }


    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.habit);
        }
    }


    public interface OnitemClick {
        void OnitemClick(int position,boolean ischeck);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }


}
