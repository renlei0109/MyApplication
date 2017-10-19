package com.example.renlei.myapplication.pullrefresh.PtrFrameLayout;

import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.RecycleAdapter;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.widget.FullyLinearLayoutManager;
import com.example.renlei.myapplication.titlebar.BaseActivity;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Time 2017/10/17.
 * User renlei
 * Email renlei@xiaomi.com
 * 测试ptrFramelayout
 */

public class PtrFrameLayoutActivity extends BaseActivity implements RecyclerViewExpandableItemManager.OnGroupCollapseListener ,RecyclerViewExpandableItemManager.OnGroupExpandListener{
    RecyclerView mRecycleView;
    RecycleAdapter mAdapter;
    RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    RecyclerViewDragDropManager recyclerViewDragDropManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;
    RecyclerView.Adapter mWrapAdapter;
    private LinearLayoutManager mLayoutManager;
    PtrFrameLayout pullRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptrframe_layout);
        pullRefreshLayout = (PtrFrameLayout) findViewById(R.id.pull_refresh_layout);
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_drag_with_section);
        mLayoutManager = new LinearLayoutManager(this);

        initView(savedInstanceState);
    }
    private void initView(Bundle savedInstanceState){
        mRecyclerViewTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
        mRecyclerViewTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
        mRecyclerViewTouchActionGuardManager.setEnabled(true);


        recyclerViewDragDropManager = new RecyclerViewDragDropManager();
        recyclerViewDragDropManager.setInitiateOnLongPress(true);
        recyclerViewDragDropManager.setDraggingItemShadowDrawable((NinePatchDrawable) ContextCompat.getDrawable(this, R.drawable.material_shadow_z3));
        recyclerViewDragDropManager.setInitiateOnMove(false);
        recyclerViewDragDropManager.setLongPressTimeout((int) (ViewConfiguration.getLongPressTimeout() * 1.5f));
        recyclerViewDragDropManager.setCheckCanDropEnabled(true);


        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(savedInstanceState);
        recyclerViewExpandableItemManager.setOnGroupCollapseListener(this);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);

        mAdapter = new RecycleAdapter(this);
        mWrapAdapter = recyclerViewExpandableItemManager.createWrappedAdapter(mAdapter);
        mWrapAdapter = recyclerViewDragDropManager.createWrappedAdapter(mWrapAdapter);
        /*mAdapter = new RecycleAdapter(this);
        mWrapAdapter = recyclerViewDragDropManager.createWrappedAdapter(mAdapter);
        mWrapAdapter = recyclerViewExpandableItemManager.createWrappedAdapter(mWrapAdapter);*/



        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(mWrapAdapter);
        mRecycleView.setHasFixedSize(true);


        mRecyclerViewTouchActionGuardManager.attachRecyclerView(mRecycleView);
        recyclerViewDragDropManager.attachRecyclerView(mRecycleView);
        recyclerViewExpandableItemManager.attachRecyclerView(mRecycleView);

        recyclerViewExpandableItemManager.expandAll();
        pullRefreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View view, View header) {

                if (android.os.Build.VERSION.SDK_INT < 14) {
                    if (view instanceof AbsListView) {
                        final AbsListView absListView = (AbsListView) view;
                        return absListView.getChildCount() > 0
                                && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                                .getTop() < absListView.getPaddingTop());
                    } else if (view instanceof RecyclerView) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutManager;
                        if(layoutManager == null) return false;
                        return layoutManager.findFirstCompletelyVisibleItemPosition() - 1 < 0;
                    } else {
                        return view.getScrollY() > 0;
                    }
                } else {
                    if (view instanceof RecyclerView) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutManager;
                        if(layoutManager == null) return false;
                        return layoutManager.findFirstCompletelyVisibleItemPosition() - 1 < 0;
                    }
//                    return view.getScrollY() > 0;
                    return view.canScrollVertically(-1);
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      pullRefreshLayout.refreshComplete();
                    }
                },1000);
            }
        });
    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {

    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {

    }
}
