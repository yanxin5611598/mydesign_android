package com.yx.aircheck.pager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.aircheck.CityPickerActivity;
<<<<<<< HEAD
=======
import com.yx.aircheck.MapModelViewActivity;
>>>>>>> add dynamic chart
import com.yx.aircheck.R;
import com.yx.aircheck.SelectAddPopupWindow;
import com.yx.aircheck.SelectSingleAddPopupWindow;
import com.yx.aircheck.ViewDetailActivity;
import com.yx.aircheck.base.TabBasePager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewPage extends TabBasePager implements View.OnClickListener{


    View view;

    private TextView mTvCotnent;
    private TextView mTvTitle;
    private ImageButton btn_select;
<<<<<<< HEAD
=======
    private ImageButton btn_mapmodel;
    private TextView tv_map;
>>>>>>> add dynamic chart
    private Context context;
    //ListView控件
    private ListView listView;
    //下拉刷新
    private SwipeRefreshLayout mSwipeLayout;
    //初始化SimpleAdapter
    private SimpleAdapter viewAdapter;
    private List<Map<String,Object>> mapList;
    private List<Map<String,Object>> realList;
<<<<<<< HEAD
=======
    private ArrayList<String> usernameList;
    private ArrayList<String> locationList;
    private ArrayList<String> rankList;
    private ArrayList<String> timeList;
    private ArrayList<String> imageinfoList;
    private ArrayList<String> contentEvaluateList;
>>>>>>> add dynamic chart
    private String username = "";
    private String rewardPointPre;
    private String rewardPointPost;
    private SharedPreferences mySharedPreferences = null;
    private String TAG = "view_page";
    private JSONObject result_json;
    //记录loadData()的执行次数====即发送请求的次数
    private int requestNum = -1;
    //请求数据的URL
<<<<<<< HEAD
    private String requestToServer_viewdata_url = "http://1856o325q1.iok.la:13523/mydesign/userUpDown/viewPublicData";
=======
    private String requestToServer_viewdata_url = "http://1856o325q1.iok.la:37325/mydesign/userUpDown/viewPublicData";
>>>>>>> add dynamic chart
    private SelectAddPopupWindow topPopWindow;
    private SelectSingleAddPopupWindow topSinglePopWindow;
    private ImageView image_nodata;
    private PtrFrameLayout mPtrFrameLayout;
    private LoadMoreListViewContainer  mLoadMoreListViewContainer;
    //初始化用来存储气体参数的map集合对象
    private Map<Integer,String> airMap;
    private JSONArray ja;
    private static final int REFRESH_UI = 1;
    public ViewPage(Context context) {
        super(context);
        this.context = context;
        mySharedPreferences = context.getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        rewardPointPre = mySharedPreferences.getString("rewardPoint","");
    }

    @Override
    public View initView() {
        view = View.inflate(mainUI, R.layout.pager_view, null);
        listView = view.findViewById(R.id.listview_view);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_ly);
<<<<<<< HEAD
        //设置在listview上下拉刷新的监听
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
=======
        loadSwipeView();
        mTvTitle = view.findViewById(R.id.tv_title_bar_title);
        btn_mapmodel = view.findViewById(R.id.ib_title_bar_mapmodel);
        tv_map = view.findViewById(R.id.tv_title_bar_map);
        btn_select = view.findViewById(R.id.ib_title_bar_menu);
        image_nodata = view.findViewById(R.id.nodata_image);
        return view;
    }
    //加载下拉刷新操作视图
    public void loadSwipeView(){
        //设置在listview上下拉刷新的监听
        final SwipeRefreshLayout.OnRefreshListener refreshListener  = new SwipeRefreshLayout.OnRefreshListener() {
>>>>>>> add dynamic chart
            @Override
            public void onRefresh() {
                //这里可以做一下下拉刷新的操作
                //例如下面代码，在方法中发送一个handler模拟延时操作
<<<<<<< HEAD
                mHandler.sendEmptyMessageDelayed(REFRESH_UI, 2000);
            }
        });
        mTvTitle = view.findViewById(R.id.tv_title_bar_title);
        btn_select = view.findViewById(R.id.ib_title_bar_menu);
        image_nodata = view.findViewById(R.id.nodata_image);
        return view;
    }
    @Override
    public void initData() {
        mTvTitle.setText(R.string.view_data);
        btn_select.setVisibility(View.VISIBLE);//设置该按钮可见
        username = mySharedPreferences.getString("username", "");
        getDataFromServer();
=======
                mHandler.sendEmptyMessageDelayed(REFRESH_UI, 1000);
            }
        };
        mSwipeLayout.setOnRefreshListener(refreshListener);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        }, 100);
    }
    @Override
    public void initData() {
        loadSwipeView();
        mTvTitle.setText(R.string.view_data);
        username = mySharedPreferences.getString("username", "");
        getDataFromServer();
        usernameList = new ArrayList<String>();
        locationList = new ArrayList<String>();
        rankList = new ArrayList<String>();
        timeList = new ArrayList<String>();
        imageinfoList = new ArrayList<String>();
        contentEvaluateList = new ArrayList<String>();
>>>>>>> add dynamic chart
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: *********点击下拉窗口按钮使能");
                showTopRightPopMenu();
            }
        });
<<<<<<< HEAD
=======
        btn_mapmodel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //加载地图Activity显示
                showMapMpdelView();
            }
        });
>>>>>>> add dynamic chart
    }

    @Override
    public void loadData() {
        requestNum++;
        //TODO 接收广播变量，将requestNum置为0，用于用户在进行积分充值之后
        rewardPointPost = mySharedPreferences.getString("rewardPoint","");
        Log.d(TAG, "rewardPointPre:******************************** "+rewardPointPre);
        Log.d(TAG, "rewardPointPost:******************************** "+rewardPointPost);
        if(Integer.parseInt(rewardPointPost)-Integer.parseInt(rewardPointPre)>=10 && Integer.parseInt(rewardPointPre)<2){
            //当且仅当用户进行了充值，并且用户之前的剩余积分数量少于2的时候，会将请求次数归0，再次请求就会扣除积分，否则不做扣除操作
            //请求次数归0
            Log.d(TAG, "loadData:******************************** "+rewardPointPost);
            requestNum = 0;
            //将rewardPointPre置为当前rewardPointPost
            rewardPointPre = rewardPointPost;
        }
        airMap = new HashMap<Integer,String>();
        mapList = new ArrayList<Map<String, Object>>();
        getDataFromServer();
        realList = getDataToView();
        getAirParams();//填充了airMap
        viewAdapter = new SimpleAdapter(context,this.realList,R.layout.pager_view_child,
                new String[]{"username","location","rank","time","params","contentEvaluate","imageInfo"},
                new int[]{R.id.view_username,R.id.view_location,R.id.view_rank,R.id.view_time,R.id.view_air_params,R.id.view_air_contentEvaluate,R.id.view_air_imageInfo});
        listView.setAdapter(viewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detail_intent = new Intent();
                detail_intent.setClass(mainUI,ViewDetailActivity.class);
                detail_intent.putExtra("username",realList.get(i).get("username").toString());
                detail_intent.putExtra("location",realList.get(i).get("location").toString());
                detail_intent.putExtra("roomNum",realList.get(i).get("roomNum").toString());
                detail_intent.putExtra("params",realList.get(i).get("params").toString());
                detail_intent.putExtra("contentEvaluate",realList.get(i).get("contentEvaluate").toString());
                detail_intent.putExtra("rank",realList.get(i).get("rank").toString());
                detail_intent.putExtra("imageInfo",realList.get(i).get("imageInfo").toString() == null ? "":realList.get(i).get("imageInfo").toString());
                detail_intent.putExtra("time",realList.get(i).get("time").toString());
                mActivity.startActivity(detail_intent);
            }
        });
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case REFRESH_UI:
                    Log.d(TAG, "handleMessage: refreshing");
                    loadData();
                    //刷新adapter
                    viewAdapter.notifyDataSetChanged();
                    //为了保险起见可以先判断当前是否在刷新中（旋转的小圈圈在旋转）....
                    if(mSwipeLayout.isRefreshing()){
                        //关闭刷新动画
                        mSwipeLayout.setRefreshing(false);
                    }
                    Log.d(TAG, "handleMessage: refreshed");
                    break;
            }
        }
    };
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
                            .add("request_num",String.valueOf(requestNum))
                            .build();
                    Request request = new Request.Builder()
                            .url(requestToServer_viewdata_url)
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
        //解析返回数据
        try{
            int view_middle_code = result_json.getInt("view_middle_code");
            int result_code = result_json.getInt("result_code");//获取服务器端的result_code数据
            String result_data = "";
            if(view_middle_code == 0){
                Toast.makeText(context,"请求数据错误！",Toast.LENGTH_LONG).show();
                Log.d(TAG, "************"+view_middle_code);
            }else if(result_code == 0){
                //TODO 这里目前是以Toast进行替代，以后可以使用Dialog
                Toast.makeText(context,"您当前积分不足，请前往积分中心充值！",Toast.LENGTH_LONG).show();
                Log.d(TAG, "************"+view_middle_code);
            }
            if(result_json.has("result_info")){
<<<<<<< HEAD
                if(requestNum == 0){
                    Toast.makeText(context,"积分-2,第一次请求查看",Toast.LENGTH_LONG).show();
=======
                btn_select.setVisibility(View.VISIBLE);//设置该按钮可见
                btn_mapmodel.setVisibility(View.VISIBLE);
                tv_map.setVisibility(View.VISIBLE);
                if(requestNum == 0){
                    //Toast.makeText(context,"积分-2,第一次请求查看",Toast.LENGTH_LONG).show();
>>>>>>> add dynamic chart
                    //再一次更新rewardPointPre的值
                    rewardPointPre = String.valueOf(Integer.parseInt(rewardPointPost)-2);
                    //最后需要将SharedPreferences中的积分值做更新
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putString("rewardPoint",rewardPointPre);
                    editor.apply();
                }
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
                    map.put("roomNum",jo.getString("roominfo"));
                    Log.d(TAG, "*******************"+jo.getString("roominfo"));
<<<<<<< HEAD
                    map.put("rank",jo.getString("airrank"));
=======
                    String myrank = jo.getString("airrank").trim();
                    map.put("rank",myrank.equals("1") ? "优":(myrank.equals("2")?"良":(myrank.equals("3"))?"中":(myrank.equals("4"))?"差":"严重"));
>>>>>>> add dynamic chart
                    Log.d(TAG, "*******************"+jo.getString("airrank"));
                    map.put("time",jo.getString("time"));
                    Log.d(TAG, "*******************"+jo.getString("time"));
                    map.put("params","温度:"+jo.getString("tem")+"℃"+"\n"+"湿度:"+jo.getString("hum")+"%"+"\n"+"甲醛:"+jo.getString("choh")+"mg/m³"+"\n"+"PM2.5:"+jo.getString("pm25")+"ug/m³"+"\n"+"PM10:"+jo.getString("pm10")+"ug/m³");
                    map.put("contentEvaluate",jo.getString("contentevaluate"));
                    map.put("imageInfo",jo.getString("imageinfo"));
                    mapList.add(map);
<<<<<<< HEAD
=======
                    usernameList.add(jo.getString("username"));
                    locationList.add(jo.getString("gpsinfo").split(":")[1]);
                    rankList.add(jo.getString("airrank"));
                    timeList.add(jo.getString("time"));
                    imageinfoList.add(jo.getString("imageinfo"));
                    contentEvaluateList.add(jo.getString("contentevaluate"));
>>>>>>> add dynamic chart
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mapList;
    }
    /**
     * 填充airMap数据*/
    public void getAirParams(){
        try{
            for (int k = 0;k < ja.length();k++) {
                JSONObject jo = (JSONObject) ja.get(k);
                airMap.put(k,"温度:"+jo.getString("tem")+"℃"+"\n"+"湿度:"+jo.getString("hum")+"%"+"\n"+"甲醛:"+jo.getString("choh")+"mg/m³"+"\n"+"PM2.5:"+jo.getString("pm25")+"ug/m³"+"\n"+"PM10:"+jo.getString("pm10")+"ug/m³");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 显示右上角popup菜单
     */
    private void showTopRightPopMenu() {
        topPopWindow = new SelectAddPopupWindow(mainUI,this,430,970);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        topPopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    topPopWindow.dismiss();
                }
            }
        });
        //设置默认获取焦点
        topPopWindow.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        topPopWindow.showAsDropDown(btn_select, 0, 0);
        //如果窗口存在，则更新
        topPopWindow.update();
    }
    /**
<<<<<<< HEAD
=======
     * 显示地图模式下的视图
     * */
    private void showMapMpdelView(){
        Intent map_intent = new Intent();
        map_intent.setClass(mainUI,MapModelViewActivity.class);
        map_intent.putStringArrayListExtra("usernames",usernameList);
        map_intent.putStringArrayListExtra("locations",locationList);
        map_intent.putStringArrayListExtra("ranks",rankList);
        map_intent.putStringArrayListExtra("times",timeList);
        map_intent.putStringArrayListExtra("imageinfos",imageinfoList);
        map_intent.putStringArrayListExtra("contentEvaluates",contentEvaluateList);
        //将所有的位置信息传递过去
        mActivity.startActivity(map_intent);
    }
    /**
>>>>>>> add dynamic chart
     * 显示右上角传感器参数popup菜单
     */
    private void showSingleTopRightPopMenu() {
        topSinglePopWindow = new SelectSingleAddPopupWindow(mainUI,this,380,500);
        //监听窗口的焦点事件，点击窗口外面则取消显示
        topSinglePopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    topSinglePopWindow.dismiss();
                }
            }
        });
        //设置默认获取焦点
        topSinglePopWindow.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        topSinglePopWindow.showAsDropDown(btn_select, 0, 0);
        //如果窗口存在，则更新
        topSinglePopWindow.update();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.view_time_de:
                Log.d(TAG, "*******************点击时间降序");
                topPopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return o2.get("time").toString().compareTo(o1.get("time").toString());
                    }
                });
                listView.invalidateViews();//刷新listView
                break;
            case R.id.view_time_in:
                Log.d(TAG, "*******************点击时间升序");
                topPopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return o1.get("time").toString().compareTo(o2.get("time").toString());
                }
            });
                listView.invalidateViews();
                break;
            case R.id.view_name_de:
                topPopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return o2.get("username").toString().compareTo(o1.get("username").toString());
                    }
                });
                listView.invalidateViews();
                break;
            case R.id.view_name_in:
                topPopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return o1.get("username").toString().compareTo(o2.get("username").toString());
                    }
                });
                listView.invalidateViews();
                break;
            case R.id.view_select_airq_de:
                topPopWindow.dismiss();
                //TODO 质量降序的实现
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String rank1 = o1.get("rank").toString();
                        String rank2 = o2.get("rank").toString();
                        Log.d(TAG, "compare: "+rank1+"-"+rank2);
                        int r1 = ((rank1 == "优")||(rank1.equals("优")))?4:((rank1 == "良")||(rank1.equals("良")))?3:((rank1 == "中")||(rank1.equals("中")))?2:((rank1 == "差")||(rank1.equals("差")))?1:0;
                        int r2 = ((rank2 == "优")||(rank2.equals("优")))?4:((rank2 == "良")||(rank2.equals("良")))?3:((rank2 == "中")||(rank2.equals("中")))?2:((rank2 == "差")||(rank2.equals("差")))?1:0;
                        Log.d(TAG, "compare: "+r1+"-"+r2);
                        return new Integer(r2).compareTo(new Integer(r1));
                    }
                });
                listView.invalidateViews();
                break;
            case R.id.view_select_airq_in:
                topPopWindow.dismiss();
                //TODO 质量降序的实现
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String rank1 = o1.get("rank").toString();
                        String rank2 = o2.get("rank").toString();
                        Log.d(TAG, "compare: "+rank1+"-"+rank2);
                        int r1 = ((rank1 == "优")||(rank1.equals("优")))?4:((rank1 == "良")||(rank1.equals("良")))?3:((rank1 == "中")||(rank1.equals("中")))?2:((rank1 == "差")||(rank1.equals("差")))?1:0;
                        int r2 = ((rank2 == "优")||(rank2.equals("优")))?4:((rank2 == "良")||(rank2.equals("良")))?3:((rank2 == "中")||(rank2.equals("中")))?2:((rank2 == "差")||(rank2.equals("差")))?1:0;
                        Log.d(TAG, "compare: "+r1+"-"+r2);
                        return new Integer(r1).compareTo(new Integer(r2));
                    }
                });
                listView.invalidateViews();
                break;
            case R.id.view_select_params:
                Toast.makeText(context,"从大到小排列",Toast.LENGTH_SHORT).show();
                topPopWindow.dismiss();
                showSingleTopRightPopMenu();
                break;
            case R.id.view_select_location:
                topPopWindow.dismiss();
                Log.d(TAG, "onClick: *******************点击了地点选择");
                Intent intent = new Intent();
                intent.setClass(mainUI, CityPickerActivity.class);
                //从popupwindow中跳转到其它的Activity中，需要使用当前视图的getContext()方法下的startActivity方法实现跳转
                //view.getContext();
                mActivity.startActivityForResult(intent, 1);
                break;
            case R.id.view_param_choh:
                topSinglePopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String choh1 = o1.get("params").toString().split("\n")[2];
                        String choh2 = o2.get("params").toString().split("\n")[2];
                        Double c2 = Double.parseDouble(choh2.substring(choh2.indexOf(":")+1,choh2.indexOf("m")));
                        Double c1 = Double.parseDouble(choh1.substring(choh1.indexOf(":")+1,choh1.indexOf("m")));
                        Log.d(TAG, "compare: "+c1+"==="+c2);
                        return c2.compareTo(c1);
                    }
                });
                listView.invalidateViews();
                break;
            case R.id.view_param_pm25:
                topSinglePopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String pm25_1 = o1.get("params").toString().split("\n")[3];
                        String pm25_2 = o2.get("params").toString().split("\n")[3];
                        Double c2 = Double.parseDouble(pm25_2.substring(pm25_2.indexOf(":")+1,pm25_2.indexOf("u")));
                        Double c1 = Double.parseDouble(pm25_1.substring(pm25_1.indexOf(":")+1,pm25_1.indexOf("u")));
                        Log.d(TAG, "compare: "+c1+"==="+c2);
                        return c2.compareTo(c1);
                    }
                });
                listView.invalidateViews();
                break;
            case R.id.view_param_pm10:
                topSinglePopWindow.dismiss();
                Collections.sort(realList,new Comparator<Map<String,Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        String pm10_1 = o1.get("params").toString().split("\n")[4];
                        String pm10_2 = o2.get("params").toString().split("\n")[4];
                        Double c2 = Double.parseDouble(pm10_2.substring(pm10_2.indexOf(":")+1,pm10_2.indexOf("u")));
                        Double c1 = Double.parseDouble(pm10_1.substring(pm10_1.indexOf(":")+1,pm10_1.indexOf("u")));
                        Log.d(TAG, "compare: "+c1+"==="+c2);
                        return c2.compareTo(c1);
                    }
                });
                listView.invalidateViews();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateView(String data) {
        Log.d(TAG, "updateView: data===================="+data);
        String location = "";
        Log.d(TAG, "updateView: result nums ===========clear pre"+mapList.size());
        List<Map<String,Object>> myList = new ArrayList<Map<String,Object>>();
        Map<Integer,String> myairMap = new HashMap<Integer,String>();
        for(int i = 0;i<mapList.size();i++){
            location = mapList.get(i).get("location").toString();
            String city = location.substring(location.indexOf("省")+1,location.indexOf("市")+1);
            Log.d(TAG, "updateView: *****************city===="+city);
            if(city.contains(data)){
                int j = 0;
                //包含data
                Log.d(TAG, "updateView: come in contains");
                myList.add(mapList.get(i));
                myairMap.put(j++,airMap.get(i));
            }
        }
        realList = myList;
        airMap = myairMap;
        Log.d(TAG, "updateView: result nums ===========clear post"+realList.size());
        if(realList.size() == 0){
            //显示没有数据的图片
            image_nodata.setVisibility(View.VISIBLE);
        }else{
            //销毁图片
            image_nodata.setVisibility(View.GONE);
        }

        //重新进行填充
        viewAdapter = new SimpleAdapter(context,realList,R.layout.pager_view_child,
                new String[]{"username","location","rank","time"},
                new int[]{R.id.view_username,R.id.view_location,R.id.view_rank,R.id.view_time});
        listView.setAdapter(viewAdapter);
    }
}