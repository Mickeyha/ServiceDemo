package com.example.mickey.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button mStartBtn;
    private Button mStopBtn;
    private Button mBindBtn;
    private Button mUnbindBtn;
    private Button mCallBtn;

    private MyService mMyService;
    private int mData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    private void initLayout(){
        mStartBtn = (Button) findViewById(R.id.startBtn);
        mStopBtn = (Button) findViewById(R.id.stopBtn);
        mBindBtn = (Button) findViewById(R.id.bindBtn);
        mUnbindBtn = (Button) findViewById(R.id.unbindBtn);
        mCallBtn = (Button) findViewById(R.id.callBtn);

        mStartBtn.setOnClickListener(mStartOnClickListener);
        mStopBtn.setOnClickListener(mStopOnClickListener);
        mBindBtn.setOnClickListener(mBindOnClickListener);
        mUnbindBtn.setOnClickListener(mUnBindOnClickListener);
        mCallBtn.setOnClickListener(mCallOnClickListener);
    }

    private View.OnClickListener mStartOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick Start Button.");
            mMyService = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            startService(it);
            mStopBtn.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener mStopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick Stop Button.");
            mMyService = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            stopService(it);
            mStopBtn.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener mBindOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick Bind Button.");
            mMyService = null;
            Intent it = new Intent(MainActivity.this, MyService.class);
            //用戶呼叫bindService()連結至服務
            bindService(it, mServiceConnection, BIND_AUTO_CREATE);
            mUnbindBtn.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener mUnBindOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick Unbind Button.");
            mMyService = null;
            unbindService(mServiceConnection);
            mUnbindBtn.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener mCallOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick Call Button.");
            if(mMyService != null) {
                Log.v(TAG, "mData = " + mData);
                mMyService.callService(mData);
                mData++;
            }
        }
    };

    //用戶須實做ServiceConnection以監視與服務之間的連線狀況
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        //Android在用戶端跟服務之間建立連線時會呼叫onServiceConnected以傳遞IBinder
        //用戶可以用IBinder與服務溝通
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() " + name.getClassName());
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mMyService = binder.getService();
            mMyService.registerCallback(mMyServiceCallback);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() " + name.getClassName());
            mMyService = null;
        }
    };

    private MyServiceCallback mMyServiceCallback = new MyServiceCallback() {
        @Override
        public void onMyServiceCallback(int data) {
            Log.v(TAG, "mMyServiceCallback.onMyServiceCallback = " + data);
        }
    };
}
