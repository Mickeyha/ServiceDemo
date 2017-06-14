package com.example.mickey.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by MICKEY on 2017/6/13.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";
    private MyServiceCallback mCallback;

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    private LocalBinder mLocalBinder = new LocalBinder();

    public void registerCallback(MyServiceCallback callback) {
        Log.v(TAG, "registerCallback");
        mCallback = callback;
    }

    public void unregisterCallback(MyServiceCallback callback) {
        Log.v(TAG, "unregisterCallback");
        mCallback = null;
    }

    public void callService(int data) {
        Log.v(TAG, "callService");
        if (mCallback != null) {
            mCallback.onMyServiceCallback(data + 1);
        }
    }

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mLocalBinder;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }
}
