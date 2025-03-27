package com.android.kobra.designPackage;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.graphics.Color;
import android.view.animation.AccelerateDecelerateInterpolator;

public class colorAnimation {
    private final View targetView;
    private int startColor;
    private int endColor;

    public colorAnimation(View targetView) {
        this.targetView = targetView;
    }

    public void setColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public void animateColorChange() {
        if (targetView == null) return;

        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimator.setDuration(300);
        colorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimator.addUpdateListener(animator ->
                targetView.setBackgroundColor((int) animator.getAnimatedValue())
        );
        colorAnimator.start();
    }
}
