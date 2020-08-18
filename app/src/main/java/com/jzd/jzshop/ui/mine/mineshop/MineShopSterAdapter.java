package com.jzd.jzshop.ui.mine.mineshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AreaAddressEntity;
import com.jzd.jzshop.entity.HabitEntity;
import com.jzd.jzshop.entity.MessageEntity;
import com.jzd.jzshop.entity.MineShopEntity;
import com.jzd.jzshop.entity.PerFectEntity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.AgeAdapter;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.EducationAdapter;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.GridImageAdapter;
import com.jzd.jzshop.ui.mine.setting.perfectdata.adpter.HabitAdapter;
import com.jzd.jzshop.utils.AddressSelectUtils;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.jzd.jzshop.utils.TimeSelectUtils;
import com.luck.picture.lib.config.PictureConfig;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author lwh
 * @description :
 * @date 2020/3/31.
 */
public class MineShopSterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MineShopEntity.ResultBean.DataBean.BaseBean> baseBeanList;
    public HashMap<String,String> hashMap =new HashMap<>();
    public HashMap<String,GridImageAdapter> map =new HashMap<>();
    private MineShopActivity activity;
    private GridImageAdapter gridImageAdapter;
    private String tp_key;

    public void setTp_key(String tp_key) {
        this.tp_key = tp_key;
    }

    public MineShopSterAdapter(List<MineShopEntity.ResultBean.DataBean.BaseBean> baseBeanList, MineShopActivity activity) {
        this.baseBeanList = baseBeanList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if(i==0){
            View inflate = from.inflate(R.layout.item_complete_zero, viewGroup,false);
            TypeZero typeZero = new TypeZero(inflate);
            return typeZero;
        }else if(i==1){
            View inflate = from.inflate(R.layout.item_complete_one, viewGroup, false);
            TypeOne typeOne = new TypeOne(inflate);
            return typeOne;
        }else if(i==3){
            View inflate = from.inflate(R.layout.item_complete_three, viewGroup,false);
            TypeThree typeThree = new TypeThree(inflate);
            return typeThree;
        }else if(i==5){
            View inflate = from.inflate(R.layout.item_complete_five, viewGroup, false);
            TypeFive typeFive = new TypeFive(inflate);
            return typeFive;
        }else if(i==8){
            View inflate = from.inflate(R.layout.item_complete_eight, viewGroup, false);
            TypeEight typeEight = new TypeEight(inflate);
            return typeEight;
        }else if(i==10){
            View inflate = from.inflate(R.layout.item_complete_ten, viewGroup, false);
            TypeTen typeTen = new TypeTen(inflate);
            return typeTen;
        }else if(i==14){
            View inflate = from.inflate(R.layout.item_complete_three, viewGroup, false);
            TypeFourteen typeFourteen = new TypeFourteen(inflate);
            return typeFourteen;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MineShopEntity.ResultBean.DataBean.BaseBean baseBean = baseBeanList.get(i);
        int itemViewType = viewHolder.getItemViewType();
        switch (itemViewType){
            case 0://文本类型
                TypeZero zero= (TypeZero) viewHolder;
                int  type = baseBean.getTp_type();
                zero.name.setText(baseBean.getTp_name());
                if(baseBean.getTp_must()==1){
                    zero.xing.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(baseBean.getTp_value())){
                    zero.value.setText(baseBean.getTp_value());
                    hashMap.put(baseBean.getTp_key(),baseBean.getTp_value());
                }else {
                    if(type!=9) {
                        zero.value.setHint(baseBean.getTp_holder());
                    }else if(baseBean.getTp_value()!=null&&!TextUtils.isEmpty(baseBean.getTp_valueaddress().getProvince())){
                        zero.value.setText("   "+baseBean.getTp_valueaddress().getProvince()+" "+baseBean.getTp_valueaddress().getArea()+" "+baseBean.getTp_valueaddress().getCity());
                    }else {
                        zero.value.setHint("   "+baseBean.getTp_holder());
                    }
                }
                if(type==9){
                    MineShopEntity.ResultBean.DataBean.TpvalueBean tp_value = baseBean.getTp_valueaddress();
                    if(baseBean.getArea()==0){
                        hashMap.put(baseBean.getTp_key(),tp_value.getProvince()+"@"+tp_value.getCity());
                    }else if(baseBean.getArea()==1){
                        hashMap.put(baseBean.getTp_key(),tp_value.getProvince()+"@"+tp_value.getCity()+"@"+tp_value.getArea());
                    }
                }
                typezero(zero,baseBean);
                break;
            case 1://多行文本
                TypeOne one = (TypeOne) viewHolder;
                one.name.setText(baseBean.getTp_name());
                if(baseBean.getTp_must()==1){
                    one.xing.setVisibility(View.VISIBLE);
                }
                if(!TextUtils.isEmpty(baseBean.getTp_value())){
                    hashMap.put(baseBean.getTp_key(),baseBean.getTp_value());
                    one.value.setText(baseBean.getTp_value());
                }else {
                    one.value.setHint(baseBean.getTp_holder());
                }
                one.value.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!TextUtils.isEmpty(s.toString())) {
                            hashMap.put(baseBean.getTp_key(),s.toString());
                        }else {
                            hashMap.remove(baseBean.getTp_key());
                        }
                    }
                });
                break;
            case 3://多选框
                TypeThree typeThree = (TypeThree) viewHolder;
                typeThree.name.setText(baseBean.getTp_name());
                if(baseBean.getTp_must()==1){
                    typeThree.xing.setVisibility(View.VISIBLE);
                }
                //todo 添加多选按钮
                typeThree(typeThree,baseBean);
                break;

            case 5://多图上传
                TypeFive typeFive = (TypeFive) viewHolder;
                typeFive.name.setText(baseBean.getTp_name());
                if(baseBean.getTp_must()==1){
                    typeFive.xing.setVisibility(View.VISIBLE);
                }
                ArrayList<LocalMedias> data = new ArrayList<>();
                List<String> tp_array = baseBean.getTp_valuelist();
                if(tp_array!=null&&tp_array.size()!=0){
                for (int j = 0; j <tp_array.size() ; j++) {
                    LocalMedias localMedias = new LocalMedias();
                    if(!TextUtils.isEmpty(tp_array.get(j))) {
                        localMedias.setUrl(tp_array.get(j));
                        data.add(localMedias);
                       }
                     }
                }

                PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(activity,baseBean.getTp_max(), PictureConfig.CHOOSE_REQUEST);
               GridImageAdapter gridImageAdapter = new GridImageAdapter(activity, pictureSelectorUtils.onAddPicClickListener);
                typeFive.value.setAdapter(gridImageAdapter);
                FullyGridLayoutManager manager = new FullyGridLayoutManager(typeFive.value.getContext(),
                        3, GridLayoutManager.VERTICAL, false);
                typeFive.value.setLayoutManager(manager);
                gridImageAdapter.setList(data);
                gridImageAdapter.setAddmipmap(R.mipmap.bg_addimage);
                gridImageAdapter.setMust(baseBean.getTp_must());
                gridImageAdapter.setKey(baseBean.getTp_key());
                gridImageAdapter.setSelectMax(baseBean.getTp_max());
                gridImageAdapter.setOnAddClickListener(new GridImageAdapter.OnAddClickListener() {
                    @Override
                    public void onAddClick(String key) {
                        setTp_key(key);
                    }
                });
                map.put(baseBean.getTp_key(),gridImageAdapter);
                break;
            case 8://时间日期  范围 格式的
                TypeEight typeEight= (TypeEight) viewHolder;
                typeEight.name.setText(baseBean.getTp_name());
                if(baseBean.getTp_must()==1){
                    typeEight.xing.setVisibility(View.VISIBLE);
                }
                if(baseBean.getTp_valuelist()!=null&&baseBean.getTp_valuelist().size()!=0&&!TextUtils.isEmpty(baseBean.getTp_valuelist().get(0))){
                    String start = baseBean.getTp_valuelist().get(0);
                    String end = baseBean.getTp_valuelist().get(1);
                    typeEight.valueone.setText(start);
                    typeEight.valuetwo.setText(end);
                    hashMap.put(baseBean.getTp_key(),start+"@"+end);
                }else {
                    typeEight.valueone.setText("开始日期");
                    typeEight.valuetwo.setText("结束日期");
                }
                typeEight(typeEight,baseBean);
                break;
            case 10://确认文本
                TypeTen typeTen= (TypeTen) viewHolder;
                typeTen.name.setText(baseBean.getTp_name());
                typeTen.name2.setText(baseBean.getTp_name2());
                if(baseBean.getTp_must()==1){
                    typeTen.xing.setVisibility(View.VISIBLE);
                }
                if(baseBean.getTp_valuelist()!=null&&baseBean.getTp_valuelist().size()!=0&&!TextUtils.isEmpty(baseBean.getTp_valuelist().get(0))){
                    typeTen.valueone.setText(baseBean.getTp_valuelist().get(0));
                    typeTen.valuetwo.setText(baseBean.getTp_valuelist().get(1));
                    hashMap.put(baseBean.getTp_key(),baseBean.getTp_valuelist().get(0)+"@"+baseBean.getTp_valuelist().get(1));
                }else {
                    typeTen.valueone.setInputType(InputType.TYPE_CLASS_TEXT);
                    typeTen.valuetwo.setInputType(InputType.TYPE_CLASS_TEXT);
                    typeTen.valueone.setHint(baseBean.getTp_holderlist().get(0));
                    typeTen.valuetwo.setHint(baseBean.getTp_holderlist().get(1));
                }
                typeTen(typeTen,baseBean);
                break;
            case 14://单选框
                TypeFourteen typeFourteen= (TypeFourteen) viewHolder;
                typeFourteen.name.setText(baseBean.getTp_name());
                if(baseBean.getTp_must()==1){
                    typeFourteen.xing.setVisibility(View.VISIBLE);
                }
                typeFourteen(typeFourteen,baseBean);
                break;
        }
    }


    /**
     * 单选框
    * */
    private void typeFourteen(TypeFourteen typeFourteen, final MineShopEntity.ResultBean.DataBean.BaseBean baseBean) {
        List<String> tp_text = baseBean.getTp_textList();
        String tp_string = baseBean.getTp_value();
        if(!TextUtils.isEmpty(tp_string)){
            hashMap.put(baseBean.getTp_key(),tp_string);
        }
        final ArrayList<MessageEntity> list = new ArrayList<>();
        for (int i = 0; i <tp_text.size() ; i++) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setAge(tp_text.get(i));
            messageEntity.setSelect(0);
            if(baseBean.getTp_value()!=null&&!TextUtils.isEmpty(baseBean.getTp_value())){
                if(tp_text.get(i).equals(baseBean.getTp_value())) {
                    messageEntity.setSelect(1);
                }
            }
            list.add(messageEntity);
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(activity,
                3, GridLayoutManager.VERTICAL, false);
        typeFourteen.value.setLayoutManager(manager);
        AgeAdapter ageAdapter = new AgeAdapter(list);
        typeFourteen.value.setAdapter(ageAdapter);
        ageAdapter.setOnitemClick(new AgeAdapter.OnitemClick() {
            @Override
            public void OnitemClick(int position) {
                MessageEntity messageEntity = list.get(position);
                hashMap.put(baseBean.getTp_key(),messageEntity.getAge());
            }
        });
    }

    /**
     * 确认文本
    * */
    private void typeTen(TypeTen typeTen, final MineShopEntity.ResultBean.DataBean.BaseBean baseBean) {
        final HashMap<String, String> hash = new HashMap<>();
        typeTen.valueone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    hash.put("START",s.toString());
                    if(hash.size()==2){
                        processingdata(hash,baseBean.getTp_key());
                    }
                }
            }
        });
        typeTen.valuetwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
                    hash.put("END",s.toString());
                    if(hash.size()==2){
                        processingdata(hash,baseBean.getTp_key());
                    }
                }
            }
        });
    }


    /**
     * 日期范围
    * */
    private void typeEight(final TypeEight typeEight, final MineShopEntity.ResultBean.DataBean.BaseBean baseBean) {
        int tp_type = baseBean.getTp_type();
        switch (tp_type){
            case 8://****年到***年
                final HashMap<String, String> data = new HashMap<>();
                typeEight.rlselectone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(typeEight.valueone);
                        initTimePicker1(typeEight.valueone,"START",data,0,baseBean.getTp_key(),100);
                    }
                });
                typeEight.rlselecttwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(typeEight.valueone);
                        initTimePicker1(typeEight.valuetwo,"END",data,0,baseBean.getTp_key(),100);
                    }
                });

                break;
            case 12://12:00到11:00
                final HashMap<String, String> sb = new HashMap<>();
                typeEight.rlselectone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time(1,"START",typeEight.valueone,sb,baseBean.getTp_key(),100);
                    }
                });
                typeEight.rlselecttwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time(1,"END",typeEight.valuetwo,sb,baseBean.getTp_key(),100);

                    }
                });
                break;
        }


    }

    //多选设置
    private void typeThree(TypeThree typeThree, final MineShopEntity.ResultBean.DataBean.BaseBean baseBean) {
        List<String> tp_text = baseBean.getTp_textList();
        List<String> tp_array = baseBean.getTp_valuelist();
        ArrayList<HabitEntity> habitlist = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        if(tp_array!=null&&tp_array.size()!=0){
            for (int i = 0; i <tp_array.size() ; i++) {
                if(i!=0){
                    sb.append("@");
                }
                sb.append(tp_array.get(i));
            }
        }
        hashMap.put(baseBean.getTp_key(),sb.toString());
        for (String s : tp_text) {
            HabitEntity habitEntity = new HabitEntity(s, false);
            if (tp_array != null && tp_array.size() != 0) {
                for (String s1 : tp_array) {

                    if (s.equals(s1)) {
                        habitEntity.setStatus(true);
                    }
                }
            }
            habitlist.add(habitEntity);
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(activity,
                3, GridLayoutManager.VERTICAL, false);
        typeThree.value.setLayoutManager(manager);
        final HabitAdapter habitAdapter = new HabitAdapter(habitlist);
        typeThree.value.setAdapter(habitAdapter);
        habitAdapter.setOnitemClick(new HabitAdapter.OnitemClick() {
            @Override
            public void OnitemClick(int position, boolean ischeck) {
                String data = habitAdapter.getData();
                hashMap.put(baseBean.getTp_key(),data);

            }
        });

    }

    //类型为普通文本
    MaterialDialog show;
    private void typezero(final TypeZero zero, final MineShopEntity.ResultBean.DataBean.BaseBean baseBean) {
        int tp_type = baseBean.getTp_type();
        switch (tp_type){
            case 0:
                zero.value.setHint(baseBean.getTp_holder());
                zero.value.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        hashMap.put(baseBean.getTp_key(),s.toString());
                    }
                });
                break;
            case 2://单点  不可编辑
                if(!TextUtils.isEmpty(baseBean.getTp_value())){
                    zero.value.setText(baseBean.getTp_value());
                }else {
                    zero.value.setText("请输入您的" + baseBean.getTp_name());
                }
                zero.value.setClickable(true);
                zero.value.setFocusable(false);
                final List<String> holder_array = baseBean.getTp_textList();
                final EducationAdapter educationAdapter = new EducationAdapter(holder_array);
                zero.value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show = new MaterialDialog.Builder(activity)
                                .adapter(educationAdapter, new LinearLayoutManager(activity))
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        dialog.dismiss();
                                    }
                                })
                                .buttonsGravity(GravityEnum.START)
                                .show();
                    }
                });
                educationAdapter.setOnitemClick(new EducationAdapter.OnitemClick() {
                    @Override
                    public void OnitemClick(int position) {
                        String s = holder_array.get(position);
                        hashMap.put(baseBean.getTp_key(),s);
                        zero.value.setText(s);
                        show.dismiss();
                    }
                });
                break;
            case 6:
                EditText editText=zero.value;
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!TextUtils.isEmpty(s.toString())){
                            hashMap.put(baseBean.getTp_key(),s.toString());
                        }else {
                            hashMap.remove(baseBean.getTp_key());
                        }
                    }
                });
                break;
            case 7:
                zero.value.setClickable(true);
                zero.value.setFocusable(false);
                zero.value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(zero.value);
                        initTimePicker1(zero.value,baseBean.getTp_key(),hashMap,0,baseBean.getTp_key(),99);
                    }
                });
                break;
            case 9:
                final int area = baseBean.getArea();
                zero.value.setClickable(true);
                zero.value.setFocusable(false);
                zero.value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(zero.value);
                        if(area==0){//二级地址
                            selectAddress(baseBean.getTp_key(),zero.value);
                        }else if(area==1){//三级地址
                            popPickerView(baseBean.getTp_key(),zero.value);
                        }
                    }
                });
                break;
            case 11://时间选择功能
                zero.value.setClickable(true);
                zero.value.setFocusable(false);
                zero.value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(zero.value);
                        KLog.a("选择时间");
//                        time(1,fieldsBean.getTp_key(),zero.value,hashMap,fieldsBean.getTp_key(),99);
                    }
                });
                break;
            case 13://提示文本
                zero.name.setText(baseBean.getTp_name());
                zero.value.setVisibility(View.GONE);
                zero.value.setClickable(false);
                zero.value.setFocusable(false);
                break;
        }
    }

    //TODO 打开三级地址
    public void popPickerView(final String key, final TextView view) {
        final AddressSelectUtils addressSelectUtils = new AddressSelectUtils();
        addressSelectUtils.thirdAddress(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String province = addressSelectUtils.options1province.get(options1);
                String city = addressSelectUtils.options2city.get(options1).get(options2);
                String area = addressSelectUtils.options3area.get(options1).get(options2).get(options3);
                StringBuffer sb = new StringBuffer();
                sb.append(province);
                sb.append("@");
                sb.append(city);
                sb.append("@");
                sb.append(area);
                hashMap.put(key,sb.toString());
                view.setText(province+" "+city+" "+area);
            }
        });
    }

    //todo 打开二级地址
    private void selectAddress(final String key, final TextView view) {
        final AddressSelectUtils addressSelectUtils = new AddressSelectUtils();
        addressSelectUtils.secondAddress(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String province = addressSelectUtils.provincedata.get(options1);
                String area = addressSelectUtils.citydata.get(options1).get(options2);
                StringBuffer sb = new StringBuffer();
                view.setText(province+" "+area);
                sb.append(province);
                sb.append("@");
                sb.append(area);
                hashMap.put(key,sb.toString());
            }
        });
    }

    //todo  获取XXXX年XX月XX日时间选择器
    private void initTimePicker1(final TextView view, final String sb , final HashMap<String,String> hash, final int type, final String key, final int scope) {//选择出生年月日
        TimeSelectUtils timeSelectUtils = new TimeSelectUtils();
        timeSelectUtils.SelectYMDTime(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date, type);
                view.setText(time);
                if(scope==99){
                    hash.put(sb,time);
                }else {
                    hash.put(sb,time);
                    KLog.i("SIZE===___"+hash.size());
                    if(hash.size()==2){
                        processingdata(hash,key);
                    }
                }
            }
        });
    }

    //todo 获取XX时XX日时间选择
    public void time(final int type, final String sb, final TextView view, final HashMap<String,String> hash, final String key, final int score) {
        TimeSelectUtils timeSelectUtils = new TimeSelectUtils();
        timeSelectUtils.SelectHMTime(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date,type);
                view.setText(time);
                if(score==99){
                    hashMap.put(key,time);
                }else if(score==100){
                    hash.put(sb,time);
                    KLog.a("SIZE+++++"+hash.size());
                    if(hash.size()==2){
                        processingdata(hash,key);
                    }
                }
            }
        });
    }


    //组装新的数据
    private void processingdata(HashMap<String,String> hash,String key){
        String start = hash.get("START");
        String end = hash.get("END");
        StringBuffer sb = new StringBuffer();
        sb.append(start);
        sb.append("@");
        sb.append(end);
        hashMap.put(key,sb.toString());
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            KLog.a("KEY==="+entry.getKey()+"            VALUE===="+entry.getValue());
        }
    }

    //获取时间的格式
    private String getTime(Date date, int type) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = null;
        if (type == 0) {//TYPE 为1时时间为  几几年 几月  几日
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else if (type == 1) {
            format = new SimpleDateFormat("HH:mm");
        }
        return format.format(date);
    }

    //添加本地选取的图片
    public void addpicture(List<LocalMedias> media){
        if(map!=null&&map.size()!=0){
            GridImageAdapter gridImageAdapter = map.get(tp_key);
            if(gridImageAdapter!=null) {
                if(gridImageAdapter.key.equals(tp_key)) {
                    if(gridImageAdapter.getLists()!=null&&gridImageAdapter.getLists().size()==0){
                        gridImageAdapter.setList(media);
                    }else {
                        gridImageAdapter.addList(media);
                    }
                    gridImageAdapter.notifyDataSetChanged();
                    KLog.a("我保存起来的KEY    "+gridImageAdapter.key);
                }
            }
        }
    }




    //查看必填项是否有没填写的
    public boolean mustInport(){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <baseBeanList.size() ; i++) {
            MineShopEntity.ResultBean.DataBean.BaseBean baseBean = baseBeanList.get(i);
             if(baseBean.getTp_must()==1&&baseBean.getTp_type()!=5){
                 list.add(baseBean.getTp_key());
             }
        }
        if(list.size()==0){
            return true;
        }
        ArrayList<String> data = new ArrayList<>();
        for (Map.Entry<String,String> entry : hashMap.entrySet()) {
            data.add(entry.getKey());
        }
        if(list.size()>data.size()){
            return false;
        }

        for (int i = 0; i <list.size() ; i++) {
            if(data.contains(list.get(i))){
                if(TextUtils.isEmpty(hashMap.get(list.get(i)))){
                    KLog.a("必填数据为空");
                   return false;
                }
            }else {
                return false;
            }
        }
          return true;
        }




    @Override
    public int getItemCount() {
        return baseBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MineShopEntity.ResultBean.DataBean.BaseBean baseBean = baseBeanList.get(position);
        int type = baseBean.getTp_type();
        if(type==0||type==2||type==6||type==7||type==9||type==11||type==13){
            return 0;
        }else if(type==1){//多行文本
            return 1;
        }else if(type==3){//复选框
            return 3;
        }else if(type==5){//图片
            return 5;
        }else if(type==8||type==12){//日期/时间范围
            return 8;
        }else if(type==10){//确认文本
            return 10;
        }else if(type==14){//单选框
            return 14;
        }
        return position;
    }

    public class TypeZero extends RecyclerView.ViewHolder{
        TextView xing,name;
        EditText value;
        public TypeZero(@NonNull View itemView) {
            super(itemView);
            xing= itemView.findViewById(R.id.tv_xing);
            name= itemView.findViewById(R.id.tv_name);
            value= itemView.findViewById(R.id.ed_value);
        }
    }
    public class TypeOne extends RecyclerView.ViewHolder{
        TextView xing,name;
        EditText value;
        public TypeOne(@NonNull View itemView) {
            super(itemView);
            xing= itemView.findViewById(R.id.tv_xing);
            name=itemView.findViewById(R.id.tv_name);
            value=itemView.findViewById(R.id.ed_value);
        }
    }
    public class TypeThree extends RecyclerView.ViewHolder{
        TextView xing,name;
        RecyclerView value;
        public TypeThree(@NonNull View itemView) {
            super(itemView);
            xing= itemView.findViewById(R.id.tv_xing);
            name= itemView.findViewById(R.id.tv_name);
            value= itemView.findViewById(R.id.rv_value);
        }
    }
    public class TypeFive extends RecyclerView.ViewHolder{
        TextView xing,name;
        RecyclerView value;
        public TypeFive(@NonNull View itemView) {
            super(itemView);
            xing= itemView.findViewById(R.id.tv_xing);
            name=itemView.findViewById(R.id.tv_name);
            value=itemView.findViewById(R.id.rv_value);
        }
    }
    public class TypeEight extends RecyclerView.ViewHolder{
        TextView xing,name,valueone,valuetwo;
        RelativeLayout rlselectone,rlselecttwo;
        public TypeEight(@NonNull View itemView) {
            super(itemView);
            rlselectone=itemView.findViewById(R.id.rl_selectone);
            rlselecttwo=itemView.findViewById(R.id.rl_selecttwo);
            name=itemView.findViewById(R.id.tv_name);
            xing=itemView.findViewById(R.id.tv_xing);
            valueone=itemView.findViewById(R.id.tv_valueone);
            valuetwo=itemView.findViewById(R.id.tv_valuetwo);
        }
    }
    public class TypeTen extends RecyclerView.ViewHolder{
        TextView xing,name,name2;
        EditText valueone,valuetwo;
        public TypeTen(@NonNull View itemView) {
            super(itemView);
            xing=itemView.findViewById(R.id.tv_xing);
            name=itemView.findViewById(R.id.tv_name);
            name2=itemView.findViewById(R.id.tv_name2);
            valueone=itemView.findViewById(R.id.ed_valueone);
            valuetwo=itemView.findViewById(R.id.ed_valuetwo);
        }
    }
    public class TypeFourteen extends RecyclerView.ViewHolder{
        TextView xing,name;
        RecyclerView value;
        public TypeFourteen(@NonNull View itemView) {
            super(itemView);
            xing= itemView.findViewById(R.id.tv_xing);
            name= itemView.findViewById(R.id.tv_name);
            value= itemView.findViewById(R.id.rv_value);
        }
    }


}
