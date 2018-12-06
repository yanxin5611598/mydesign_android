package com.yx.aircheck.pager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.aircheck.DeviceDetailActivity;
import com.yx.aircheck.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.yx.aircheck.ScanActivity;
import com.yx.aircheck.ViewDetailActivity;
import com.yx.aircheck.base.TabBasePager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DevicePage extends TabBasePager implements OnClickListener {

    View view;
    // 标题栏
    private TextView mTvTitle;
    private Context context;
    private TextView tv_device_id;
    private TextView tv_device_state;
    private ListView listView;
    private String TAG = "device_page";
    private JSONObject result_json;
    private JSONArray ja;
    private List<Map<String,Object>> mapList;
    private List<Map<String,Object>> realList;
    private SimpleAdapter sa;
<<<<<<< HEAD
    private String requestToServer_getDevice_url = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/getDeviceInfo";
=======
    private String requestToServer_getDevice_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/getDeviceInfo";
>>>>>>> add dynamic chart
    public DevicePage(Context context) {
        super(context);
        this.context = context;
        getDeviceInfoFromServer();
    }

    @Override
    public View initView() {
        view = View.inflate(mainUI, R.layout.pager_device, null);
        // 标题栏
        mTvTitle = (TextView) view.findViewById(R.id.tv_title_bar_title);
        listView = (ListView) view.findViewById(R.id.listview_device);
        tv_device_id = (TextView)view.findViewById(R.id.id_device_id_value);
        tv_device_state = (TextView)view.findViewById(R.id.id_device_state_value);
        return view;
    }
    @Override
    public void initData() {
        mTvTitle.setText(mainUI.getString(R.string.deviceCenter));//设备
    }
    @Override
    public void loadData() {
        mapList = new ArrayList<Map<String, Object>>();
        getDeviceInfoFromServer();
        realList = getDataToView();
        sa = new SimpleAdapter(context,this.realList,R.layout.pager_device_child,new String[]{
                "deviceId","deviceState","placefrom","timefrom","placecurrent","timecurrent","username"},
                new int[]{R.id.id_device_id_value,R.id.id_device_state_value,R.id.id_device_child_placefrom_value,
                        R.id.id_device_child_timefrom_value,R.id.id_device_child_placecurrent_value,
                        R.id.id_device_child_timecurrent_value,R.id.id_device_child_username_value});
        listView.setAdapter(sa);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detail_intent = new Intent();
                detail_intent.setClass(mainUI,DeviceDetailActivity.class);
                detail_intent.putExtra("deviceId",realList.get(i).get("deviceid").toString());
                detail_intent.putExtra("placefrom",realList.get(i).get("placefrom").toString());
                detail_intent.putExtra("timefrom",realList.get(i).get("timefrom").toString());
                detail_intent.putExtra("state",realList.get(i).get("state").toString());
                detail_intent.putExtra("placecurrent",realList.get(i).get("placecurrent").toString());
                detail_intent.putExtra("timecurrent",realList.get(i).get("timecurrent").toString());
                detail_intent.putExtra("username",realList.get(i).get("username").toString());
                mActivity.startActivity(detail_intent);
            }
        });*/
    }
    private void getDeviceInfoFromServer(){
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
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServer_getDevice_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "******************************" + responseData);
                    result_json = new JSONObject(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public List<Map<String,Object>> getDataToView(){
        String result_data = "";

        Log.d(TAG, "getDataToView: "+result_json);
        try{
            result_data = result_json.getString("result_info");
            Log.d(TAG, "****************************a"+result_data);
            //解析result_info
            String json_array = result_data.substring(result_data.indexOf("["),result_data.lastIndexOf("]")+1);
            Log.d(TAG, "*******************"+json_array);
            ja = new JSONArray(json_array);
            for (int i = 0;i < ja.length();i++){
                JSONObject jo = (JSONObject) ja.get(i);
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("deviceId",jo.getString("deviceid"));
                Log.d(TAG, "*******************"+jo.getString("deviceid"));
                map.put("deviceState",(jo.getString("state").equals("1"))?"使用中":"未使用");
                Log.d(TAG, "*******************"+jo.getString("state"));
                map.put("placefrom",jo.getString("placefrom"));
                map.put("timefrom",jo.getString("timefrom"));
                map.put("placecurrent",jo.getString("placecurrent"));
                map.put("timecurrent",jo.getString("timecurrent"));
                map.put("username",jo.getString("username"));

                mapList.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapList;
    }
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.ib_title_bar_livable_menu:
                // 扫码获取数据
                new IntentIntegrator(mainUI)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是ScanActivity
                        .initiateScan(); // 初始化扫描
                break;

            default:
                break;
        }

    }
}
