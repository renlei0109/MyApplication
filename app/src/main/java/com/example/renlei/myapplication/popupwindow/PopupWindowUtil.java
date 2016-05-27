package com.example.renlei.myapplication.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.renlei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  renlei
 * DATE: 16/5/26
 * Time: 16:02
 */
public class PopupWindowUtil {
    Context mContext;
    PopupWindow mPopupWindow;
    ListView mListView;
    BaseAdapter mAdapter;
    public PopupWindowUtil(Context mContext) {
        this.mContext = mContext;
        initPopupWindow();

    }
    private void initPopupWindow(){
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.popup_window_layout,null);
        mPopupWindow = new PopupWindow(convertView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//PopupWindow弹出后，所有屏和物理按键都有PopupWindows处理。其他任何事件的响应都必须发生在PopupWindow消失之后    http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0702/1627.html
        mListView = (ListView) convertView.findViewById(R.id.popup_window_lv);
//        mListView.setAdapter(mAdapter);
    }

    public void show(View v){
        if (mPopupWindow!=null&&!mPopupWindow.isShowing()){
            mPopupWindow.showAsDropDown(v,0,0);
        }else if (mPopupWindow!=null&&mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }
    public void disMiss(){
        if (mPopupWindow!=null&&mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }


    class PopupWindowAdapter extends BaseAdapter{
        private List<String>mList = new ArrayList<>();
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
            }
            return null;
        }
    }
}
