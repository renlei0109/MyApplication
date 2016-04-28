package com.example.renlei.myapplication.animation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-4-28
 * Time: 下午3:08
 * Email: lei.ren@renren-inc.com
 */
public class AnimationMainActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main_layout);
        findViewById(R.id.test_value_anmation_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_value_anmation_activity:
                startActivity(new Intent(this,ValueAnimationActivity.class));
                break;
        }
    }
}
