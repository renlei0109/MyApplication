package viewutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.renlei.myapplication.R;

/**
 * Created by renlei
 * DATE: 15-12-21
 * Time: 下午4:43
 * Email: lei.ren@renren-inc.com
 */
public class NumView extends View {
    private String mNumViewText;
    private int mNumViewTextColor;
    private int mNumViewTextSize;
    private Paint mPaint;
    private Rect mRect;

    public NumView(Context context) {
        this(context, null);
    }

    public NumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取自定义样式的属性
         */
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumView, defStyleAttr, 0);
        int n = typedArray.length();
        for (int i = 0; i < n; i++) {

            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.NumView_numViewText:
                    mNumViewText = typedArray.getString(attr);
                    break;
                case R.styleable.NumView_numViewTextColor:
                    mNumViewTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.NumView_numViewTextSize:
                    mNumViewTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        /**
         * 设置画笔
         */
        mPaint = new Paint();
        mPaint.setTextSize(mNumViewTextSize);
        mRect = new Rect();//文本的大小
        mPaint.getTextBounds(mNumViewText, 0, mNumViewText.length(), mRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mNumViewTextColor);
        /**
         * Draw the text, with origin at (x,y), using the specified paint. The
         * origin is interpreted based on the Align setting in the paint.
         *
         * @param text  The text to be drawn
         * @param x     The x-coordinate of the origin of the text being drawn
         * @param y     The y-coordinate of the baseline of the text being drawn
         * @param paint The paint used for the text (e.g. color, size, style)
         */
        canvas.drawText(mNumViewText, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);
    }

    /**
     * 重写之前先了解MeasureSpec的specMode,一共三种类型：
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {// at most
            mPaint.setTextSize(mNumViewTextSize);
            mPaint.getTextBounds(mNumViewText,0,mNumViewText.length(),mRect);
            width = (int)getPaddingLeft()+mRect.width()+getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {// at most
            mPaint.setTextSize(mNumViewTextSize);
            mPaint.getTextBounds(mNumViewText,0,mNumViewText.length(),mRect);
            height = (int)getPaddingTop()+mRect.height()+getPaddingBottom();
        }
        setMeasuredDimension(width,height);
    }
}
