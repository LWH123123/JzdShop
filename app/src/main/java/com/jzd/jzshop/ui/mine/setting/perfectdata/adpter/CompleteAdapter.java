package com.jzd.jzshop.ui.mine.setting.perfectdata.adpter;

import android.content.Context;
import android.databinding.ObservableList;
import android.graphics.Color;
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
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jzd.jzshop.R;
import com.jzd.jzshop.entity.AreaAddressEntity;
import com.jzd.jzshop.entity.HabitEntity;
import com.jzd.jzshop.entity.MessageEntity;
import com.jzd.jzshop.entity.MineShopEntity;
import com.jzd.jzshop.entity.PerFectEntity;
import com.jzd.jzshop.ui.mine.setting.perfectdata.complete.CompleteAty;
import com.jzd.jzshop.utils.AddressSelectUtils;
import com.jzd.jzshop.utils.FullyGridLayoutManager;
import com.jzd.jzshop.utils.KeyboardUtils;
import com.jzd.jzshop.utils.LocalMedias;
import com.jzd.jzshop.utils.PictureSelectorUtils;
import com.jzd.jzshop.utils.TimeSelectUtils;
import com.luck.picture.lib.config.PictureConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;

public class CompleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PerFectEntity.ResultBean.FieldsBean> resultBean;

    public HashMap<String,String> hashMap =new HashMap<>();

    //三级地址
    private List<AreaAddressEntity> address=new ArrayList<>();

    private CompleteAty completeAty;
    public GridImageAdapter gridImageAdapter;
    public String ImageUrl;
    public  ArrayList<LocalMedias> data = new ArrayList<>();

    public void setData(CompleteAty completeAty, List<PerFectEntity.ResultBean.FieldsBean> resultBean) {
        this.completeAty=completeAty;
        this.resultBean = resultBean;
    }

    @Override
    public int getItemCount() {
        return resultBean.size();
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
        final PerFectEntity.ResultBean.FieldsBean fieldsBean = resultBean.get(i);
        int itemViewType = viewHolder.getItemViewType();
         switch (itemViewType){
             case 0://文本
                 TypeZero zero= (TypeZero) viewHolder;
                 double tp_type = fieldsBean.getTp_type();
                 int type = Double.valueOf(tp_type).intValue();
                 zero.name.setText(fieldsBean.getTp_name());
                 if(fieldsBean.getTp_must()==1){
                     zero.xing.setVisibility(View.VISIBLE);
                 }
                 if(!TextUtils.isEmpty(fieldsBean.getTp_string())){
                     zero.value.setText(fieldsBean.getTp_string());
                     hashMap.put(fieldsBean.getTp_key(),fieldsBean.getTp_string());
                 }else {
                     if(type!=9) {
                         zero.value.setHint(fieldsBean.getHolder_string());
                     }else if(fieldsBean.getTp_value()!=null&&!TextUtils.isEmpty(fieldsBean.getTp_value().getProvince())){
                         zero.value.setText("   "+fieldsBean.getTp_value().getProvince()+" "+fieldsBean.getTp_value().getArea()+" "+fieldsBean.getTp_value().getCity());
                     }else {
                         zero.value.setHint("   "+fieldsBean.getHolder_string());
                     }
                 }
                 if(type==9){
                     PerFectEntity.ResultBean.FieldsBean.TpvalueBean tp_value = fieldsBean.getTp_value();
                     if(fieldsBean.getTp_area()==0){
                         hashMap.put(fieldsBean.getTp_key(),tp_value.getProvince()+"@"+tp_value.getCity());
                     }else if(fieldsBean.getTp_area()==1){
                         hashMap.put(fieldsBean.getTp_key(),tp_value.getProvince()+"@"+tp_value.getCity()+"@"+tp_value.getArea());
                     }
                 }
                 typezero(zero,fieldsBean);
                 break;
             case 1://多行文本
                 TypeOne one = (TypeOne) viewHolder;
                 one.name.setText(fieldsBean.getTp_name());
                 if(fieldsBean.getTp_must()==1){
                     one.xing.setVisibility(View.VISIBLE);
                 }
                 if(!TextUtils.isEmpty(fieldsBean.getTp_string())){
                     hashMap.put(fieldsBean.getTp_key(),fieldsBean.getTp_string());
                     one.value.setText(fieldsBean.getTp_string());
                 }else {
                     one.value.setHint(fieldsBean.getHolder_string());
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
                             hashMap.put(fieldsBean.getTp_key(),s.toString());
                         }else {
                             hashMap.remove(fieldsBean.getTp_key());
                         }
                     }
                 });
                 break;
             case 3://复选框
                 TypeThree typeThree = (TypeThree) viewHolder;
                 typeThree.name.setText(fieldsBean.getTp_name());
                 if(fieldsBean.getTp_must()==1){
                     typeThree.xing.setVisibility(View.VISIBLE);
                 }
                 //todo 添加多选按钮
                 typeThree(typeThree,fieldsBean);
                 break;
             case 5://多图上传
                 TypeFive typeFive = (TypeFive) viewHolder;
                 typeFive.name.setText(fieldsBean.getTp_name());
                 if(fieldsBean.getTp_must()==1){
                     typeFive.xing.setVisibility(View.VISIBLE);
                 }
                 List<String> tp_array = fieldsBean.getTp_array();
                 for (int j = 0; j <tp_array.size() ; j++) {
                     LocalMedias localMedias = new LocalMedias();
                     localMedias.setUrl(tp_array.get(j));
                     data.add(localMedias);
                 }
                 PictureSelectorUtils pictureSelectorUtils = new PictureSelectorUtils(completeAty, Double.valueOf(fieldsBean.getTp_max()).intValue(), PictureConfig.CHOOSE_REQUEST);
                 gridImageAdapter = new GridImageAdapter(completeAty, pictureSelectorUtils.onAddPicClickListener);
                 typeFive.value.setAdapter(gridImageAdapter);
                 FullyGridLayoutManager manager = new FullyGridLayoutManager(typeFive.value.getContext(),
                         4, GridLayoutManager.VERTICAL, false);
                 typeFive.value.setLayoutManager(manager);
                 gridImageAdapter.setList(data);
                 ImageUrl=gridImageAdapter.httpImg();
                 double tp_max = fieldsBean.getTp_max();
                 gridImageAdapter.setSelectMax(Double.valueOf(tp_max).intValue());
                 gridImageAdapter.setKey(fieldsBean.getTp_key());
                 gridImageAdapter.notifyDataSetChanged();
                 break;
             case 8://时间  日期 范围
                 TypeEight typeEight= (TypeEight) viewHolder;
                 typeEight.name.setText(fieldsBean.getTp_name());
                 if(fieldsBean.getTp_must()==1){
                     typeEight.xing.setVisibility(View.VISIBLE);
                 }
                 if(fieldsBean.getTp_array()!=null&&fieldsBean.getTp_array().size()!=0&&!TextUtils.isEmpty(fieldsBean.getTp_array().get(0))){
                     String start = fieldsBean.getTp_array().get(0);
                     String end = fieldsBean.getTp_array().get(1);
                     typeEight.valueone.setText(start);
                     typeEight.valuetwo.setText(end);
                 hashMap.put(fieldsBean.getTp_key(),start+"@"+end);
                 }else {
                     typeEight.valueone.setText("开始日期");
                     typeEight.valuetwo.setText("结束日期");
                 }
                 typeEight(typeEight,fieldsBean);
                 break;
             case 10://确认文本
                 TypeTen typeTen= (TypeTen) viewHolder;
                 typeTen.name.setText(fieldsBean.getTp_name());
                 typeTen.name2.setText(fieldsBean.getTp_name2());
                 if(fieldsBean.getTp_must()==1){
                     typeTen.xing.setVisibility(View.VISIBLE);
                 }
                 if(fieldsBean.getTp_array()!=null&&fieldsBean.getTp_array().size()!=0&&!TextUtils.isEmpty(fieldsBean.getTp_array().get(0))){
                 typeTen.valueone.setText(fieldsBean.getTp_array().get(0));
                 typeTen.valuetwo.setText(fieldsBean.getTp_array().get(1));
                 hashMap.put(fieldsBean.getTp_key(),fieldsBean.getTp_array().get(0)+"@"+fieldsBean.getTp_array().get(1));
                 }else {
                     typeTen.valueone.setInputType(InputType.TYPE_CLASS_TEXT);
                     typeTen.valuetwo.setInputType(InputType.TYPE_CLASS_TEXT);
                     typeTen.valueone.setHint(fieldsBean.getHolder_array().get(0));
                     typeTen.valuetwo.setHint(fieldsBean.getHolder_array().get(1));
                 }
                 typeTen(typeTen,fieldsBean);
                 break;
             case 14:
               TypeFourteen typeFourteen= (TypeFourteen) viewHolder;
               typeFourteen.name.setText(fieldsBean.getTp_name());
               if(fieldsBean.getTp_must()==1){
                   typeFourteen.xing.setVisibility(View.VISIBLE);
               }
               typeFourteen(typeFourteen,fieldsBean);
                 break;
         }
    }

    //确认文本
    private void typeTen(TypeTen typeTen, final PerFectEntity.ResultBean.FieldsBean fieldsBean) {
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
                        processingdata(hash,fieldsBean.getTp_key());
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
                       processingdata(hash,fieldsBean.getTp_key());
                   }
               }
            }
        });
    }

    //时间范围选择
    private void typeEight(final TypeEight typeEight, final PerFectEntity.ResultBean.FieldsBean fieldsBean) {
        final double tp_type = fieldsBean.getTp_type();
        int i = Double.valueOf(tp_type).intValue();
        switch (i){
            case 8://****年到***年
                final HashMap<String, String> data = new HashMap<>();
                typeEight.rlselectone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(typeEight.valueone);
                        initTimePicker1(typeEight.valueone,"START",data,0,fieldsBean.getTp_key(),100);
                    }
                });
                typeEight.rlselecttwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(typeEight.valueone);
                        initTimePicker1(typeEight.valuetwo,"END",data,0,fieldsBean.getTp_key(),100);
                    }
                });

                break;
            case 12://12:00到11:00
                final HashMap<String, String> sb = new HashMap<>();
                typeEight.rlselectone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time(1,"START",typeEight.valueone,sb,fieldsBean.getTp_key(),100);
                    }
                });
                typeEight.rlselecttwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time(1,"END",typeEight.valuetwo,sb,fieldsBean.getTp_key(),100);

                    }
                });
                break;
        }
    }

    //单选框
    private void typeFourteen(final TypeFourteen typeFourteen, final PerFectEntity.ResultBean.FieldsBean fieldsBean) {
        List<String> tp_text = fieldsBean.getTp_text();
        String tp_string = fieldsBean.getTp_string();
        if(!TextUtils.isEmpty(tp_string)){
            hashMap.put(fieldsBean.getTp_key(),tp_string);
        }
        final ArrayList<MessageEntity> list = new ArrayList<>();
        for (int i = 0; i <tp_text.size() ; i++) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setAge(tp_text.get(i));
        messageEntity.setSelect(0);
         if(fieldsBean.getTp_string()!=null&&!TextUtils.isEmpty(fieldsBean.getTp_string())){
             if(tp_text.get(i).equals(fieldsBean.getTp_string())) {
                 messageEntity.setSelect(1);
             }
         }
         list.add(messageEntity);
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(completeAty,
                3, GridLayoutManager.VERTICAL, false);
        typeFourteen.value.setLayoutManager(manager);
        AgeAdapter ageAdapter = new AgeAdapter(list);
        typeFourteen.value.setAdapter(ageAdapter);
        ageAdapter.setOnitemClick(new AgeAdapter.OnitemClick() {
            @Override
            public void OnitemClick(int position) {
                MessageEntity messageEntity = list.get(position);
                hashMap.put(fieldsBean.getTp_key(),messageEntity.getAge());
            }
        });


    }

    //复选框
    private void typeThree(TypeThree typeThree, final PerFectEntity.ResultBean.FieldsBean fieldsBean) {
        List<String> tp_text = fieldsBean.getTp_text();
        List<String> tp_array = fieldsBean.getTp_array();
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
        hashMap.put(fieldsBean.getTp_key(),sb.toString());
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
        FullyGridLayoutManager manager = new FullyGridLayoutManager(completeAty,
                3, GridLayoutManager.VERTICAL, false);
        typeThree.value.setLayoutManager(manager);
        final HabitAdapter habitAdapter = new HabitAdapter(habitlist);
        typeThree.value.setAdapter(habitAdapter);
        habitAdapter.setOnitemClick(new HabitAdapter.OnitemClick() {
            @Override
            public void OnitemClick(int position, boolean ischeck) {
                String data = habitAdapter.getData();
                hashMap.put(fieldsBean.getTp_key(),data);

            }
        });
    }

    //类型为普通文本
    MaterialDialog show;
    private void typezero(final TypeZero zero, final PerFectEntity.ResultBean.FieldsBean fieldsBean) {
        double tp_type = fieldsBean.getTp_type();
        int i = Double.valueOf(tp_type).intValue();
        switch (i){
            case 0:
                zero.value.setHint(fieldsBean.getHolder_string());
                zero.value.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                           hashMap.put(fieldsBean.getTp_key(),s.toString());
                    }
                });
                break;
            case 2://单点  不可编辑
                if(!TextUtils.isEmpty(fieldsBean.getTp_string())){
                zero.value.setText(fieldsBean.getTp_string());
                }else {
                    zero.value.setText("请输入您的" + fieldsBean.getTp_name());
                }
                zero.value.setClickable(true);
                zero.value.setFocusable(false);
                final List<String> holder_array = fieldsBean.getTp_text();
                final EducationAdapter educationAdapter = new EducationAdapter(holder_array);
                zero.value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show = new MaterialDialog.Builder(completeAty)
                                .adapter(educationAdapter, new LinearLayoutManager(completeAty))
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
                        hashMap.put(fieldsBean.getTp_key(),s);
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
                         hashMap.put(fieldsBean.getTp_key(),s.toString());
                     }else {
                         hashMap.remove(fieldsBean.getTp_key());
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
                        initTimePicker1(zero.value,fieldsBean.getTp_key(),hashMap,0,fieldsBean.getTp_key(),99);
                    }
                });
                break;
            case 9:
                double tp_area = fieldsBean.getTp_area();
                final int type = Double.valueOf(tp_area).intValue();//区分二级  三级地址
                zero.value.setClickable(true);
                zero.value.setFocusable(false);
                zero.value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeyboardUtils.hideKeyboard(zero.value);
                        if(type==0){//二级地址
                            selectAddress(fieldsBean.getTp_key(),zero.value);
                        }else if(type==1){//三级地址
                            popPickerView(fieldsBean.getTp_key(),zero.value);
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
                        time(1,fieldsBean.getTp_key(),zero.value,hashMap,fieldsBean.getTp_key(),99);
                    }
                });
                break;
            case 13://提示文本
                zero.name.setText(fieldsBean.getTp_name());
                zero.value.setVisibility(View.GONE);
                zero.value.setClickable(false);
                zero.value.setFocusable(false);
                break;
        }
    }

    //添加本地选取的图片
    public void addpicture(List<LocalMedias> media){
        KLog.a("图片--->>>"+media.size());
        if(gridImageAdapter!=null){
            data.addAll(media);
            gridImageAdapter.notifyDataSetChanged();
        }
    }


    //todo  获取XXXX年XX月XX日时间选择器
    private void initTimePicker1(final TextView view, final String sb , final HashMap<String,String> hash, final int type, final String key, final int scope) {//选择出生年月日
        TimeSelectUtils timeSelectUtils = new TimeSelectUtils();
        timeSelectUtils.SelectYMDTime(completeAty, new OnTimeSelectListener() {
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
        timeSelectUtils.SelectHMTime(completeAty, new OnTimeSelectListener() {
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

    //TODO 打开三级地址
    public void popPickerView(final String key, final TextView view) {
        final AddressSelectUtils addressSelectUtils = new AddressSelectUtils();
        addressSelectUtils.thirdAddress(completeAty, new OnOptionsSelectListener() {
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
        addressSelectUtils.secondAddress(completeAty, new OnOptionsSelectListener() {
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


    //查看必填项是否有没填写的
    public boolean mustInport(){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <resultBean.size() ; i++) {
            PerFectEntity.ResultBean.FieldsBean fieldsBean = resultBean.get(i);
            if(fieldsBean.getTp_must()==1&&fieldsBean.getTp_type()!=5){
                list.add(fieldsBean.getTp_key());
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
    public int getItemViewType(int position) {
        double tp_type = resultBean.get(position).getTp_type();
        int type = Double.valueOf(tp_type).intValue();
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
