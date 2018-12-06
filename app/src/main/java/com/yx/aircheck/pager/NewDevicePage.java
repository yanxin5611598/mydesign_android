package com.yx.aircheck.pager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.yx.aircheck.R;
import com.yx.aircheck.ScanActivity;
import com.yx.aircheck.base.TabBasePager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
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

public class NewDevicePage extends TabBasePager implements OnClickListener {

    View view;
    // 标题栏
    private TextView mTvTitle;
    private Context context;
    private TextView tv_device_id;
    private TextView tv_device_state;
    private ListView listView;
    private String TAG = "aircheck   new_device_page";
    private String comment;
    private JSONObject result_json;
    private JSONArray ja;
    private List<Map<String,Object>> mapList;
    private List<Map<String,Object>> realList;
    private SimpleAdapter sa;
    private WebSocketClient mWebSocketClient;
    private List<String> allDevices = new ArrayList<String>();
    private String requestToServer_getSenorData_url = "ws://1856o325q1.iok.la:37325/mydesign/dataDisplayWebsocket";
    private String requestToServer_getDevice_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/getDeviceInfo";
    public NewDevicePage(Context context) {
        super(context);
        this.context = context;
        allDevices.add("D0001");
        allDevices.add("D0002");
        try{
            initWebSocketClient();
            mWebSocketClient.connectBlocking();
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        getDeviceInfoFromServer();
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
        realList = getDataToView(comment);
        sa = new SimpleAdapter(context,this.realList,R.layout.pager_device_child,new String[]{
                "deviceId","deviceState","placefrom","timefrom","placecurrent","timecurrent","username"},
                new int[]{R.id.id_device_id_value,R.id.id_device_state_value,R.id.id_device_child_placefrom_value,
                        R.id.id_device_child_timefrom_value,R.id.id_device_child_placecurrent_value,
                        R.id.id_device_child_timecurrent_value,R.id.id_device_child_username_value});
        listView.setAdapter(sa);
    }
    /**
     * 向服务器发送请求获取传感器的数据
     */
    public void initWebSocketClient() throws URISyntaxException {
        Log.d(TAG, "execute");
        mWebSocketClient = new WebSocketClient(new URI(requestToServer_getSenorData_url),new Draft_17()){
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d(TAG, "run() returned: " + "连接到服务器");
            }

            @Override
            public void onMessage(String message) {
                if (message.contains("deviceNums")) {
                    //关闭
                    Log.d(TAG, "run() returned: " + message);
                    comment = message;
                    getDataToView(comment);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "onClose() returned: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.d(TAG, "onError() returned: " + ex);
            }

        };
    }

    public List<Map<String,Object>> getDataToView(String message){
        try{
            Log.d(TAG, "****************************a"+message);
            //解析result_info
            String[] devices = message.substring(message.indexOf("[")+1,message.lastIndexOf("]")).trim().split(",");
            Log.d(TAG, "****************************a"+allDevices.size());
            for (int i = 0;i < allDevices.size();i++){
                Map<String,Object> map = new HashMap<String,Object>();
                //预定有两个设备
                if(!message.contains("D")){
                    Log.d(TAG, "****************************not contain"+message);
                    map.put("deviceId",allDevices.get(i));
                    map.put("deviceState","未使用");
                }else{
                    Log.d(TAG, "****************************contain"+message);
                    if(devices.length == 1){
                        map.put("deviceId",allDevices.get(i));
                        Log.d(TAG, "****************************contain1111    "+devices[0]+"    "+allDevices.get(i));
                        map.put("deviceState",(allDevices.get(i) == devices[0] || allDevices.get(i).equals(devices[0]))?"使用中":"未使用");
                    }
                    if(devices.length == 2){
                        map.put("deviceId",allDevices.get(i));
                        map.put("deviceState","使用中");
                    }
                }

                mapList.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapList;
    }
}
