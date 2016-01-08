package com.example.renlei.myapplication.chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.chart.config.LineChartConfig;
import com.example.renlei.myapplication.chart.data.BaseLineChartData;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {
    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        mLineChart = (LineChart) findViewById(R.id.line_chart_view);


        LineChartConfig config = new LineChartConfig(getApplicationContext());

        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            xValues.add("" + (i));
        }
        config.xAxisValues = xValues;


        for (int j = 0; j < 3; j++) {//循环产生三条曲

            BaseLineChartData baseLineChartData = new BaseLineChartData();

            ArrayList<Float> yValues = new ArrayList<>();
            for (int i = 0; i < 12; i++) {//注意y的数据不能比x的多否则会One or more of the DataSet Entry arrays are longer than the x-values array of this ChartData object.
                yValues.add((float) (Math.random() * 100 - 20));
            }
            baseLineChartData.yAxisValues = yValues;
            baseLineChartData.mDrawFilled = true;
            config.yAxisValuesArray.add(baseLineChartData);
        }


        ChartUtil.getInstance().initLineChart(mLineChart, config);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
