package com.example.renlei.myapplication.baseandroid.chapterfour;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by  renlei
 * DATE: 16/6/28
 * Time: 15:32
 */
public class BitmapTask extends AsyncTask<Integer, Void, Bitmap> {
    ImageView mIV;
    Context mContext;
    String url;
    private WeakReference<ImageView> imageViewReference;

    public BitmapTask(ImageView mIV, Context context) {
        this.mIV = mIV;
        mContext = context;
        imageViewReference = new WeakReference<ImageView>(mIV);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            if (mIV.getTag() != null && mIV.getTag().equals(url)) {
                mIV.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        int resId = params[0];
        url = String.valueOf(resId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), resId, options);

        int inSampleSize = Math.min(options.outHeight/ 200, options.outWidth / 400);
        Log.d("doInBackground", "inSampleSize" + inSampleSize);
        Log.d("doInBackground", "bitmap.width" +options.outWidth + "options.outHeight" + options.outHeight);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap realBitmap = BitmapFactory.decodeResource(mContext.getResources(), resId, options);
        Log.d("doInBackground", "realBitmap.width" + realBitmap.getWidth() + "realBitmap.getHeight()" + realBitmap.getHeight());

        if (realBitmap != null) {
            BitmapCache.addToMemory(String.valueOf(resId), realBitmap);
            return realBitmap;
        }
        return null;
    }


}
