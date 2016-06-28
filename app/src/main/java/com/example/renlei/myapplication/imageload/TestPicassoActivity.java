package com.example.renlei.myapplication.imageload;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/23
 * Time: 15:43
 */
public class TestPicassoActivity extends BaseActivity{
    ListView mlistview;
    ImageLoadAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_load_layout);
        mAdapter = new ImageLoadAdapter(ImageLoadAdapter.FROM_PICASSO,this);
        mlistview = (ListView)findViewById(R.id.image_load_lv);
        mlistview.setAdapter(mAdapter);
    }
}
