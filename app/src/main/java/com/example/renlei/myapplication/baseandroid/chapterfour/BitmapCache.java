package com.example.renlei.myapplication.baseandroid.chapterfour;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 15:53
 */
public class BitmapCache {
    public static LruCache<String, Bitmap> mMemoryCache;


    public static void init() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxSize / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static void addToMemory(String key, Bitmap bitmap) {

        Log.d("BitmapCaChe", "addToMemory" + "key" + key);
        Log.d("BitmapCaChe", "size" + mMemoryCache.size() + "map");
        mMemoryCache.put(key, bitmap);
    }

    public static Bitmap getMemory(String key) {
        Log.d("BitmapCaChe", "getMemory" + "key" + key);
        if (mMemoryCache != null)
            return mMemoryCache.get(key);
        return null;
    }

}
