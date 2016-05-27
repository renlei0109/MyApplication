package com.example.renlei.myapplication.popupwindow;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by  renlei
 * DATE: 16/5/26
 * Time: 16:02
 */
public class PopupWindowMainActivity extends BaseActivity implements View.OnClickListener{
   @Bind(R.id.popup_window_show_btn)
    Button showBtn;

    PopupWindowUtil mPopupWindowUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupwindow_main_layout);
        ButterKnife.bind(this);
        mPopupWindowUtil = new PopupWindowUtil(this);

        showBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popup_window_show_btn:
                mPopupWindowUtil.show(v);
                break;
        }
    }
}
