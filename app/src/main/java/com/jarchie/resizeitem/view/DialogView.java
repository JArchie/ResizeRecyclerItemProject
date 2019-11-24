package com.jarchie.resizeitem.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-12 21:24:08
 * 邮箱：jarchie520@gmail.com
 * 说明：自定义提示框
 */
public class DialogView extends Dialog {

    public DialogView(Context context,int layout, int style,int gravity) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
    }

}
