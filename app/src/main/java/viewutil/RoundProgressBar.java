package viewutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.renlei.myapplication.R;

import util.Methods;

/**
 * Created by renlei
 * DATE: 16-1-26
 * Time: 下午4:41
 * Email: lei.ren@renren-inc.com
 * 自定义
 */
public class RoundProgressBar extends View {
    private Paint mPaint;
    private float mTextSize;
    private int mTextColor;
    private int mPbBgColor;
    private int mPbProgressColor;
    private float mPbWidth;
    private int progress = 50;
    private int mMax;
    private Rect mRect;
    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.RoundProgressBar_textSize, 12);
        mTextColor = typedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.BLACK);
        mPbBgColor = typedArray.getColor(R.styleable.RoundProgressBar_pb_bg_color, Color.GRAY);
        mPbProgressColor = typedArray.getColor(R.styleable.RoundProgressBar_pb_progress_color, Color.BLACK);
        mPbWidth = typedArray.getDimension(R.styleable.RoundProgressBar_pb_width, Methods.dp2sp(3, context));
        mMax = typedArray.getInt(R.styleable.RoundProgressBar_pb_max_progress, 100);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSpec;
        } else {
            width = getMeasuredWidth();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSpec;
        } else {
            height = getMeasuredHeight();
        }
        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("RoundProgressBar", "getMeasuredWidth" + getMeasuredWidth() + "getwidth" + getWidth());
        int center = getMeasuredWidth() / 2;
        int radius = (int) (center - mPbWidth / 2);//圆环的半径
        mPaint.setColor(mPbBgColor);
        mPaint.setStyle(Paint.Style.STROKE);//设置为空心圆
        mPaint.setStrokeWidth(mPbWidth);//圆环的宽度
        mPaint.setAntiAlias(true);//消除锯齿
        canvas.drawCircle(center, center, radius, mPaint);

        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setStrokeWidth(0);
        String des = progress + "%";
        mRect = new Rect();
        mPaint.getTextBounds(des,0,des.length(),mRect);
        int desWidthSize = (int) mPaint.measureText(des);
        Log.d("RoundProgressBar", "desWidthSize" + desWidthSize);
        Log.d("RoundProgressBar", "mRect" + mRect.width()+"*"+mRect.height());
        canvas.drawText(des, center - mRect.width()/2, center + mRect.height()/2, mPaint);

        Log.d("RoundProgressBar", "center" + center + "radius" + radius);
        mPaint.setColor(mPbProgressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mPbWidth);
        mPaint.setAntiAlias(true);
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, -90, progress * 360 / mMax, false, mPaint);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
