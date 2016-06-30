package com.example.renlei.myapplication.imageload.myimageload;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 15:53
 */
public class MemoryCache {
    public static LruCache<String, Bitmap> mMemoryCache;


    public static void init() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxSize / 8;
        if (mMemoryCache == null){
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() / 1024;
                }
            };
        }
    }

    public static synchronized void addToMemory(String key, Bitmap bitmap) {
        if (key != null && bitmap != null) {
            Log.d("BitmapCaChe", "addToMemory" + "key" + DiskCache.getFilepath(key));
            Log.d("BitmapCaChe", "size" + mMemoryCache.size() + "map");
            mMemoryCache.put(DiskCache.getFilepath(key), bitmap);
        }

    }

    public static synchronized Bitmap getMemory(String key) {
        if (mMemoryCache != null && key != null){
            Log.d("BitmapCaChe", "getMemory" + "key" + DiskCache.getFilepath(key) + "从缓存中进行加载");
            return mMemoryCache.get(DiskCache.getFilepath(key));
        }
        return null;
    }

}
