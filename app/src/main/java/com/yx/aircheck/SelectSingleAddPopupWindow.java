package com.yx.aircheck;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class SelectSingleAddPopupWindow extends PopupWindow {


    private View mainView;
    private LinearLayout layout_choh,layout_co2,layout_pm25,layout_pm10;
    public SelectSingleAddPopupWindow(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){

        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.view_popwindow_single, null);
        layout_choh = ((LinearLayout)mainView.findViewById(R.id.view_param_choh));
        layout_co2 = ((LinearLayout)mainView.findViewById(R.id.view_param_co2));
        layout_pm25 = ((LinearLayout)mainView.findViewById(R.id.view_param_pm25));
        layout_pm10 = ((LinearLayout)mainView.findViewById(R.id.view_param_pm10));
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layout_choh.setOnClickListener(paramOnClickListener);
            layout_co2.setOnClickListener(paramOnClickListener);
            layout_pm25.setOnClickListener(paramOnClickListener);
            layout_pm10.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}