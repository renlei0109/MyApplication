package com.example.renlei.myapplication.imageload.myimageload;

import android.os.Bundle;
import android.widget.ListView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.baseandroid.chapterfour.BitmapAdapter;
import com.example.renlei.myapplication.baseandroid.chapterfour.BitmapCache;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 14:29
 */
public class TestMyImageLoadActivity extends BaseActivity {
    ListView mListView;
    MyImageLoadAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list_view_layout);
        MemoryCache.init();
        mListView = (ListView) findViewById(R.id.common_lv);
        adapter = new MyImageLoadAdapter(this);
        mListView.setAdapter(adapter);
    }

}
