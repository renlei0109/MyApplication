package com.example.renlei.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Time 2017/7/5.
 * User renlei
 * Email renlei@xiaomi.com
 * 获取厂商以及系统信息
 */

public class GetProductorInfoActivity extends BaseActivity{
    TextView tv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_productor_info_layout);
        tv = (TextView)findViewById(R.id.result_info);
        findViewById(R.id.get_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("GetProductorInfoActivity","result"+new OSUtils().isEMUI()+"&&&"+new OSUtils().isFlyme());
            }
        });
    }

    public class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            Iterator iteratorKey = properties.keySet().iterator();
            Iterator iteratorValue = properties.values().iterator();

            while (iteratorKey.hasNext()&&iteratorValue.hasNext()){
                Log.e("properties key",iteratorKey.next().toString()+"-----"+iteratorValue.next().toString());
            }

//            Log.d("properties key",properties.keySet().toArray().toString());
//            Log.d("properties value",properties.values().toArray().toString());
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {

            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public  BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }

    }



    /**
     * 判断系统是不是miui，flyme，emui
     */
    public class OSUtils {
        private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
        private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
        private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
        private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

        private  boolean isPropertiesExist(String... keys) {
            try {
                BuildProperties prop = new BuildProperties();
                for (String key : keys) {
                    Log.d("GetProductorInfoActivity","key:"+key);
                    String str = prop.getProperty(key);
                    if (str == null)
                        return false;
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        public  boolean isEMUI() {
            return isPropertiesExist(KEY_EMUI_VERSION_CODE);
        }

        public  boolean isMIUI() {
            return isPropertiesExist(KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME, KEY_MIUI_INTERNAL_STORAGE);
        }

        public  boolean isFlyme() {
            try {
                final Method method = Build.class.getMethod("hasSmartBar");
                return method != null;
            } catch (final Exception e) {
                return false;
            }
        }
    }
}
