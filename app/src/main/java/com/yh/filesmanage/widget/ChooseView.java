package com.yh.filesmanage.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yh.filesmanage.R;


/**
 * @创建者 ly
 * @创建时间 2020/6/10
 * @描述
 * @更新者 $
 * @更新时间 $
 * @更新描述
 */
public class ChooseView extends RelativeLayout {

    float textSize;
    int textColor;
    String textValue;
    TextView tvValue;

    public ChooseView(Context context) {
        super(context);
//        initView(context);
    }

    public ChooseView(Context context, AttributeSet attr) {
        super(context,attr);
        initView(context,attr);
    }

    private void initView(Context context,AttributeSet attr) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_choose, this, true);
        tvValue = view.findViewById(R.id.tv_choose_value);

        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.chooseView);
        if(typedArray != null) {
            textValue = typedArray.getString(R.styleable.chooseView_chooseValue);
            textColor = typedArray.getColor(R.styleable.chooseView_chooseColor, 0x000);
            textSize = typedArray.getDimension(R.styleable.chooseView_chooseSize, 16);
        }
        tvValue.setTextColor(textColor);
        tvValue.setTextSize(textSize);
        tvValue.setText(textValue);
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
        tvValue.setText(textValue);
    }
}
