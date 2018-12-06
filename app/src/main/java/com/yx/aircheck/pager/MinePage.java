package com.yx.aircheck.pager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yx.aircheck.R;
import com.yx.aircheck.base.TabBasePager;

public class MinePage extends TabBasePager {

    View view;
    private TextView mTvTitle;
    private ImageView iv_vip_no;
    private ImageView iv_vip_yes;
    private Context context;
    private String TAG = "mine_page";
    private static final int REFRESH_UI = 1;
    private SharedPreferences mineSharedPreferences;
    //下拉刷新
    private SwipeRefreshLayout mSwipeLayout;
    //TextView
    private TextView tv_username;
    private TextView tv_point;
    public MinePage(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View initView() {

        view = View.inflate(mainUI, R.layout.pager_mine, null);
        mTvTitle = view.findViewById(R.id.tv_title_bar_title);
        iv_vip_no = view.findViewById(R.id.mine_image_no);
        iv_vip_yes = view.findViewById(R.id.mine_image_yes);
        tv_username = view.findViewById(R.id.tv_mine_account_name);
        tv_point = view.findViewById(R.id.mine_point);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_mine);
        //设置在listview上下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里可以做一下下拉刷新的操作
                //例如下面代码，在方法中发送一个handler模拟延时操作
                mHandler.sendEmptyMessageDelayed(REFRESH_UI, 1500);
            }
        });
        return view;

    }

    /*public void refresh(){
        mHandler.sendEmptyMessageDelayed(REFRESH_UI, 2000);
    }*/
    @Override
    public void initData() {
        mineSharedPreferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String username = mineSharedPreferences.getString("username","");
        String point = mineSharedPreferences.getString("rewardPoint","");
        String vip = mineSharedPreferences.getString("isVIP","");
        mTvTitle.setText(R.string.mine);
        tv_username.setText(username);
        tv_point.setText("积分:"+point);
        if(vip == "1" || vip.equals("1")){
            //是VIP
            iv_vip_yes.setVisibility(View.VISIBLE);
        }else{
            //不是VIP
            iv_vip_no.setVisibility(View.VISIBLE);
        }
    }
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case REFRESH_UI:
                    Log.d(TAG, "handleMessage: refreshing");
                    initData();
                    //为了保险起见可以先判断当前是否在刷新中（旋转的小圈圈在旋转）....
                    if(mSwipeLayout.isRefreshing()){
                        //关闭刷新动画
                        mSwipeLayout.setRefreshing(false);
                    }
                    Log.d(TAG, "handleMessage: refreshed");
                    break;
            }
        }
    };

}
