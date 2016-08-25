package com.example.renlei.myapplication.badge;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by  renlei
 * DATE: 16/8/2
 * Time: 16:05
 * 测试红泡
 */
public class TestBadge extends BaseActivity{
    Button mBtn;
    int count = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_layout);
        mBtn = (Button) findViewById(R.id.send_badge);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadgeUtil.setBadgeCount(TestBadge.this,count++);
//                int badgeCount = 1;
//                ShortcutBadger.applyCount(context, badgeCount);
            }
        });
    }
}
