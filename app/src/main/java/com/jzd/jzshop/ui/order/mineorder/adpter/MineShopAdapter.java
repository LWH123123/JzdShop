package com.jzd.jzshop.ui.order.mineorder.adpter;

import android.annotation.SuppressLint;
import android.app.assist.AssistStructure;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.FirmOrderEntity;
import com.jzd.jzshop.entity.MineOrderEntity;
import com.jzd.jzshop.ui.order.comment.CommentAty;
import com.jzd.jzshop.ui.order.logistics.LogisticInfoFragment;
import com.jzd.jzshop.ui.order.logistics.LogisticsFragment;
import com.jzd.jzshop.ui.order.mineorder.MineOrderViewModel;
import com.jzd.jzshop.ui.pay.PayFragment;
import com.jzd.jzshop.utils.Constants;
import com.jzd.jzshop.utils.dialog.MessageDialog;
import com.slodonapp.ywj_release.wxapi.WechatInfoSpHelper;
import com.umeng.analytics.MobclickAgent;

import java.nio.BufferUnderflowException;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author LWH
 * @description:
 * @date :2020/1/10 15:11
 */
public class MineShopAdapter extends RecyclerView.Adapter {


    private MineOrderEntity.ResultBean.DataBeanX dataBeanX;

    public void SetList(MineOrderEntity.ResultBean.DataBeanX dataBeanX) {
        this.dataBeanX = dataBeanX;
    }

    private MineOrderViewModel viewModel;

    public MineShopAdapter(MineOrderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mineorder_number, viewGroup, false);
            return new OrderNumber(inflate);
        } else if (itemViewType == 1) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mineorder, viewGroup, false);
            return new OrderMessage(inflate);

        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_shop_status, viewGroup, false);
            return new OrderPrice(inflate);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof OrderNumber) {
            OrderNumber number = (OrderNumber) viewHolder;
            number.ordernumber.setText("订单号: " + dataBeanX.getOrdersn());
            number.orderstatus.setText(dataBeanX.getStatusstr());
            switch (dataBeanX.getStatus()) {
                case -1:
//                    number.orderstatus.setText("订单已取消");
                    break;
                case 0:
                    if (dataBeanX.getPaytype() == 3) {
//                        number.orderstatus.setText("等待卖家发货");
                    } else {
//                        number.orderstatus.setText("等待买家付款");
                    }
                    break;
                case 1:
//                    number.orderstatus.setText("等待卖家发货");
                    break;
                case 2:
//                    number.orderstatus.setText("等待买家收货");
                    break;
                case 3:
                    number.im_status.setVisibility(View.VISIBLE);
                    number.orderstatus.setTextColor(Color.parseColor("#69DB77"));
                    int closecomment = dataBeanX.getClosecomment();
                    int iscomment = dataBeanX.getIscomment();
                    if (closecomment == 0) {
                        if (iscomment == 0) {
//                            number.orderstatus.setText("等待买家评价");
                        } else {
                            number.im_status.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        number.im_status.setVisibility(View.INVISIBLE);
                    }

                    break;
                case 4:
                    number.orderstatus.setText(dataBeanX.getStatusstr());
                    break;
                case 7:
                    number.orderstatus.setText("已取消");
                    number.orderstatus.setTextColor(Color.GRAY);
                    number.im_status.setVisibility(View.INVISIBLE);
                    break;
            }
        } else if (viewHolder instanceof OrderMessage) {
            OrderMessage message = (OrderMessage) viewHolder;
            message.recycle.setLayoutManager(new LinearLayoutManager(message.recycle.getContext()));
            MineStoreAdapter mineOrderAdapter = new MineStoreAdapter();
            mineOrderAdapter.SetList(dataBeanX.getData());
            message.recycle.setAdapter(mineOrderAdapter);
            mineOrderAdapter.setOnitemClick(new MineStoreAdapter.OnitemClick() {
                @Override
                public void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean) {
                    if (onitemClick != null) {
                        bean.setOrderid(dataBeanX.getOrder_id());
                        bean.setAllprice(dataBeanX.getPrice());
                        onitemClick.OnitemClick(bean);
                    }
                }
            });
            mineOrderAdapter.setonStoreClick(new MineStoreAdapter.OnitemStoreClick() {
                @Override
                public void onitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean) {
                    if (onitemStoreClick != null) {
                        onitemStoreClick.onitemClick(dataBean);
                    }
                }
            });
        } else if (viewHolder instanceof OrderPrice) {
            final OrderPrice price = (OrderPrice) viewHolder;
            price.goodnumber.setText("共计" + dataBeanX.getData().size() + "件商品  实付:");
            price.money.setText("¥" + dataBeanX.getPrice());
            int status = dataBeanX.getStatus();
            switch (status) {
                case 0://待付款group_pay
                    price.group_pay.setVisibility(View.VISIBLE);//待付款
                    if (dataBeanX.getIs_peerpay() == 0) {
                        price.payorder.setText("付款");
                    } else {
                        price.payorder.setText("代付订单");
                    }
                    break;
                case 1://待发货remind
                    //提醒发货  待开启
                    price.remind.setVisibility(View.GONE);//待发货
                    price.view32.setVisibility(View.GONE);
                    break;
                case 2://待收货group_checklogis
                    price.group_checklogis.setVisibility(View.VISIBLE);//待收货
                    break;
                case 3://待评价group_evaluate
                    price.im_delete.setVisibility(View.VISIBLE);
                    price.group_evaluate.setVisibility(View.VISIBLE);//已完成
                    int iscomment = dataBeanX.getIscomment();
                    if (dataBeanX.getClosecomment() == 1) {
                        price.evaluate.setVisibility(View.GONE);
                    } else {
                        price.evaluate.setVisibility(View.VISIBLE);
                        switch (iscomment) {
                            case 0:
                                price.evaluate.setText("评价");
                                break;
                            case 1:
                                price.evaluate.setText("追加评价");
                                break;
                            case 2:
                                price.evaluate.setText("已评价");
                                break;
                        }
                    }
                    break;
                case -1:
                    price.im_delete.setVisibility(View.VISIBLE);
                    break;
            }
            /*联系商家*/
            price.contactstore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.MerchantCustomerService();
                }
            });
            /*取消订单*/
            price.concleorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.cancleOrder(dataBeanX.getOrder_id());
                    viewModel.dismissDialog();
                    dataBeanX.setStatus(7);
                    price.im_delete.setVisibility(View.VISIBLE);
                    price.group_pay.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }
            });

            /*付款*/
            price.payorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.saveFlag();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.BUNDLE_KEY_MONEY,dataBeanX.getPrice());
                    bundle.putString(Constants.BUNDLE_KEY_ORDERID, dataBeanX.getOrder_id());
                    viewModel.startContainerActivity(PayFragment.class.getCanonicalName(), bundle);
                }
            });
            /*查看物流*/
            price.checklogistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sendtype = dataBeanX.getSendtype();
                    if(sendtype==0){
                        // TODO: 2020/1/16 跳转物流详情页面
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.MyOrder,dataBeanX.getOrder_id());
                        bundle.putString(Constants.ORDER_SEND_TYPE,String.valueOf(dataBeanX.getSendtype()));
                        viewModel.startContainerActivity(LogisticInfoFragment.class.getCanonicalName(),bundle);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.ORDER_LOGISTICS,dataBeanX.getOrder_id());
                        viewModel.startContainerActivity(LogisticsFragment.class.getCanonicalName(),bundle);
                    }
                }
            });
            /*查看物流*/
            price.checklogis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sendtype = dataBeanX.getSendtype();
                    if(sendtype==0){
                        // TODO: 2020/1/16 跳转物流详情页面
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.MyOrder,dataBeanX.getOrder_id());
                        bundle.putString(Constants.ORDER_SEND_TYPE,String.valueOf(dataBeanX.getSendtype()));
                        viewModel.startContainerActivity(LogisticInfoFragment.class.getCanonicalName(),bundle);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.ORDER_LOGISTICS,dataBeanX.getOrder_id());
                        viewModel.startContainerActivity(LogisticsFragment.class.getCanonicalName(),bundle);
                    }
                }
            });
            /*评价*/
            price.evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = price.evaluate.getText().toString();
                    if(s.equals("已评价")){
                        return;
                    }
                    int iscomment = dataBeanX.getIscomment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.ORDER_ID, dataBeanX.getOrder_id());
                    bundle.putString(Constants.IS_EVALUATION, String.valueOf(iscomment));
                    viewModel.startActivity(CommentAty.class,bundle);
                }
            });

            /*提醒发货*/
            price.remind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            /*确认收货*/
            price.tv_consignee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewModel.onfirmGoods(dataBeanX.getOrder_id());
                    viewModel.dismissDialog();
                }
            });


            /*删除订单*/
            price.im_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageDialog.showCancelAndSureDialog(price.im_delete.getContext(), "确认要删除吗？","", R.color.textColor, R.color.textColor, new MessageDialog.DialogClick() {
                        @Override
                        public void onCancelClickListener() {
                        }

                        @Override
                        public void onSureClickListener() {
                            notifyDataSetChanged();
                            viewModel.deleteOrder(dataBeanX.getOrder_id());
                            viewModel.dismissDialog();
                            if (onitemDeleteClick != null) {
                                onitemDeleteClick.onitemDeleteClick(dataBeanX);
                            }
                        }
                    });



                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (dataBeanX != null && dataBeanX.getData() != null) {
            return 3;
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (dataBeanX != null && dataBeanX.getOrdersn() != null && !TextUtils.isEmpty(dataBeanX.getOrdersn()) && position == 0) {
            return 0;
        } else if (dataBeanX != null && dataBeanX.getData() != null && dataBeanX.getData().size() != 0 && position == 1) {
            return 1;
        } else {
            return 2;
        }
    }


    //订单号
    public class OrderNumber extends RecyclerView.ViewHolder {
        TextView ordernumber;
        TextView orderstatus;
        ImageView im_status;

        public OrderNumber(@NonNull View itemView) {
            super(itemView);
            ordernumber = itemView.findViewById(R.id.tv_ordernumber);
            orderstatus = itemView.findViewById(R.id.tv_orderstatus);
            im_status = itemView.findViewById(R.id.im_status);
        }
    }

    //店铺信息
    public class OrderMessage extends RecyclerView.ViewHolder {
        RecyclerView recycle;

        public OrderMessage(@NonNull View itemView) {
            super(itemView);
            recycle = itemView.findViewById(R.id.recycle);

        }
    }

    /*待付款group_pay

                    break;
                case 1://已付款remind

                    break;
                case 2://待收货group_checklogis

                    break;
                case 3://待评价group_evaluate
*/
    //总额计算
    public class OrderPrice extends RecyclerView.ViewHolder {
        TextView goodnumber;
        TextView money, remind, contactstore, concleorder, payorder, checklogistics, evaluate, tv_consignee, checklogis;
        Group group_pay, group_checklogis, group_evaluate;
        ImageView im_delete;
        View view32;

        public OrderPrice(@NonNull View itemView) {
            super(itemView);
            goodnumber = itemView.findViewById(R.id.tv_goodnumber);
            money = itemView.findViewById(R.id.money);
            group_pay = itemView.findViewById(R.id.group_pay);
            group_checklogis = itemView.findViewById(R.id.group_checklogis);
            group_evaluate = itemView.findViewById(R.id.group_evaluate);
            remind = itemView.findViewById(R.id.remind);
            contactstore = itemView.findViewById(R.id.contactstore);
            concleorder = itemView.findViewById(R.id.concleorder);
            payorder = itemView.findViewById(R.id.payorder);
            checklogistics = itemView.findViewById(R.id.checklogistics);
            evaluate = itemView.findViewById(R.id.evaluate);
            tv_consignee = itemView.findViewById(R.id.tv_consignee);
            checklogis = itemView.findViewById(R.id.checklogis);
            im_delete = itemView.findViewById(R.id.im_delete);
            view32 = itemView.findViewById(R.id.view32);


        }
    }

    public interface OnitemClick {
        void OnitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean.GoodsBean bean);
    }

    private OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick listeren) {
        this.onitemClick = listeren;
    }


    private OnitemStoreClick onitemStoreClick;

    public interface OnitemStoreClick {
        void onitemClick(MineOrderEntity.ResultBean.DataBeanX.DataBean dataBean);
    }

    public void setonStoreClick(OnitemStoreClick listeren) {
        this.onitemStoreClick = listeren;
    }

    private OnitemDeleteClick onitemDeleteClick;

    public interface OnitemDeleteClick {
        void onitemDeleteClick(MineOrderEntity.ResultBean.DataBeanX dataBean);
    }

    public void setonDeleteClick(OnitemDeleteClick listeren) {
        this.onitemDeleteClick = listeren;
    }





}
