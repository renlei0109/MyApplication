package com.example.renlei.myapplication.baseandroid.chaptertwo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.renlei.myapplication.R;
import com.example.renlei.myapplication.titlebar.BaseActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by  renlei
 * DATE: 16/6/5
 * Time: 12:30
 */
public class ShareFileActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharefile_layout);
        findViewById(R.id.share_text).setOnClickListener(this);
        findViewById(R.id.share_img).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_text:
                shareText();
                break;
            case R.id.share_img:
                shareImg();
                break;
        }
    }

    private void shareText() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "renlei renlei"));
    }

    private void shareImg(){
//        Log.d("renlei",getPath(Uri.parse("content://media/external/images/media/4")));
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
//        imageUris.add(Uri.parse("content://media/external/images/media/4")); // Add your image URIs here
        imageUris.add(Uri.fromFile(new File("/storage/emulated/0/bluetooth/IMG_20160225_082351.jpg"))); // Add your image URIs here
        imageUris.add(Uri.fromFile(new File("/storage/emulated/0/bluetooth/IMG_20160225_082337.jpg"))); // Add your image URIs here
        imageUris.add(Uri.fromFile(new File("/storage/emulated/0/bluetooth/IMG_20160225_082314.jpg"))); // Add your image URIs here
        imageUris.add(Uri.fromFile(new File("/storage/emulated/0/bluetooth/IMG_20160225_082245.jpg"))); // Add your image URIs here
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }
    public String getPath(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
