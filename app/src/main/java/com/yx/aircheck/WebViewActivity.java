package com.yx.aircheck;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class WebViewActivity extends Activity implements View.OnClickListener{

<<<<<<< HEAD
    private String webURL = "http://1856o325q1.iok.la:13523/mydesign/viewSensorData?deviceID=";
=======
    private String webURL = "http://1856o325q1.iok.la:37325/mydesign/viewSensorData?deviceID=";
>>>>>>> add dynamic chart
    private WebView webView;
    private WebSettings webSettings;
    private TextView mTvTitle;
    private String deviceID;
    private String username;
    private ImageButton web_return;
    private ImageButton web_refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.webview);
        Intent intent = super.getIntent();
        deviceID = intent.getStringExtra("deviceID");
        username = intent.getStringExtra("username");
        webView = (WebView)findViewById(R.id.web);
        mTvTitle = (TextView)findViewById(R.id.tv_title_bar_title);
        mTvTitle.setText(R.string.air_display);
        // 刷新按钮的设置
        web_refresh = (ImageButton)findViewById(R.id.ib_power_devices_refrash);
        web_refresh.setVisibility(View.VISIBLE);
        web_refresh.setOnClickListener(this);
        webURL = webURL+deviceID+"&username="+username;
        Log.d("webview", "onCreate: "+webURL);
        webView.loadUrl(webURL);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ib_power_devices_refrash:
                webView.reload();
                break;
            default:
                break;
        }
    }
}
