package com.example.renlei.myapplication.recycleview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.pullrefresh.PtrFrameLayout.PtrFrameLayoutActivity;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.RecycleviewDrapWithSection;
import com.example.renlei.myapplication.recycleview.advancerecycleview.recycledragwithsection.ScrollViewExpandableListview;

public class AdvanceRecycleview extends Activity {
    TextView mResultTV;
    Context mAppContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_recycleview);
        mAppContext = this;
        mResultTV = (TextView)findViewById(R.id.location_tv);
        findViewById(R.id.expand_with_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvanceRecycleview.this, RecycleviewDrapWithSection.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.expand_listview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdvanceRecycleview.this,ScrollViewExpandableListview.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.ptr_frame_layout_recyclerview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdvanceRecycleview.this,PtrFrameLayoutActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.test_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultTV.setText("定位结果："+isOPen(AdvanceRecycleview.this)

                +"\n"+"isNetworkLocationEnabled"+isNetworkLocationEnabled()
                +"\n"+"isGPSLocationEnable"+isGPSLocationEnable()
                +"\n"+"check"+check());
            }
        });
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    public boolean isNetworkLocationEnabled() {
        boolean result = false;
        try {
            LocationManager locMgr = (LocationManager) mAppContext
                    .getSystemService(Context.LOCATION_SERVICE);
            result = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public boolean isGPSLocationEnable() {
        boolean result = false;
        try {
            LocationManager locMgr = (LocationManager) mAppContext
                    .getSystemService(Context.LOCATION_SERVICE);
            result = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }


    public boolean check(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
    }

}
