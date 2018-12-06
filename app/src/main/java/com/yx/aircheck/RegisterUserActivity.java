package com.yx.aircheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterUserActivity extends Activity implements OnClickListener {
	//请求服务器端的url
<<<<<<< HEAD
	public static final String requestToServer_register_url = "http://1856o325q1.iok.la:13523/mydesign/userlogin_register/register";
=======
	public static final String requestToServer_register_url = "http://1856o325q1.iok.la:37325/mydesign/userlogin_register/register";
>>>>>>> add dynamic chart
	public static final int MSG_CREATE_RESULT = 1;
	private EditText eUsername;
	private EditText ePwd1;
	private EditText ePwd2;
	private RadioGroup rGender;
	private EditText eAge;
	private EditText ePhone;
	private EditText eEmail;
    private TextView tv_login;
	private Button btnSubmit;
	private Button btnReset;

	ProgressDialog progress;
	private static final int MSG_REGISTER_RESULT = 0;
	/*
	*负责异步消息接收处理，并按计划发送和处理消息
	* Handler用于同一个进程的线程间通信
	*
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {   //匿名内部类
			switch(msg.what) {       //根据message中的what值得不同来分发处理
				case MSG_REGISTER_RESULT:
					//progress.dismiss();//取消关闭对话框
					//把服务器传来的相应的对象转化为JSONobject
					JSONObject json = (JSONObject) msg.obj;//msg.obj存放对象，msg.what只能存放数字
					try{
						Log.i("TAG", "handle接收处理" + ((JSONObject) msg.obj).getInt("result_code"));
					}catch (Exception e){
						e.printStackTrace();
					}
					handleCreateAccountResult(json);
					break;
			}
		}
	};

	private void handleCreateAccountResult(JSONObject json) {
		/*
		 *   result_code:
		 * 0  注册成功
		 * 1  用户名已存在
		 * 2 数据库操作异常
		 * */
		int result;
		try {
			result = json.getInt("result_code");
			Log.i("TAG", "接收处理" + result);
		} catch (JSONException e) {
			Toast.makeText(this, "没有获取到网络的响应！", Toast.LENGTH_LONG).show();
			e.printStackTrace();
			return;
		}

		if(result == 1) {
			Toast.makeText(this, "用户名已存在！", Toast.LENGTH_LONG).show();
			return;
		}

		if(result == 2) {
			Toast.makeText(this, "注册失败！服务端出现异常！", Toast.LENGTH_LONG).show();
			return;
		}

		if(result == 0) {
			Toast.makeText(this, "注册成功!", Toast.LENGTH_LONG).show();
			/*Intent intent = new Intent();
			intent.putExtra("username",eUsername.getText().toString());
			setResult(RESULT_OK,intent);
			finish();*/
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_user);

		initViews();
	}
	/*
	*界面控件初始化
	 */
	private void initViews() {
		eUsername = (EditText)findViewById(R.id.new_username);
		ePwd1 = (EditText)findViewById(R.id.new_password_1);
		rGender = (RadioGroup)findViewById(R.id.new_radio_group_gender);
		eAge = (EditText)findViewById(R.id.new_age);
		ePhone = (EditText)findViewById(R.id.new_phone);
		eEmail = (EditText)findViewById(R.id.new_email);
		btnSubmit = (Button)findViewById(R.id.new_btn_submit);
		btnReset = (Button)findViewById(R.id.new_btn_reset);
		tv_login = (TextView) findViewById(R.id.link_login);
		btnSubmit.setOnClickListener(this);
		btnReset.setOnClickListener(this);
        tv_login.setOnClickListener(this);
	}
	/*
    *鼠标点击事件（提交和重置）
     */
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.new_btn_submit:
				handleCreateAccount();
				break;
			case R.id.new_btn_reset:
				handleReset();
				break;
            case R.id.link_login:
                toLoginActivity();
		}

	}
    public void toLoginActivity(){
	    Intent intent = new Intent();
	    intent.setClass(RegisterUserActivity.this,LoginActivity.class);
//	    intent.putExtra("usernmae")
        startActivity(intent);
        finish();
    }
	private void handleCreateAccount() {
		boolean isUsernameValid = checkUsername();
		if(!isUsernameValid) {
			Toast.makeText(this, "用户名不正确，请重新输入", Toast.LENGTH_LONG).show();
			return;
		}

		int pwdResult = checkPassword();
	/*	if(pwdResult == 1) {
			Toast.makeText(this, "两次输入的密码不一致，请确认！", Toast.LENGTH_LONG).show();
			return;
		}*/
		if (pwdResult == 2) {
			Toast.makeText(this,"密码不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (pwdResult == 3) {
			Toast.makeText(this,"密码必须是大于6位的数字与字母的组合！", Toast.LENGTH_LONG).show();
			return;
		}

		int isAgeValid = checkAge();
		if(isAgeValid == -1) {
			Toast.makeText(this, "年龄不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if(isAgeValid == -2) {
			Toast.makeText(this, "年龄超出范围(1~100)！", Toast.LENGTH_LONG).show();
			return;
		}
		if(isAgeValid == -3) {
			Toast.makeText(this, "年龄格式输入错误，请不要输入字母、符号等其他字符串！", Toast.LENGTH_LONG).show();
			return;
		}

		boolean isEmailValid = isEmail();
		if(isEmailValid==false) {
			Toast.makeText(this, "请检查您的邮箱是否正确！", Toast.LENGTH_LONG).show();
			return;
		}
		boolean isPhoneValid = isCellphone();
		if(isPhoneValid==false) {
			Toast.makeText(this, "请检查您的手机号码是否正确！", Toast.LENGTH_LONG).show();
			return;
		}

		createAccount();
	}
	/*
	*提交注册信息
	* 主线程使用HttpClient发送请求、接收响应步骤：
	* 1.创建HttpClient对象
	* 2.创建POST请求方法，并指定请求URL
	* 3.发送请求参数，对于HTTPPOST，可以调用setEntity(HTTPEntity entity)方法来请求参数
	* 	UrlEncodedFormEntity(params, HTTP.UTF_8)这个类是用来把输入数据编码成合适的内容
	* 4.调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse
	* 5.调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
	 */
	private void createAccount() {
		progress = new ProgressDialog(this);
		progress.setCancelable(false);
		progress.setCanceledOnTouchOutside(false);
		//progress.show(this, null, "注册中...");
        Toast.makeText(getApplicationContext(),"regist", Toast.LENGTH_SHORT).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
			    try {
                    RadioButton selectedGender = (RadioButton) RegisterUserActivity.this.findViewById(rGender.getCheckedRadioButtonId());
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(40, TimeUnit.SECONDS)
                            .build();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", eUsername.getText().toString())
                            .add("password", ePwd1.getText().toString())
                            .add("gender", selectedGender.getText().toString())
                            .add("age", eAge.getText().toString())
                            .add("phone", ePhone.getText().toString())
                            .add("email", eEmail.getText().toString())
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServer_register_url)
                            .post(requestBody)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
					JSONObject json = new JSONObject(responseData);//转成json(这是个人信息部分)
					//发送handler显示到界面
					sendMessage(MSG_REGISTER_RESULT, json);
                }catch(Exception e){
			        e.printStackTrace();
                }
			}
		}).start();

	}

	private boolean checkUsername() {
		String username = eUsername.getText().toString();//取出view视图中“名字”输入的字符串
		if(TextUtils.isEmpty(username)) {
			return false;
		}
		return true;
	}

	private int checkPassword() {
		/*
		 * return value:
		 * 0 password valid
		 * 1 password not equal 2 inputs
		 * 2 password empty
		 * 3 password don't match
		 * */
		String pwd1 = ePwd1.getText().toString();
		if(TextUtils.isEmpty(pwd1)) {
			return 2;
		} else if(ispsd(pwd1)==false){
			return 3;
		}
		else {
			return 0;
		}
	}

	public static boolean ispsd(String psd) {
		Pattern p = Pattern
				.compile("^(?=.*?[0-9])[a-zA-Z0-9]{7,}$");
		Matcher m = p.matcher(psd);

		return m.matches();
	}

	private int checkAge() {
		/*
		 * return value
		 * 0 输入合法
		 * -1 输入为空
		 * -2输入为负数
		 * -3输入为非数值字符串或包括小数
		 * */
		int ageNum;
		String age = eAge.getText().toString();
		if(TextUtils.isEmpty(age)) {
			return -1;
		}
		try {
			ageNum = Integer.parseInt(age);//字符串转换为整数
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -3;
		}
		if(ageNum <= 0 || ageNum > 100) {
			return -2;
		}
		return 0;
	}
	/*
	*验证邮箱是否合法
	 */
	private boolean isEmail() {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		String strEmail = eEmail.getText().toString();
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		if (m.matches()) {
			return true;
		}else {
			return false;
		}
	}
	/*
	*验证号码是否合法
	 */
	private boolean isCellphone() {
		boolean isValid = false;
		String expression = "((^(13|15|18|17)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
				+ "(^0[3-9] {1}d{2}-?d{7,8}$)|"
				+ "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
				+ "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
		CharSequence inputStr = ePhone.getText().toString();
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/*
	重置
	 */
	private void handleReset() {
		eUsername.setText("");
		ePwd1.setText("");
		ePwd2.setText("");
		((RadioButton)(rGender.getChildAt(0))).setChecked(true);
		eAge.setText("");
		ePhone.setText("");
		eEmail.setText("");
	}
	/*
	*注册的信息都封装在一个message中
	 */
	private void sendMessage(int what, Object obj) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.obj = obj;
		mHandler.sendMessage(msg);  //接收处理handler，主线程创建的Handler handler
		Log.i("TAG", "接收"+msg.toString());
	}								//实例发送消息到主线程的消息队列里，消息队列从无到有
	//主线程堵塞被唤醒

	/*
   *返回上一Activity
    */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
	{
		// TODO Auto-generated method stub

		if(keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}
}
