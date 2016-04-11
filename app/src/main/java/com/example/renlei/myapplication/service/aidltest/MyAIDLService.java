package com.example.renlei.myapplication.service.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by renlei
 * DATE: 16-4-8
 * Time: 下午4:53
 * Email: lei.ren@renren-inc.com
 */
public class MyAIDLService extends Service{
    public MyAIDLService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyAILDServiceImpl();
    }
        public class MyAILDServiceImpl extends IMyService.Stub{

            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public String getValue() throws RemoteException {
                return "从AIDL服务获得的值.";
            }
        }
}
