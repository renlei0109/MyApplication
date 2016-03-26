package com.example.renlei.myapplication.pullrefresh.listview;

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


    private Context mContext;

    private static int PULL_TO_REFRESH = 1;
    private static int RELEASE_TO_REFRESH = 2;
    private static int REFRESHING = 3;
    private static int REFRESH_COMPLETED = 4;

    //下拉刷新相关
    private boolean hasDownY = false;//判断是否记录刚开始按下去的downY值
    private int mHeadHeight;
    private ImageView mHeadArrowIV;//头部的箭头
    private ImageView mHeadProgressBarIV;//头部的progressbar
    private TextView mHeadTV;
    private View mHeadView;
    private int mFirstVisiblePos;


    ///加载更多相关的　
    private View mFooterView;
    //    private ImageView mFooterArrowIV;//头部的箭头
    private ImageView mFooterProgressBarIV;//底部的progressbar
    private TextView mFooterTV;
    private int mFooterHeight;
    private boolean mIsScrollToBottom = false;//是否滑动到了最底部
    private boolean mIsLoadingMore = false;//是否正在加载
    private int mStartLoadMorePos = 1;///从哪个位置开始加载，如果从最后一个加载则为１　，如果需要提前两个加载则为３以此类推
    private boolean enableLoadMore = true;///是否允许加载更多，当没有数据时后就应该不允许加载更多了


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

    private IPullRefreshListener mPullRefreshListener;

    public PullRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initHeaderView();
        initFooterView();
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


    private void initFooterView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mFooterView = inflater.inflate(R.layout.pull_to_refresh_footer_view_layout, null);
        mFooterTV = (TextView) mFooterView.findViewById(R.id.footer_tv);
        mFooterProgressBarIV = (ImageView) mFooterView.findViewById(R.id.footer_progress);
//        mFooterArrowIV = (ImageView) mFooterView.findViewById(R.id.footer_arrow);
        mFooterView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mFooterHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, 0, 0, -mFooterHeight);
        this.addFooterView(mFooterView);
    }

    private void initAnimation() {
        downAnimation = new RotateAnimation(90, 270, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
//        downAnimation.setRepeatCount(1);
        downAnimation.setFillAfter(true);

        upAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);


        progressAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        progressAnimation.setDuration(500);
        progressAnimation.setRepeatCount(Integer.MAX_VALUE);
        progressAnimation.setFillAfter(false);

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
                hasDownY = true;
                Log.d("PullRefreshListView", "downY" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!hasDownY) {///必须进行一个记录，否则外面convertview 的click事件会覆盖该touch的down事件，导致没有记录downy,而使得刷新会有跳跃
                    downY = ev.getY();
                    hasDownY = true;
                }
                moveDis = ev.getY() - downY;

                paddingTop = -mHeadHeight + moveDis / 2;//除以２相当于增大了滑动系数
                if (mFirstVisiblePos == 0 && mCurrentState != REFRESHING && -mHeadHeight < paddingTop) {
                    if (paddingTop >= 0 && mCurrentState == PULL_TO_REFRESH) {
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
                    Log.d("PullRefreshListView", "moveDis" + moveDis + "downY:" + downY + "mCurrentState" + mCurrentState + "ev.getY()" + ev.getY() + "paddingTop" + paddingTop + "mHeadHeight" + mHeadHeight);
                    if (paddingTop < 100) {///只允许头部往下多拉取100
                        mHeadView.setPadding(0, (int) paddingTop, 0, 0);
                    }
                    return true;
                }

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mCurrentState == RELEASE_TO_REFRESH) {
                    mCurrentState = REFRESHING;
                    mHeadView.setPadding(0, 0, 0, 0);
                    //todo 刷新数据
                    refreshHeadView();

                    if (mPullRefreshListener != null) {
                        mPullRefreshListener.onRefresh();
                    }
                } else if (mCurrentState == PULL_TO_REFRESH) {
                    mCurrentState = PULL_TO_REFRESH;
                    mHeadView.setPadding(0, -mHeadHeight, 0, 0);
                    refreshHeadView();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 刷新完成
     */
    public void onRefreshComplete() {
        mCurrentState = PULL_TO_REFRESH;
        mHeadView.setPadding(0, -mHeadHeight, 0, 0);
        refreshHeadView();
    }

    private void refreshHeadView() {
        Log.d("PullRefreshListView", "mCurrentState" + mCurrentState);
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
        } else if (mCurrentState == REFRESHING) {
            mHeadArrowIV.clearAnimation();///此处如果不cleadAnimation则设置visible gone不起作用
            mHeadArrowIV.setVisibility(GONE);
            mHeadProgressBarIV.setVisibility(VISIBLE);
            mHeadProgressBarIV.startAnimation(progressAnimation);
            mHeadTV.setText("刷新中．．．");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_FLING ||//// 持续滚动开始，只触发一次
                scrollState == OnScrollListener.SCROLL_STATE_IDLE) {///// 整个滚动事件结束，只触发一次
            if (mIsScrollToBottom&&enableLoadMore) {
                showLoadMore();
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisiblePos = firstVisibleItem;
        if (getLastVisiblePosition() >= totalItemCount - mStartLoadMorePos) {
            mIsScrollToBottom = true;
        } else {
            mIsScrollToBottom = false;
        }
    }


    /**
     * 设置下拉与加载更多的回调
     * @param mPullRefreshListener
     */
    public void setPullRefreshListener(IPullRefreshListener mPullRefreshListener) {
        this.mPullRefreshListener = mPullRefreshListener;
    }

    /**
     * 设置提前加载的位置
     * @param mStartLoadMorePos
     */
    public void setmStartLoadMorePos(int mStartLoadMorePos) {
        if (mStartLoadMorePos >= 1) {
            this.mStartLoadMorePos = mStartLoadMorePos;
        }
    }

    private void showLoadMore() {
        if (!mIsLoadingMore) {
            mIsLoadingMore = true;
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterTV.setText("加载更多...");
            mFooterProgressBarIV.startAnimation(progressAnimation);
            mPullRefreshListener.onLoadMore();
        }
    }

    /**
     * 隐藏loadMore当已经没有数据返回时则需要隐藏
     */
    public void hideLoadMore(){
        mIsLoadingMore = false;
        mFooterView.setPadding(0, 0, 0, -mFooterHeight);
        mFooterProgressBarIV.clearAnimation();
        enableLoadMore = false;

    }

    /**
     * 加载更多完成
     */
    public void onLoadMoreCompleted() {
        mIsLoadingMore = false;
        mFooterView.setPadding(0, 0, 0, -mFooterHeight);
        mFooterProgressBarIV.clearAnimation();
    }

    public interface IPullRefreshListener {
        public void onRefresh();

        public void onLoadMore();
    }
}
