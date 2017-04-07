package kr.ac.incheon.ns.helperapp;


import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import kr.ac.incheon.ns.helperapp.volly.LruBitmapCache;


public class MyApp extends Application {
    private static MyApp one;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;

    @Override
    public void onCreate() {
        super.onCreate();
        one = this;
    }

    public static synchronized MyApp getInstance() {
        return one;
    }

    public static MyApp getApplication() {
        return one;
    }

    public static Context getApplicationCtx() {
        return one.getApplicationContext();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

}
