package com.example.renlei.myapplication.anr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.lang.ref.SoftReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renlei
 * DATE: 16-5-6
 * Time: 下午3:13
 * Email: lei.ren@renren-inc.com
 */
public class TestANRActivity extends BaseActivity {
    @Bind(R.id.start_btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        ButterKnife.bind(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (long i = 0l; i < 99999999999999999l; i++) {
                    i++;
                }
            }
        });
    }
}
