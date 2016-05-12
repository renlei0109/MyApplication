package com.example.renlei.myapplication.Cache;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.example.renlei.myapplication.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by renlei
 * DATE: 16-5-10
 * Time: 下午3:10
 * Email: lei.ren@renren-inc.com
 */
public class JsonCache {
    private volatile static JsonCache instance;
    private static final String SEPARATOR = "_";
    private static ArrayList<String> mPathList ;
    public static final String TEST_CACHE = "test_cache";
    public static JsonCache getInstance(){
        if (instance == null){
            synchronized (JsonCache.class){
                if (instance == null){
                    instance = new JsonCache();
                    instance.init();
                }
            }
        }
        return instance;
    }
    private void init(){
        String rootPath = getJsonCacheRootPath();
        File file = new File(rootPath);
        mPathList =new ArrayList<>();
        if (file!=null){
            File []fileList = file.listFiles();
            if (fileList!=null){
                for (File addFile : fileList){
                    mPathList.add(addFile.getAbsolutePath());
                }
            }

        }
    }
    /**
     * 保存到内存
     * @param type
     * @param secondKey  防止同一业务不好区分
     * @return
     */
    public void saveIntoCache(String type,String secondKey,String content){
        String filePath = isFileExist(type, secondKey);
        if (!TextUtils.isEmpty(filePath)){//存在
        }else {
            filePath = getFilePath(type,secondKey);
            mPathList.add(filePath);
        }
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            Log.d("renlei",file.getParentFile().getPath().toString());
            if (!file.exists()){
                file.createNewFile();
            }
            fos =new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  String getFromCache(String type,String secondKey){
        String filePath = isFileExist(type,secondKey);
        String result = "";
        if (TextUtils.isEmpty(filePath)){
            return result;
        }else {
            FileInputStream fis= null;
            try {
                File file = new File(filePath);
                fis = new FileInputStream(file);
                byte []buffer = new byte[(int) file.length()];
                fis.read(buffer);
                result = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 判断是否存在,如果存在则返回文件路径
     */
    private String isFileExist(String type,String secondKey){
        String filePath = getFilePath(type,secondKey);
        if (mPathList.contains(filePath)){
            return filePath;
        }
        return "";
    }

    /**
     * 根据type以及secondkey获取改文件的路径
     * @param type
     * @param secondKey
     * @return
     */
    private String getFilePath(String type,String secondKey){
        String rootPath = getJsonCacheRootPath();
        StringBuffer sb = new StringBuffer();
        sb.append(rootPath);
        sb.append(File.separator).append(type);
        if (!TextUtils.isEmpty(secondKey)){
            sb.append(SEPARATOR).append(secondKey);
        }
        sb.append(SEPARATOR).append(".txt");
        return sb.toString();
    }


    /**
     * 获取存放JsonCache的根目录
     * @return
     */
    private String getJsonCacheRootPath(){
        String rootPath;
        File basePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){//存在sd卡
            basePath = MyApplication.getContext().getApplicationContext().getExternalCacheDir();
        }else {

            basePath = MyApplication.getContext().getApplicationContext().getCacheDir();
        }
        rootPath = basePath+File.separator+"JSONCacheDir";
        return rootPath;
    }
}
