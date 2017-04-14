package kr.ac.incheon.ns.helperapp.utility;

import android.text.TextUtils;
import android.widget.Toast;

import kr.ac.incheon.ns.helperapp.MyApp;


public class Log {

    private static final String TAG = "myLog";

    public static final void e(String msg) {
        if(!TextUtils.isEmpty(msg))
            android.util.Log.e(TAG, msg);
    }

    public static final void e(String msg, Throwable e) {
        if(!TextUtils.isEmpty(msg))
            android.util.Log.e(TAG, msg, e);
    }

    public static final void d(String msg) {
        if(!TextUtils.isEmpty(msg))
            android.util.Log.d(TAG, msg);
    }

    private static Toast mCurrentToast;
    public static void showToast(CharSequence text) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }

        if(!TextUtils.isEmpty(text)){
            mCurrentToast = Toast.makeText(MyApp.getApplicationCtx(), text, Toast.LENGTH_SHORT);
            mCurrentToast.show();
        }
    }

}