package com.jzd.jzshop.ui.order.firmorder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.jzshop.R;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * @author LXB
 * @description:
 * @date :2019/12/13 16:54
 */
public class CustomBindingRecyclerViewAdapter extends BindingRecyclerViewAdapter<BindingRecyclerViewAdapter.ViewHolderFactory> {
    private static final String TAG = CustomBindingRecyclerViewAdapter.class.getSimpleName();
    @Override
    public ViewDataBinding onCreateBinding(LayoutInflater inflater, int layoutId, ViewGroup viewGroup) {
        ViewDataBinding binding = super.onCreateBinding(inflater, layoutId, viewGroup);
        Log.d(TAG, "created binding: " + binding);
        return binding;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        return super.onCreateViewHolder(binding);
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, ViewHolderFactory item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        Log.d(TAG, "bound binding: " + binding + " at position: " + position);
        TextView textView = binding.getRoot().findViewById(R.id.ifog_number);
        textView.setText("1313131");
    }
}
