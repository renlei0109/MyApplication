package com.example.renlei.myapplication.imageload.myimageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by  renlei
 * DATE: 16/6/29
 * Time: 16:23
 */
public class ImageLoadTask implements Runnable {
    String imaUrl;
    ImageView mIV;
    DiskCache diskCache = DiskCache.getInstance();

    public ImageLoadTask(String imaUrl, ImageView imageView) {
        this.imaUrl = imaUrl;
        mIV = imageView;
    }

    @Override
    public void run() {
        Log.d("ImageLoad", "ImageLoadTaskRun");
        if (TextUtils.isEmpty(imaUrl)) {
            return;
        }
        File file = diskCache.get(imaUrl);
        if (file != null) {///瓷盘中有从磁盘中加载
            Log.d("ImageLoad", "从瓷盘中加载");
            final Bitmap bitmap = decodeFileToBitmap(file);
            if (mIV.getTag() != null && mIV.getTag().equals(imaUrl)) {
                mIV.post(new Runnable() {
                    @Override
                    public void run() {
                        mIV.setImageBitmap(bitmap);
                    }
                });
            }
            MemoryCache.addToMemory(imaUrl, bitmap);
        } else {//否则从网络上下载
            Log.d("ImageLoad", "从网络上下载");

            HttpURLConnection connection;
            URL url;
            try {
                url = new URL(imaUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    /*  同一个stream只能读取一次
                    InputStream inputStream = connection.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                    if (mIV.getTag() != null && mIV.getTag().equals(imaUrl)) {
                        mIV.post(new Runnable() {
                            @Override
                            public void run() {
                                mIV.setImageBitmap(bitmap);
                            }
                        });
                    }

                    MemoryCache.addToMemory(imaUrl, bitmap);
                    DiskCache.getInstance().save(imaUrl, connection.getInputStream());*/
                    DiskCache.getInstance().save(imaUrl, connection.getInputStream());
                    final Bitmap bitmap = decodeFileToBitmap(DiskCache.getInstance().get(imaUrl));
                    if (mIV.getTag() != null && mIV.getTag().equals(imaUrl)) {
                        mIV.post(new Runnable() {
                            @Override
                            public void run() {
                                mIV.setImageBitmap(bitmap);
                            }
                        });
                    }
                    MemoryCache.addToMemory(imaUrl,bitmap);
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }

        }
    }


    private Bitmap decodeFileToBitmap(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        return bitmap;
    }
}
