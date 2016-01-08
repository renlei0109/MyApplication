package com.example.renlei.myapplication.titlebar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.renlei.myapplication.R;

/**
 * Created by renlei
 * DATE: 16-1-6
 * Time: 下午4:17
 * Email: lei.ren@renren-inc.com
 */
public class TestTitleBarActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_titlebar_test);
        findViewById(R.id.title_bar_test_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestTitleBarActivity.this,TestTitleBarActivity2.class));
//                startActivity(TestTitleBarActivity.this,new Intent(TestTitleBarActivity.this,TestTitleBarActivity2.class));
            }
        });
        setTitle("test1");
    }

    @Override
    public View getMiddleView(Context context, ViewGroup parentView) {
        return super.getMiddleView(context, parentView);
    }

    @Override
    public View getRightView(Context context, ViewGroup parentView) {
        return TitleBarUtil.getLeftBackBtn(context);
    }
}
