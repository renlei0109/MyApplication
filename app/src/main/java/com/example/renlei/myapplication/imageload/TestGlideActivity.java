package com.example.renlei.myapplication.imageload;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by  renlei
 * DATE: 16/6/23
 * Time: 15:43
 */
public class TestGlideActivity extends BaseActivity{
    ListView mlistview;
    ImageLoadAdapter mAdapter;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_load_layout);
        mAdapter = new ImageLoadAdapter(ImageLoadAdapter.FROM_GLIDE,this);
        mlistview = (ListView)findViewById(R.id.image_load_lv);
        iv = (ImageView) findViewById(R.id.image_load_iv);
        Glide.with(this).load("http://ww1.sinaimg.cn/mw690/730d3ca2jw1f553slvyhig205m09ye8b.gif").placeholder(R.mipmap.ic_launcher).error(R.mipmap.default_head_image).into(iv);
        mlistview.setAdapter(mAdapter);
    }
}
