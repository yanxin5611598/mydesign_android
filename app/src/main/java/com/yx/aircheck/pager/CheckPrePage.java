package com.yx.aircheck.pager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
=======
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
>>>>>>> add dynamic chart
import com.google.zxing.integration.android.IntentIntegrator;
import com.yx.aircheck.MainUI;
import com.yx.aircheck.R;
import com.yx.aircheck.ScanActivity;
<<<<<<< HEAD
=======
import com.yx.aircheck.VIPActivity;
>>>>>>> add dynamic chart
import com.yx.aircheck.ViewDetailActivity;
import com.yx.aircheck.WebViewActivity;
import com.yx.aircheck.base.TabBasePager;
import com.yx.aircheck.bean.CheckData;
import com.yx.aircheck.fragment.MainContentFragment;
import com.yx.aircheck.utils.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

<<<<<<< HEAD
public class CheckPrePage extends TabBasePager implements OnClickListener{
=======
public class CheckPrePage extends TabBasePager implements OnClickListener,OnChartValueSelectedListener{
>>>>>>> add dynamic chart

    private String TAG = "yanxin_check";
    private Button saoButton;//扫一扫按钮
    private Button uploadButton;//上传按钮
    private Context context;
    // 标题栏
    private TextView mTvTitle;
    //更新UI的相关参数
    private TextView mTemperature;// 温度
    private TextView mHumidity;// 湿度
    private TextView mchoh;// 甲醛
    private TextView mpm25;// PM2.5
    private TextView mpm10;//PM10
    private TextView mAir;//空气质量
    //调用外部API获取的天气参数（室外的）
    private TextView cityLocation;
    private TextView cityDate;
    private TextView cityWeek;
    private TextView cityWeatehr;
    private TextView cityWind;
    private TextView cityTem;
    private TextView cityHum;
    private LinearLayout params_layout;
<<<<<<< HEAD
    private RelativeLayout topR;
    private FrameLayout centerF;
    private FrameLayout bottomF1;
    private FrameLayout bottomF2;
    private RelativeLayout check_main;
=======
    private LinearLayout real_values;
    private LinearLayout check_main;
    private LineChart mDoubleLineChar;
>>>>>>> add dynamic chart
    private static final int MSG_SERVER_SUBMIT_RESULT = 1;
    private static final int MSG_SERVER_ADDPOINT_RESULT = 0;
    private static final int MSG_SERVER_PARAMLIST_RESULT = 2;
    private SharedPreferences addpoint_sharedPreferences;
    private SharedPreferences userInfo_sharedPreferences;
    private SharedPreferences gpsInfo_sharedPreferences;
    private SharedPreferences deviceInfo_sharedPreferences;
    private String username;
    private String GPSInfo;
    private String deviceId;
    private EditText inputInfoNum;//房间输入信息对象
    private String roomNum;
    private String contentEvaluate;
    private List<String> timeList = new ArrayList<String>();
    private List<String> temList= new ArrayList<String>();
    private List<String> humList= new ArrayList<String>();
    private List<String> chohList= new ArrayList<String>();
    private List<String> pm25List= new ArrayList<String>();
    private List<String> pm10List= new ArrayList<String>();
<<<<<<< HEAD
    private List<String> co2List= new ArrayList<String>();

    private LinearLayout layout_web;
    private WebView webView;
=======
    private List<String> rankList= new ArrayList<String>();
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colors = new ArrayList<>();//折线颜色集合
    private LinearLayout layout_web;
    private WebView webView;
    private LineDataSet lineDataSet;
    private static boolean flag = false;
    LineDataSet chohSet = null;
    LineDataSet pm25Set = null;
    LineDataSet pm10Set = null;
    LineData lineData = null;
    private List<LineDataSet> lineDataSets = new ArrayList<>();
>>>>>>> add dynamic chart
    //    private SharedPreferences inputInfoSharedPreferences;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    private SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
    public CheckPrePage(Context context) {
        super(context);
        this.context = context;
    }
    //向服务器发送上传数据请求的URL
<<<<<<< HEAD
    private String requestToServer_upload_url = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/uploadCheckData";
    private String requestToServer_addPint_url = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/addUserPoint";
    private String requestToServer_upload_paramList_url = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/uploadParamList";
    //发送给服务器的数据
    private CheckData data;
=======
    private String requestToServer_upload_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/uploadCheckData";
    private String requestToServer_addPint_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/addUserPoint";
    private String requestToServer_upload_paramList_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/uploadParamList";
>>>>>>> add dynamic chart
    //等待对话框显示的内容
    private static final String dialog_message = "正在上传";
    @Override
    public View initView() {

        View view = View.inflate(mainUI, R.layout.pager_check_pre, null);
        // 标题栏
        mTvTitle = (TextView) view.findViewById(R.id.tv_title_bar_title);
<<<<<<< HEAD
        check_main = (RelativeLayout) view.findViewById(R.id.check_main_ui);
        params_layout = (LinearLayout) view.findViewById(R.id.check_city_params);
=======
        check_main = (LinearLayout)view.findViewById(R.id.sum_ui);
        params_layout = (LinearLayout) view.findViewById(R.id.check_city_params);
        real_values = (LinearLayout)view.findViewById(R.id.lay_real_values);
>>>>>>> add dynamic chart
        cityLocation = (TextView) view.findViewById(R.id.check_city_location_value);
        cityDate = (TextView) view.findViewById(R.id.check_city_date_value);
        cityWeek = (TextView) view.findViewById(R.id.check_city_week_value);
        cityWind = (TextView) view.findViewById(R.id.check_city_wind_value);
        cityWeatehr = (TextView) view.findViewById(R.id.check_city_weather_value);
        cityTem = (TextView) view.findViewById(R.id.check_city_tem_value);
        cityHum = (TextView) view.findViewById(R.id.check_city_hum_value);
        mTemperature = (TextView) view.findViewById(R.id.tv_livablepage_temp);
        mHumidity = (TextView) view.findViewById(R.id.tv_livablepage_humidity);
        mAir = (TextView) view.findViewById(R.id.tv_livablepage_air_quality);
        mchoh = (TextView) view.findViewById(R.id.tv_livablepage_jiaquan);
        mpm25 = (TextView) view.findViewById(R.id.tv_livablepage_pm25);
        mpm10 = (TextView) view.findViewById(R.id.tv_livablepage_pm10);
<<<<<<< HEAD
        topR = (RelativeLayout) view.findViewById(R.id.top_view);
        centerF = (FrameLayout) view.findViewById(R.id.center_view);
        bottomF1 = (FrameLayout) view.findViewById(R.id.bottom_view1);
        bottomF2 = (FrameLayout) view.findViewById(R.id.bottom_view2);
=======
>>>>>>> add dynamic chart
        layout_web = (LinearLayout) view.findViewById(R.id.layout_web);
        webView = (WebView) view.findViewById(R.id.web);
        uploadButton = (Button) view.findViewById(R.id.id_upload_btn);
        uploadButton.setOnClickListener(this);
        //设置扫码检测按钮
        saoButton = (Button) view.findViewById(R.id.id_saoyisao_btn);
        saoButton.setOnClickListener(this);
<<<<<<< HEAD
=======
        mDoubleLineChar = (LineChart) view.findViewById(R.id.mDoubleLineChar);
>>>>>>> add dynamic chart
        return view;

    }

    @Override
    public void initData() {

        mTvTitle.setText(mainUI.getString(R.string.check));//检测
        addpoint_sharedPreferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        //根据city获取各个环境参数
        deviceInfo_sharedPreferences = context.getSharedPreferences("deviceInfo",Activity.MODE_PRIVATE);
        deviceId = deviceInfo_sharedPreferences.getString("deviceId","");
        userInfo_sharedPreferences = context.getSharedPreferences("userinfo",Activity.MODE_PRIVATE);
        username = userInfo_sharedPreferences.getString("username", "");
<<<<<<< HEAD
    }


=======
        //设置chart
        setChartPre();
    }
    private void setChartPre(){
        //设置数值选择监听
        mDoubleLineChar.setOnChartValueSelectedListener(this);

        // 支持触控手势
        mDoubleLineChar.setTouchEnabled(true);
        mDoubleLineChar.setDragDecelerationFrictionCoef(0.9f);
        // 支持缩放和拖动
        mDoubleLineChar.setDragEnabled(true);
        mDoubleLineChar.setScaleEnabled(true);
        mDoubleLineChar.setDrawGridBackground(false);
        mDoubleLineChar.setHighlightPerDragEnabled(true);
        // 如果禁用,扩展可以在x轴和y轴分别完成
        mDoubleLineChar.setPinchZoom(true);
        // 设置背景颜色(灰色)
        mDoubleLineChar.setBackgroundColor(Color.parseColor("#e1e1e1"));


        //默认x动画
        mDoubleLineChar.animateX(2500);
        //添加一个空的 LineData
        mDoubleLineChar.setData(new LineData());
        //获得数据
        Legend l = mDoubleLineChar.getLegend();

        //修改
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        //x轴
        XAxis xAxis = mDoubleLineChar.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        //左边y轴---显示甲醛
        YAxis leftAxis = mDoubleLineChar.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMinValue(0.0f);
        leftAxis.setAxisMaxValue(0.5f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        //右边---显示PM
        YAxis rightAxis = mDoubleLineChar.getAxisRight();
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMinValue(0);
        rightAxis.setAxisMaxValue(200);
        rightAxis.setDrawGridLines(false);//隐藏X轴竖网格线
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);//启用在放大时限制y轴间隔的粒度特性。默认值:false
        //initLineDataSet(names,colors);
        lineData = mDoubleLineChar.getData();
            if(mDoubleLineChar.isEmpty()){
                Log.d(TAG, "addData: mdoublechart is empty******************************");
            }
            if (chohSet == null) {
                chohSet = createLineDataSet("甲醛",Color.CYAN,true);
                lineData.addDataSet(chohSet);
            }
            if (pm25Set == null) {
                pm25Set = createLineDataSet("PM2.5",Color.GREEN,false);
                lineData.addDataSet(pm25Set);
            }
            if (pm10Set == null) {
                pm10Set = createLineDataSet("PM10",Color.BLUE,false);
                lineData.addDataSet(pm10Set);
            }

    }
    private void initLineDataSet(List<String> names, List<Integer> colors) {
        for (int i = 0; i < names.size(); i++) {
            lineDataSet = new LineDataSet(null, names.get(i));
            lineDataSet.setColor(colors.get(i));
            lineDataSet.setLineWidth(1.5f);
            lineDataSet.setCircleRadius(1.5f);
            lineDataSet.setColor(colors.get(i));

            lineDataSet.setDrawFilled(true);
            lineDataSet.setCircleColor(colors.get(i));
            lineDataSet.setHighLightColor(colors.get(i));
//            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setValueTextSize(10f);
            lineDataSets.add(lineDataSet);
        }
    }
    //设置数据
    private void addData(float choh,float pm25,float pm10) {
        Log.d(TAG, "addData: " + choh + ":" + pm25 + ":" + pm10);
        if(mDoubleLineChar.isActivated()){
            Log.d(TAG, "addData: isActivatedyesyesyesyesyesyesyesyes");
        }else{
            Log.d(TAG, "addData: isActivatednononononononononononono");
        }
        if(mDoubleLineChar.isEnabled()){
            Log.d(TAG, "addData: isEnabledyesyesyesyesyesyesyesyes");
        }else{
            Log.d(TAG, "addData: isEnablednononononononononononono");
        }
        if(mDoubleLineChar.isEmpty()){
            Log.d(TAG, "addData: isEmptyyesyesyesyesyesyesyesyes");
        }else{
            Log.d(TAG, "addData: isEmptynononononononononononono");
        }
        if (lineData != null && !flag &&
                mDoubleLineChar.getData().getDataSetCount() > 0) {
            chohSet = (LineDataSet) lineData.getDataSetByIndex(0);
            pm25Set = (LineDataSet) lineData.getDataSetByIndex(1);
            pm10Set = (LineDataSet) lineData.getDataSetByIndex(2);
            // set.addEntry(...); // can be called as well
            // 这里要注意，x轴的index是从零开始的
            // 假设index=2，那么getEntryCount()就等于3了
            int chohCount = chohSet.getEntryCount();
            int pm25Count = pm25Set.getEntryCount();
            int pm10Count = pm10Set.getEntryCount();
            Log.d(TAG, "addData: chohCount=" + chohCount + " ，" + pm25Count + " ," + pm10Count);
            // add a new x-value first 这行代码不能少
            lineData.addXValue(chohCount + "");
            // 位最后一个DataSet添加entry
            lineData.addEntry(new Entry(choh, chohCount), 0);
            mDoubleLineChar.notifyDataSetChanged();
            //设置在曲线图中显示的最大数量
            mDoubleLineChar.setVisibleXRangeMaximum(10);
            //移到某个位置
            mDoubleLineChar.moveViewToX(chohSet.getEntryCount() - 5);
            lineData.addEntry(new Entry(pm25, pm25Count), 1);
            mDoubleLineChar.notifyDataSetChanged();
            //设置在曲线图中显示的最大数量
            mDoubleLineChar.setVisibleXRangeMaximum(10);
            //移到某个位置
            mDoubleLineChar.moveViewToX(chohSet.getEntryCount() - 5);
            lineData.addEntry(new Entry(pm10, pm10Count), 2);
            mDoubleLineChar.notifyDataSetChanged();
            //设置在曲线图中显示的最大数量
            mDoubleLineChar.setVisibleXRangeMaximum(10);
            //移到某个位置
            mDoubleLineChar.moveViewToX(chohSet.getEntryCount() - 5);
            //mDoubleLineChar.moveViewTo(choh, chohCount, YAxis.AxisDependency.LEFT);
            //mDoubleLineChar.moveViewTo(pm25, pm25Count, YAxis.AxisDependency.LEFT);
            //mDoubleLineChar.moveViewTo(pm10, pm10Count, YAxis.AxisDependency.LEFT);
            Log.d(TAG, "set.getEntryCount()=" + chohSet.getEntryCount());
        }
    }

    @Override
    public void displayChart() {
        Log.d(TAG, "displayChart: **********************************");
        //显示chart
        mDoubleLineChar.setVisibility(View.VISIBLE);
    }

    /**
     * 获取最后一个LineDataSet的索引
     */
    private int getLastDataSetIndex(LineData lineData) {
        int dataSetCount = lineData.getDataSetCount();
        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
    }
    private LineDataSet createLineDataSet(String label,Integer color,boolean leftOrRight) {
        LineDataSet dataSet = new LineDataSet(null, label);
        if(leftOrRight) {
            //左边
            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        }else{
            //右边
            dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        }
        dataSet.setColor(color);
        dataSet.setLineWidth(1.5f);
        dataSet.setCircleRadius(1.5f);

        dataSet.setDrawFilled(true);
        dataSet.setCircleColor(color);
        dataSet.setHighLightColor(color);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawValues(false);
        return dataSet;
    }
>>>>>>> add dynamic chart
    @Override
    public void onClick(View v) {
        //发送请求到服务器******需要附带手机的GPS信息以及用户名信息
//        getGPSInfo();
        int id = v.getId();
        switch (id) {
            case R.id.id_saoyisao_btn:
<<<<<<< HEAD
                //首先是预输入房间号信息
=======
                //首先是判断用户是不是VIP并预输入房间号信息
>>>>>>> add dynamic chart
                InputInfoPreSaoyisao();
                break;
            case R.id.id_upload_btn:
                //TODO 点击了上传数据的按钮，应该首先关闭掉websocket连接
<<<<<<< HEAD
                mainUI.closeSocket();
                //上传这一段时间的所有数据(包括时间、温湿度、甲醛等List)系统
                uploadParamListToServer();
=======
                //mainUI.closeSocket();
                //上传这一段时间的所有数据(包括时间、温湿度、甲醛等List)系统
                uploadParamListToServer();
                flag = true;
                mDoubleLineChar.clear();
                mDoubleLineChar.setVisibility(View.INVISIBLE);
>>>>>>> add dynamic chart
                //TODO 在进行存储之前可以选择进行评价
                //createInputDialog();
            default:
                break;
        }
    }
    private void infoResultDisplayToUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("已为您生成该时间段曲线!");
        builder.setCancelable(false);
        //设置左边按钮监听
        builder.setPositiveButton("查看",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        intent.setClass(mainUI,WebViewActivity.class);
                        intent.putExtra("deviceID", deviceId);
                        intent.putExtra("username", username);
                        mainUI.startActivity(intent); //启动浏览器
                        try{
                            Thread.sleep(2000);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        createInputDialog();
                    }
                });
        //设置右边按钮监听
        builder.setNeutralButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        Toast.makeText(context,"前往上传图片吧!",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
    /**用于生成一个可以输入评价内容的弹出框*/
    private void createInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请输入您的评价");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        final EditText editText = view.findViewById(R.id.input_evaluate);
        editText.setSingleLine(false);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        builder.setCancelable(false);

        //设置左边按钮监听
        builder.setPositiveButton("确定",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        contentEvaluate = editText.getText().toString();
                        arg0.dismiss();
                        Log.d(TAG, "contentEvaluate: "+contentEvaluate);
                        //对用户输入的内容进行存储操作
                        gpsInfo_sharedPreferences = context.getSharedPreferences("location",Activity.MODE_PRIVATE);

                        GPSInfo = gpsInfo_sharedPreferences.getString("gps_info","");
                        //显示等待对话框
                        DialogUtil.showDialog(dialog_message, context);
                        uploadDataToServer();
                    }
                });
        //设置右边按钮监听
        builder.setNeutralButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        Toast.makeText(context,"建议您评价一下",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    private void InputInfoPreSaoyisao(){
<<<<<<< HEAD
        final Dialog dialog=new Dialog(mainUI,R.style.Dialog);
        dialog.show();
        Window window=dialog.getWindow();
        window.setContentView(R.layout.input_info_dialog);
        inputInfoNum = (EditText)window.findViewById(R.id.No);//输入框在此处初始化
        Button btnOk=(Button)window.findViewById(R.id.btnok);
        Button btnCancel=(Button)window.findViewById(R.id.btncancel);
        btnOk.setTextColor(0xff1E90FF);
        btnCancel.setTextColor(0xff1E90FF);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(inputInfoNum.getText().toString().isEmpty()) {
                    Toast.makeText(mainUI, "请输入房间编号！", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context,"房间编号:"+inputInfoNum.getText().toString(),Toast.LENGTH_LONG).show();
                    Log.d(TAG, "CheckPrePage====onClick: "+inputInfoNum.getText().toString());
                    //将获取到的房间信息保存到一个变量中
                    roomNum = inputInfoNum.getText().toString();
                    /*SharedPreferences.Editor editor = inputInfoSharedPreferences.edit();
                    try{
                        editor.putString("inputRoomInfo",inputInfoNum.getText().toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    editor.apply();*/
                    Toast.makeText(context,inputInfoNum.getText(),Toast.LENGTH_LONG).show();
                    Log.d(TAG, "CheckPrePage========onClick: "+inputInfoNum.getText());
                    dialog.dismiss();
                    // 扫码获取数据
                    new IntentIntegrator(mainUI)
                            .setOrientationLocked(false)
                            .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                            .initiateScan(); // 初始化扫描
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
=======
        //判断用户是不是会员
        SharedPreferences vipSharedPreferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String isVIP = vipSharedPreferences.getString("isVIP","");
        if(isVIP == "0" || isVIP.equals("0")){
            //创建对话框提示
            AlertDialog.Builder da = new AlertDialog.Builder(context);
            da.setTitle("提示：");
            da.setMessage("此项服务需开通会员");
            da.setCancelable(false);
            //设置左边按钮监听
            da.setPositiveButton("确定",
                    new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            return;
                        }
                    });
            //设置右边按钮监听
            da.setNeutralButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            return;
                        }
                    });
            da.show();
        }else {
            final Dialog dialog = new Dialog(mainUI, R.style.Dialog);
            dialog.show();
            Window window = dialog.getWindow();
            window.setContentView(R.layout.input_info_dialog);
            inputInfoNum = (EditText) window.findViewById(R.id.No);//输入框在此处初始化
            Button btnOk = (Button) window.findViewById(R.id.btnok);
            Button btnCancel = (Button) window.findViewById(R.id.btncancel);
            btnOk.setTextColor(0xff1E90FF);
            btnCancel.setTextColor(0xff1E90FF);
            btnOk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (inputInfoNum.getText().toString().isEmpty()) {
                        Toast.makeText(mainUI, "请输入房间编号！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "房间编号:" + inputInfoNum.getText().toString(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "CheckPrePage====onClick: " + inputInfoNum.getText().toString());
                        //将获取到的房间信息保存到一个变量中
                        roomNum = inputInfoNum.getText().toString();
                        /*SharedPreferences.Editor editor = inputInfoSharedPreferences.edit();
                        try{
                            editor.putString("inputRoomInfo",inputInfoNum.getText().toString());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        editor.apply();*/
                        Toast.makeText(context, inputInfoNum.getText(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "CheckPrePage========onClick: " + inputInfoNum.getText());
                        dialog.dismiss();
                        // 扫码获取数据
                        new IntentIntegrator(mainUI)
                                .setOrientationLocked(false)
                                .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                                .initiateScan(); // 初始化扫描
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
>>>>>>> add dynamic chart
    }
    //上传数据到服务器，需要附带用户名，检测结果，设备ID，位置信息等。
    private void uploadParamListToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "uploadParamListToServer: ***********come in");
                //创建OkHttpClient对象
                try{
                    //设置带超时时间设置的client
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(40,TimeUnit.SECONDS)
                            .build();
                    //请求体
                    RequestBody requestBody = new FormBody.Builder()
                            .add("deviceID",deviceId)
                            .add("temList",temList.toString())
                            .add("humList",humList.toString())
                            .add("chohList",chohList.toString())
                            .add("pm25List",pm25List.toString())
                            .add("pm10List",pm10List.toString())
                            .add("timeList",timeList.toString())
<<<<<<< HEAD
=======
                            .add("rankList",rankList.toString())
>>>>>>> add dynamic chart
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServer_upload_paramList_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject result_json = new JSONObject(responseData);
                    sendMessage(MSG_SERVER_PARAMLIST_RESULT,result_json);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //上传数据到服务器，需要附带用户名，检测结果，设备ID，位置信息等。
    private void uploadDataToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "uploadDataToServer: ***********come in");
<<<<<<< HEAD
=======
                String data = temList.get(temList.size()-1)+","+humList.get(humList.size()-1)+","+chohList.get(chohList.size()-1)+","+pm25List.get(pm25List.size()-1)+","+pm10List.get(pm10List.size()-1)+","+rankList.get(rankList.size()-1)+","+username+","+GPSInfo+","+deviceId+","+simpleDateFormat.format(new Date(System.currentTimeMillis()));
                Log.d(TAG, "displayResultParams: data========"+data);
>>>>>>> add dynamic chart
                //创建OkHttpClient对象
                try{
                    //设置带超时时间设置的client
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(40,TimeUnit.SECONDS)
                            .build();
                    //请求体
                    RequestBody requestBody = new FormBody.Builder()
<<<<<<< HEAD
                            .add("data",data.toString())
=======
                            .add("data",data)
>>>>>>> add dynamic chart
                            .add("roomNum",roomNum)
                            .add("contentEvaluate",contentEvaluate)
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServer_upload_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
<<<<<<< HEAD
=======
                    //清空各个list
                    temList.clear();
                    humList.clear();
                    chohList.clear();
                    pm10List.clear();
                    pm25List.clear();
                    timeList.clear();
                    rankList.clear();
>>>>>>> add dynamic chart
                    String responseData = response.body().string();
                    Log.d(TAG,"******************************"+responseData);
                    //关闭对话框
                    DialogUtil.closeDialog();
                    JSONObject result_json = new JSONObject(responseData);

                    sendMessage(MSG_SERVER_SUBMIT_RESULT,result_json);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*
     *发送消息
     */
    private void sendMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        cHandler.sendMessage(msg);
    }
    public Handler cHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case MSG_SERVER_SUBMIT_RESULT:
                    JSONObject json = (JSONObject) msg.obj;
                    handleSubmitResult(json);  //获取服务器的返回结果
                    break;
            }
            switch (msg.what){
                case MSG_SERVER_ADDPOINT_RESULT:
                    JSONObject jsonObject = (JSONObject)msg.obj;
                    handleAddPointResult(jsonObject);
                    break;
            }
            switch (msg.what){
                case MSG_SERVER_PARAMLIST_RESULT:
                    JSONObject jsonObject = (JSONObject)msg.obj;
                    handleParamListResult(jsonObject);
                    break;
            }
        }
    };
    private void handleParamListResult(JSONObject jsonObject){
        int result_code = -1;
        try{
            result_code = jsonObject.getInt("result_code");
            if(result_code == 1){
                //成功
                Toast.makeText(mainUI,"生成曲线成功",Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
=======
                //将显示数值参数的区域设置为不可见
                real_values.setVisibility(View.INVISIBLE);
>>>>>>> add dynamic chart
                //生成对话框提示用户查看系统为其生成的参数曲线
                infoResultDisplayToUser();
            }
            else{
                Toast.makeText(mainUI,"生成曲线失败",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void handleAddPointResult(JSONObject jsonObject){
        int result_code = -1;
        try{
            result_code = jsonObject.getInt("result_code");
            if(result_code == 1){
                //成功
                //首先将SharedPreferences中的rewardpoint值进行更新
                SharedPreferences.Editor editor = addpoint_sharedPreferences.edit();
                editor.putString("rewardPoint",String.valueOf(jsonObject.getInt("reward_point")));
                editor.apply();
                Toast.makeText(mainUI,"增加积分成功",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(mainUI,"增加积分异常",Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void handleSubmitResult(JSONObject json){
        int upload_middle_code = -1;
        int resultCode = -1;
        try {
            upload_middle_code = json.getInt("vip_code");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(upload_middle_code == 0){
            //请求的数据错误
            Toast.makeText(context, "请求的数据错误！", Toast.LENGTH_LONG).show();
        }

            //请求的数据正确
            try {
                resultCode = json.getInt("result_code");
            }catch(JSONException e) {
                e.printStackTrace();
            }
            switch (resultCode){
                case 0:
                    //上传失败
                    Toast.makeText(context, "服务器出错，上传失败！", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(context, "上传成功，积分+5", Toast.LENGTH_LONG).show();
                    //TODO 该用户积分增加5个
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "uploadDataToServer: ***********come in");
                            //创建OkHttpClient对象
                            try{
                                //设置带超时时间设置的client
                                OkHttpClient client = new OkHttpClient.Builder()
                                        .connectTimeout(10, TimeUnit.SECONDS)
                                        .readTimeout(40,TimeUnit.SECONDS)
                                        .build();
                                //请求体
                                RequestBody requestBody = new FormBody.Builder()
<<<<<<< HEAD
                                        .add("username",data.getUsername())
=======
                                        .add("username",username)
>>>>>>> add dynamic chart
                                        .add("pointnums","5")
                                        .build();
                                Request request = new Request.Builder()
                                        .url(requestToServer_addPint_url)
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String responseData = response.body().string();

                                Log.d(TAG,"******************************"+responseData);
                                //关闭对话框
                                DialogUtil.closeDialog();
                                JSONObject result_json = new JSONObject(responseData);
                                sendMessage(MSG_SERVER_ADDPOINT_RESULT,result_json);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    //关闭上传数据按钮的显示
                    uploadButton.setVisibility(View.GONE);
                    //显示扫码检测按钮
                    saoButton.setVisibility(View.VISIBLE);
                    //隐藏掉显示的检测数据
<<<<<<< HEAD
                    topR.setVisibility(View.GONE);
                    centerF.setVisibility(View.GONE);
                    bottomF1.setVisibility(View.GONE);
                    bottomF2.setVisibility(View.GONE);
=======
                    real_values.setVisibility(View.INVISIBLE);
                    //隐藏图表展示
                    mDoubleLineChar.setVisibility(View.INVISIBLE);
>>>>>>> add dynamic chart
                    //显示天气参数信息
                    params_layout.setVisibility(View.VISIBLE);
                    break;
            }
    }

    @Override
    public void displayCheckPrePage() {
        check_main.setVisibility(View.INVISIBLE);
    }

    @Override
    public void update(String tem,String hum,String choh,String pm25,String pm10,String range){
<<<<<<< HEAD
        Log.d(TAG, "update: ******************************come in update");
        //首先将之前的天气参数设置为不可见
        params_layout.setVisibility(View.GONE);
=======
        /*//不显示调用高德接口获得的天气参数
        //首先将之前的天气参数设置为不可见
        params_layout.setVisibility(View.GONE);*/
        //显示数值动态数据
        real_values.setVisibility(View.VISIBLE);
        Log.d(TAG, "update: ******************************come in update");
        addData(Float.valueOf(choh),Float.valueOf(pm25),Float.valueOf(pm10));
>>>>>>> add dynamic chart
        mTemperature.setText(tem);
        mHumidity.setText(hum);
        mchoh.setText(choh);
        mAir.setText(range);
        mpm25.setText(pm25);
        mpm10.setText(pm10);
        timeList.add(simpleTimeFormat.format(new Date(System.currentTimeMillis())));
        temList.add(tem);
        humList.add(hum);
        chohList.add(choh);
        pm25List.add(pm25);
        pm10List.add(pm10);
<<<<<<< HEAD
        Log.d(TAG, "update: ******************************set text");
        topR.setVisibility(View.VISIBLE);
        centerF.setVisibility(View.VISIBLE);
        bottomF1.setVisibility(View.VISIBLE);
        bottomF2.setVisibility(View.VISIBLE);
=======
        switch (range){
            case "优":
                rankList.add("1");
                break;
            case "良":
                rankList.add("2");
                break;
            case "中":
                rankList.add("3");
                break;
            case "差":
                rankList.add("4");
                break;
            case "重度":
                rankList.add("5");
                break;
        }
        Log.d(TAG, "update: ******************************set text");
>>>>>>> add dynamic chart
        Log.d(TAG, "update: ******************************set visibility");
        saoButton.setVisibility(View.GONE);//设置扫一扫按钮不可见
        uploadButton.setVisibility(View.VISIBLE);//设置上传按钮可见
    }

    @Override
    public void openDialog() {
        createInputDialog();
    }

    @Override
    public void updateWeather(String city, String reportTime, String weather, String windDirection, String windPower, String hum, String tem) {
        cityLocation.setText(city);
        cityLocation.setTextColor(Color.GREEN);
        cityDate.setText(reportTime);
        cityWeatehr.setText(weather);
        cityWind.setText(windDirection+"风("+windPower+"级)");
        cityTem.setText(tem+"℃");
        cityHum.setText(hum+"%");
        String week = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        if("1".equals(week)){
            week ="星期天";
        }else if("2".equals(week)){
            week ="星期一";
        }else if("3".equals(week)){
            week ="星期二";
        }else if("4".equals(week)){
            week ="星期三";
        }else if("5".equals(week)){
            week ="星期四";
        }else if("6".equals(week)){
            week ="星期五";
        }else if("7".equals(week)){
            week ="星期六";
        }
        cityWeek.setText(week);
    }
<<<<<<< HEAD
=======

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }
>>>>>>> add dynamic chart
    /*public void getGPSInfo(){
        Log.d("gps_info is come in","********************************************");
        try {
            LocationManager lm = LocationUtil.getLocationManager(context);
            if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                AlertDialog.Builder da = new AlertDialog.Builder(context);
                da.setTitle("提示：");
                da.setMessage("为了更好的为您服务，请您打开您的GPS!");
                da.setCancelable(false);
                //设置左边按钮监听
                da.setNeutralButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                // 转到手机设置界面，用户设置GPS
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                ((Activity)context).startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

                            }
                        });
                //设置右边按钮监听
                da.setPositiveButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
                da.show();
            }else{
                Location location = LocationUtil.getLocation(lm);
                location_str = LocationUtil.getAddress(location, context.getApplicationContext());
                Log.d("位置信息",location_str);
                //设备信息存储，方便设备界面显示调用
                Log.d(TAG, "Activity:" + mActivity.toString());
                SharedPreferences mySharedPreferences = context.getSharedPreferences("location",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("gps_info",location_str);
                editor.apply();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
}
