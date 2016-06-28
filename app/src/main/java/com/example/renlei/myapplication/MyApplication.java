package com.example.renlei.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by renlei
 * DATE: 16-2-25
 * Time: 下午4:51
 * Email: lei.ren@renren-inc.com
 */
public class MyApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("leilei", "启动application");
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/SF_Light.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
//        LeakCanary.install(this);
    }


    public MyApplication() {
        this.mContext = this;
    }
    public static Context getContext(){
        return mContext;
    }

}
