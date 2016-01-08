package com.example.renlei.myapplication.chart;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.renlei.myapplication.chart.config.LineChartConfig;
import com.example.renlei.myapplication.chart.data.BaseLineChartData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by renlei
 * DATE: 15-11-30
 * Time: 下午2:36
 * Email: lei.ren@renren-inc.com
 * 用于实现各种图形的
 */
public class ChartUtil {
    private Context mContext;
    private static ChartUtil chartUtil = new ChartUtil();
    public static ChartUtil getInstance(){
        return chartUtil;
    }
    private int mColor;
    /**
     * @param xValues  //xVals用来表示每个饼块上的内容,横坐标
     * @param yValues 用来表示封装每个饼块的实际数据，纵坐标
     *                具体用法参考 getTestPieChartData（）函数
     * @return
     */
    public PieData parsePieData(Context context,List<String>xValues,List<Entry>yValues) {
        this.mContext = context;
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*显示在比例图上*/);  //Quarterly Revenue 2014比例图的名字,可以为“”，此时不显示--renlei
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        Random random = new Random();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0;i<xValues.size();i++){
            /*switch (i){
                case 0:
                    colors.add(Color.rgb(205, 205, 205));
                    break;
                case 1:
                    colors.add(Color.rgb(114, 188, 223));
                case 2:
                    colors.add(Color.rgb(255, 123, 124));
                case 3:
                    colors.add(Color.rgb(57, 135, 200));
            }*/
            int r = random.nextInt(256);
            int g= random.nextInt(256);
            int b = random.nextInt(256);
            mColor = Color.rgb(r, g, b);
            colors.add(mColor);
        }
        // 饼图颜色

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float px = 2 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度   默认值为(metrics.densityDpi / 160f)

        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        return pieData;
    }

    public void initPieChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);//设置中间部分透明


        pieChart.setTransparentCircleRadius(55f);// 半透明圈，数字越大，透明的部分越多,55表示从55%的半径出开始办透明
        pieChart.setHoleRadius(50f);  //半径 //设置中间透明的地方的大小，当为0的时候中间没有空白，就是实心圆，数字越大中间的空白部分越大，默认为50--renlei

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
    public void getTestPieChartData(List<String>xValues,List<Entry>yValues){
        for (int i = 0; i < 4; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }
        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = 14;
        float quarterly2 = 14;
        float quarterly3 = 34;
        float quarterly4 = 38;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));
    }

    /**
     * 初始化linechart
     * @param lineChart
     * @param config
     */
    public void initLineChart(LineChart lineChart,LineChartConfig config){
        lineChart.setDescription(config.mDescription);
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.removeAllLimitLines();
        leftYAxis.setAxisMaxValue(config.yAxisMaxValue);
        leftYAxis.setAxisMinValue(config.yAxisMinValue);
        lineChart.setNoDataTextDescription(config.mNoDataTextDescription);
        lineChart.setMarkerView(config.mv);
        lineChart.setPinchZoom(config.mPinchZoomEnabled);
        lineChart.setScaleEnabled(config.mScaleEnable);
        lineChart.setDragEnabled(config.mDragEnabled);
        lineChart.getXAxis().setPosition(config.mXAxisPos);
        lineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        lineChart.getAxisRight().setEnabled(false);
        LineData lineData = new LineData();

        ArrayList xAxisValue = config.xAxisValues;
        List yAxisValueArray = config.yAxisValuesArray;
        ArrayList<LineDataSet>dataSets = new ArrayList<>();
        if (xAxisValue!=null&&yAxisValueArray!=null){
            for (int i = 0;i<yAxisValueArray.size();i++){
                BaseLineChartData baseLineChartData = (BaseLineChartData)yAxisValueArray.get(i);
                List<Float>yValuesFloat = baseLineChartData.yAxisValues;
                List<Entry>yValuesEntry = new ArrayList<>();
                for (int j = 0;j<yValuesFloat.size();j++){
                    yValuesEntry.add(new Entry(yValuesFloat.get(j),j));
                }
                LineDataSet lineDataSet = new LineDataSet(yValuesEntry,baseLineChartData.lineName);
                lineDataSet.setColor(baseLineChartData.color);
                lineDataSet.setLineWidth(baseLineChartData.mLineWidth);

                lineDataSet.setCircleColor(baseLineChartData.circleColor);
                lineDataSet.setDrawCircleHole(baseLineChartData.mDrawCircleHole);//如果true显示的点为实心，否则则为空心
                lineDataSet.setCircleSize(baseLineChartData.mCircleSize);


                lineDataSet.setValueTextSize(baseLineChartData.mValueTextSize);

                lineDataSet.setDrawFilled(baseLineChartData.mDrawFilled);
                lineDataSet.setFillAlpha(baseLineChartData.mFillAlpha);
                lineDataSet.setFillColor(baseLineChartData.mFillColor);//设置填充的颜色

                lineDataSet.setDrawCubic(baseLineChartData.mDrawCubic);
                dataSets.add(lineDataSet);
            }
            lineData = new LineData(xAxisValue,dataSets);
            lineChart.setData(lineData);
        }
    }


}
