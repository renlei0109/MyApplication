package com.example.renlei.myapplication.pullrefresh.basepull;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.pullrefresh.listview.RefreshListView;

/**
 * Created by renlei
 * DATE: 16-3-26
 * Time: 下午5:33
 * Email: lei.ren@renren-inc.com
 */
public class PullToRefreshLayout extends FrameLayout {
    private Context mContext;
    private static String TAG = "PullToRefresh";

    private View mHeadView;
    private TextView mHeadTV;
    private ProgressBar mHeadPbar;
    private int mHeadHeight;


    private View mFooterView;
    private TextView mFooterTV;
    private TextView mFooterPbar;
    private int mFooterHeight;

    private View mRefreshView;//中间存放数据的部分
    private Pullable mPullable;
    private int pullDownY;//下拉的距离
    private int pullUpY;//上拉的距离\

    private boolean isLayout = false;

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
        initHeadView();

    }

    private void initHeadView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mHeadView = inflater.inflate(R.layout.pull_to_refresh_linearlayout_head_view_layout, null);
        mHeadPbar = (ProgressBar) mHeadView.findViewById(R.id.pull_ll_head_progress);
        mHeadTV = (TextView) mHeadView.findViewById(R.id.pull_ll_head_tv);

        mHeadView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mHeadHeight = mHeadView.getMeasuredHeight();
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
//        Log.d(TAG, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (((Pullable) mRefreshView).canPullRefresh()) {
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                if (((Pullable) mRefreshView).canPullRefresh()) {
                    pullDownY = (int)event.getY()-downY;
                    requestLayout();
                    return true;
                }

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            // mesure child
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
//            getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
//                    MeasureSpec.UNSPECIFIED);
        }
//        getChildAt(0).measure(getMeasuredWidth(),
//                getMeasuredHeight());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout){
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                switch (i) {
                    case 0:
//                    mHeadView = getChildAt(i);
                        break;
                    case 1:
                        mRefreshView = getChildAt(i);
                        break;
                    case 2:
                        mFooterView = getChildAt(i);
                        break;
                }
            }
            isLayout = true;
        }

//        mHeadView.setPadding(0,paddingTop,0,0);
//        mRefreshView.setPadding(0,paddingTop,0,0);
        Log.d("renlei", "pullDownY" + pullDownY + " pullUpY" + pullUpY + "mRefreshView.getMeasuredHeight()" + mRefreshView.getMeasuredHeight() + "mRefreshView.getMeasuredWidth()" + mRefreshView.getMeasuredWidth() + "mHeadHeight" + mHeadHeight);
        mHeadView.layout(0, pullDownY + pullUpY - mHeadHeight, mHeadView.getMeasuredWidth(), pullDownY + pullUpY);
//        mHeadView.layout(0, 0, mHeadView.getMeasuredWidth(), pullDownY + pullUpY + mHeadHeight);
        mRefreshView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);


//        mRefreshView.layout(0, pullDownY + pullUpY+mHeadHeight, mRefreshView.getMeasuredWidth(), pullDownY + pullUpY + mRefreshView.getMeasuredHeight()+mHeadHeight);
        LayoutParams params = (FrameLayout.LayoutParams)mRefreshView.getLayoutParams();
        params.width = mRefreshView.getMeasuredWidth();
        params.height = mRefreshView.getMeasuredHeight();
        mRefreshView.setLayoutParams(params);
        mRefreshView.layout(0, pullDownY + pullUpY, mRefreshView.getMeasuredWidth(), pullDownY + pullUpY + mRefreshView.getMeasuredHeight());
        Log.d("renlei",mRefreshView.getLayoutParams().width+"height"+mRefreshView.getLayoutParams().height);
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
}
