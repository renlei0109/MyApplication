package com.example.renlei.myapplication.animation;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Created by renlei
 * DATE: 16-4-28
 * Time: 下午3:13
 * Email: lei.ren@renren-inc.com
 */
public class ValueAnimationActivity extends BaseActivity {
    private static final String TAG = "ValueAnimationActivity";
    private ImageView ivBall;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animation_layout);

        ivBall = (ImageView) findViewById(R.id.value_animation_iv);
        findViewById(R.id.value_animation_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run(ivBall);
            }
        });

    }


    /**
     * 15      *  对应Button的点击事件
     * 16      * @param view
     * 17
     */


    public void run(View view)


    {
        //设置自定义的TypeEvaluator，起始属性，终止属性

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(), new Point(0, 0), new Point(0, 0));
        //设置持续时间
        valueAnimator.setDuration(2000);
        //设置加速时间插值器
        valueAnimator.setInterpolator(new MyInperpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {    //设置监听器
            @Override


            public void onAnimationUpdate(ValueAnimator animation) {
                //将最新计算出的属性值设置给ImageView
                Point point = (Point) animation.getAnimatedValue();
                Log.d(TAG,"onAnimationUpdate"+"point.x"+point.x+"point.x"+point.y);
                ivBall.setX(point.x);
                ivBall.setY(point.y);

            }


        });

        //开启动画
        valueAnimator.start();

    }


    /**
     * 41      * 自定义时间插值器，这里实现了线性时间插值器
     * 42
     */

    class MyInperpolator implements TimeInterpolator


    {

        @Override
        public float getInterpolation(float input) {
            Log.d(TAG,"getInterpolation input:"+input);
            return input;

        }

    }


    /**
     * 53      * 实现的自己的TypeEvaluator
     * 54
     */


    class MyTypeEvaluator implements TypeEvaluator<Point> {


        @Override


        public Point evaluate(float fraction, Point startValue, Point endValue) {
            Point point = new Point();
            Log.d(TAG,"fraction"+fraction+"startValue"+startValue+"endValue"+endValue);
            point.x = startValue.x + fraction * 500;
            point.y = startValue.y + fraction * 500;

            return point;

        }


    }


    /**
     * 69      * 保存坐标信息
     * 70
     */


    class Point


    {
        float x;
        float y;

        public Point() {
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;

        }

    }


}