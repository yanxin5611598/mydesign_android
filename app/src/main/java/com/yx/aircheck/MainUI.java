package com.yx.aircheck;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yx.aircheck.bean.CheckData;
import com.yx.aircheck.bean.LocateState;
import com.yx.aircheck.fragment.CityPickerFragment;
import com.yx.aircheck.fragment.LeftMenuFragment;
import com.yx.aircheck.fragment.MainContentFragment;
import com.yx.aircheck.utils.DBUtil;
import com.yx.aircheck.utils.DialogUtil;
import com.yx.aircheck.utils.LocationUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.tablemanager.Connector;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainUI extends SlidingFragmentActivity implements WeatherSearch.OnWeatherSearchListener {
    // 左侧菜单fragment的tag
    private final String LEFT_MENU_FRAGMENT_TAG = "left_menu";
    // 主界面fragment的tag
    private final String MAIN_CONTENT_FRAGMENT_TAG = "main_content";
    private SharedPreferences userinfoSharedPreferences;
<<<<<<< HEAD
    private SharedPreferences mySharedPreferences;
    private String gps_info = null;
    private String requestToServer_getSenorData_url = "ws://1856o325q1.iok.la:13523/mydesign/websocket";
=======
    private String gps_info = null;
    private String requestToServer_getSenorData_url = "ws://1856o325q1.iok.la:37325/mydesign/websocket";
>>>>>>> add dynamic chart
    private String username = "";
    private String isVIP = "";
    private String result = "";
    private SharedPreferences deviceSharedPreferences;
    private static final int MSG_SUBMIT_RESULT = 1;
    private static final int MSG_CITY_RESULT = 2;
    private static final int MSG_GETDATA_RESULT = 3;
    private String TAG = "Main_UI_Yanxin";
    private String cityString;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    LocationManager lm = null;
    private String scanResult = "";
    private ProgressDialog dialog = null;
    private String dialog_message = "请稍等！正在获取数据...";
    private GestureDetector mGestureDetector;// 手势
    private long exitTime = 0;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    //onkeydown_
    private static boolean isQuit = false;
//    MainContentFragment mainContentFragment;
    private Timer timer = new Timer();
    private String[] split;
    private static final int REQUEST_CODE_PICK_CITY = 233;
    private WebSocketClient mWebSocketClient;
    //初始化数据库连接
    private DBUtil dbUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ui_activity);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkIsLocation();
        initLocation();
        setBehindContentView(R.layout.left_menu); // 左侧菜单布局
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setBehindOffset((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, this.getResources().getDisplayMetrics())); // 侧边栏展开剩余空间大小
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        initFragment();
//        mainContentFragment = (MainContentFragment) getFragmentManager().findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
        Connector.getDatabase();
        SQLiteStudioService.instance().start(this);
        Log.d(TAG, "onCreate: city=" + cityString);
        /*WeatherSearchQuery weatherQuery = new WeatherSearchQuery(
                cityString,
                WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch weatherSearch = new WeatherSearch(this);
        weatherSearch.setOnWeatherSearchListener(MainUI.this);
        weatherSearch.setQuery(weatherQuery);
        weatherSearch.searchWeatherAsyn();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
        SQLiteStudioService.instance().stop();
    }

    /**
     * 初始化菜单和主界面Fragment
     */
    private void initFragment() {
        // 获取Fragment管理器对象
        FragmentManager fm = getFragmentManager();

        // 开启fragment事务
        FragmentTransaction ft = fm.beginTransaction();

        // 替换左侧菜单布局
        ft.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU_FRAGMENT_TAG);
        // 替换主界面布局
        ft.replace(R.id.fl_main_content, new MainContentFragment(), MAIN_CONTENT_FRAGMENT_TAG);

        // 提交
        ft.commit();
       /* mySharedPreferences = getSharedPreferences("location",
                Activity.MODE_PRIVATE);
        gps_info = mySharedPreferences.getString("gps_info", "");*/
        /*cityString = (result.length()>0)?result.substring(result.indexOf("省")+1,result.indexOf("市")):"成都市";
        Log.d(TAG, "initFragment: city=======**********************"+cityString);*/
    }

    private void checkIsLocation() {
        lm = LocationUtil.getLocationManager(this);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder da = new AlertDialog.Builder(this);
            da.setTitle("提示：");
            da.setMessage("为了更好的为您服务，请您打开您的GPS!");
            da.setCancelable(false);
            //设置左边按钮监听
            da.setPositiveButton("确定",
                    new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

                        }
                    });
            //设置右边按钮监听
            da.setNeutralButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            //隐藏CheckPrePage部分的UI
                            MainContentFragment mf1 = (MainContentFragment) getFragmentManager().findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
                            mf1.pagerList.get(1).displayCheckPrePage();
                            Toast.makeText(MainUI.this,"不能体验检测服务！",Toast.LENGTH_LONG).show();
                        }
                    });
            da.show();
        }
    }

    // 通过onActivityResult接受二维码扫描结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + intentResult);
        //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            /*if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //这次就打开了GPS
                //TODO 读取GPS信息，将其以键值对的形式保存在SharedParference中
            } */
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerFragment.KEY_PICKED_CITY);
                Log.d(TAG, "onActivityResult: **********************" + city);
                Toast.makeText(this, "选择的城市：" + city, Toast.LENGTH_LONG).show();
                //发送城市消息，更新UI
                sendMessage(MSG_CITY_RESULT, city);
            }
            return;
        }
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
              //  Toast.makeText(this, "扫描成功", Toast.LENGTH_LONG).show();
                scanResult = intentResult.getContents();
                //将scanResult存入本地数据库
                deviceSharedPreferences = getSharedPreferences("deviceInfo",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = deviceSharedPreferences.edit();
                editor.putString("deviceId",scanResult);
                editor.apply();
                Log.d(TAG, "onActivityResult: 扫描到的设备信息数据成功写入SharedPreferences");
                userinfoSharedPreferences = getSharedPreferences("userinfo",
                        Activity.MODE_PRIVATE);
                username = userinfoSharedPreferences.getString("username", "");
                isVIP = userinfoSharedPreferences.getString("isVIP", "");
                if (isVIP == "0") {
                    //不是VIP
                    Toast.makeText(this, "对不起，成为会员才有该权限哦!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //是VIP
                    try {
                        initWebSocketClient();
                        mWebSocketClient.connectBlocking();
                        mWebSocketClient.send(username + "--" + gps_info + "--" + scanResult);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    //TODO 进入等待效果，直到接收到了服务器的响应
                    DialogUtil.showDialog(dialog_message, MainUI.this);
                }
//                    showDialog(dialog_message);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    /**
     * 向服务器发送请求获取传感器的数据
     */
    public void initWebSocketClient() throws URISyntaxException{
        mWebSocketClient = new WebSocketClient(new URI(requestToServer_getSenorData_url),new Draft_17()){
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG, "run() returned: " + "连接到服务器");
<<<<<<< HEAD
=======
                //创建chart区域
                FragmentManager fragmentManager = getFragmentManager();
                MainContentFragment mf3Chart = (MainContentFragment)fragmentManager.findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
                mf3Chart.pagerList.get(1).displayChart();
                Log.d(TAG, "display mpChart");
>>>>>>> add dynamic chart
               // send(username + "-" + gps_info + "-" + scanResult);
            }

            @Override
            public void onMessage(String message) {
                if (message.equals("0") || message == "0") {
                    //关闭
                    close();
                }
                Log.d(TAG, "run() returned: " + message);
                sendMessage(MSG_GETDATA_RESULT, message);
<<<<<<< HEAD
                send(scanResult);
=======
               // 之前是加了的，但是忘记了起什么作用，而且造成了一个bug,send(scanResult);
>>>>>>> add dynamic chart
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
<<<<<<< HEAD
=======
                Log.d(TAG, "onClose() returned: " + remote);
>>>>>>> add dynamic chart
                Log.d(TAG, "onClose() returned: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.d(TAG, "onError() returned: " + ex);
            }

        };
    }
    /**
     * 关闭websocket客户端的方法
     * */
    public void closeSocket(){
        Log.d(TAG, "closeSocket mecthod is executing");
<<<<<<< HEAD
        mWebSocketClient.close();
=======
        mWebSocketClient.send("0");
>>>>>>> add dynamic chart
    }
    /*
     *发送消息
     */
    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    /*
     *发送和处理信息（message）
     * 处理服务器查询结果，更新UI
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CITY_RESULT:
                    //首先获取fragment下的viewPager
                    //更新UI
                    String data = msg.obj.toString();
                    FragmentManager fragmentManager = getFragmentManager();
                    MainContentFragment mf2 = (MainContentFragment) fragmentManager.findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
                    mf2.pagerList.get(2).updateView(data);
                    break;
                case MSG_GETDATA_RESULT:
                    //处理服务器响应回来的传感器数据
                    handleSenorDataResult(String.valueOf(msg.obj));
            }
        }

        ;
    };

    private void handleSenorDataResult(String data) {
        Log.d(TAG, "handleSenorDataResult: data====" + data);
<<<<<<< HEAD
        /*int resultCode = -1;
        if(data.length() == 1){
            resultCode = Integer.parseInt(data);
        }
        switch (resultCode){
            case 0:
                //检测失败
                break;
            case 1:
                try {*/
        if (data.split("\t").length == 6) {
            Log.d(TAG, "handleSenorDataResult: data.split=====" + data.split("\t"));
            Log.d(TAG, "handleSenorDataResult: split=====" + data.split("\t").length);
            //检测成功
            DialogUtil.closeDialog();
            //TODO 更新当前界面--显示各个参数值
            displayResultParams(data.split("\t"));
=======
        if (data.split("\\s+").length == 7) {
            //检测成功
            DialogUtil.closeDialog();
            //TODO 更新当前界面--显示各个参数值
            displayResultParams(data.split("\\s+"));
>>>>>>> add dynamic chart
        } else {
            Toast.makeText(this, "服务器返回信息异常，请重新请求！", Toast.LENGTH_LONG).show();
        }
                /*} catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                Toast.makeText(this, "请求服务器失败！", Toast.LENGTH_LONG).show();
                break;
        }*/
    }

    //更新UI显示结果参数
    private void displayResultParams(String[] str_split) {

<<<<<<< HEAD
        //D/Main_UI_Yanxin: displayResultParams: deviceID=1111	Tem=26.0	Hum=56.0	CHOH=0.024	PM2.5= 2.0	PM10= 4.6

        String device_id = str_split[0].split("=")[1];
        String tem = str_split[1].split("=")[1];
        String hum = str_split[2].split("=")[1];
        String choh = str_split[3].split("=")[1];
        String pm25 = str_split[4].split("=")[1];
        String pm10 = str_split[5].split("=")[1];
        String range = "良";
        Log.d(TAG, "温度:" + tem + ",湿度:" + hum + ",甲醛:" + choh + ",PM2.5:" + pm25 + ",PM10:" + pm10);
=======
        //D/Main_UI_Yanxin: displayResultParams: deviceID=D0001	Tem=19.0	Hum=65.0	CHOH=0.018	PM2.5=46.4	PM10=62.2	range=优

        String device_id = str_split[0];
        String tem = str_split[1];
        String hum = str_split[2];
        String choh = str_split[3];
        String pm25 = str_split[4];
        String pm10 = str_split[5];
        String range = str_split[6];
        Log.d(TAG, "温度:" + tem + ",湿度:" + hum + ",甲醛:" + choh + ",PM2.5:" + pm25 + ",PM10:" + pm10+",等级:" + range);
>>>>>>> add dynamic chart
        //更新UI
        FragmentManager fragmentManager = getFragmentManager();
        MainContentFragment mf3 = (MainContentFragment)fragmentManager.findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
        mf3.pagerList.get(1).update(tem, hum, choh, pm25, pm10, range);
        Log.d(TAG, "displayResultParams: ========接收到了一条服务器返回的下位机数据");
    }


    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：

//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);


        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        Log.d(TAG, "initLocation: 启动了定位开关");
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    mLocationClient.stopLocation();
                    //解析定位结果
                    result = LocationUtil.getLocationStr(amapLocation);
                    Log.d("位置信息",result);
                    cityString = result.substring(result.indexOf("省")+1,result.indexOf("市"));
                    Log.d(TAG, "initFragment: city=======**********************"+cityString);
                    WeatherSearchQuery weatherQuery = new WeatherSearchQuery(
                            cityString,
                            WeatherSearchQuery.WEATHER_TYPE_LIVE);
                    WeatherSearch weatherSearch = new WeatherSearch(MainUI.this);
                    weatherSearch.setOnWeatherSearchListener(MainUI.this);
                    weatherSearch.setQuery(weatherQuery);
                    weatherSearch.searchWeatherAsyn();

                    //设备信息存储，方便设备界面显示调用
                    SharedPreferences mySharedPreferences = getSharedPreferences("location",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString("gps_info",result);
                    editor.apply();
                    gps_info = result;
                    //只获取一次定位
                    destroyLocation();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
    /**
     * 回退按钮两次退出
     */

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            //彻底关闭整个APP
            int currentVersion = android.os.Build.VERSION.SDK_INT;
            if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            } else {// android2.1
                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
            }
        }
    }


    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != mLocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int rCode) {
        if(rCode==1000){
            LocalWeatherLive liveWeather= localWeatherLiveResult.getLiveResult();
            MainContentFragment mf4 = (MainContentFragment)getFragmentManager().findFragmentByTag(MAIN_CONTENT_FRAGMENT_TAG);
            mf4.pagerList.get(1).updateWeather(liveWeather.getCity(),liveWeather.getReportTime().split(" ")[0],liveWeather.getWeather(),liveWeather.getWindDirection(),liveWeather.getWindPower(),liveWeather.getHumidity(),liveWeather.getTemperature());
        }else{
            Log.e("","查询天气失败");
        }
    }
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}
