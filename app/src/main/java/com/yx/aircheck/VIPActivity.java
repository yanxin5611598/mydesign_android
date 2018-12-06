package com.yx.aircheck;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class VIPActivity extends Activity implements View.OnClickListener {
    private TextView tv_point_value;
    private ImageButton point_return;
    private String TAG = "VIPActivity";
    private String username;
    private TextView mTvTitle;
    private TextView tv_username;
    private TextView tv_vip_info;
    private ImageView iv_vip_yes;
    private ImageView iv_vip_no;
    private Button btn_openvip;
    private String isVIP;
    private SharedPreferences left_vip_sharedPreferences;
    //发起支付所需参数
    String userid = "trpay@52yszd.com";//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
    String outtradeno = UUID.randomUUID() + "";//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
    String tradename = "openvip";//商品名称
    String backparams = "name=2&age=22";//商户系统回调参数
    String notifyurl = "http://101.200.13.92/notify/alipayTestNotify";//商户系统回调地址
    Long amount_vip = Long.parseLong("10");
    String amount_string;
    private static final int MSG_SERVER_SUBMIT_VIP_ORDER_RESULT = 1;
<<<<<<< HEAD
    private String requestToServerAboutVIPOrder_url = "http://1856o325q1.iok.la:13523/mydesign/userorder/generateVIPOrder";
=======
    private String requestToServerAboutVIPOrder_url = "http://1856o325q1.iok.la:37325/mydesign/userorder/generateVIPOrder";
>>>>>>> add dynamic chart
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_vip);
        initView();
        /**

         * 初始化PaySdk(context请传入当前Activity对象(如：MainActivity.this))

         * 第一个参数:是您在trPay后面应用的appkey（需要先提交应用资料(若应用未上线，需上传测试APK文件)，审核通过后appkey生效）
         * 第二个参数:是您的渠道，一般是baidu,360,xiaomi等

         */

        TrPay.getInstance(VIPActivity.this).initPaySdk("be6c44e655104d3d90e0d42432eb3c4d","baidu");
    }

    private void initView() {
        mTvTitle = (TextView)findViewById(R.id.tv_title_bar_title);
        tv_username = (TextView)findViewById(R.id.tv_left_activity_vip_username);
        tv_vip_info = (TextView)findViewById(R.id.tv_left_vip_info);
        iv_vip_yes = (ImageView)findViewById(R.id.left_vip_vip_image_yes);
        iv_vip_no = (ImageView)findViewById(R.id.left_vip_vip_image_no);
        btn_openvip = (Button)findViewById(R.id.id_vip_btn_chongzhi);
        left_vip_sharedPreferences = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        username = getUserName();
        isVIP = checkIsVIP();
        mTvTitle.setText(R.string.vip);
        tv_username.setText(username);
        if(isVIP == "1" || isVIP.equals("1")){
            //显示VIP图标
            iv_vip_yes.setVisibility(View.VISIBLE);
            //显示当前已开通的字样
            tv_vip_info.setText(R.string.vip_info_yes);
            //禁用立即开通按钮
            btn_openvip.setBackgroundColor(Color.parseColor("#bfbfbf"));
            btn_openvip.setClickable(false);
            btn_openvip.setEnabled(false);
        }
        else{
            //显示非VIP图标
            iv_vip_no.setVisibility(View.VISIBLE);
            //显示当前未开通的字样
            tv_vip_info.setText(R.string.vip_info_no);
        }
        //返回按钮的设置
        point_return = (ImageButton)findViewById(R.id.ib_title_bar_back);
        point_return.setVisibility(View.VISIBLE);
        point_return.setOnClickListener(this);
        //绑定点击事件
        btn_openvip.setOnClickListener(this);
    }
    public String getUserName()
    {
        return left_vip_sharedPreferences.getString("username","");
    }
    public String checkIsVIP(){
        return left_vip_sharedPreferences.getString("isVIP","");
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.id_vip_btn_chongzhi:
<<<<<<< HEAD
                /**
                 * 发起支付宝支付调用
                 */
                TrPay.getInstance(this).callAlipay(tradename, outtradeno, amount_vip, backparams, notifyurl, userid, new PayResultListener() {
                    /**
                     * 支付完成回调
                     * @param context      上下文
                     * @param outtradeno   商户系统订单号
                     * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
                     * @param resultString 支付结果
                     * @param payType      支付类型（1：支付宝 2：微信）
                     * @param amount       支付金额
                     * @param tradename    商品名称
                     */
                    @Override
                    public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                        if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                            TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                            Toast.makeText(VIPActivity.this, resultString, Toast.LENGTH_LONG).show();
                            //支付成功逻辑处理
                            //TODO将订单信息发送给服务器，并请求服务器将积分数增加对应个数,个数为10
                            amount_string = String.valueOf(amount_vip);
                            Log.d(TAG, "onPayFinish: *******************************amount"+amount_string+"********************"+amount_vip+"****************"+amount);
                            requestToServerAboutVIPOrder();
                        } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                            Toast.makeText(VIPActivity.this, resultString, Toast.LENGTH_LONG).show();
                            //支付失败逻辑处理
                        }
                    }
                });
=======
                aliPayToGetVIP();
>>>>>>> add dynamic chart
                break;
            case R.id.ib_title_bar_back:
                finish();//让按钮拥有返回键的功能很简单，在点击事件加上finish();就OK了。
                //但是finish() 仅仅是把activity从当前的状态退出，但是资源并没有给清理。这里可以完成跳转的功能即可
            default:
                break;
        }
    }
    /**
<<<<<<< HEAD
=======
     * 支付宝充值*/
    public void aliPayToGetVIP(){
        /**
         * 发起支付宝支付调用
         */
        TrPay.getInstance(this).callAlipay(tradename, outtradeno, amount_vip, backparams, notifyurl, userid, new PayResultListener() {
            /**
             * 支付完成回调
             * @param context      上下文
             * @param outtradeno   商户系统订单号
             * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
             * @param resultString 支付结果
             * @param payType      支付类型（1：支付宝 2：微信）
             * @param amount       支付金额
             * @param tradename    商品名称
             */
            @Override
            public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                    TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                    Toast.makeText(VIPActivity.this, resultString, Toast.LENGTH_LONG).show();
                    //支付成功逻辑处理
                    //TODO将订单信息发送给服务器，并请求服务器将积分数增加对应个数,个数为10
                    amount_string = String.valueOf(amount_vip);
                    Log.d(TAG, "onPayFinish: *******************************amount"+amount_string+"********************"+amount_vip+"****************"+amount);
                    requestToServerAboutVIPOrder();
                } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                    Toast.makeText(VIPActivity.this, resultString, Toast.LENGTH_LONG).show();
                    //支付失败逻辑处理
                }
            }
        });
    }
    /**
>>>>>>> add dynamic chart
    * 发送请求给服务器处理订单相关的逻辑*/
    public void requestToServerAboutVIPOrder(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "requestToServerAboutPointOrder: ***********come in");
                //创建OkHttpClient对象
                try{
                    //设置带超时时间设置的client
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(40,TimeUnit.SECONDS)
                            .build();
                    //请求体
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",username)
                            .add("outtradeno",outtradeno)//增加商品订单号
                            .add("tradename",tradename)//增加交易的名称
                            .add("amount",amount_string)//增加交易的金额
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServerAboutVIPOrder_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "requestToServerAboutPointOrder: ******************************"+responseData);
                    JSONObject result_json = new JSONObject(responseData);
                    sendMessage(MSG_SERVER_SUBMIT_VIP_ORDER_RESULT,result_json);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 发送消息*/
    public void sendMessage(int what,Object obj){
        Message message = Message.obtain();
        message.what = what;
        message.obj = obj;
        pHandler.sendMessage(message);
    }
    /***
     * 创建更新UI的Handler*/
    public Handler pHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case MSG_SERVER_SUBMIT_VIP_ORDER_RESULT:
                    JSONObject json = (JSONObject) msg.obj;
                    handleSubmitResult(json);  //获取服务器的返回结果
                    break;
            }
        }
    };
    private void handleSubmitResult(JSONObject json){
        int resultCode = -1;
        //请求的数据正确
        try {
            resultCode = json.getInt("result_code");
        }catch(JSONException e) {
            e.printStackTrace();
        }
        switch (resultCode){
            case 0:
                //请求失败
                Toast.makeText(this, "请求参数错误！", Toast.LENGTH_LONG).show();
                break;
            case -1:
                //更新用户积分失败
                Toast.makeText(this, "更新用户状态失败！", Toast.LENGTH_LONG).show();
                break;
            case -2:
                //新增用户订单失败
                Toast.makeText(this, "新增VIP订单失败！", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this, "恭喜成为VIP！", Toast.LENGTH_LONG).show();
                //TODO 更新SharedPreferences中的VIP状态，并更新当前页面的VIP图标
                SharedPreferences.Editor editor = left_vip_sharedPreferences.edit();
                editor.putString("isVIP","1");//更新VIP状态为VIP
                editor.apply();
                //最后将标志VIP的图片进行替换，并将按钮禁用
                //显示VIP图标
                iv_vip_no.setVisibility(View.GONE);
                iv_vip_yes.setVisibility(View.VISIBLE);
                //显示当前已开通的字样
                tv_vip_info.setText(R.string.vip_info_yes);
                //禁用立即开通按钮
                btn_openvip.setBackgroundColor(Color.parseColor("#bfbfbf"));
                btn_openvip.setClickable(false);
                btn_openvip.setEnabled(false);
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
