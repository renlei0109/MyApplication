package com.example.renlei.myapplication.chart.data;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Methods;

/**
 * Created by renlei
 * DATE: 16-2-17
 * Time: 下午3:20
 * Email: lei.ren@renren-inc.com
 * 图表的基本设置以及数据
 */
public class BasePieChartData {
    public List<String>mXValues = new ArrayList<>();
    public List<Entry>mYValues = new ArrayList<>();
    public static PieData mPieData = new PieData();

    /** the space in degrees between the chart-slices, default 0f */
    public static float mSliceSpace = 0f;
    /** indicates the selection distance of a pie slice */
    public static float mShift = 18f;//选中时多出的长度　原来默认的是18f

    public static float mValueTextSize = 8f;
    public static int mValueTextColor = Color.WHITE;
    /**
     * custom formatter that is used instead of the auto-formatter if set
     */
    public static transient ValueFormatter mValueFormatter = new PercentFormatter();

    public static List<Integer>mColors;
    private int mColor;
    public BasePieChartData(List<String> xValues, List<Float> yValues){
        if (xValues.size()!=yValues.size()){
            Log.e("BasePieChartData","xvalues length must be equals yvalues length");
        }else {
            for (int i =0;i<xValues.size();i++){
                this.mXValues.add(xValues.get(i));
                this.mYValues.add(new Entry(yValues.get(i),i));
            }
            PieDataSet dataSet = new PieDataSet(this.mYValues,"");
            dataSet.setSliceSpace(mSliceSpace);//设置个饼状图之间的距离


            Random random = new Random();
            ArrayList<Integer> colors = new ArrayList<Integer>();
            for (int i = 0;i<xValues.size();i++){
                int r = random.nextInt(256);
                int g= random.nextInt(256);
                int b = random.nextInt(256);
                mColor = Color.rgb(r, g, b);
                colors.add(mColor);
            }
            // 饼图颜色
            dataSet.setColors(colors);

            dataSet.setSelectionShift(mShift); // 选中态多出的长度   默认值为(metrics.densityDpi / 160f)

            mPieData = new PieData(xValues,dataSet);
            mPieData.setValueTextColor(mValueTextColor);
            mPieData.setValueTextSize(mValueTextSize);
            mPieData.setValueFormatter(mValueFormatter);
            Log.d("BasePieChartData","BasePieChartData　初始化完成");
        }
    }

}
