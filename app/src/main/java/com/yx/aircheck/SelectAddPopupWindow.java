package com.yx.aircheck;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class SelectAddPopupWindow extends PopupWindow {

<<<<<<< HEAD

=======
>>>>>>> add dynamic chart
    private View mainView;
    private LinearLayout layout_timein,layout_timede,layout_namein,layout_namede,layout_airqde,layout_airqin,layout_params,layout_location;
    public SelectAddPopupWindow(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){

        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.view_popwindow, null);
        //按照时间降序布局
        layout_timede = ((LinearLayout)mainView.findViewById(R.id.view_time_de));
        //按照时间升序布局
        layout_timein = ((LinearLayout)mainView.findViewById(R.id.view_time_in));
        //按照名称降序布局
        layout_namede = ((LinearLayout)mainView.findViewById(R.id.view_name_de));
        //按照时间降序布局
        layout_namein = ((LinearLayout)mainView.findViewById(R.id.view_name_in));
        //按照时间降序布局
        layout_airqde = ((LinearLayout)mainView.findViewById(R.id.view_select_airq_de));
        layout_airqin = ((LinearLayout)mainView.findViewById(R.id.view_select_airq_in));
        layout_params = ((LinearLayout)mainView.findViewById(R.id.view_select_params));
        layout_location = (LinearLayout) mainView.findViewById(R.id.view_select_location);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layout_timede.setOnClickListener(paramOnClickListener);
            layout_timein.setOnClickListener(paramOnClickListener);
            layout_namede.setOnClickListener(paramOnClickListener);
            layout_namein.setOnClickListener(paramOnClickListener);
            layout_airqde.setOnClickListener(paramOnClickListener);
            layout_airqin.setOnClickListener(paramOnClickListener);
            layout_params.setOnClickListener(paramOnClickListener);
            layout_location.setOnClickListener(paramOnClickListener);
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