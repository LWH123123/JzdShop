package com.jzd.jzshop.chat.model;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.databinding.PopItemSpecChatBinding;
import com.jzd.jzshop.databinding.PopItemSpecFlowBinding;
import com.jzd.jzshop.databinding.PoplayoutSpecChatBinding;
import com.jzd.jzshop.entity.GoodsSpecEntity;
import com.jzd.jzshop.ui.goods.PopGoodsSpecAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import me.goldze.mvvmhabit.utils.KLog;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * @author LXB
 * @description:
 * @date :2020/4/27 14:37
 */
public class PopGoodsSpecAdapterChat extends BindingRecyclerViewAdapter<PopGoodsSpecItemViewModelChat> {

    public PopGoodsSpecAdapterChat() {
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, final int position, final PopGoodsSpecItemViewModelChat item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        final PopItemSpecChatBinding specBinding = (PopItemSpecChatBinding) binding;
        if (specBinding.pisFlow.getAdapter() == null) {
            specBinding.pisFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    item.setSpecID(position);
                    if (onitemClick != null) {
                        onitemClick.OnitemClick();
                    }
                    return false;
                }
            });


            specBinding.pisFlow.setAdapter(new TagAdapter<GoodsSpecEntity.ResultBean.ListBean.ItemsBean>(item.entity.get().getItems()) {
                @Override
                public View getView(FlowLayout parent, int position, GoodsSpecEntity.ResultBean.ListBean.ItemsBean itemsBean) {
                    PopItemSpecFlowBinding flowBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.pop_item_spec_flow,
                            parent,
                            false);
//                View flowlayout=LayoutInflater.from(specBinding.pisFlow.getContext()).inflate(R.layout.pop_item_spec_flow,specBinding.pisFlow,true);
                    flowBinding.setFlowIVM(itemsBean);
                    return flowBinding.getRoot();
                }

                @Override
                public void onSelected(int flowPosition, View view) {
                    super.onSelected(flowPosition, view);
                    ((CardView) view).setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blue));
                    view.setBackgroundResource(R.drawable.boder_red_textview);
                    TextView textView = view.findViewById(R.id.pisf_title_tv);
                    textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorred));
                }

                @Override
                public void unSelected(int flowPosition, View view) {
                    super.unSelected(flowPosition, view);
                    ((CardView) view).setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.spec_text_color));
                    view.setBackgroundResource(R.drawable.boder_gray_textview);
                    TextView textView = view.findViewById(R.id.pisf_title_tv);
                    textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.textColor));
//                    specBinding.pisFlow.getAdapter().notifyDataChanged();
                }
            });
        }
    }


    public interface OnitemClick {
        void OnitemClick();
    }

    private PopGoodsSpecAdapterChat.OnitemClick onitemClick;

    public void setOnitemClick(PopGoodsSpecAdapterChat.OnitemClick listeren) {
        this.onitemClick = listeren;
    }

}
