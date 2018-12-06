package com.yx.aircheck;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mPbProgress;
    private Button mBtnDetecting;
    public static final int STATE_DETECTTED = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case STATE_DETECTTED:
                    mPbProgress.setVisibility(View.INVISIBLE);
                    mBtnDetecting.setEnabled(true);
                    String result = (String) msg.obj;
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("result", result);
                    MainActivity.this.startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mPbProgress = findViewById(R.id.pb_progress);
        mBtnDetecting = findViewById(R.id.btn_detecting);
        mPbProgress.setVisibility(View.INVISIBLE);
        mBtnDetecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetecting();
            }
        });
    }

    private void startDetecting() {
        mPbProgress.setVisibility(View.VISIBLE);
        mBtnDetecting.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.arg1 = STATE_DETECTTED;
                msg.obj = "差";
                mHandler.sendMessage(msg);
            }
        }.start();
    }
}
