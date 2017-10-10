package com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableDraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.expandable.GroupPositionItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;

import java.util.List;

/**
 * Time 2017/7/26.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class RecycleAdapter extends AbstractExpandableItemAdapter<RecycleAdapter.ParentViewHolder,RecycleAdapter.ChildViewHolder> implements ExpandableDraggableItemAdapter<RecycleAdapter.ParentViewHolder, RecycleAdapter.ChildViewHolder> {
    private static final String TAG = "RecycleAdapter";
    RecycleData recycleData = new RecycleData();
    List<Pair<RecycleData.GroupData, List<RecycleData.ChildData>>> mData = recycleData.getData();
    private Context mContext;
    LayoutInflater mInflate;

    public RecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mInflate = LayoutInflater.from(mContext);
        setHasStableIds(true);
    }

    public void setData(List<Pair<RecycleData.GroupData, List<RecycleData.ChildData>>> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).second.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mData.get(groupPosition).second.get(childPosition).name.hashCode();
    }

    @Override
    public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
       View parentView = mInflate.inflate(R.layout.recycle_drap_with_section_group_item,null);
        ParentViewHolder holder = new ParentViewHolder(parentView);
        return holder;
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View childView = mInflate.inflate(R.layout.recycle_drap_with_section_child_item,null);
        ChildViewHolder holder = new ChildViewHolder(childView);
        return holder;
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolder holder, int groupPosition, int viewType) {
        Log.d("RecyclerAdapter","onBindGroupViewHolder");
        holder.mTV.setText(mData.get(groupPosition).first.name);
        holder.mLL.removeAllViews();
        Log.d("RecyclerAdapter","removeAllViews");

        for (int i = 0;i<1;i++){
            Log.d("RecyclerAdapter","addView"+mData.get(groupPosition).first.name);
            holder.mLL.addView(createView());
        }
        if (groupPosition == 1){
            holder.mParentLL.setVisibility(View.GONE);
        }else {
            holder.mParentLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
        holder.mTV.setText(mData.get(groupPosition).second.get(childPosition).name);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(ParentViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        Log.d(TAG,"onCheckCanExpandOrCollapseGroup" + mData.get(groupPosition).first.type);
        if (mData.get(groupPosition).first.type  == 0){
            return true;
        }else {
            return true;
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
//        Log.d(TAG,"getGroupItemViewType" + mData.get(groupPosition).first.type);

        return mData.get(groupPosition).first.type;
    }

    class ParentViewHolder extends AbstractDraggableItemViewHolder implements ExpandableItemViewHolder {
        private int stateFlag;
        private View rootView;
        public TextView mTV;
        LinearLayout mLL;
        LinearLayout mParentLL;
        public ParentViewHolder( View rootView) {
            super(rootView);
            this.rootView = rootView;
            mTV = (TextView) rootView.findViewById(R.id.recycle_view_group_tv);
            mLL = (LinearLayout) rootView.findViewById(R.id.lite_scene_icon_ll);
            mParentLL = (LinearLayout) rootView.findViewById(R.id.parent_view);
        }

        @Override
        public void setExpandStateFlags(int flags) {
            this.stateFlag = flags;
        }

        @Override
        public int getExpandStateFlags() {
            return stateFlag;
        }
    }

    private View createView(){
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.recycle_imageview,null);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv);
        imageView.setImageResource(R.drawable.std_scene_icon_push);
        return rootView;
    }


    class ChildViewHolder extends AbstractDraggableItemViewHolder implements ExpandableItemViewHolder {
        private int stateFlag;
        private View rootView;
        public TextView mTV;
        public ChildViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            mTV = (TextView) rootView.findViewById(R.id.recycle_view_child_tv);
        }

        @Override
        public void setExpandStateFlags(int flags) {
            this.stateFlag = flags;
        }

        @Override
        public int getExpandStateFlags() {
            return stateFlag;
        }
    }

    @Override
    public boolean onCheckGroupCanStartDrag(ParentViewHolder holder, int groupPosition, int x, int y) {
        Log.d(TAG,"onCheckGroupCanStartDrag");
        if (mData.get(groupPosition).first.type == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean onCheckChildCanStartDrag(ChildViewHolder holder, int groupPosition, int childPosition, int x, int y) {
        Log.d(TAG,"onCheckChildCanStartDrag");

        return true;
    }

    @Override
    public ItemDraggableRange onGetGroupItemDraggableRange(ParentViewHolder holder, int groupPosition) {
        Log.d(TAG,"onGetGroupItemDraggableRange"+mData.size());
        if (mData.get(groupPosition).first.type == 1){
            return new ItemDraggableRange(1,mData.get(groupPosition).second.size());
        }else if (mData.get(groupPosition).first.type == 2){
            return new ItemDraggableRange(7,mData.size()-1);
        }
        return null;
    }

    @Override
    public ItemDraggableRange onGetChildItemDraggableRange(ChildViewHolder holder, int groupPosition, int childPosition) {
        Log.d(TAG,"onGetChildItemDraggableRange  groupPosition"+groupPosition);
        return new GroupPositionItemDraggableRange(groupPosition,groupPosition);
    }

    @Override
    public void onMoveGroupItem(int fromGroupPosition, int toGroupPosition) {
        Log.d(TAG,"onMoveGroupItem"+"    fromGroupPosition"+fromGroupPosition+"    toGroupPosition"+toGroupPosition);
        if (fromGroupPosition == toGroupPosition)
            return;

        Pair<RecycleData.GroupData, List<RecycleData.ChildData>> from = mData.remove(fromGroupPosition);
        mData.add(toGroupPosition,from);
        notifyItemChanged(fromGroupPosition,toGroupPosition);

    }

    @Override
    public void onMoveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        Log.d(TAG,"onMoveChildItem"+"    fromGroupPosition"+fromGroupPosition+"    toGroupPosition"+toGroupPosition);

    }

    @Override
    public boolean onCheckGroupCanDrop(int draggingGroupPosition, int dropGroupPosition) {
        boolean result;
        if (mData.get(draggingGroupPosition).first.type != mData.get(dropGroupPosition).first.type){
            result =  false;
        }else {
            result = true;

        }
        Log.d(TAG,"onCheckGroupCanDrop" +"    result"+result +"    draggingGroupPosition"+draggingGroupPosition+"   dropGroupPosition"+dropGroupPosition);
        return result;
    }

    @Override
    public boolean onCheckChildCanDrop(int draggingGroupPosition, int draggingChildPosition, int dropGroupPosition, int dropChildPosition) {

        Log.d(TAG,"onCheckChildCanDrop");
        return true;
    }
}
