package com.yx.aircheck.pager;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yx.aircheck.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.yx.aircheck.ScanActivity;
import com.yx.aircheck.base.TabBasePager;

public class CheckPage extends TabBasePager implements OnClickListener {

    private ImageButton mLivableMenu;

    private TextView mTemperature;// 温度
    private TextView mHumidity;// 湿度
    private TextView mAirQuality;// 空气质量
    private TextView mIllumination;// 光照
    private TextView mNoise;// 噪音

    // 标题栏
    private TextView mTvTitle;

    public CheckPage(Context context) {
        super(context);

    }

    @Override
    public View initView() {

        View view = View.inflate(mainUI, R.layout.pager_check, null);
        // 标题栏
        mTvTitle = (TextView) view.findViewById(R.id.tv_title_bar_title);

        mTemperature = (TextView) view.findViewById(R.id.tv_livablepage_temp);
        mHumidity = (TextView) view.findViewById(R.id.tv_livablepage_humidity);
        mAirQuality = (TextView) view.findViewById(R.id.tv_livablepage_air_quality);
//        mIllumination = (TextView) view.findViewById(R.id.tv_livablepage_illumination);
//        mNoise = (TextView) view.findViewById(R.id.tv_livablepage_isound);

        mLivableMenu = (ImageButton) view.findViewById(R.id.ib_title_bar_livable_menu);
        mLivableMenu.setVisibility(View.VISIBLE);
        mLivableMenu.setOnClickListener(this);

        return view;

    }

    @Override
    public void initData() {

        mTvTitle.setText(mainUI.getString(R.string.check));//检测
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
