package com.yx.aircheck;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserActivity extends Activity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageButton user_return;
    private ImageButton user_edit;
    private TextView tv_username;
    private TextView tv_gender;
    private TextView tv_age;
    private TextView tv_phone;
    private TextView tv_email;
    private TextView tv_point;
    private String TAG = "UserActivity";
    private SharedPreferences user_sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_user);
        user_sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        initView();
    }

    private void initView() {
        mTvTitle = (TextView)findViewById(R.id.tv_title_bar_title);
        mTvTitle.setText(R.string.user_);
        //返回按钮的设置
        user_return = (ImageButton)findViewById(R.id.ib_title_bar_back);
        user_return.setVisibility(View.VISIBLE);
        user_return.setOnClickListener(this);
        // 编辑按钮的设置
        user_edit = (ImageButton)findViewById(R.id.ib_title_bar_livable_menu);
        user_edit.setVisibility(View.VISIBLE);
        user_edit.setOnClickListener(this);
        //填充各个参数的值
        tv_username = (TextView)findViewById(R.id.id_user_username_value);
        tv_gender = (TextView)findViewById(R.id.id_user_gender_value);
        tv_age = (TextView)findViewById(R.id.id_user_age_value);
        tv_email = (TextView)findViewById(R.id.id_user_email_value);
        tv_phone = (TextView)findViewById(R.id.id_user_phone_value);
        tv_point = (TextView)findViewById(R.id.id_user_point_value);
        tv_username.setText(user_sharedPreferences.getString("username",""));
        tv_gender.setText(user_sharedPreferences.getString("gender",""));
        tv_age.setText(String.valueOf(user_sharedPreferences.getInt("age",0)));
        tv_phone.setText(user_sharedPreferences.getString("phone",""));
        tv_email.setText(user_sharedPreferences.getString("email",""));
        tv_point.setText(user_sharedPreferences.getString("rewardPoint",""));
        //绑定点击事件

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ib_title_bar_livable_menu:
                //TODO 编辑按钮的设置
                break;
            case R.id.ib_title_bar_back:
                finish();//直接返回
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
