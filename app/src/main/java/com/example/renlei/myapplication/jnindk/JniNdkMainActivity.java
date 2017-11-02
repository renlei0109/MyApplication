package com.example.renlei.myapplication.jnindk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Time 2017/10/19.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class JniNdkMainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnindk_main_layout);
        findViewById(R.id.test_jni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JniNdkMainActivity.this,TestJniActivity.class));
            }
        });
    }
}
