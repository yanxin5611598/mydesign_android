package com.yx.aircheck.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yx.aircheck.LoginActivity;
import com.yx.aircheck.MainUI;
import com.yx.aircheck.PointActivity;
import com.yx.aircheck.R;
import com.yx.aircheck.UserActivity;
import com.yx.aircheck.VIPActivity;
import com.yx.aircheck.base.BaseFragment;

import org.w3c.dom.Text;


public class LeftMenuFragment extends BaseFragment implements View.OnClickListener,SlidingMenu.OnOpenedListener{


    /**
     * 初始化侧滑边框中的每个控件*/
    private TextView tv_username;
    private ImageView iv_gender_boy;
    private ImageView iv_gender_gril;
    private FrameLayout f_user;
    private FrameLayout f_vip;
    private FrameLayout f_point;
    private FrameLayout f_help;
    private SharedPreferences left_main_shared;
    private String username;
    private String point;
    private String TAG = "leftmenufragment";
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_left, null);
        left_main_shared = mActivity.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        tv_username = view.findViewById(R.id.tv_mine_account_name);
        iv_gender_boy = view.findViewById(R.id.left_gender_image_boy);
        iv_gender_gril = view.findViewById(R.id.left_gender_image_gril);
        f_user = view.findViewById(R.id.f_user);//用户
        f_vip = view.findViewById(R.id.f_vip);//用户
        f_point = view.findViewById(R.id.f_point);//用户
        f_help = view.findViewById(R.id.f_help);//用户
        f_user.setOnClickListener(this);
        f_vip.setOnClickListener(this);
        f_point.setOnClickListener(this);
        f_help.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        username = left_main_shared.getString("username","");
        point = left_main_shared.getString("rewardPoint","");
        String gender = left_main_shared.getString("gender","");
        if(gender == "男" || gender.equals("男")){
            //是VIP
            iv_gender_boy.setVisibility(View.VISIBLE);
        }else{
            //不是VIP
            iv_gender_gril.setVisibility(View.VISIBLE);
        }
        tv_username.setText(username);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.f_user:
                Toast.makeText(mActivity, "点击了用户中心", Toast.LENGTH_LONG).show();
                Intent user_intent = new Intent();
                user_intent.setClass(mActivity, UserActivity.class);
                startActivity(user_intent);
                break;
            case R.id.f_vip:
                Toast.makeText(mActivity, "点击了VIP中心", Toast.LENGTH_LONG).show();
                Intent vip_intent = new Intent();
                vip_intent.setClass(mActivity, VIPActivity.class);
                startActivity(vip_intent);
                break;
            case R.id.f_point:
                Toast.makeText(mActivity,"点击了积分中心", Toast.LENGTH_LONG).show();
                Intent point_intent = new Intent();
                point_intent.setClass(mActivity, PointActivity.class);
                startActivity(point_intent);
                break;
            case R.id.f_help:
                Toast.makeText(mActivity, "点击了帮助中心", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onOpened() {
        Log.d(TAG, "onOpened: ***************************");
        Toast.makeText(getActivity(), "点击了侧滑边框", Toast.LENGTH_SHORT).show();
    }
}
