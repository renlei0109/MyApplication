package com.example.renlei.myapplication.baseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/5
 * Time: 12:30
 */
public class ChapterOneActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_one_android_layout);
        findViewById(R.id.test_storage_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_storage_activity:
                startActivity(new Intent(this,StorageActivity.class));
                break;
        }
    }
}
