package com.yx.aircheck;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
<<<<<<< HEAD

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.InputStream;
=======
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.yx.aircheck.utils.ChartUtil;
import com.yx.aircheck.view.LineGraphicView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
>>>>>>> add dynamic chart
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 用户发布的数据信息详情页面*/
public class ViewDetailActivity extends Activity{
<<<<<<< HEAD
    private TextView tv_username;
    private TextView tv_location;
    private TextView tv_room;
    private TextView tv_params;
    private TextView tv_rank;
    private TextView tv_time;
    private TextView mTvTitle;
    private TextView tv_contentEvaluate;
    private TextView tv_imageInfo;
    private ImageView iv_image;
=======
    private TextView tv_location;
    private TextView tv_rank;
    private TextView mTvTitle;
    private TextView tv_contentEvaluate;
    private TextView tv_imageInfo;
    private TextView tv_history_more;
    private TextView tv_history_one;
    private ImageView iv_image;
    private LineChart line_history_display;
>>>>>>> add dynamic chart
    private ImageButton btn_back;
    private TextureMapView mMapView;
    private ScrollView  scrollView;
    private TableRow tb_image;
<<<<<<< HEAD
    private LinearLayout l_image_display;
    private double longitude;
    private double latitude;
    private String location;
    private String getImageURL = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/getImageInfo";
    private String TAG = "view_page_detail";
    private String imageInfo;
    private static final int IMAGE_RESULT = 0;
=======
    private LinearLayout l_image_display,l_history_image_display;
    private double longitude;
    private double latitude;
    private String location;
    private String getImageURL = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/getImageInfo";
    private String getRankTimeURL = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/getRankAndTimeList";
    private String TAG = "view_page_detail";
    private String imageInfo;
    private String username;
    private String time;
    private static final int IMAGE_RESULT = 0;
    private static final int RANK_RESULT = 1;
>>>>>>> add dynamic chart
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail);
        initView();
        initData();
        mMapView = (TextureMapView) findViewById(R.id.map);
        //       重写onTouch()事件,在事件里通过requestDisallowInterceptTouchEvent(boolean)方法来设置父类的不可用,true表示父类的不可用
        //解决地图的touch事件和scrollView的touch事件冲突问题
        mMapView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }else{
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        scrollView = (ScrollView)findViewById(R.id.scrollView1);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mMapView.getMap();
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 地图模式
        LatLng latLng = new LatLng(latitude,longitude);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));

        final Marker maker = aMap.addMarker(new MarkerOptions().position(latLng).title(location.substring(location.indexOf("省")+1,location.indexOf("市")+1)).snippet(location.substring(location.indexOf("市")+1)));
        if(!maker.isInfoWindowShown()){
            maker.showInfoWindow();
        }
    }
    private void initView() {
        mTvTitle = (TextView)findViewById(R.id.tv_title_bar_title);
        btn_back = (ImageButton)findViewById(R.id.ib_title_bar_back);
<<<<<<< HEAD
        tv_username = (TextView)findViewById(R.id.id_view_detail_username_value);
        tv_location = (TextView)findViewById(R.id.id_view_detail_location_value);
        tv_rank = (TextView)findViewById(R.id.id_view_detail_rank_value);
        tv_params = (TextView)findViewById(R.id.id_view_detail_params_value);
        tv_time = (TextView)findViewById(R.id.id_view_detail_time_value);
        tv_room = (TextView)findViewById(R.id.id_view_detail_room_value);
        tv_contentEvaluate = (TextView)findViewById(R.id.id_view_detail_contentEvaluate_value);
        tb_image = (TableRow)findViewById(R.id.id_tb_image);
        l_image_display = (LinearLayout)findViewById(R.id.id_ll_image_display);
        tv_imageInfo = (TextView)findViewById(R.id.id_view_detail_image_info);
        iv_image = (ImageView)findViewById(R.id.id_view_detail_image_display);
=======
        tv_location = (TextView)findViewById(R.id.id_view_detail_location_value);
        tv_rank = (TextView)findViewById(R.id.id_view_detail_rank_value);
        tv_contentEvaluate = (TextView)findViewById(R.id.id_view_detail_contentEvaluate_value);
        tb_image = (TableRow)findViewById(R.id.id_tb_image);
        l_image_display = (LinearLayout)findViewById(R.id.id_ll_image_display);
        l_history_image_display = (LinearLayout)findViewById(R.id.id_ll_history_image_display);
        tv_imageInfo = (TextView)findViewById(R.id.id_view_detail_image_info);
        iv_image = (ImageView)findViewById(R.id.id_view_detail_image_display);
        line_history_display = (LineChart)findViewById(R.id.id_view_detail_history_image_display);
        tv_history_more = (TextView)findViewById(R.id.view_detail_history_more);
        tv_history_one = (TextView)findViewById(R.id.view_detail_history_one);
>>>>>>> add dynamic chart
    }
    public void initData(){
        mTvTitle.setText(R.string.view_detail_info);
        Intent intent = getIntent();
<<<<<<< HEAD
        tv_username.setText(intent.getStringExtra("username"));
=======
        username = intent.getStringExtra("username");
>>>>>>> add dynamic chart
        location = intent.getStringExtra("location").split("\t")[0];
        longitude = Double.parseDouble(intent.getStringExtra("location").split("\t")[1]);
        latitude = Double.parseDouble(intent.getStringExtra("location").split("\t")[2]);
        tv_location.setText(location);
<<<<<<< HEAD
        tv_room.setText(intent.getStringExtra("roomNum"));
        tv_rank.setText(intent.getStringExtra("rank"));
        tv_time.setText(intent.getStringExtra("time"));
        tv_params.setText(intent.getStringExtra("params"));
=======
        tv_rank.setText(intent.getStringExtra("rank"));
        time = intent.getStringExtra("time");
>>>>>>> add dynamic chart
        tv_contentEvaluate.setText(intent.getStringExtra("contentEvaluate"));
        imageInfo = intent.getStringExtra("imageInfo");
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getImageFromServer();
<<<<<<< HEAD
    }

=======
        getRankListFromServer();
    }
    private void getRankListFromServer() {
        //TODO 发送请求给服务端根据地址获取等级和时间的集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置带超时时间设置的client
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(40, TimeUnit.SECONDS)
                            .build();
                    //请求体
                    RequestBody requestBody = new FormBody.Builder()
                            .add("address", location)
                            .build();
                    Request request = new Request.Builder()
                            .url(getRankTimeURL)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "******************************" + "success getRankTimeURL");
                    String responseData = response.body().string();
                    JSONObject result_json = new JSONObject(responseData);
                    sendMessage(RANK_RESULT,result_json);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
>>>>>>> add dynamic chart
    private void getImageFromServer() {
        //TODO 发送请求给服务端获取平台的共享数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置带超时时间设置的client
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(40, TimeUnit.SECONDS)
                            .build();
                    //请求体
                    RequestBody requestBody = new FormBody.Builder()
<<<<<<< HEAD
                            .add("username", tv_username.getText().toString())
                            .add("time",String.valueOf(tv_time.getText()))
=======
                            .add("username", username)
                            .add("time",String.valueOf(time))
>>>>>>> add dynamic chart
                            .build();
                    Request request = new Request.Builder()
                            .url(getImageURL)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "******************************" + "success get image stream");
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if(bitmap != null){
                        sendMessage(IMAGE_RESULT, bitmap);
                    }
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
        mHandler.sendMessage(msg);
    }
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case IMAGE_RESULT:
                    Log.d(TAG, "handleMessage: loading image");
                    tb_image.setVisibility(View.VISIBLE);
                    l_image_display.setVisibility(View.VISIBLE);
                    Log.d(TAG, "handleMessage: loading image"+imageInfo);
                    tv_imageInfo.setText(imageInfo);
                    tv_imageInfo.setTextColor(Color.parseColor("#ca0909"));
                    Bitmap bitmap = (Bitmap)msg.obj;
                    iv_image.setImageBitmap(bitmap);
<<<<<<< HEAD
                    iv_image.setBackgroundColor(Color.parseColor("#cbfaf5 "));
                    break;
            }
        }
    };
    private void addMarkersToMap() {
        MarkerOptions markerOption = new MarkerOptions();
        double dlong=Double.valueOf(103.985798).doubleValue();
        double dlat=Double.valueOf(30.76813).doubleValue();
        markerOption.position(new LatLng(dlat, dlong));
        markerOption.title("成都市").snippet("成都市：103.985798, 30.76813");

        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(),R.mipmap.boy_16)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
    }
=======
                    iv_image.setBackgroundColor(Color.parseColor("#cbfaf5"));
                    break;
                case RANK_RESULT:
                    l_history_image_display.setVisibility(View.VISIBLE);
                    Log.d(TAG, "handleMessage: "+msg.obj);
                    JSONObject json = (JSONObject) msg.obj;
                    int resultCode = -1;
                    //请求的数据正确
                    try {
                        resultCode = json.getInt("result_code");
                        switch (resultCode) {
                            case 0:
                                //请求失败或者返回的list结果为null
                                tv_history_one.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                //返回了结果
                                tv_history_more.setVisibility(View.VISIBLE);
                                String result_data = json.getString("result_info");
                                Log.d(TAG, "****************************a" + result_data);
                                //解析result_info
                                String json_array = result_data.substring(result_data.indexOf("["), result_data.lastIndexOf("]") + 1);
                                JSONArray ja = new JSONArray(json_array);
                                List<String> xDataList = new ArrayList<>();// x轴数据源
                                List<Entry> yDataList = new ArrayList<>();// y轴数据数据源
                                for (int i = 0;i < ja.length();i++){
                                    xDataList.add(ja.get(i).toString().split("\t")[1]);
                                    Log.d(TAG, "****************************a" + ja.get(i).toString());
                                    yDataList.add(new Entry(Float.parseFloat(ja.get(i).toString().split("\t")[0]), i));
                                }
                                //显示图表,参数（ 上下文，图表对象， X轴数据，Y轴数据，图表标题，曲线图例名称，坐标点击弹出提示框中数字单位）
                                ChartUtil.showChart(ViewDetailActivity.this, line_history_display, xDataList, yDataList, "历史检测结果", "等级/时间", "rank");
                                break;
                            }
                        }catch(JSONException e) {
                                e.printStackTrace();
                        }
                break;
            }
        }
    };
>>>>>>> add dynamic chart
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

   /* @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }*/
}
