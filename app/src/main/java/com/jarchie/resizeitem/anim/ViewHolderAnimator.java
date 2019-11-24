package com.jarchie.resizeitem.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 11:22:23
 * 邮箱：jarchie520@gmail.com
 * 说明：ViewHolder动画工具类
 */
public class ViewHolderAnimator {

    public static class ViewHolderAnimatorListener extends AnimatorListenerAdapter {
        private final RecyclerView.ViewHolder _holder;

        public ViewHolderAnimatorListener(RecyclerView.ViewHolder holder) {
            _holder = holder;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            _holder.setIsRecyclable(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            _holder.setIsRecyclable(true);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            _holder.setIsRecyclable(true);
        }
    }

    public static class LayoutParamsAnimatorListener extends AnimatorListenerAdapter {
        private final View _view;
        private final int _paramsWidth;
        private final int _paramsHeight;

        public LayoutParamsAnimatorListener(View view, int paramsWidth, int paramsHeight) {
            _view = view;
            _paramsWidth = paramsWidth;
            _paramsHeight = paramsHeight;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            final ViewGroup.LayoutParams params = _view.getLayoutParams();
            params.width = _paramsWidth;
            params.height = _paramsHeight;
            _view.setLayoutParams(params);
        }
    }

    public static Animator ofItemViewHeight(RecyclerView.ViewHolder holder) {
        View parent = (View) holder.itemView.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");

        int start = holder.itemView.getMeasuredHeight();
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = holder.itemView.getMeasuredHeight();

        final Animator animator = LayoutAnimator.ofHeight(holder.itemView, start, end);
        animator.addListener(new ViewHolderAnimatorListener(holder));

        animator.addListener(new LayoutParamsAnimatorListener(holder.itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return animator;
    }

}
