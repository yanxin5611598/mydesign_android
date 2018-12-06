package com.yx.aircheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.maps.AMap;
import com.amap.api.maps.Projection;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.yx.aircheck.utils.LocationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户发布的数据信息详情页面*/
public class MapModelViewActivity extends Activity{
    private TextView mTvTitle;
    private ImageButton btn_back;
    private MapView mapView;
    private AMap aMap;
    private LocationManager lm = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private MyLocationStyle myLocationStyle;
    private ArrayList<String> usernames;
    private ArrayList<String> locations;
    private ArrayList<String> ranks;
    private ArrayList<String> times;
    private ArrayList<String> imageinfos;
    private ArrayList<String> contentEvaluates;
    private double longitude;
    private double latitude;
    private String getImageURL = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/getImageInfo";
    private String TAG = "mapmodel_detail";
    private static final int IMAGE_RESULT = 0;
    //为了实现marker的点击事件
    private List<Marker> mList=new ArrayList<Marker>();
    ArrayList<MarkerOptions> markerOptionlst = new ArrayList<MarkerOptions>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        initView();

        mapView.onCreate(savedInstanceState);// 此方法必须重写
        //检查GPS是否开启
        checkIsLocation();
        //initLocation();
        mapMethod();
        initData();
    }

    private void initView() {
        mTvTitle = (TextView)findViewById(R.id.tv_title_bar_title);
        mapView = (MapView)findViewById(R.id.map_model) ;
        btn_back = (ImageButton)findViewById(R.id.ib_title_bar_back);
    }
    public void mapMethod(){
        aMap = mapView.getMap();
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 地图模式
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        //初始化定位蓝点样式类
        myLocationStyle = new MyLocationStyle();
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        //不显示精度圈
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色

        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        //设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "onMarkerClick: mark was clicked");
                for (int i = 0; i < mList.size(); i++) {
                    if (marker.equals(mList.get(i))) {
                        if (aMap != null) {
                            Intent detail_intent = new Intent();
                            detail_intent.setClass(MapModelViewActivity.this, ViewDetailActivity.class);
                            detail_intent.putExtra("username", usernames.get(i));
                            detail_intent.putExtra("location", locations.get(i));
                            detail_intent.putExtra("contentEvaluate", contentEvaluates.get(i));
                            detail_intent.putExtra("rank", ranks.get(i));
                            detail_intent.putExtra("time", times.get(i));
                            Log.d(TAG, "onMarkerClick: "+imageinfos.get(i));
                            detail_intent.putExtra("imageInfo", imageinfos.get(i) == null ? "" : imageinfos.get(i));
                            MapModelViewActivity.this.startActivity(detail_intent);
                        }
                    }
                }
                return false;
            }
        });
    }
    public void initData(){
        mTvTitle.setText(R.string.map_title);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        usernames = intent.getStringArrayListExtra("usernames");
        locations = intent.getStringArrayListExtra("locations");
        ranks = intent.getStringArrayListExtra("ranks");
        times = intent.getStringArrayListExtra("times");
        imageinfos = intent.getStringArrayListExtra("imageinfos");
        contentEvaluates = intent.getStringArrayListExtra("contentEvaluates");
        for(int i = 0;i < locations.size();i++){
            Log.d(TAG, "location: "+locations.get(i));
            LatLng latLng = new LatLng(Double.parseDouble(locations.get(i).split("\t")[2]),Double.parseDouble(locations.get(i).split("\t")[1]));
            addMarkersToMap(latLng,ranks.get(i),i);
        }
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        mList = aMap.addMarkers(markerOptionlst,true);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
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
                        mapView.onDestroy();
                        Toast.makeText(MapModelViewActivity.this,"不能使用地图模式！",Toast.LENGTH_LONG).show();
                        }
                    });
            da.show();
        }
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            //地图变化的时候触发
            Log.d(TAG, "位置发生变化");
        }
    };
    private void addMarkersToMap(LatLng latLng,String rank,int point) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        Log.d(TAG, "point"+String.valueOf(point));
        switch (rank){
            case "1":
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.you)));
                break;
            case "2":
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.liang)));
                break;
            case "3":
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.zhong)));
                break;
            case "4":
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.cha)));
                break;
            default:
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.yanzhong)));
                break;
        }
        markerOptionlst.add(markerOption);
    }
}