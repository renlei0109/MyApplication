package com.example.renlei.myapplication.jnindk;

import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Time 2017/10/19.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class TestJniActivity extends BaseActivity {
    static {
        System.loadLibrary("JniTest");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testjni_layout);
        findViewById(R.id.test_jni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainJni mainJni = new MainJni();
                mainJni.print("renlei from main");
            }
        });
    }
}
