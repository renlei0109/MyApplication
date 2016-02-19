package com.example.renlei.myapplication.chart;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.chart.config.PieChartConfig;
import com.example.renlei.myapplication.chart.data.BasePieChartData;
import com.example.renlei.myapplication.chart.util.RotateAnimation;
import com.example.renlei.myapplication.titlebar.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by renlei
 * DATE: 16-2-17
 * Time: 下午4:48
 * Email: lei.ren@renren-inc.com
 */
public class PieChartActivity extends BaseActivity implements RotateAnimation.InterpolatedTimeListener {
    private PieChart pieChart;
    private BasePieChartData mBasePieChartData;
    private PieChartConfig mPieChartConfig;
    private List<String> xValues;
    private List<Float> yValues;
    private int count = 6;
    private TextView mContentTV;
    private TextView mPercentContentTV;
    private String mContentStr;
    private String mPercentContentStr;
    private LinearLayout mContentLL;
    private boolean hasMeasure = false;
    private RelativeLayout homeRL;

    private TextView mHeadDescTV;
    private ImageView mHeadIV;
    private TextView mHeadNameTV;
    private ImageView mHeadHideIV;
    /**
     * TextNumber是否允许显示最新的数字。
     */
    private boolean enableRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piechart_activity_layout);
        pieChart = (PieChart) findViewById(R.id.piechart);
        mContentTV = (TextView) findViewById(R.id.piechart_select_item_content_tv);
        mContentLL = (LinearLayout) findViewById(R.id.piechart_select_item_content_ll);
        mPercentContentTV = (TextView) findViewById(R.id.piechart_select_item_percent_content_tv);
        homeRL = (RelativeLayout) findViewById(R.id.piechart_home_rl);
        mHeadDescTV = (TextView) findViewById(R.id.piechart_head_desc);
        mHeadIV = (ImageView) findViewById(R.id.piechart_head_icon_iv);
        mHeadNameTV = (TextView) findViewById(R.id.piechart_head_name);
        mHeadHideIV = (ImageView)findViewById(R.id.piechart_headhide_icon_iv);
        getData();
        /*RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)pieChart.getLayoutParams();
        layoutParams.height = layoutParams.width;
        Log.d("PieChartActivity oncreate","width"+layoutParams.width);
        pieChart.setLayoutParams(layoutParams);*/
        ViewTreeObserver vto = homeRL.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (hasMeasure == false) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pieChart.getLayoutParams();
                    layoutParams.height = pieChart.getMeasuredWidth();
                    Log.d("PieChartActivity onPreDraw", "width" + pieChart.getMeasuredWidth());
                    hasMeasure = true;
                }
                return true;
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        /*RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)pieChart.getLayoutParams();
        layoutParams.height = layoutParams.width;
        Log.d("PieChartActivity onCreateView","width"+layoutParams.width);*/
        return super.onCreateView(name, context, attrs);
    }

    private void getData() {
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        float sum = 0;
        for (int i = 0; i < count; i++) {
            Random random = new Random();
            xValues.add("renlei" + i);
            yValues.add(random.nextFloat() * 500);
            sum += yValues.get(i);
        }
        final float totalSum = sum;
        mBasePieChartData = new BasePieChartData(xValues, yValues);
        mPieChartConfig = new PieChartConfig(mBasePieChartData);
        mPieChartConfig.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.d("renlei onvalueselected", "entry" + e.getVal() + "dataindex" + dataSetIndex + "Highlight" + h.toString());
                //entry  Entry, xIndex: 0 val (sum): 4.0 dataindex0 Highlight Highlight, xIndex: 0, dataSetIndex: 0, stackIndex (only stacked barentry): -1
                Log.d("onValueSelected", "x :" + mPieChartConfig.basePieChartData.mXValues.get(h.getXIndex()) + " y :" + mPieChartConfig.basePieChartData.mYValues.get(h.getXIndex()).getVal());
                /*mContentTV.setText(Math.round(mPieChartConfig.basePieChartData.mYValues.get(h.getXIndex()).getVal()*10)/10f + "");
                mPercentContentTV.setText(Math.round(mPieChartConfig.basePieChartData.mYValues.get(h.getXIndex()).getVal()/totalSum*100*10)/10f+"%");*/
                mContentStr = Math.round(mPieChartConfig.basePieChartData.mYValues.get(h.getXIndex()).getVal() * 10) / 10f + "";
                mPercentContentStr = Math.round(mPieChartConfig.basePieChartData.mYValues.get(h.getXIndex()).getVal() / totalSum * 100 * 10) / 10f + "%";
                changeValue();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        ChartUtil.getInstance().initPieChart(pieChart, mPieChartConfig);
    }

    public void changeValue() {
        enableRefresh = true;
        RotateAnimation rotateAnim = null;
        float cX = mContentLL.getWidth() / 2.0f;
        float cY = mContentLL.getHeight() / 2.0f;
//        if (v == btnDecrease) {
//            number--;
//            rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_DECREASE);
//        } else if (v == btnIncrease) {
//            number++;
        rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_INCREASE);
//        }
        if (rotateAnim != null) {
            rotateAnim.setInterpolatedTimeListener(this);
            rotateAnim.setFillAfter(true);
            mContentLL.startAnimation(rotateAnim);
        }

        /**名字的动画**/
        AlphaAnimation nameAnimation = new AlphaAnimation(1.0f, 0f);
        nameAnimation.setDuration(500);
        mHeadNameTV.startAnimation(nameAnimation);
        nameAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHeadNameTV.setText("renlei,,,");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        AnimationSet headAnimationSet = new AnimationSet(true);
        AlphaAnimation headAlphaAnimation = new AlphaAnimation(1.0f,0f);
        TranslateAnimation headTraslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        headAnimationSet.addAnimation(headAlphaAnimation);
        headAnimationSet.addAnimation(headTraslateAnimation);
        headAnimationSet.setDuration(500);
        mHeadIV.startAnimation(headAnimationSet);

        AnimationSet headHideAnimationSet = new AnimationSet(true);
        TranslateAnimation headHideTraslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        headHideAnimationSet.addAnimation(headHideTraslateAnimation);
        headHideAnimationSet.setDuration(500);
        mHeadHideIV.startAnimation(headHideAnimationSet);



    }

    @Override
    public void interpolatedTime(float interpolatedTime) {
        // 监听到翻转进度过半时，更新txtNumber显示内容。
        if (enableRefresh && interpolatedTime > 0.5f) {
            mContentTV.setText(mContentStr);
            mPercentContentTV.setText(mPercentContentStr);
            enableRefresh = false;
        }
    }
}
