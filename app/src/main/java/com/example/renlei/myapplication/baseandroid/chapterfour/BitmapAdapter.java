package com.example.renlei.myapplication.baseandroid.chapterfour;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StringLoader;
import com.example.renlei.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 14:36
 */
public class BitmapAdapter extends BaseAdapter {
    int[] res = new int[]{R.mipmap.test1, R.mipmap.test2, R.mipmap.test3, R.mipmap.test4,
            R.mipmap.test5, R.mipmap.test6, R.mipmap.test7, R.mipmap.test8, R.mipmap.test9,
            R.mipmap.test10, R.mipmap.test11, R.mipmap.test12, R.mipmap.test13, R.mipmap.test14,
            R.mipmap.test15, R.mipmap.test16};
    int[] res2 = new int[]{R.mipmap.t1, R.mipmap.t2, R.mipmap.t3, R.mipmap.t4, R.mipmap.t5, R.mipmap.t6,
            R.mipmap.t7, R.mipmap.t8, R.mipmap.t9, R.mipmap.t10, R.mipmap.t11, R.mipmap.t12, R.mipmap.t13
            , R.mipmap.t14, R.mipmap.t15, R.mipmap.t16, R.mipmap.t17, R.mipmap.t18, R.mipmap.t19, R.mipmap.t20, R.mipmap.t21};
    List<String> imgUrls;
    private void initData() {
        imgUrls = new ArrayList<String>();
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/mpst/public/p2199507156.jpg");
        imgUrls.add("http://img5.douban.com/lpic/s27397768.jpg");
        imgUrls.add("http://img3.douban.com/lpic/s27505695.jpg");
        imgUrls.add("http://img3.douban.com/lpic/s27461340.jpg");
        imgUrls.add("http://img3.douban.com/lpic/s27451442.jpg");
        imgUrls.add("http://img5.douban.com/lpic/s27453059.jpg");
        imgUrls.add("http://img3.douban.com/lpic/s27452925.jpg");
        imgUrls.add("http://img5.douban.com/lpic/s27452846.jpg");
        imgUrls.add("http://img3.douban.com/lpic/s27325332.jpg");
        imgUrls.add("http://img3.douban.com/lpic/s27325315.jpg");
        imgUrls.add("http://img5.douban.com/lpic/s27389537.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/mpst/public/p2199638985.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/mpst/public/p2199637691.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2195768476.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2180002996.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2182978810.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2164841498.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2161445839.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2183221886.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2164383301.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2199638985.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2199407040.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2176900061.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2180405730.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2197059721.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2196353608.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2187391526.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2185073849.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2196328516.jpg");
        imgUrls.add("http://img5.douban.com/view/movie_poster_cover/lpst/public/p2166850749.jpg");
        imgUrls.add("http://img3.douban.com/view/movie_poster_cover/lpst/public/p2183957412.jpg");
        imgUrls.add("http://img3.douban.com/spic/s27459164.jpg");
        imgUrls.add("http://img3.douban.com/spic/s27480483.jpg");
        imgUrls.add("http://img3.douban.com/spic/s27460951.jpg");
        imgUrls.add("http://img3.douban.com/spic/s27453772.jpg");
        imgUrls.add("http://img3.douban.com/spic/s27410904.jpg");
        imgUrls.add("http://img5.douban.com/spic/s27466008.jpg");
        imgUrls.add("http://img3.douban.com/spic/s3259484.jpg");
        imgUrls.add("http://img3.douban.com/spic/s3081692.jpg");
        imgUrls.add("http://img3.douban.com/spic/s1400630.jpg");
        imgUrls.add("http://img5.douban.com/spic/s4715377.jpg");
        imgUrls.add("http://img3.douban.com/spic/s6201192.jpg");
        imgUrls.add("http://img5.douban.com/spic/s2650727.jpg");
        imgUrls.add("http://img3.douban.com/spic/s3636124.jpg");
        imgUrls.add("http://img5.douban.com/spic/s3595798.jpg");
        imgUrls.add("http://img3.douban.com/spic/s27078194.jpg");
        imgUrls.add("http://img3.douban.com/view/event_poster/large/public/2a35a85a873e8e7.jpg");
        imgUrls.add("http://img3.douban.com/view/event_poster/large/public/ce66e0569742fd7.jpg");
        imgUrls.add("http://img3.douban.com/view/event_poster/large/public/4c44752e83900d9.jpg");
        imgUrls.add("http://img3.douban.com/view/event_poster/large/public/d22f7b9e5c8c92c.jpg");
    }
    LayoutInflater inflater;
    Context mContext;
    int length;
    public BitmapAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return res2.length * 10;
    }

    @Override
    public Object getItem(int position) {
        return res2[position % 21];
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
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), res2[position % 21]);
//        viewHolder.iv.setImageBitmap(bitmap);
//        viewHolder.iv.setImageResource(res2[position%21]);
//        viewHolder.iv.setImageResource(R.mipmap.ic_launcher);
//        new BitmapTask(viewHolder.iv,mContext).execute(res2[position%21]);
        loadBitmap(res2[position%21],viewHolder.iv,mContext);
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;

        public ViewHolder(View rootView) {
            iv = (ImageView) rootView.findViewById(R.id.iv);
        }
    }


    private void loadBitmap(int key,ImageView iv,Context context){
        iv.setTag(String.valueOf(key));///必须给其设置一个tag否则在加载的时候会有错位的额现象（跳跃）
        BitmapTask task = new BitmapTask(iv,context);
        Bitmap bitmap = BitmapCache.getMemory(String.valueOf(key));
        if (bitmap != null){
            iv.setImageBitmap(bitmap);
            Log.d("loadBitmap","bitmap != null");
        }else {
            Log.d("loadBitmap","task.execute(key);");
            iv.setImageResource(R.mipmap.ic_launcher);
            task.execute(key);

        }
    }



}
