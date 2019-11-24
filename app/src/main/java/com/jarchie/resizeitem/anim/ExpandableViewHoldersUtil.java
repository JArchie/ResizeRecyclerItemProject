package com.jarchie.resizeitem.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.jarchie.resizeitem.R;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 11:21:03
 * 邮箱：jarchie520@gmail.com
 * 说明：折叠透明度动画工具类
 */
public class ExpandableViewHoldersUtil {
    //自定义处理列表中右侧图标，这里是一个旋转动画
    public static void rotateExpandIcon(final ImageView mImage, float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mImage.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

    public static void openH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandView, View.ALPHA, 1);
                    alphaAnimator.addListener(new ViewHolderAnimator.ViewHolderAnimatorListener(holder));
                    alphaAnimator.start();
                }
            });
            animator.start();
        } else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
        }
    }

    public static void closeH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.GONE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
            });
            animator.start();
        } else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
        }
    }

    public interface Expandable {
        View getExpandView();
    }

    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;

        public void bind(VH holder, int pos) {
            if (pos == _opened)
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false);
            else
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false);
        }

        @SuppressWarnings({"unchecked", "deprecation"})
        public void toggle(VH holder, ImageView imageView) {
            if (_opened == holder.getPosition()) {
                _opened = -1;
                ExpandableViewHoldersUtil.rotateExpandIcon(imageView, 90, 0);
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true);
            } else {
                int previous = _opened;
                _opened = holder.getPosition();
                ExpandableViewHoldersUtil.rotateExpandIcon(imageView, 0, 90);
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true);

                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForPosition(previous);
                if (oldHolder != null) {
                    ImageView oldImageView = oldHolder.itemView.findViewById(R.id.mRight);
                    ExpandableViewHoldersUtil.rotateExpandIcon(oldImageView, 90, 0);
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true);
                }
            }
        }
    }
}
