package com.example.renlei.myapplication.chart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.chart.util.RotateAnimation;

public class ActRotate extends Activity implements View.OnClickListener, RotateAnimation.InterpolatedTimeListener {
    private Button btnIncrease, btnDecrease;
    private TextView txtNumber;
    private int number;  
    /** TextNumber是否允许显示最新的数字。 */  
    private boolean enableRefresh;  
    private LinearLayout mContentLL;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.actrotate_activity_layout);
  
        btnIncrease = (Button) findViewById(R.id.btnIncrease);  
        btnDecrease = (Button) findViewById(R.id.btnDecrease);  
        txtNumber = (TextView) findViewById(R.id.piechart_select_item_content_tv);
        mContentLL = (LinearLayout) findViewById(R.id.piechart_select_item_content_ll);
        btnIncrease.setOnClickListener(this);  
        btnDecrease.setOnClickListener(this);  
  
        number = 3;  
        txtNumber.setText(Integer.toString(number));
    }  
  
    public void onClick(View v) {  
        enableRefresh = true;  
        RotateAnimation rotateAnim = null;  
        float cX = mContentLL.getWidth() / 2.0f;
        float cY = mContentLL.getHeight() / 2.0f;
        if (v == btnDecrease) {  
            number--;  
            rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_DECREASE);  
        } else if (v == btnIncrease) {  
            number++;  
            rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_INCREASE);  
        }  
        if (rotateAnim != null) {  
            rotateAnim.setInterpolatedTimeListener(this);  
            rotateAnim.setFillAfter(true);
            mContentLL.startAnimation(rotateAnim);
        }  
    }  
  
    @Override  
    public void interpolatedTime(float interpolatedTime) {  
        // 监听到翻转进度过半时，更新txtNumber显示内容。  
        if (enableRefresh && interpolatedTime > 0.5f) {  
            txtNumber.setText(Integer.toString(number));  
            Log.d("ANDROID_LAB", "setNumber:" + number);
            enableRefresh = false;  
        }  
    }  
}  