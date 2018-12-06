package com.yx.aircheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 用户发布的数据信息详情页面*/
public class DeviceDetailActivity extends Activity{
    private TextView tv_username;
    private TextView tv_deviceId;
    private TextView tv_deviceState;
    private TextView tv_placefrom;
    private TextView tv_timefrom;
    private TextView tv_placecurrent;
    private TextView tv_placelast;
    private TextView mTvTitle;
    private TextView tv_timelast;
    private ImageButton btn_back;
    private TableRow tb_placecurrent;
    private TableRow tb_timecurrent_last;
    private TableRow tb_placecurrent_last;
    private View placecurrent_view;
    private View placecurrent_last_view;
    private View timecurrent_last_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);
        initView();
        initData();
    }
    private void initView() {
        mTvTitle = (TextView)findViewById(R.id.tv_title_bar_title);
        btn_back = (ImageButton)findViewById(R.id.ib_title_bar_back);
        tv_username = (TextView)findViewById(R.id.id_device_detail_username_value);
        tv_deviceId = (TextView)findViewById(R.id.id_device_detail_id_value);
        tv_deviceState = (TextView)findViewById(R.id.id_device_detail_state_value);
        tv_placefrom = (TextView)findViewById(R.id.id_device_detail_placefrom_value);
        tv_timefrom = (TextView)findViewById(R.id.id_device_detail_timefrom_value);
        tv_placecurrent = (TextView)findViewById(R.id.id_device_detail_placecurrent_value);
        tv_placelast = (TextView)findViewById(R.id.id_device_detail_placecurrent_last_value);
        tv_timelast = (TextView)findViewById(R.id.id_device_detail_timecurrent_last_value);
        tb_placecurrent = (TableRow)findViewById(R.id.tb_placecurrent);
        tb_timecurrent_last = (TableRow)findViewById(R.id.tb_timecurrent_last);
        tb_placecurrent_last = (TableRow)findViewById(R.id.tb_placecurrent_last);
        placecurrent_view = (View)findViewById(R.id.placecurrent_view);
        placecurrent_last_view = (View)findViewById(R.id.placecurrent_last_view);
        timecurrent_last_view = (View)findViewById(R.id.id_timecurrent_last_view);
    }
    public void initData(){
        mTvTitle.setText(R.string.view_detail_info);
        Intent intent = getIntent();
        tv_username.setText(intent.getStringExtra("username"));
        tv_deviceId.setText(intent.getStringExtra("deviceId"));
        tv_deviceState.setText(intent.getStringExtra("state"));
        tv_placefrom.setText(intent.getStringExtra("placefrom"));
        tv_timefrom.setText(intent.getStringExtra("timefrom"));
        if(intent.getStringExtra("state").equals("1")){
            //使用中
            tb_placecurrent.setVisibility(View.VISIBLE);
            placecurrent_view.setVisibility(View.VISIBLE);
            tv_placecurrent.setText(intent.getStringExtra("placecurrent"));
        }else{
            //未使用
            tb_placecurrent_last.setVisibility(View.VISIBLE);
            tb_timecurrent_last.setVisibility(View.VISIBLE);
            placecurrent_last_view.setVisibility(View.VISIBLE);
            timecurrent_last_view.setVisibility(View.VISIBLE);
            tv_placelast.setText(intent.getStringExtra("placecurrent"));
            tv_timelast.setText(intent.getStringExtra("timecurrent"));
        }
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
