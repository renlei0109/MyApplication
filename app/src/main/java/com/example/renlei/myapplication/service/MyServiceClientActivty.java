package com.example.renlei.myapplication.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-3-7
 * Time: 下午4:14
 * Email: lei.ren@renren-inc.com
 */
public class MyServiceClientActivty extends BaseActivity {
    private Button bindBtn;
    private Button unbindBtn;
    private String SERVICE_ACTION = "com.renlei.MyService";

    private static final int SEND_MESSAGE_CODE = 0x0001;
    private static final int RECEIVE_MESSAGE_CODE = 0x0002;
    //serviceMessenger表示的是Service端的Messenger，其内部指向了MyService的ServiceHandler实例
    //可以用serviceMessenger向MyService发送消息
    private Messenger serviceMessenger = null;
    //clientMessenger是客户端自身的Messenger，内部指向了ClientHandler的实例
    //MyService可以通过Message的replyTo得到clientMessenger，从而MyService可以向客户端发送消息，
    //并由ClientHandler接收并处理来自于Service的消息
    private Messenger clientMessenger = new Messenger(new ClientHandler());
    private boolean isBound = false;

    //客户端用ClientHandler接收并处理来自于Service的消息
    private class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d("MyServiceClientActivty", "ClientHandler -> handleMessage");
            if (msg.what == RECEIVE_MESSAGE_CODE) {
                Bundle data = msg.getData();
                if (data != null) {
                    String str = data.getString("msg");
                    Log.d("MyServiceClientActivty", "客户端收到Service的消息: " + str);

                }
                /*serviceMessenger = msg.replyTo;
                if (serviceMessenger!=null){

                }*/
            }
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //客户端与Service建立连接
            Log.d("MyService", "客户端 onServiceConnected");
            serviceMessenger = new Messenger(service);
            Message msg = Message.obtain();
            msg.what = SEND_MESSAGE_CODE;
            Bundle bundle = new Bundle();
            bundle.putString("msg", "这里是客户端");
            msg.setData(bundle);
            msg.replyTo = clientMessenger;
            try {
                Log.d("MyService", "客户端向service发送信息");
                serviceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //客户端与Service失去连接
            serviceMessenger = null;
            isBound = false;
            Log.d("DemoLog", "客户端 onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myserviceclient_layout);
        Log.d("MyServiceClientActivty", "android.os.Process.myPid();"+android.os.Process.myPid());


        bindBtn = (Button) findViewById(R.id.bind_btn);
        unbindBtn = (Button) findViewById(R.id.unbind_btn);
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBound){
                    Intent intent = new Intent();
                    intent.setAction(SERVICE_ACTION);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);

                    PackageManager packageManager = getPackageManager();
                    ResolveInfo info = packageManager.resolveService(intent,0);
                    if (info!=null){
                        String packname = info.serviceInfo.packageName;
                        String serviceName = info.serviceInfo.name;

                        ComponentName componentName = new ComponentName(packname,serviceName);
                        intent.setComponent(componentName);
                        Log.d("MyServiceClientActivty", "客户端调用bindService方法");
                        bindService(intent,connection,BIND_AUTO_CREATE);

                    }
                }
            }
        });

        unbindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBound){
                    Log.d("MyServiceClientActivty", "客户端调用unbindService方法");
                    unbindService(connection);
                }
            }
        });
    }
}
