package com.example.renlei.myapplication.chart.config;

import com.example.renlei.myapplication.chart.data.BasePieChartData;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

/**
 * Created by renlei
 * DATE: 16-2-17
 * Time: 下午3:20
 * Email: lei.ren@renren-inc.com
 * 初始化一个图表需要的一些数据，
 */
public class PieChartConfig {

    public static BasePieChartData basePieChartData;

    /**
     * flag that indicates if rotation is enabled or not
     */
    public static boolean mRotateEnabled = true;//设置该饼状图是否可以手动旋转
    /**
     * if true, the values inside the piechart are drawn as percent values
     */
    public static boolean mUsePercentValues = true;  //显示成百分比
    public static String mCenterText = "";//饼状图中间的文字
    /**
     * flag indicating if the x-labels should be drawn or not
     */
    public static boolean mDrawXLabels = false;//是否显示x轴的数据在图上
    private OnChartValueSelectedListener onChartValueSelectedListener;
    public PieChartConfig(BasePieChartData basePieChartData) {
        if (basePieChartData != null) {
            this.basePieChartData = basePieChartData;
        }
    }

    /**
     * indicates the size of the hole in the center of the piechart, default:
     * radius / 2
     */
    public static float mHoleRadiusPercent = 70f;//半径 //设置中间透明的地方的大小，当为0的时候中间没有空白，就是实心圆，数字越大中间的空白部分越大，默认为50--renlei
    /**
     * the radius of the transparent circle next to the chart-hole in the center
     */
    public static float mTransparentCircleRadiusPercent = 55f;// 半透明圈，数字越大，透明的部分越多,55表示从55%的半径出开始办透明
    public static boolean mHoloChartTransparent = true;//中间部分是否透明
    /**
     * description text that appears in the bottom right corner of the chart
     */
    public static String mDescription = "";// 并不是中间的描述，默认是在右下角的一个描述
    /**
     * if enabled, centertext is drawn
     */
    public static boolean mDrawCenterText = true;//如果为true饼状图中间可以添加文字
    /**
     * if true, the white hole inside the chart will be drawn
     */
    public static boolean mDrawHole = true;
    public void setOnChartValueSelectedListener(OnChartValueSelectedListener onChartValueSelectedListener){
        this.onChartValueSelectedListener = onChartValueSelectedListener;
    }

    public OnChartValueSelectedListener getOnChartValueSelectedListener() {
        return onChartValueSelectedListener;
    }
}
