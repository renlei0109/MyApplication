package com.example.renlei.myapplication.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.renlei.myapplication.R;

/**
 * Created by renlei
 * DATE: 16-3-24
 * Time: 上午11:38
 * Email: lei.ren@renren-inc.com
 */
public class PullRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private View mHeadView;
    private View mFooterView;
    private ImageView mHeadArrowIV;//头部的箭头
    private ImageView mHeadProgressBarIV;//头部的progressbar
    private TextView mHeadTV;

    private Context mContext;
    private int mHeadHeight;
    private int mFirstVisiblePos;
    private static int PULL_TO_REFRESH = 1;
    private static int RELEASE_TO_REFRESH = 2;
    private static int REFRESHING = 3;
    private static int REFRESH_COMPLETED = 4;
    private int mCurrentState = PULL_TO_REFRESH;
    private Animation downAnimation;
    private Animation upAnimation;
    private Animation progressAnimation;
    public PullRefreshListView(Context context) {
        this(context, null);
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initHeaderView();
        initAnimation();
        this.setOnScrollListener(this);
    }


    private void initHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mHeadView = inflater.inflate(R.layout.pull_to_refresh_head_view_layout, null);
        mHeadTV = (TextView) mHeadView.findViewById(R.id.head_tv);
        mHeadProgressBarIV = (ImageView) mHeadView.findViewById(R.id.head_progress);
        mHeadArrowIV = (ImageView) mHeadView.findViewById(R.id.head_arrow);
        mHeadView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mHeadHeight = mHeadView.getMeasuredHeight();
        mHeadView.setPadding(0, -mHeadHeight, 0, 0);
        this.addHeaderView(mHeadView);

    }
    private void initAnimation(){
        downAnimation = new RotateAnimation(90,270,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        downAnimation.setDuration(500);
//        downAnimation.setRepeatCount(1);
        downAnimation.setFillAfter(true);

        upAnimation = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);


        progressAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        progressAnimation.setDuration(500);
        progressAnimation.setRepeatCount(Integer.MAX_VALUE);
        progressAnimation.setFillAfter(false);

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private float downY;
    private float moveY;
    private float moveDis;//移动的距离
    private float paddingTop;//headView paddingTop的距离

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                moveDis = ev.getY() - downY;

                paddingTop = -mHeadHeight + moveDis/2;//除以２相当于增大了滑动系数
                if (mFirstVisiblePos == 0&&mCurrentState!=REFRESHING&&-mHeadHeight<paddingTop) {
                    /*if (paddingTop < 0 && mCurrentState == PULL_TO_REFRESH) {
                        Log.d("PullRefreshListView","paddingTop < 0 && mCurrentState == PULL_TO_REFRESH");
                        refreshHeadView();
                        mCurrentState = PULL_TO_REFRESH;

                    } else*/ if (paddingTop >= 0 && mCurrentState == PULL_TO_REFRESH) {
                        Log.d("PullRefreshListView", "paddingTop >= 0 && mCurrentState == PULL_TO_REFRESH");
                        mCurrentState = RELEASE_TO_REFRESH;
                        refreshHeadView();

                    } else if (paddingTop < 0
                            && mCurrentState == RELEASE_TO_REFRESH) { // 没有显示完全
                        Log.d("PullRefreshListView", "paddingTop < 0\n" +
                                "                            && mCurrentState == RELEASE_TO_REFRESH");

                        mCurrentState = PULL_TO_REFRESH;
                        refreshHeadView();
                    }
                    if (paddingTop<100){///只允许头部往下多拉取100
                        mHeadView.setPadding(0, (int) paddingTop, 0, 0);
                    }
                    return true;
                }

                /*moveDis = (ev.getY() - downY)/2;
                paddingTop = -mHeadHeight + moveDis;
                if (mFirstVisiblePos == 0
                        && -mHeadHeight < paddingTop) {

                     if (mCurrentState == PULL_TO_REFRESH && paddingTop >= 0) {
                        mCurrentState = RELEASE_TO_REFRESH;
                        refreshHeadView();
                    }else if (paddingTop < 0
                             && mCurrentState == RELEASE_TO_REFRESH) { // 没有显示完全
                         mCurrentState = PULL_TO_REFRESH;
                         refreshHeadView();
                     }
//                    if (paddingTop<50){
                        // 下拉头布局
                        mHeadView.setPadding(0, (int) paddingTop, 0, 0);
//                    }
                    Log.d("PullRefreshListView", "moveDis" + moveDis + "downY:" + downY + "mCurrentState" + mCurrentState + "ev.getY()" + ev.getY() + "paddingTop" + paddingTop + "mHeadHeight"+mHeadHeight);

                    return true;
                }*/
                /*int moveY = (int) ev.getY();
                // 移动中的y - 按下的y = 间距.
                int diff = (moveY - (int)downY) / 2;
                // -头布局的高度 + 间距 = paddingTop
                int paddingTop = -mHeadHeight + diff;
                // 如果: -头布局的高度 > paddingTop的值 执行super.onTouchEvent(ev);
                if (mFirstVisiblePos == 0
                        && -mHeadHeight < paddingTop) {
                    if (paddingTop > 0 && mCurrentState == PULL_TO_REFRESH) { // 完全显示了.
                        mCurrentState = RELEASE_TO_REFRESH;
                        refreshHeadView();
                    } else if (paddingTop < 0
                            && mCurrentState == RELEASE_TO_REFRESH) { // 没有显示完全
                        mCurrentState = PULL_TO_REFRESH;
                        refreshHeadView();
                    }
                    if (paddingTop<50){
                        // 下拉头布局
                        mHeadView.setPadding(0, paddingTop, 0, 0);
                    }

                    Log.d("PullRefreshListView", "moveDis" + moveDis + "downY:" + downY + "mCurrentState" + mCurrentState + "ev.getY()" + ev.getY() + "paddingTop" + paddingTop + "mHeadHeight"+mHeadHeight);
                    return true;
                }*/
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mCurrentState == RELEASE_TO_REFRESH){
                    mCurrentState = REFRESHING;
                    mHeadView.setPadding(0, 0, 0, 0);
                    //todo 刷新数据
                    refreshHeadView();
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onRefreshComplete();
                        }
                    },2000);
                }else if (mCurrentState == PULL_TO_REFRESH){
                    mCurrentState = PULL_TO_REFRESH;
                    mHeadView.setPadding(0,-mHeadHeight,0,0);
                    refreshHeadView();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    private void onRefreshComplete(){
        mCurrentState = PULL_TO_REFRESH;
        mHeadView.setPadding(0,-mHeadHeight,0,0);
        refreshHeadView();
    }
    private void refreshHeadView() {
        Log.d("PullRefreshListView","mCurrentState"+mCurrentState);
        if (mCurrentState == PULL_TO_REFRESH) {
            mHeadArrowIV.clearAnimation();
            mHeadProgressBarIV.clearAnimation();
            mHeadArrowIV.setVisibility(VISIBLE);
            mHeadProgressBarIV.setVisibility(GONE);
            mHeadTV.setText("下拉刷新．．．");
        } else if (mCurrentState == RELEASE_TO_REFRESH) {
            mHeadArrowIV.setVisibility(VISIBLE);
            mHeadProgressBarIV.setVisibility(GONE);
            mHeadTV.setText("松开刷新．．．");
//            mHeadArrowIV.clearAnimation();
            mHeadArrowIV.startAnimation(upAnimation);
        }else if (mCurrentState == REFRESHING){
            mHeadArrowIV.clearAnimation();///此处如果不cleadAnimation则设置visible gone不起作用
            mHeadArrowIV.setVisibility(GONE);
            mHeadProgressBarIV.setVisibility(VISIBLE);
            mHeadProgressBarIV.startAnimation(progressAnimation);
            mHeadTV.setText("刷新中．．．");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisiblePos = firstVisibleItem;
    }


}
