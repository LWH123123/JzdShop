package com.jzd.jzshop.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzd.jzshop.R;


/**
 * @author LWH
 * @description:自定义组合输入控件 * @date :2020/1/2 17:08
 */
public class MessageImportView extends LinearLayout {

    private String namespace = "http://schemas.android.com/apk/res/com.example.MessageImportView";

    private TextView name;
    private TextView xing;
    private EditText edimport;


    //显示的内容
    private String titlename;
    private String hintname;
    private int xingvisible;
    private int titleheight;
    private int inputtype;
    private final String text;

    public MessageImportView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {//解决可视化编辑器无法识别自定义控件的问题
            // 在构造函数中将Xml中定义的布局解析出来。
            LayoutInflater.from(context).inflate(R.layout.include_messageinfo, this, true);
            name = findViewById(R.id.name);
            xing = findViewById(R.id.xing);
            edimport = findViewById(R.id.ed_import);
        }


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageImportView);
        titlename = typedArray.getString(R.styleable.MessageImportView_titletext);
        titleheight = typedArray.getIndex(R.styleable.MessageImportView_titleheright);
        xingvisible = typedArray.getInt(R.styleable.MessageImportView_xingvisible, VISIBLE);
        inputtype = typedArray.getInt(R.styleable.MessageImportView_importtype, InputType.TYPE_CLASS_TEXT);
        hintname = typedArray.getString(R.styleable.MessageImportView_hinttext);
        text = typedArray.getString(R.styleable.MessageImportView_text);


        if (!TextUtils.isEmpty(titlename)) {
            name.setText(titlename);
        }
        /*VISIBLE:0  意思是可见的
        INVISIBILITY:4 意思是不可见的，但还占着原来的空间
        GONE:8  意思是不可见的，不占用原来的布局空间*/
        if (inputtype == InputType.TYPE_CLASS_TEXT) {
            edimport.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (inputtype == InputType.TYPE_CLASS_PHONE) {
            edimport.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (inputtype == InputType.TYPE_CLASS_NUMBER) {
            edimport.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputtype == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            edimport.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        if (xingvisible == VISIBLE) {
            xing.setVisibility(VISIBLE);
        } else if (xingvisible == INVISIBLE) {
            xing.setVisibility(INVISIBLE);
        } else {
            xing.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(titlename)) {
            name.setText(titlename);
        }
        if (!TextUtils.isEmpty(hintname)) {
            edimport.setHint(hintname);
        }
        if (!TextUtils.isEmpty(text)) {
            edimport.setText(text);
        }


        typedArray.recycle();
    }

    public void setName(String name) {
        if (!TextUtils.isEmpty(name)) {
            this.name.setText(name);
        }
    }

    public String getMessage() {
        if (edimport != null) {
            String s = edimport.getText().toString();
            if (!TextUtils.isEmpty(s)) {
                return s;
            }
        }
        return "";
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.edimport.setText(text);
        }
    }


}
