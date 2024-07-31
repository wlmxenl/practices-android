package com.demo.archive.customview.fozhu.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils

object FoZhuTextHelper {

    fun animBottom2Top(context: Context, animText: String, animationArea: FrameLayout) {
        val textView = TextView(context).apply {
            text = animText
            textSize = 18f
            setTextColor(Color.WHITE)
        }
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        }

        animationArea.addView(textView, params)
        // 动画：移动、透明度
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            textView,
            PropertyValuesHolder.ofFloat("translationY", 0f, -animationArea.height.toFloat() + SizeUtils.dp2px(10f).toFloat()),
            PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f)
        )
        animator.setDuration(1000)
        animator.start()

        // 动画结束后移除textView
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
                animationArea.removeView(textView)
            }
        })
    }

}