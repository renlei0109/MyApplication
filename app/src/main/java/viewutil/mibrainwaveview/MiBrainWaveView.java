package viewutil.mibrainwaveview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.renlei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import viewutil.mibrainwaveview.CircleView;

/**
 * Time 2017/3/3.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class MiBrainWaveView extends RelativeLayout {
    private static final String TAG = "MiBrainWaveView";
    private CircleView mCircleView;
    private CircleView mCicleViewRing;
    private RelativeLayout mMiBrainListeningRl;
    private ImageView mMiBrainNormalIV;
    private static final float MAX_VOLUME = 80f;//假定声音的最大值是80
    private static final float MIN_VOLUME = 40f;//假定声音的最小的声音是40
    private static final float MIN_SCALE = 1.5f;
    private static final float DRAW_COUNT = 15;///分多少次画完
    public static final int MIN_RECEIVE_DELAY_TIME = 300;//最小接收的时间
    private static final int MSG_SET_RADIUS = 1;
    private static final int MSG_SCALE_RADIUS = 2;
    private long mLastReceiveTime = 0;
    private double mCurrentMaxVolume = 0;
    private float mEveryRadius = 0;//每次需要增加的半径


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SCALE_RADIUS:
                    AnimatorSet animatorSet = createAnimatorSet();
                    animatorSet.start();
                    break;
            }
        }
    };

    public MiBrainWaveView(Context context) {
        super(context);
    }

    public MiBrainWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MiBrainWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircleView = (CircleView) findViewById(R.id.mi_brain_circle_view);
        mMiBrainListeningRl = (RelativeLayout) findViewById(R.id.mi_brain_wave_view_listening_rl);
        mMiBrainNormalIV = (ImageView) findViewById(R.id.mi_brain_wave_view_normal_iv);
        mCicleViewRing = (CircleView) findViewById(R.id.mi_brain_circle_view_ring);
    }

    /*public void receiveVoice(double volume) {
        if (volume <= MIN_VOLUME)
            return;
        mCurrentMaxVolume = mCurrentMaxVolume > volume ? mCurrentMaxVolume : volume;
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastReceiveTime > MIN_RECEIVE_DELAY_TIME) {///防止多次快速收到，动画错乱
            mLastReceiveTime = currentTime;
            mFinalRadius = getRadius(mCurrentMaxVolume);

            if (mFinalRadius == -1)
                return;

            mEveryRadius = (mFinalRadius - mCircleView.getRadius()) / DRAW_COUNT;
            mHandler.sendEmptyMessage(MSG_SET_RADIUS);
        }

    }*/

    public void receiveVoice(double volume) {
//        mScaleStart = mCircleView.getRadius() / mCircleView.getMeasuredWidth();
        mScaleStart = 1;
        Log.d("MiBrainWaveView", "volume" + volume);

        if (volume <= MIN_VOLUME)
            return;

        mCurrentMaxVolume = mCurrentMaxVolume > volume ? mCurrentMaxVolume : volume;
        Log.d("MiBrainWaveView", "mCurrentMaxVolume" + mCurrentMaxVolume);

        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastReceiveTime > MIN_RECEIVE_DELAY_TIME) {///防止多次快速收到，动画错乱
            mLastReceiveTime = currentTime;
            mScaleEnd = getScaleRadius(mCurrentMaxVolume);
            mCurrentMaxVolume = 0;
            if (mScaleEnd == -1)
                return;
            mHandler.sendEmptyMessage(MSG_SCALE_RADIUS);
        }

    }


    /**
     * 根据声音的大小获取半径
     *
     * @param volume
     */
    private float getRadius(double volume) {
        if (mCircleView == null)
            return -1;
        if (mCircleView.getRadius() <= 0)
            return -1;

        float radius;
        float volumeDis = (float) volume / MAX_VOLUME;
        radius = mCircleView.getRadius() * (1 + volumeDis > 2 ? 2 : volumeDis);//最大半径为原来半径的3倍
        Log.d("MiBrainWaveView", "getScaleRadius" + radius + "----volumeDis" + volumeDis);
        return radius;
    }

    /**
     * 获取缩放比例
     *
     * @param volume
     * @return
     */
    private float getScaleRadius(double volume) {
        /*if (mCircleView == null)
            return -1;
        if (mCircleView.getRadius() <= 0)
            return -1;
        float endScale = (float) volume / MAX_VOLUME;
        return endScale > mScaleStart ? endScale : mScaleStart;*/
        if (mCircleView == null)
            return -1;
        if (mCircleView.getRadius() <= 0)
            return -1;
        mRadiusEnd = ((float) (volume / MAX_VOLUME) * getMeasuredHeight()/2);
        float endScale = mRadiusEnd / mCircleView.getRadius();
        return endScale > MIN_SCALE ? endScale : MIN_SCALE;
    }

    public void setRadius(float radius) {
        if (mCircleView == null)
            return;
        mCircleView.setRadius(radius);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        Log.d(TAG,"density"+getResources().getDisplayMetrics().density);
        return (int) (dpValue * scale + 0.5f);
    }

    private AnimatorSet createAnimatorSet() {
        Log.d(TAG, "createAnimators mScaleStart" + mScaleStart + "----mScaleEnd" + mScaleEnd);
        List<Animator> animators = new ArrayList<Animator>();
        animators.add(create(mCircleView, "scaleX", 0, 0, mScaleStart, mScaleEnd, true));
        animators.add(create(mCircleView, "scaleY", 0, 0, mScaleStart, mScaleEnd, true));
        animators.add(create(mCircleView, "alpha", 0, 0, 1f, 1f, true));
        AnimatorSet mAnimatorSet;

        mAnimatorSet = new AnimatorSet();
        mInterpolator = new DecelerateInterpolator(2);
        mAnimatorSet.playTogether(animators);
        mAnimatorSet.setInterpolator(mInterpolator);
        mAnimatorSet.setDuration(mDuration);

        mAnimatorSet.addListener(mAnimatorListener);
        return mAnimatorSet;
    }


    private static final int DURATION = 1000;
    public int mDuration = DURATION;
    private float mScaleStart;
    private float mScaleEnd = 0;//最终需要到达的半径
    private float mRadiusEnd;
    private Interpolator mInterpolator = new LinearInterpolator();


    public float getRadiusEnd() {
        Log.d(TAG,"getRadiusEnd"+mRadiusEnd);
        return mRadiusEnd;
    }

    private ObjectAnimator create(View target, String propertyName, int repeatCount, long delay, float from, float to, boolean backToOrigin) {
        ObjectAnimator animator;
        if (backToOrigin) {
            animator = ObjectAnimator.ofFloat(target, propertyName, from, to, from);
        } else {
            animator = ObjectAnimator.ofFloat(target, propertyName, from, to);
        }
//        animator = ObjectAnimator.ofFloat(target, propertyName, from, to);
        animator.setRepeatCount(repeatCount);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setStartDelay(delay);
        animator.setDuration(DURATION);
        return animator;
    }

    private final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animator) {
//            mIsStarted = true;
        }

        @Override
        public void onAnimationEnd(Animator animator) {
//            mIsStarted = false;
        }

        @Override
        public void onAnimationCancel(Animator animator) {
//            mIsStarted = false;
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    };

}
