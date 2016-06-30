package com.example.renlei.myapplication.imageload.myimageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.renlei.myapplication.R;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by  renlei
 * DATE: 16/6/29
 * Time: 15:18
 */
public class ImageLoad {
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10), new RejectDone());

    static class RejectDone extends ThreadPoolExecutor.AbortPolicy {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
//            super.rejectedExecution(r, e);
//            Log.d(TAG, ((ThreadPoolTask) r).taskName + "被阻止");
        }
    }

    public static void loadImage(ImageView iv, String url, BitmapFactory.Options options) {
        if (iv == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            iv.setImageResource(R.mipmap.ic_launcher);
            return;
        }
        /**
         * 从缓存中进行读取
         */
        Bitmap memoryBitmap = MemoryCache.getMemory(url);
        if (memoryBitmap != null) {
            Log.d("ImageLoad", "从缓存中进行加载");
            iv.setImageBitmap(memoryBitmap);
            return;
        }
        /**
         * 缓存中没有，设置默认图
         */
        iv.setImageResource(R.mipmap.ic_launcher);
        iv.setTag(url);//设置tag防止错位
        /**
         * 从硬盘中查询
         */
        poolExecutor.execute(new ImageLoadTask(url,iv));
    }
}
