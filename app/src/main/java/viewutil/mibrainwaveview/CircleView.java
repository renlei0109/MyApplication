package viewutil.mibrainwaveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.renlei.myapplication.R;

/**
 * Time 2017/3/3.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class CircleView extends View {
    private float mCenterX;
    private float mCenterY;
    private float mMaxRaduis;

    private Paint mPaint;
    private float mRadius;
    private boolean mUseRing = false;//标示是否使用环，如果用环则传true，否则则传false
    private int mColor;
    private float mStrokeWidth;
    private static final int DEFAULT_COLOR = Color.rgb(0, 116, 193);
    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public void setRadius(float mRadius) {
        Log.d("CircleView","setRadius"+mRadius);
        if (this.mRadius != mRadius) {
            this.mRadius = mRadius;
            reset();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        mCenterX = width * 0.5f;
        mCenterY = height * 0.5f;
        mMaxRaduis = Math.max(width, height) * 0.5f;
        mRadius = mMaxRaduis;
        setMeasuredDimension(width,height);
        Log.d("CircleView","onMeasure"+width+"*"+height);
//        setMeasuredDimension(width,height);
    }

    public void setUseRing(boolean mUseRing) {
        if (this.mUseRing != mUseRing) {
            this.mUseRing = mUseRing;
            reset();
            invalidate();
        }
    }

    public void setColor(int mColor) {
        if (this.mColor != mColor) {
            this.mColor = mColor;
            reset();
            invalidate();
        }
    }

    public void receiveVoice(double volume){

    }

    public void setStrokeWidth(float mStrokeWidth) {
        if (this.mStrokeWidth != mStrokeWidth) {
            this.mStrokeWidth = mStrokeWidth;
            reset();
            invalidate();
        }
    }

    private void reset() {
        if (mPaint != null)
            mPaint = null;

    }

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefault();
    }

    private void initDefault() {
        mColor = getResources().getColor(R.color.mi_brain_circle_view_green);
        mStrokeWidth = dip2px(1);
        mUseRing = false;
        mRadius = dip2px(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(mColor);
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(mUseRing ? mStrokeWidth : 0);
            mPaint.setStyle(mUseRing ? Paint.Style.STROKE : Paint.Style.FILL);
        }
        Log.d("CircleView","onDraw"+mCenterX+"y"+mCenterY);
        if (mRadius > dip2px(50)){
            canvas.drawCircle(mCenterX,mCenterY,dip2px(26),mPaint);
        }else {
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
        }
    }
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getRadius() {
        return mRadius;
    }


}