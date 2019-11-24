package com.jarchie.resizeitem.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 17:35:59
 * 邮箱：jarchie520@gmail.com
 * 说明：工具方法
 */
public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return displayMetrics;
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static BitmapDrawable decodeImage(String base64drawable) {
        byte[] rawImageData = Base64.decode(base64drawable, 0);
        return new BitmapDrawable(null, new ByteArrayInputStream(rawImageData));
    }
}
