package viewutil;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.renlei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Time 2017/2/22.
 * User renlei
 * Email renlei@xiaomi.com
 * 实现水波纹的效果
 */

public class CircleWaveView2 extends View {
    private Context mContext;
    private Paint mPaint;
    private int mWaveColor;
    private float mWaveWidth;
    private int radius;
    private int mAnimationDuration = 1500;
    private int x;
    private int y;
    private final int CHANGE_COUNT = 20;//多少次改变完
    private boolean drawCircle = true;//判断是画圆，还是在快要结束的时候画弧形
    private int progress = 30;
    List<Circle> circles = new ArrayList<>();
    Canvas mCanvas;
    public CircleWaveView2(Context context) {
        super(context);
        mContext = context;
        initView(null);
    }

    public CircleWaveView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(attrs);
    }

    public CircleWaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("CircleWaveView", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = (int) (widthSpec + getPaddingLeft() + getPaddingRight() + mWaveWidth);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = (int) (heightSpec + getPaddingTop() + getPaddingBottom() + mWaveWidth);
        }
        int circleWidth = Math.max(width, height);
        radius = (int) (Math.min(widthSpec, heightSpec) / 2 - mWaveWidth / 2) / 2;

        setMeasuredDimension(circleWidth, circleWidth);
    }

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleWaveView);
        mWaveColor = typedArray.getColor(R.styleable.CircleWaveView_wave_color, Color.BLUE);
        mWaveWidth = typedArray.getDimension(R.styleable.CircleWaveView_wave_width, 1);
        Log.d("CircleWaveView", "mWaveWidth" + mWaveWidth);
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
        Log.d("CircleWaveView", "onDraw");

        super.onDraw(canvas);
        this.mCanvas = canvas;
        x = getMeasuredWidth() / 2;
        y = getMeasuredHeight() / 2;
        if (drawCircle){
            canvas.drawCircle(x, y, radius, mPaint);///这个是一直存在在那里的圆
        }else {
            Paint paint = new Paint();
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);//使用抗锯齿功能
                         paint.setColor(0xFFA4C739);    //设置画笔的颜色为绿色
                         paint.setStyle(Paint.Style.STROKE);//设置画笔类型为STROKE类型（个人感觉是描边的意思）
//            final RectF oval = new RectF(0+mWaveWidth/2, y-radius, getWidth()-mWaveWidth/2, y+radius);
            RectF oval = new RectF(x - radius, x - radius, x + radius, x + radius);

            /*final RectF oval = new RectF(10, 10, 100
                    , 100);;*/
            canvas.drawArc(oval, -230, progress * 360 / 100, false, mPaint);
        }
        Log.d("CircleWaveView", "onDraw " +"getAlpha："+mPaint.getAlpha()+" radius："+radius+" drawCircle："+drawCircle+"  progress："+progress);
        for (int i = 0; i < circles.size(); i++) {
            if (circles.get(i).paint.getAlpha()>0){
                Log.d("CircleWaveView", "onDraw----- " + i+"getAlpha"+circles.get(i).paint.getAlpha()+"radius"+circles.get(i).radius);
                circles.get(i).drawCircle(canvas);
            }
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    /**
     * 实现圆向外扩散
     */
    public void startAnimation() {
//        CircleWaveView view = new CircleWaveView()
        postInvalidate();
        AnimatorSet animatorSet = new AnimatorSet();
        /*//将圆变大
        ObjectAnimator scaleAnimation = new ScaleAnimation(1f ,2f,1f,2f,ScaleAnimation.RELATIVE_TO_SELF,ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(mAnimationDuration);
        scaleAnimation.setRepeatCount(Animation.INFINITE);*/

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "scaleX",
                1.0f, 2f);
        anim1.setRepeatCount(1);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(this, "scaleY",
                1.0f, 2f);
        anim2.setRepeatCount(1);

        ObjectAnimator anim3 = ObjectAnimator.ofFloat(this, "alpha",
                1.0f, 0);
        anim3.setRepeatCount(1);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(mAnimationDuration);
        alphaAnimation.setRepeatCount(Animation.INFINITE);

//        animationSet.addAnimation(anim1);
//        animationSet.addAnimation(alphaAnimation);
        animatorSet.play(anim1).with(anim2).with(anim3);
        animatorSet.setDuration(mAnimationDuration);
        animatorSet.start();
    }

    /**
     * 收到声音添加circle
     */
    public void addCircle() {
        final Circle circle = new Circle(x, y, mPaint, radius);
        circles.add(circle);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int everyRaduis = radius / CHANGE_COUNT;
                int everyAlpha = 255 / CHANGE_COUNT;
                for (int i = 0; i < CHANGE_COUNT; i++) {
                    try {
                        Thread.sleep(30);
                        if (i < CHANGE_COUNT-1){
                            circle.resetSizeRadiusAndAlpha(everyRaduis, everyAlpha,false);
                        }else {
                            circle.resetSizeRadiusAndAlpha(everyRaduis, everyAlpha,true);
                        }
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    EnterThread enterThread ;
    /**
     * 进入录音状态
     */
    public void enterRecordStatus(){
        drawCircle = true;
        enterThread = new EnterThread();
        mPaint.setColor(getResources().getColor(R.color.gateway_green));
        postInvalidate();
        enterThread.start();
    }

    class EnterThread extends Thread{
        private boolean stop = true;
        @Override
        public void run() {
            try {
                while (stop){
                    addCircle();
                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stopThread(){
            this.stop = false;
        }
    }


    /**
     * 退出录音状态
     */
    public void exitRecordStatus(boolean showAnimation){
        if (enterThread != null && enterThread.isAlive()){
            enterThread.stopThread();//关闭之前的自动创建的线程
        }
        mPaint.setColor(Color.GRAY);
        if (showAnimation){
            drawCircle = false;//让其去划圆弧
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (progress<100){
                        try {
                            Thread.sleep(20);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        progress +=2;
                        postInvalidate();
                    }
                    if (progress>=100){
                        drawCircle = true;
                        progress = 30;
                        postInvalidate();
                    }

                }
            }).start();
        }else {
            drawCircle = true;
            progress = 30;
            postInvalidate();
        }
//        mPaint.setAlpha(30);


    }


}
