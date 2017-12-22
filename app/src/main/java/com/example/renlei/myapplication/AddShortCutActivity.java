package com.example.renlei.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.renlei.myapplication.titlebar.BaseActivity;

/**
 * Time 2017/12/21.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class AddShortCutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0;i<4;i++){
                    addToLauncher(AddShortCutActivity.this,"快捷方式"+i,R.drawable.icon_voice2);
                }
            }
        });
    }

    public static void addToLauncher(Activity activity,String name, int drawable) {
        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.renlei.myapplication",
                "com.example.renlei.myapplication.StartActivity"));
        shortcut.putExtra("duplicate", false);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

        Intent.ShortcutIconResource res = Intent.ShortcutIconResource.fromContext(
                activity, drawable);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, res);
        activity.sendBroadcast(shortcut);

        Toast.makeText(activity,
                name,
                Toast.LENGTH_SHORT).show();
    }

}
