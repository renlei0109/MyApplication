package viewutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.renlei.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by  renlei
 * DATE: 16/8/1
 * Time: 12:24
 */
public class GifView extends View {
    private int mGifResourceId;
    private boolean mPaused;
    private Movie mMovie;
    private long mStartTime;
    private final String GIFVIEW = "GifView";
    private int mCurrentFrame;//当前帧
    /**
     * 默认为1秒
     */
    private static final int DEFAULT_MOVIE_DURATION = 1000;
    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GifView, defStyleAttr, -1);
        mGifResourceId = typedArray.getResourceId(R.styleable.GifView_gif, -1);
        mPaused = typedArray.getBoolean(R.styleable.GifView_paused, false);
        typedArray.recycle();
        if (mGifResourceId != -1) {
            mMovie = Movie.decodeStream(getResources().openRawResource(mGifResourceId));
        }else {

            try {
                InputStream is = getResources().getAssets().open("gif2.gif");
                mMovie = Movie.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    /**
     * 设置gif图资源
     *
     * @param movieResId
     */
    public void setMovieResource(int movieResId) {
        this.mGifResourceId = movieResId;
        mMovie = Movie.decodeStream(getResources().openRawResource(
                mGifResourceId));
        requestLayout();
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
        requestLayout();
    }

    public Movie getMovie() {
        return mMovie;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int movieWidth = 0;
        int movieHeight = 0;
        if (mMovie!=null){
            movieHeight = mMovie.height();
            movieWidth = mMovie.width();
            Log.d(GIFVIEW, "movieHeight" + movieHeight + "width" + movieWidth);

        }
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + movieWidth + getPaddingRight();
            Log.d(GIFVIEW, "getPaddingLeft" + getPaddingLeft() + "movieWidth" + movieWidth+ "getPaddingRight" + getPaddingRight());

        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + movieHeight + getPaddingBottom();
            Log.d(GIFVIEW, "getPaddingTop" + getPaddingTop() + "movieHeight" + movieHeight + "getPaddingBottom" + getPaddingBottom());
        }
        setMeasuredDimension(width,height);
        Log.d(GIFVIEW, "height" + height + "width" + width);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMovie != null) {
            updateAnimationTime();
            drawMovieFrame(canvas);
            invalidate();
        }

    }

    private void updateAnimationTime(){
        long now = android.os.SystemClock.uptimeMillis();
        if (mStartTime == 0){
            mStartTime = now;
        }

        int dur = mMovie.duration();
        if (dur == 0){
            dur = DEFAULT_MOVIE_DURATION;
        }
        mCurrentFrame  = (int) ((now - mStartTime) % dur);

    }

    private void drawMovieFrame(Canvas canvas){
        if (mMovie==null){
            return;
        }
        mMovie.setTime(mCurrentFrame);
        mMovie.draw(canvas,0,0);
        canvas.restore();
    }
}
