package com.example.renlei.myapplication.imageload.myimageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.baseandroid.chapterfour.BitmapCache;
import com.example.renlei.myapplication.baseandroid.chapterfour.BitmapTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 14:36
 */
public class MyImageLoadAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context mContext;
    int length;
    public MyImageLoadAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return ImageUrlConstant.imgUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return ImageUrlConstant.imgUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.image_load_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoad.loadImage(viewHolder.iv,ImageUrlConstant.imgUrls[position],null);
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;

        public ViewHolder(View rootView) {
            iv = (ImageView) rootView.findViewById(R.id.iv);
        }
    }

}
