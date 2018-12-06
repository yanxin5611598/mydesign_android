package com.yx.aircheck;

import android.app.Activity;
<<<<<<< HEAD
=======
import android.app.Dialog;
>>>>>>> add dynamic chart
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yx.aircheck.bean.UserInfo;
<<<<<<< HEAD
=======
import com.yx.aircheck.utils.LoadingDialogUtils;
>>>>>>> add dynamic chart

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText loginUsername;
	private EditText loginPassword;
	private Button loginButton;
	private TextView createTextView;

	private ProgressDialog loginProgress;

	public static final int MSG_LOGIN_RESULT = 0;

	private String TAG = "Login";

<<<<<<< HEAD
	public String requestToServer_login_url = "http://1856o325q1.iok.la:13523/mydesign/userlogin_register/login";


=======
	public String requestToServer_login_url = "http://1856o325q1.iok.la:37325/mydesign/userlogin_register/login";

	private Dialog registerLodingDialog;
>>>>>>> add dynamic chart
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_user);
		initViews();
		Connector.getDatabase();
	}
	/*
	 *初始化界面各组件
	 */
	private void initViews() {
		loginUsername = (EditText)findViewById(R.id.login_username);
		loginPassword = (EditText)findViewById(R.id.login_password);
		loginButton   = (Button)findViewById(R.id.login);
		createTextView  = (TextView) findViewById(R.id.login_register);
		loginButton.setOnClickListener(this);
		createTextView.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.login:
				final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this
						);
				progressDialog.setIndeterminate(true);
				progressDialog.setMessage("Authenticating...");
				progressDialog.show();
				handleLogin();
				progressDialog.dismiss();
				break;
			case R.id.login_register:
<<<<<<< HEAD
=======
				registerLodingDialog = LoadingDialogUtils.createLoadingDialog(LoginActivity.this,"注册中");
>>>>>>> add dynamic chart
				handleRegister();
				break;
			default:
				break;
		}
		finish();
	}

	private void sendMessage(int what, Object obj) {
		//安卓建议使用这种方式创建Message
		Message msg = Message.obtain();
		msg.what = what;
		msg.obj = obj;
		//子线程发送消息给主线程Handler进行处理
		mHandler.sendMessage(msg);
	}
	/*
	 *发送和处理信息（message）
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case MSG_LOGIN_RESULT:
					//loginProgress.dismiss();
					JSONObject json = (JSONObject) msg.obj;

					handleLoginResult(json);  //获取服务器的返回结果
					break;
			}
		};
	};

	/*
    *登录处理机制（可看为主函数）
     */
	private void handleLogin() {
		String username = loginUsername.getText().toString();
		String password = loginPassword.getText().toString();
		login(username, password);

	}
	/*
	*客户端登录
	* 功能：建立http连接进行登录验证
	 */
	private void login(final String username, final String password) {
		loginProgress = new ProgressDialog(this);
		//ProgressDialog用于一些比较耗时的操作====相当于一个ProgressBar===是一个对话框的等待样式
		loginProgress.setCancelable(false); //提示框弹出后点击屏幕或物理干会见，提示框不消失
		loginProgress.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
		//loginProgress.show(this, null, "登录中...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "start network!");
				//使用OkHttp
				try {
					OkHttpClient client = new OkHttpClient.Builder()
							.connectTimeout(10, TimeUnit.SECONDS)
							.readTimeout(40, TimeUnit.SECONDS)
							.build();
					RequestBody requestBody = new FormBody.Builder()
							.add("username", username)
							.add("password", password)
							.build();
					Request request = new Request.Builder()
							.url(requestToServer_login_url)
							.post(requestBody)
							.build();
					Response response = client.newCall(request).execute();
					String responseData = response.body().string();
					Log.d(TAG, "******************************" + responseData);
					JSONObject json = new JSONObject(responseData);//转成json(这是个人信息部分)
					//发送handler显示到界面
					sendMessage(MSG_LOGIN_RESULT, json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**跳转至注册界面*/
	private void handleRegister() {
		Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
<<<<<<< HEAD
=======
		LoadingDialogUtils.closeDialog(registerLodingDialog);
>>>>>>> add dynamic chart
		startActivity(intent);
		finish();
	}

	private void handleLoginResult(JSONObject json){
		/*
		 * login_result:
		 * -1：登陆失败，未知错误！
		 * 0: 登陆成功！
		 * 1：登陆失败，用户名或密码错误！
		 * 2：登陆失败，用户名不存在！
		 * */
		int resultCode = -1;
		try {
			resultCode = json.getInt("result_code");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		switch(resultCode) {
			case 0:
				onLoginSuccess(json);
				break;
			case 1:
				Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
				break;
			case 2:
				Toast.makeText(this, "用户名不存在！", Toast.LENGTH_LONG).show();
				break;
			case -1:
			default:
				Toast.makeText(this, "登陆失败！未知错误！", Toast.LENGTH_LONG).show();
				break;
		}
	}

	private void onLoginSuccess(JSONObject json) {
		//SharedPreferences键值对存储该用户的信息
		SharedPreferences mySharedPreferences = getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		try {
			editor.putString("username", json.getString("username"));
			editor.putString("gender", json.getString("gender"));
			editor.putInt("age", json.getInt("age"));
			editor.putString("phone", json.getString("phone"));
			editor.putString("email", json.getString("email"));
			editor.putString("isVIP", json.getString("isVIP"));
			editor.putString("rewardPoint", json.getString("rewardPoint"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
		editor.apply();
		Toast.makeText(this, "数据成功写入SharedPreferences！" , Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, MainUI.class);
		startActivity(intent);
		finish();
		return;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					//更新username的显示
					Log.d(TAG, "onActivityResult: ************************"+data.getStringExtra("username"));
					loginUsername.setText(data.getStringExtra("username"));
				}
				break;
			default:
				break;
		}
	}
}