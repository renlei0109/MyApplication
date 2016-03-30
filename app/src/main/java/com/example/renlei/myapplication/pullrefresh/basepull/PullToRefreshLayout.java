package com.example.renlei.myapplication.pullrefresh.basepull;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.pullrefresh.listview.PullRefreshListView;
import com.example.renlei.myapplication.pullrefresh.listview.RefreshListView;

/**
 * Created by renlei
 * DATE: 16-3-26
 * Time: 下午5:33
 * Email: lei.ren@renren-inc.com
 */
public class PullToRefreshLayout extends FrameLayout {
    private Context mContext;

    private View mHeadView;
    private TextView mHeadTV;
    private ProgressBar mHeadPbar;
    private ImageView mHeadArrow;
    private int mHeadHeight;

    private View mFooterView;
    private TextView mFooterTV;
    private TextView mFooterPbar;
    private int mFooterHeight;

    private View mRefreshView;//中间存放数据的部分
    private Pullable mPullable;
    private int pullDownY;//下拉的距离
    private int pullUpY;//上拉的距离\


    private Animation mPullDownAnimation;
    private Animation mProgrebarAnimation;


    private static String TAG = "PullToRefresh";
    private boolean isLayout = false;
    private static final int PULL_TO_REFRESH = 1;//下拉刷新
    private static final int RELEASE_TO_REFRSH = 2;//松开刷新
    private static final int REFRESHING = 3;//正在刷新
    private static final int REFRESHING_SUCCESS = 4;//刷新成功

    private int mCurrentState = PULL_TO_REFRESH;//默认状态为下拉刷新状态
    private boolean isRefreshing = false;//标识是否正在刷新
    private int mPullDelta = 200;//允许下拉的最大距离
    private PullRefreshListener mPUllListener;

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        this.setOrientation(VERTICAL);
//        initHeadView();///
        initAnimation();

    }

    private void initHeadView() {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        mHeadView = inflater.inflate(R.layout.pull_to_refresh_linearlayout_head_view_layout, null);///这里不用进行初始化,因为在layout里面已经初始化了,如果在这里在进行一次初始化会有问题.
        mHeadPbar = (ProgressBar) mHeadView.findViewById(R.id.pull_ll_head_progress);
        mHeadTV = (TextView) mHeadView.findViewById(R.id.pull_ll_head_tv);
        mHeadArrow = (ImageView) mHeadView.findViewById(R.id.pull_ll_head_arrow);
//        mHeadView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//        mHeadHeight = mHeadView.getMeasuredHeight();
    }


    private int downY;

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent"+ev.getAction()+"");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "dispatchTouchEvent"+ev.getAction()+"ACTION_MOVE");
                break;
        }

        return super.dispatchTouchEvent(ev);
    }*/


    private void changeHeadState() {
        Log.d("renlei","mCurrentState"+mCurrentState);
        switch (mCurrentState) {
            case PULL_TO_REFRESH:
                mHeadTV.setText("下拉刷新....");
                mHeadPbar.clearAnimation();
                mHeadArrow.clearAnimation();
                mHeadPbar.setVisibility(GONE);
                mHeadArrow.setVisibility(VISIBLE);
                break;
            case RELEASE_TO_REFRSH:
                mHeadTV.setText("松开刷新....");
                mHeadPbar.setVisibility(GONE);
                mHeadArrow.setVisibility(VISIBLE);
                mHeadArrow.startAnimation(mPullDownAnimation);
                break;
            case REFRESHING:
                mHeadTV.setText("正在刷新....");
                mHeadPbar.setVisibility(VISIBLE);
                mHeadPbar.startAnimation(mProgrebarAnimation);
                mHeadArrow.clearAnimation();
                mHeadArrow.setVisibility(GONE);
                break;
            case REFRESHING_SUCCESS:
                mHeadTV.setText("刷新成功....");
                mHeadPbar.clearAnimation();
                mHeadArrow.clearAnimation();
                mHeadPbar.setVisibility(GONE);
                mHeadArrow.setVisibility(GONE);
                break;
        }
    }

    private void initAnimation() {
        mPullDownAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mPullDownAnimation.setDuration(500);
        mPullDownAnimation.setFillAfter(true);

        mProgrebarAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mProgrebarAnimation.setDuration(500);
        mProgrebarAnimation.setFillAfter(true);
        mProgrebarAnimation.setRepeatCount(Integer.MAX_VALUE);

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (((Pullable) mRefreshView).canPullRefresh()) {
                    Log.d(TAG, "onInterceptTouchEvent" + "MotionEvent.ACTION_MOVE");
                    return true;
                }


        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (((Pullable) mRefreshView).canPullRefresh()&&!isRefreshing) {
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                if (((Pullable) mRefreshView).canPullRefresh()&&!isRefreshing) {
                    if (pullDownY >= 0) {//头部最少为0不能进去了
                        pullDownY = ((int) event.getY() - downY) / 2;
                        Log.d("renlei", "ACTION_MOVE" + "pullDownY" + pullDownY);
                        if (pullDownY >= mPullDelta&&mCurrentState == PULL_TO_REFRESH) {///当下拉的距离超过头部时,mCurrentState == PULL_TO_REFRESH必须加上,不然会一直刷新
                            pullDownY = mPullDelta;
                            Log.d("renlei", "当下拉的距离超过头部时");
                            mCurrentState = RELEASE_TO_REFRSH;
                            changeHeadState();
                        }
                        requestLayout();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (((Pullable) mRefreshView).canPullRefresh()) {
                    if (mCurrentState == PULL_TO_REFRESH) {
                        Log.d("renlei", "让其回到原位");
                        pullDownY = 0;//让其回到原位
                        isRefreshing = false;
                        changeHeadState();
                    } else if (mCurrentState == RELEASE_TO_REFRSH&&!isRefreshing) {
                        Log.d("renlei", "让其跟head等高  正在刷新");
                        pullDownY = mPullDelta-50;//让其跟head等高
                        mCurrentState = REFRESHING;
                        isRefreshing = true;
                        changeHeadState();
                        if (mPUllListener!=null){
                            mPUllListener.onRefresh();
                        }
                    }
                    requestLayout();
                    return true;
                }
                break;


        }
        return super.onTouchEvent(event);
    }

    /**
     * 完成刷新
     */
    public void onRefreshCompleted(){

        mCurrentState = REFRESHING_SUCCESS;
        changeHeadState();
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCurrentState = PULL_TO_REFRESH;
                changeHeadState();
                pullDownY = 0;
                requestLayout();
                isRefreshing = false;
            }
        },1000);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout) {
            mHeadView = getChildAt(0);
            mRefreshView = getChildAt(1);
            mHeadHeight = mHeadView.getMeasuredHeight();
            isLayout = true;
            initHeadView();
        }

        mHeadView.layout(0, pullDownY + pullUpY - mHeadHeight, mHeadView.getMeasuredWidth(), pullDownY + pullUpY);
        Log.d("renlei", "pullDownY" + pullDownY + " pullUpY" + pullUpY + "mRefreshView.getMeasuredHeight()" + mRefreshView.getMeasuredHeight() + "mRefreshView.getMeasuredWidth()" + mRefreshView.getMeasuredWidth() + "mHeadHeight" + mHeadHeight);
        mRefreshView.layout(0, pullDownY + pullUpY, mRefreshView.getMeasuredWidth(), pullDownY + pullUpY + mRefreshView.getMeasuredHeight());
    }

    private void initFooterView() {

    }


    /**
     * 设置是否可以刷新
     *
     * @param mPullable
     */
    public void setmPullable(Pullable mPullable) {
        this.mPullable = mPullable;
    }

    public void setmPUllListener(PullRefreshListener mPUllListener) {
        this.mPUllListener = mPUllListener;
    }
}
