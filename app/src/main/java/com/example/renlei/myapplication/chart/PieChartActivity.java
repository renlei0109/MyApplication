package com.example.renlei.myapplication.chart;

import android.os.Bundle;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.chart.config.PieChartConfig;
import com.example.renlei.myapplication.chart.data.BasePieChartData;
import com.example.renlei.myapplication.titlebar.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by renlei
 * DATE: 16-2-17
 * Time: 下午4:48
 * Email: lei.ren@renren-inc.com
 */
public class PieChartActivity extends BaseActivity {
    private PieChart pieChart;
    private BasePieChartData mBasePieChartData;
    private PieChartConfig mPieChartConfig;
    private List<String> xValues;
    private List<Float> yValues;
    private int count = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piechart_activity_layout);
        pieChart = (PieChart) findViewById(R.id.piechart);
        getData();
    }

    private void getData() {
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Random random = new Random();
            xValues.add("renlei"+i);
            yValues.add(random.nextFloat()*500);
        }
        mBasePieChartData = new BasePieChartData(xValues,yValues);
        mPieChartConfig = new PieChartConfig(mBasePieChartData);
        ChartUtil.getInstance().initPieChart(pieChart,mPieChartConfig);
    }
}
