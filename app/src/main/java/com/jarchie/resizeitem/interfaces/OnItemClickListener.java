package com.jarchie.resizeitem.interfaces;

import android.view.View;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 15:32:24
 * 邮箱：jarchie520@gmail.com
 * 说明：点击事件的回调接口
 */
public interface OnItemClickListener<T> {

    /**
     * Item点击事件
     *
     * @param bean
     * @param view
     * @param position
     */
    void onItemClick(T bean, View view, int position);

}
