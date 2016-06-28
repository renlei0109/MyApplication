package com.example.renlei.myapplication.baseandroid.chapterfour;

import android.os.Bundle;
import android.widget.ListView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 14:29
 */
public class TestBItmapActivity extends BaseActivity{
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list_view_layout);
    }
}
