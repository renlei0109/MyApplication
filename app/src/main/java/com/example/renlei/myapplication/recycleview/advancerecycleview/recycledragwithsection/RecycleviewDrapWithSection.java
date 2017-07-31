package com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection;

import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewConfiguration;

import com.example.renlei.myapplication.R;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;

public class RecycleviewDrapWithSection extends AppCompatActivity implements RecyclerViewExpandableItemManager.OnGroupCollapseListener ,RecyclerViewExpandableItemManager.OnGroupExpandListener{
    private static final String TAG = "RecycleviewDrapWithSection";
    RecyclerView mRecycleView;
    RecycleAdapter mAdapter;
    RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    RecyclerViewDragDropManager recyclerViewDragDropManager;
    private RecyclerViewTouchActionGuardManager mRecyclerViewTouchActionGuardManager;
    RecyclerView.Adapter mWrapAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_drap_with_section);
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_drag_with_section);
        mLayoutManager = new LinearLayoutManager(this);









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
//        recyclerViewExpandableItemManager.expandAll();


    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {
        Log.d("RecycleAdapter","onGroupCollapse");
    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {
        Log.d("RecycleAdapter","onGroupExpand");

    }
}
