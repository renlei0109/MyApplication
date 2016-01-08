package com.example.renlei.myapplication.chart.data;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renlei
 * DATE: 16-1-5
 * Time: 下午3:39
 * Email: lei.ren@renren-inc.com
 */
public class BaseLineChartData {
    public List<Float> yAxisValues = new ArrayList<>();
    public int color = Color.BLACK;
    public int circleColor = Color.BLACK;
    public float mValueTextSize = 9f;//dp
    public String lineName = "renlei line";//这条线段的名字
    /** if true, the data will also be drawn filled */
    public boolean mDrawFilled = false;
    /** if true, cubic lines are drawn instead of linear */
    public boolean mDrawCubic = false;
    /** transparency used for filling line surface */
    public int mFillAlpha = 85;//填充透明度
    /** the color that is used for filling the line surface */
    public int mFillColor = Color.BLUE;//填充色
    /** the width of the drawn data lines */
    public float mLineWidth = 1.5f;
    /** the radius of the circle-shaped value indicators */
    public float mCircleSize = 3f;
    public boolean mDrawCircleHole = false;//如果false显示的点为实心，否则则为空心
    public BaseLineChartData(ArrayList<Float> yAxisValues) {
        this.yAxisValues = yAxisValues;
    }

    public BaseLineChartData() {
    }
}
