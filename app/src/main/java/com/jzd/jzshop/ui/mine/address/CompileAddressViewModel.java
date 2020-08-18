package com.jzd.jzshop.ui.mine.address;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jzd.jzshop.data.Repository;
import com.jzd.jzshop.databinding.FragmentCompileAddressBinding;
import com.jzd.jzshop.entity.AddressListEntity;
import com.jzd.jzshop.entity.AreaAddressEntity;
import com.jzd.jzshop.ui.base.viewmodel.ToolbarViewModel;
import com.jzd.jzshop.utils.GetJsonDataUtil;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.net_utils.ParseDisposableObserver;
import com.jzd.jzshop.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class CompileAddressViewModel extends ToolbarViewModel<Repository> {
    private static final String TAG = CompileAddressViewModel.class.getSimpleName();
    private ArrayList<String> options1Items = new ArrayList<>();// 省集合
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();// 市集合
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();// 区集合
    private Context mcontext;
    public AddressListEntity.ResultBean.DataBean entity;

    //姓名
    public ObservableField<String> name = new ObservableField<>("");
    //收货电话
    public ObservableField<String> phone = new ObservableField<>("");
    //省  市  区
    public ObservableField<String> addressName = new ObservableField<>("");
    //详细地址
    public ObservableField<String> address = new ObservableField<>("");
    //默认地址
    public ObservableInt isdefault = new ObservableInt();

    public ObservableList<AreaAddressEntity> area = new ObservableArrayList<>();
    //选择地址最终结果
    private ArrayList<String> lastaddress = new ArrayList<>();

    public CompileAddressViewModel(@NonNull Application application, Repository model) {
        super(application, model);
    }

    public void setContext(Context context) {
        this.mcontext = context;
    }

    private FragmentCompileAddressBinding binding;

    public void compile(AddressListEntity.ResultBean.DataBean entity) {
        this.entity = entity;
        name.set(entity.getRealname());
        phone.set(entity.getMobile());
        address.set(entity.getAddress());
        addressName.set(entity.getProvince() + "  " + entity.getCity() + " " + entity.getArea());
        isdefault.set(entity.getIsdefault());
        lastaddress.add(entity.getProvince());
        lastaddress.add(entity.getCity());
        lastaddress.add(entity.getArea());
    }

    public void setBinding(FragmentCompileAddressBinding binding) {
        this.binding = binding;
    }

    @Override
    protected void setBackOnClick() {
        super.setBackOnClick();
        finish();
    }

    public void initToolbar(String title) {
        //初始化标题栏
        setRightIconVisible(View.GONE);
        setTitleText(title);
    }

    public BindingCommand onClickaddress = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KeyboardUtils.hideKeyboard(binding.editText);
            if (area.size() != 0) {
                popPickerView(area);
            }
        }
    });

    //点击开关
    public BindingCommand onClickSwitch = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            KeyboardUtils.hideKeyboard(binding.editText);
        }
    });

    private void popPickerView(final ObservableList<AreaAddressEntity> area) {
        parseData(area);
        lastaddress.clear();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mcontext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String province = options1Items.get(options1);
                String city = options2Items.get(options1).get(options2);
                String area = options3Items.get(options1).get(options2).get(options3);
                lastaddress.add(province);
                lastaddress.add(city);
                if (area != null) {
                    lastaddress.add(area);
                    addressName.set(province + "   " + city + "   " + area);
                } else {
                    lastaddress.add("");
                }
            }
        }).setContentTextSize(20)
                .isCenterLabel(false)
                .setCancelColor(Color.GRAY)
                .setSubmitColor(Color.RED)
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    //保存提交按钮
    public BindingCommand OnCLickCommt = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            String username = name.get();
            String iphone = phone.get();
            String addressa = address.get();
            boolean checked = binding.switchC.isChecked();
            if(checked){
                isdefault.set(1);
            }else {
                isdefault.set(0);
            }
            int i = isdefault.get();
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(iphone) && !TextUtils.isEmpty(addressa) && lastaddress.size() != 0) {
                String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
                Pattern p = Pattern.compile(regExp);
                Matcher matcher = p.matcher(iphone);
                boolean mobileNO = SystemUtils.isMobileNO(iphone);
                if (mobileNO) {
                    if (entity != null) {
                        compile(entity.getAddr_id(), username, iphone, lastaddress.get(0), lastaddress.get(1), lastaddress.get(2), addressa, i);
                    } else {
                        compile("", username, iphone, lastaddress.get(0), lastaddress.get(1), lastaddress.get(2), addressa, i);
                    }
                } else {
                    ToastUtils.showLong("请填写正确的手机号");
                }
            } else {
                ToastUtils.showLong("请填写详细地址");
            }
        }
    });

    void compile(String arr_id, String realname, String mobile, String province, String city, String area, String address, int isdefault) {
        addSubscribe(model.postCompileAddress(model.getUserToken(), arr_id, realname, mobile, province, city, area, address, isdefault), new ParseDisposableObserver<BaseResponse>() {
            @Override
            public void onResult(BaseResponse o) {
                dismissDialog();
                Log.d(TAG,"postCompileAddress: 添加地址成功");
                //发送刷新我地址列表数据  //发送刷新 积分商城 确认下单页
                Messenger.getDefault().sendNoMsg(CompileAddressFragment.ADDRESS_VIEWMODEL_REFRESH);
                finish();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
                Log.i("添加新地址", "2onResult: " + errarMessage);
            }
        });
    }

    @SuppressLint("CheckResult")
    void getAddress() {

        String json = new GetJsonDataUtil().getJson(mcontext, "mineaddress.json");
        String replace = json.replace(" ", "");
        List<AreaAddressEntity> o = new Gson().fromJson(replace, new TypeToken<List<AreaAddressEntity>>() {
        }.getType());
        area.addAll(o);
        //TODO 使用本地数据加载三级地址数据
        /*addSubscribe(model.getAddressData(), new ParseDisposableObserver<List<AreaAddressEntity>>() {
            @Override
            public void onResult(List<AreaAddressEntity> o) {
                dismissDialog();
            }

            @Override
            public void onError(String errarMessage) {
                dismissDialog();
            }
        });*/
    }

    private void parseData(ObservableList<AreaAddressEntity> area) {
        for (int i = 0; i < area.size(); i++) {
            options1Items.add(area.get(i).getText());
            //存放城市
            ArrayList<String> cityList = new ArrayList<>();
            //存放区
            ArrayList<ArrayList<String>> areaList = new ArrayList<>();
            for (int j = 0; j < area.get(i).getChildren().size(); j++) {
                String text = area.get(i).getChildren().get(j).getText();
                cityList.add(text);
                //给城市的地区列表
                ArrayList<String> city_address = new ArrayList<>();

                if (area.get(i).getChildren().get(j).getChildren() == null || area.get(i).getChildren().get(j).getChildren().size() == 0) {
                    city_address.add("");
                } else {
                    city_address.addAll(area.get(i).getChildren().get(j).getChildren());
                }
                areaList.add(city_address);
            }

            //城市的数据
            options2Items.add(cityList);
            //区的数据
            options3Items.add(areaList);

        }
    }


}
