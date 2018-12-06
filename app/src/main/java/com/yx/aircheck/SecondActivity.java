package com.yx.aircheck;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by nan on 18-3-27.
 */

public class SecondActivity extends AppCompatActivity {

    private TextView mTvResult;
    private Intent mDetectIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mDetectIntent = getIntent();
        init();
    }


    private void init() {
        mTvResult = findViewById(R.id.tv_result);
        String result = mDetectIntent.getStringExtra("result");
        mTvResult.setText(result);
        analysisResult(result);
    }

    private void analysisResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            if (!result.equals("合格")) {
                showWarningDialog();
            }
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("警告")
                .setIcon(android.R.drawable.stat_sys_warning)
                .setMessage("超标")
                .setPositiveButton("净化", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvResult.setText("合格");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }
}
