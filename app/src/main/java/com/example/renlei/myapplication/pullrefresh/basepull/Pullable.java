package com.example.renlei.myapplication.pullrefresh.basepull;

/**
 * Created by renlei
 * DATE: 16-3-28
 * Time: 下午3:45
 * Email: lei.ren@renren-inc.com
 */
public interface Pullable {
    public boolean canPullRefresh();
    public boolean canPullLoadMore();
}
