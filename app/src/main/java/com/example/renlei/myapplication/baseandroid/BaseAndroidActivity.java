package com.example.renlei.myapplication.baseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.baseandroid.chapterfour.TestBItmapActivity;
import com.example.renlei.myapplication.baseandroid.chapterone.ChapterOneActivity;
import com.example.renlei.myapplication.baseandroid.chapterthree.ChapterThreeActivity;
import com.example.renlei.myapplication.baseandroid.chaptertwo.ChapterTwoActivity;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/5
 * Time: 12:26
 */
public class BaseAndroidActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_android);
        findViewById(R.id.test_chapter_one_activity).setOnClickListener(this);
        findViewById(R.id.test_chapter_two_activity).setOnClickListener(this);
        findViewById(R.id.test_chapter_three_activity).setOnClickListener(this);
        findViewById(R.id.test_bitmap_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_chapter_one_activity:
                startActivity(new Intent(this,ChapterOneActivity.class));
                break;
            case R.id.test_chapter_two_activity:
                startActivity(new Intent(this, ChapterTwoActivity.class));
                break;
            case R.id.test_chapter_three_activity:
                startActivity(new Intent(this, ChapterThreeActivity.class));
                break;
            case R.id.test_bitmap_activity:
            startActivity(new Intent(this, TestBItmapActivity.class));
            break;
        }
    }
}
