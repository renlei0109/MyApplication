package com.example.renlei.myapplication.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;


/**
 * Created by renlei
 * DATE: 16-4-28
 * Time: 下午7:21
 * Email: lei.ren@renren-inc.com
 */
public class TestHandlerActvity extends BaseActivity {
    private Handler mainHandler;
    Thread childThread;
    Button button;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        button = (Button) findViewById(R.id.start_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childThread.start();
            }
        });
        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int text = msg.what;
                button.setText(text + "");
            }
        };
        childThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
//                    Looper.prepare();
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(1000);
                        count++;
                        Message message = mainHandler.obtainMessage();
                        message.what = count;
                        mainHandler.sendMessage(message);
                    }

//                    Looper.loop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }


}
