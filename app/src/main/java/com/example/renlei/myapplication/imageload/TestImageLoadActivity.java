package com.example.renlei.myapplication.imageload;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/6/23
 * Time: 16:02
 */
public class TestImageLoadActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load_layout);
        findViewById(R.id.test_glide_btn).setOnClickListener(this);
        findViewById(R.id.test_picasso_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_glide_btn:
                startActivity(new Intent(this,TestGlideActivity.class));
                break;
            case R.id.test_picasso_btn:
                startActivity(new Intent(this,TestPicassoActivity.class));
                break;

        }
    }
}
