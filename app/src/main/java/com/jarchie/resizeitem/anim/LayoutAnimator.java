package com.jarchie.resizeitem.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 11:24:29
 * 邮箱：jarchie520@gmail.com
 * 说明：Layout布局动画工具类
 */
public class LayoutAnimator {

    public static class LayoutHeightUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private final View _view;

        public LayoutHeightUpdateListener(View view) {
            _view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final ViewGroup.LayoutParams lp = _view.getLayoutParams();
            lp.height = (int) animation.getAnimatedValue();
            _view.setLayoutParams(lp);
        }
    }

    public static Animator ofHeight(View view, int start, int end) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new LayoutHeightUpdateListener(view));
        return animator;
    }
}
