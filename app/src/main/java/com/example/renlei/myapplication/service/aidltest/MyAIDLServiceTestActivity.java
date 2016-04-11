package com.example.renlei.myapplication.service.aidltest;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-4-8
 * Time: 下午5:03
 * Email: lei.ren@renren-inc.com
 */
public class MyAIDLServiceTestActivity extends BaseActivity{
    private IMyService myAIDLService;
    private ServiceConnection serviceConnection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAIDLService = (IMyService)service;
            try {
                new AlertDialog.Builder(MyAIDLServiceTestActivity.this).setMessage(
                        myAIDLService.getValue()).setPositiveButton("确定", null)
                        .show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_view);
        bindService(new Intent(MyAIDLServiceTestActivity.this,MyAIDLService.class),serviceConnection, Context.BIND_AUTO_CREATE);

    }
}
