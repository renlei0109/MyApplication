package viewutil;

/**
 * Created by renlei
 * DATE: 16-1-22
 * Time: 下午7:19
 * Email: lei.ren@renren-inc.com
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.renlei.myapplication.R;

public class MySurfaceViews extends SurfaceView implements SurfaceHolder.Callback{
    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint mPaint;
    private Thread mThread;
    private int mText;
    private boolean isRuning = false;
    float radius = 20f;
    float x = 10f;
    public MySurfaceViews(Context context) {
        this(context, null);
    }

    public MySurfaceViews(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceViews(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.titlebar_bg_color));
        mPaint.setTextSize(80);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRuning){
                    mText ++;
                    Log.d("zyr", "running,mText:" + mText);
                    draw();
                    try {
                        Thread.sleep(100);//睡眠时间为1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void draw() {
        try
        {
            synchronized (mSurfaceHolder){
                // 获得canvas
                mCanvas = mSurfaceHolder.lockCanvas();
                if (mCanvas != null)
                {
                    // drawSomething..
                    /*mCanvas.drawColor(Color.BLACK);
                    mCanvas.drawText(mText+"",100,100,mPaint);*/
                    mCanvas.drawColor(Color.BLACK);//清屏操作，不然之前的图像会保留在屏幕上
                    x+=30;
                    mCanvas.translate(x, 200);
//                    mCanvas.drawCircle(0, 0, radius, mPaint);
                    mCanvas.drawText(mText+"",100,100,mPaint);
                }
            }

        } catch (Exception e)
        {
        } finally
        {
            if (mCanvas != null)
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRuning = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRuning = false;
    }
}