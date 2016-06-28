package com.example.renlei.myapplication.baseandroid.chapterthree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.baseandroid.chapterone.StorageActivity;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/5
 * Time: 12:30
 */
public class ChapterThreeActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_three_android_layout);
        findViewById(R.id.test_videoview_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_videoview_activity:
                startActivity(new Intent(this,VideoViewActivity.class));
                break;
        }
    }
}
