package com.yx.aircheck.pager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.aircheck.R;
import com.yx.aircheck.adapter.HistoryAdapter;
import com.yx.aircheck.base.TabBasePager;
import com.yx.aircheck.bean.CheckData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

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

public class HistoryPage extends TabBasePager implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{
    View view;

    private ListView mListView;
    private TextView mTvTitle;
    private ImageView image_nodata;
    //适配器
    private SimpleAdapter historyAdapter;
    private Context context;
    private List<Map<String,Object>> mapList;
    private List<Map<String,Object>> realList;
    private String TAG = "history_page";
    private SharedPreferences userinfoSharedPreferences;
    private String username;
    private JSONObject result_json;
    private JSONArray ja;
<<<<<<< HEAD
    private String requestToServer_myhistorydata_url = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/getHistoryData";
=======
    private String requestToServer_myhistorydata_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/getHistoryData";
>>>>>>> add dynamic chart
    public HistoryPage(Context context) {
        super(context);
        this.context = context;
        userinfoSharedPreferences = context.getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        username = userinfoSharedPreferences.getString("username","");
    }


    @Override
    public View initView() {

        view = View.inflate(mainUI, R.layout.pager_history, null);
        mTvTitle = view.findViewById(R.id.tv_title_bar_title);
        mListView = view.findViewById(R.id.listview_history);
        return view;

    }


    @Override
    public void initData() {
        mTvTitle.setText(R.string.history_record);
        image_nodata = view.findViewById(R.id.history_nodata_image);
        getDataFromServer();
    }

    @Override
    public void loadData() {
        mapList = new ArrayList<Map<String, Object>>();
        getDataFromServer();
        realList = getDataToView();
        historyAdapter = new SimpleAdapter(context,this.realList,R.layout.pager_history_child,new String[]{"username","location","params","time"},new int[]{R.id.history_username,R.id.history_location,R.id.history_params,R.id.history_time});
        //为该ListView添加适配器
        mListView.setAdapter(historyAdapter);
    }

    /**
     * 从服务器获取历史记录的数据*/
    //TODO 发送请求给服务端获取平台的共享数据
    private void getDataFromServer() {
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
                            .add("username", username)
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServer_myhistorydata_url)
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
        //解析返回数据
        try{
            if(!result_json.has("result_info")){
                //返回的数据为空
                //显示没有数据的图片
                image_nodata.setVisibility(View.VISIBLE);
                Toast.makeText(mainUI,"您当前没有历史记录！",Toast.LENGTH_LONG).show();
            }
            else{
                //返回的数据不为空
                //销毁显示没有数据的图片
                image_nodata.setVisibility(View.GONE);
                result_data = result_json.getString("result_info");
                Log.d(TAG, "****************************a"+result_data);
                //解析result_info
                String json_array = result_data.substring(result_data.indexOf("["),result_data.lastIndexOf("]")+1);
                Log.d(TAG, "*******************"+json_array);
                ja = new JSONArray(json_array);
                for (int i = 0;i < ja.length();i++){
                    JSONObject jo = (JSONObject) ja.get(i);
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("username",jo.getString("username"));
                    Log.d(TAG, "*******************"+jo.getString("username"));
                    map.put("location",jo.getString("gpsinfo").split(":")[1]);
                    Log.d(TAG, "*******************"+jo.getString("gpsinfo").split(":")[1]);
                    map.put("time",jo.getString("time"));
                    Log.d(TAG, "*******************"+jo.getString("time"));
                    map.put("params","温度:"+jo.getString("tem")+"℃"+","+"湿度:"+jo.getString("hum")+"%"+","+"甲醛:"+jo.getString("choh")+"mg/m³"+","+"PM2.5:"+jo.getString("pm25")+"ug/m³"+","+"PM10:"+jo.getString("pm10")+"ug/m³"+"等级:"+jo.getString("airrank"));
                    mapList.add(map);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapList;
    }
    /*private List<Map<String,Object>> getData(){
        List<CheckData> datas = DataSupport.findAll(CheckData.class);
        for(CheckData data:datas){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("username",data.getUsername());
            Log.d(TAG, "getData: "+data.getUsername());
            Log.d(TAG, "getData: "+data.newToString());
            map.put("location",data.getGpsInfo());
            map.put("params",data.newToString());
            map.put("time",data.getTime());
            mapList.add(map);
        }
        return mapList;
    }*/

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String text = mListView.getItemAtPosition(position)+"";
        Toast.makeText(this,"position="+position+" text="+text,Toast.LENGTH_SHORT).show();
    }
}
