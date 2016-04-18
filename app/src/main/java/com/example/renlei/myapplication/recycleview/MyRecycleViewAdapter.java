package com.example.renlei.myapplication.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renlei.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by renlei
 * DATE: 16-4-18
 * Time: 上午11:32
 * Email: lei.ren@renren-inc.com
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    Random random;
    OnItemClickLitener onItemClickLitener;
    List<Integer>mHeights;
    public MyRecycleViewAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        random = new Random();
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++)
        {
            mHeights.add( (int) (100 + Math.random() * 500));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {//赋值数据
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.tv.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        Log.d("onBindViewHolder", "layoutParams.height" + layoutParams.height);
        holder.tv.setLayoutParams(layoutParams);
        holder.tv.setText(mDatas.get(position));
        /*if (onItemClickLitener!=null){
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(holder.tv,position);
                }
            });

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(holder.btn,position);
                }
            });
        }*/

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClickLitener.onItemClick(holder.tv,position);
                Toast.makeText(mContext, position + " tv click" + mDatas.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onItemClickLitener.onItemClick(holder.btn,position);
                Toast.makeText(mContext, position + " btn click" + mDatas.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {//用来装载子view数据的容器盒子
        TextView tv;
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            btn = (Button) itemView.findViewById(R.id.btn);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
