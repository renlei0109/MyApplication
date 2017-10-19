package com.example.renlei.myapplication.pullrefresh.PtrFrameLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by novia on 2/23/16.
 */
public class DevicePtrFrameLayout extends PtrFrameLayout {
    public DevicePtrFrameLayout(Context context) {
        super(context);
    }

    public DevicePtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DevicePtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    boolean touchEnabled = true;

    public void enableTouch(){
        touchEnabled = true;
    }

    public void disableTouch(){
        touchEnabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!touchEnabled){
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if(!touchEnabled){
            return true;
        }
        return super.dispatchTouchEvent(e);
    }

}
