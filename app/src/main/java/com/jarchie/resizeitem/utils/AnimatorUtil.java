package com.jarchie.resizeitem.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-09 16:29:55
 * 邮箱：jarchie520@gmail.com
 * 说明：属性动画工具类
 */
public class AnimatorUtil {

    /**
     * 旋转动画
     *
     * @param view
     * @return
     */
    public static ObjectAnimator rotation(View view) {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        mAnimator.setDuration(2 * 1000);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        return mAnimator;
    }
}
