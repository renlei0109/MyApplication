package com.example.renlei.myapplication.chart.config;

import android.content.Context;
import android.graphics.Color;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.chart.data.BaseLineChartData;
import com.example.renlei.myapplication.chart.view.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;

import java.util.ArrayList;

/**
 * Created by renlei
 * DATE: 16-1-4
 * Time: 下午5:17
 * Email: lei.ren@renren-inc.com
 * 用来初始化linechart的一下信息
 */
public class LineChartConfig {
    private Context mContext;
    public float yAxisMaxValue = 100f;
    public float yAxisMinValue = -50f;
    public String mDescription;// 图表的描述
    public String mNoDataTextDescription = "no data";// 没有文案时的描述
    public ArrayList<String>xAxisValues = new ArrayList<>();//x坐标的值，必须有的
    public ArrayList<BaseLineChartData>yAxisValuesArray = new ArrayList<>();//y轴的集合





    public LineChartConfig(Context mContext) {
        this.mContext = mContext;
        mv = new MyMarkerView(mContext, R.layout.custom_marker_view);//高粱显示的样式
    }
    public MyMarkerView mv = null;
    /**
     * the size of the value-text labels
     */

    /**
     * if true, dragging is enabled for the chart
     */
    public boolean mDragEnabled = true;

    public boolean mScaleEnable = true;//是否可以进行缩放

    /**
     * flag that indicates if pinch-zoom is enabled. if true, both x and y axis
     * can be scaled with 2 fingers, if false, x and y axis can be scaled
     * separately
     */
    public boolean mPinchZoomEnabled = true;
    public XAxis.XAxisPosition mXAxisPos = XAxis.XAxisPosition.BOTTOM;


}
