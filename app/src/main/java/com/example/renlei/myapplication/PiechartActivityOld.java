package com.example.renlei.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.renlei.myapplication.chart.ChartUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;
import java.util.List;

public class PiechartActivityOld extends AppCompatActivity {
    private LineChart chart;
    private LineData lineData;
    private PieChart pieChart;
    private PieData pieData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChart) findViewById(R.id.chart);
        pieChart = (PieChart) findViewById(R.id.piechart);
        setLineChartData();
        initChartView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void setLineChartData() {
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();


        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(80.000f, 2); // 1 == quarter 2 ...
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(150.000f, 3); // 1 == quarter 2 ...
        valsComp1.add(c1e4);
        // and so on ...

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);

       /* Entry c3e1 = new Entry(130.000f, 0); // 0 == quarter 1
        valsComp2.add(c3e1);
        Entry c3e2 = new Entry(120.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c3e2);

        Entry c4e1 = new Entry(140.000f, 0); // 0 == quarter 1
        valsComp2.add(c4e1);
        Entry c4e2 = new Entry(130.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c4e2);*/



        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private void initChartView(){
        List<String>xPieCharts = new ArrayList<>();
        List<Entry>yPieCharts = new ArrayList<>();
        ChartUtil.getInstance().getTestPieChartData(xPieCharts,yPieCharts);
        pieData = ChartUtil.getInstance().parsePieData(this,xPieCharts,yPieCharts);
        ChartUtil.getInstance().initPieChart(pieChart,pieData);
    }
    /*private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);//设置中间部分透明


        pieChart.setTransparentCircleRadius(55f);// 半透明圈，数字越大，透明的部分越多,55表示从55%的半径出开始办透明
        pieChart.setHoleRadius(60f);  //半径 //设置中间透明的地方的大小，当为0的时候中间没有空白，就是实心圆，数字越大中间的空白部分越大，默认为50--renlei

        pieChart.setDescription("测试饼状图");//饼状图的名称
//        pieChart.setDescriptionPosition(100,100);//设置描述的位置，默认是getWidth() - mViewPortHandler.offsetRight() - 10,
                                                    //        getHeight() - mViewPortHandler.offsetBottom()-10

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
//        pieChart.setUnit(" €");
//        pieChart.setDrawUnitsInChart(true);

        // add a selection listener--选择某一部分时候的回调renlei
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.d("renlei onvalueselected", "entry" + e.toString() + "dataindex" + dataSetIndex + "Highlight" + h.toString());
                //entry  Entry, xIndex: 0 val (sum): 4.0 dataindex0 Highlight Highlight, xIndex: 0, dataSetIndex: 0, stackIndex (only stacked barentry): -1
            }

            @Override
            public void onNothingSelected() {

            }
        });
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);
        // undo all highlights
//      pieChart.highlightValues(null);//暂时未发现什么用--renlei
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);  //设置标示颜色（比例图）代表的位置，现在所处的位置--renlei
        mLegend.setForm(Legend.LegendForm.SQUARE);  //设置比例图的形状，默认是方形，也可以换成CIRCLE圆形等--renlei
        mLegend.setXEntrySpace(7f);//设置比例图两个之间的横向距离，当时竖着排列时没有效果该值
        mLegend.setYEntrySpace(5f);//设置比例图两个之间的纵向向距离，当时横着排列时没有效果该值

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    *//**
     * @param count 分成几部分
     * @param range
     *//*
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        *//**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         *//*
        float quarterly1 = 14;
        float quarterly2 = 14;
        float quarterly3 = 34;
        float quarterly4 = 38;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"*//*显示在比例图上*//*);  //Quarterly Revenue 2014比例图的名字,可以为“”，此时不显示--renlei
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }*/

}
