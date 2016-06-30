package com.example.renlei.myapplication.imageload.myimageload;

import android.text.TextUtils;
import android.util.Log;

import com.example.renlei.myapplication.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by  renlei
 * DATE: 16/6/29
 * Time: 15:21
 */
public class DiskCache {
    private volatile static DiskCache instance;

    public static DiskCache getInstance() {
        if (instance == null) {
            synchronized (DiskCache.class) {
                if (instance == null) {
                    instance = new DiskCache();
                }
            }
        }
        return instance;
    }

    private static String cachePath;

    static {
        cachePath = MyApplication.getContext().getExternalCacheDir() + "/img";
        File file = new File(cachePath);
        file.mkdirs();

    }

    public boolean save(String imgUrl, InputStream inputStream) {
        if (TextUtils.isEmpty(imgUrl) || inputStream == null) {
            return false;
        }
        String filePath = getFilepath(imgUrl);
        File file = new File(filePath);

        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("DiskCache", "save disk" + "  " + filePath);
        return true;
    }

    public File get(String imgUrl) {
        String filePath = getFilepath(imgUrl);
        File file = new File(filePath);
        if (file.exists()) {
            Log.d("DiskCache", "get disk" + "  " + filePath);
            return file;
        }
        return null;
    }


    public static String getFilepath(String url) {
        if (url.contains("/")) {
            String[] urls = url.split("/");
            return cachePath + File.separator + urls[urls.length - 1];
        }
        return url;
    }

}
