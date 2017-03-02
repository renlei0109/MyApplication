package viewutil;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import com.example.renlei.myapplication.R;

/**
 * Time 2017/2/22.
 * User renlei
 * Email renlei@xiaomi.com
 * 实现水波纹的效果
 */

public class CircleWaveView extends View{
    private Context mContext;
    private Paint mPaint;
    private int mWaveColor;
    private float mWaveWidth;
    private int radius;
    private int mAnimationDuration = 1500;
    public CircleWaveView(Context context) {
        super(context);
        mContext = context;
        initView(null);
    }

    public CircleWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    public CircleWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY){
            width = (int)(widthSpec + getPaddingLeft() + getPaddingRight() + mWaveWidth);
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height = (int)(heightSpec + getPaddingTop() + getPaddingBottom() + mWaveWidth);
        }
        int circleWidth = Math.max(width,height);
        radius = (int)(Math.min(widthSpec,heightSpec)/2-mWaveWidth/2)/2;

        setMeasuredDimension(circleWidth,circleWidth);
        /*if(widthMode == MeasureSpec.AT_MOST && heightMode ==     MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        }else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(heightSpec,heightSpec);
        }else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpec,widthSpec);
        }*/




//        width = (int)(getPaddingLeft() + getPaddingRight() + width+ mWaveWidth/2);
//        setMeasuredDimension(width,width);//因为是圆形，强制其宽高一样
    }

    private void initView(AttributeSet attrs){
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleWaveView);
        mWaveColor = typedArray.getColor(R.styleable.CircleWaveView_wave_color, Color.BLUE);
        mWaveWidth = typedArray.getDimension(R.styleable.CircleWaveView_wave_width,1);
        Log.d("CircleWaveView","mWaveWidth"+mWaveWidth);
        mPaint = new Paint();
        // 抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(mWaveColor);
        mPaint.setStrokeWidth(mWaveWidth);
        // 设置空心
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAlpha(255);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        radius = (int)(getMeasuredWidth())/2;
        float x = getMeasuredWidth()/2;
        float y = getMeasuredHeight()/2;
        Log.d("CircleWaveView","x*y"+x+"--"+y+"radius"+radius);
        Log.d("CircleWaveView","w"+getMeasuredWidth()+"--"+"hei"+getMeasuredHeight());
        Log.d("CircleWaveView","getPaddingLeft"+getPaddingLeft()+"--"+"hei"+getMeasuredHeight());
        canvas.drawCircle(x,y,radius,mPaint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    /**
     * 实现圆向外扩散
     */
    public void startAnimation(){
//        CircleWaveView view = new CircleWaveView()
        try {
            Thread.sleep(10);
            postInvalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        /*//将圆变大
        ObjectAnimator scaleAnimation = new ScaleAnimation(1f ,2f,1f,2f,ScaleAnimation.RELATIVE_TO_SELF,ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(mAnimationDuration);
        scaleAnimation.setRepeatCount(Animation.INFINITE);*/

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "scaleX",
                1.0f, 2f);
//        anim1.setRepeatCount(1);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "scaleY",
                1.0f, 2f);
//        anim2.setRepeatCount(1);

        ObjectAnimator anim3 = ObjectAnimator.ofFloat(this, "alpha",
                1.0f, 0);
//        anim3.setRepeatCount(1);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(mAnimationDuration);
        alphaAnimation.setRepeatCount(Animation.INFINITE);

//        animationSet.addAnimation(anim1);
//        animationSet.addAnimation(alphaAnimation);
        animatorSet.play(anim1).with(anim2).with(anim3);
        animatorSet.setDuration(mAnimationDuration);
        animatorSet.start();
    }
}
