package com.yx.aircheck.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.yx.aircheck.CityPickerActivity;
import com.yx.aircheck.MainUI;
import com.yx.aircheck.R;
import com.yx.aircheck.SelectAddPopupWindow;

public abstract class TabBasePager extends Activity implements View.OnClickListener{
    View rootView;
    public Activity mActivity;
    protected MainUI mainUI;
    public TabBasePager(Context context) {
        mainUI = (MainUI)context;
        mActivity = mainUI;
        rootView = initView();
    }

    public abstract View initView();

    @Override
    public void onClick(View view) {
    }

    public void initData() {

    }
    public void loadData(){

    }
    public void onActivityResult(){

    }
    public View getRootView() {
        return rootView;
    }
    /**
     * 更新UI
     * 根据检测的结果参数更新checkpager*/
    public void update(String tem,String hum,String choh,String pm25,String pm10,String range){

    }
    public void openDialog(){}
    /**
     * 更新UI
     * 根据选择的城市信息更新viewPager*/
    public void updateView(String data){}
    /**
     * 显示CheckPrePage的UI内容*/
    public void displayCheckPrePage(){}
    /**
     * 更新UI
     * 根据选择的城市信息更新viewPager
     * update(liveWeather.getCity(),
     *        liveWeather.getReportTime(),
     *        liveWeather.getWeather(),
     *        liveWeather.getWindDirection(),
     *        liveWeather.getWindPower(),
     *        liveWeather.getHumidity(),
     *        liveWeather.getTemperature());
     */
    public void updateWeather(String city,String reportTime,String weather,String windDirection,String windPower,String hum,String tem){}
<<<<<<< HEAD
=======

    public void displayChart(){}
>>>>>>> add dynamic chart
}
