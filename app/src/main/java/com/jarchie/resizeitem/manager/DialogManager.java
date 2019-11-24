package com.jarchie.resizeitem.manager;

import android.content.Context;
import android.view.Gravity;

import com.jarchie.resizeitem.R;
import com.jarchie.resizeitem.view.DialogView;


/**
 * 作者：created by Jarchie
 * 时间：2019-11-12 21:28:58
 * 邮箱：jarchie520@gmail.com
 * 说明：提示框管理类
 */
public class DialogManager {

    //单例模式
    private static volatile DialogManager mInstance = null;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context mContext, int layout) {
        return new DialogView(mContext, layout, R.style.Theme_Dialog, Gravity.CENTER);
    }

    public DialogView initView(Context mContext, int layout, int gravity) {
        return new DialogView(mContext, layout, R.style.Theme_Dialog, gravity);
    }

    public void show(DialogView view){
        if (view!=null){
            if (!view.isShowing()){
                view.show();
            }
        }
    }

    public void hide(DialogView view){
        if (view !=null){
            if (view.isShowing()){
                view.dismiss();
            }
        }
    }

}
