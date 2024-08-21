package com.demo.archive.customview.beads

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.demo.archive.customview.R
import com.demo.archive.customview.databinding.BeadsLayoutBinding
import com.demo.core.common.base.BaseFragment
import com.demo.core.util.dp2pxFloat
import kotlin.math.abs

class BeadsViewFragment : BaseFragment<BeadsLayoutBinding>() {

    private val beadViews = mutableListOf<ImageView>()
    private var isAnimating = false

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        beadViews.addAll(
            arrayOf(binding.ivBead0, binding.ivBead1, binding.ivBead2, binding.ivBead3, binding.ivBead4,
            binding.ivBead5, binding.ivBead6, binding.ivBead7, binding.ivBead8, binding.ivBead9)
        )

        val gestureDetector = GestureDetector(requireActivity(), object : GestureDetector.SimpleOnGestureListener() {
            // 滑动事件
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 != null) {
                    val deltaY = e2.y - e1.y
                    if (abs(deltaY) > abs(e2.x - e1.x)) {
                        if (deltaY > 0) {
                            startAnimation(true)
                        } else {
                            startAnimation(false)
                        }
                    }
                }
                return true
            }
        })

        // 设置触摸监听器并将事件传递给 GestureDetector
        binding.viewClickArea.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
        binding.viewClickArea.setOnClickListener {
            startAnimation(true)
        }
    }


    private fun startAnimation(scrollDown: Boolean) {
        if (isAnimating) return
        isAnimating = true

        val animatorList = mutableListOf<Animator>()
        if (scrollDown) {
            // 倒序遍历
            for (i in beadViews.size - 1 downTo 0) {
                val previousIndex = if (i == 0) beadViews.size - 1 else i - 1
                animateView(i, previousIndex, animatorList)
            }
        } else {
            // 正序遍历
            for (i in 0 until beadViews.size) {
                val nextIndex = (i + 1) % beadViews.size
                animateView(i, nextIndex, animatorList)
            }
        }

        // 层级
        if (scrollDown) {
            beadViews[0].bringToFront()
            beadViews[9].bringToFront()
            beadViews[8].bringToFront()
            beadViews[7].bringToFront()
            beadViews[6].bringToFront()
        } else {
            beadViews[1].bringToFront()
            beadViews[2].bringToFront()
            beadViews[3].bringToFront()
            beadViews[4].bringToFront()
            beadViews[5].bringToFront()
        }

        // 动画集合
        val animatorSet = AnimatorSet().apply {
            playTogether(animatorList)
            duration = 300L
            interpolator = LinearInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // 位移后交换下标
                    if (scrollDown) {
                        val lastView = beadViews.removeAt(0)
                        beadViews.add(lastView)
                    } else {
                        val lastView = beadViews.removeAt(beadViews.size - 1)
                        beadViews.add(0, lastView)
                    }

                    isAnimating = false
                }
            })
        }
        animatorSet.start()
    }


    private fun animateView(currentIndex: Int, targetIndex: Int, animatorList: MutableList<Animator>) {
        val currentView = beadViews[currentIndex]
        val targetView = beadViews[targetIndex]

        // 目标宽度
        val targetWidth = targetView.width.toFloat()
        val initialWidth = currentView.layoutParams.width.toFloat()

        // 获取当前View的LayoutParams
        val layoutParams = currentView.layoutParams as FrameLayout.LayoutParams
        val initialLeftMargin = layoutParams.leftMargin
        val initialTopMargin = layoutParams.topMargin

        // 计算目标位置的leftMargin和topMargin
        val targetLayoutParams = targetView.layoutParams as FrameLayout.LayoutParams
        val targetLeftMargin = targetLayoutParams.leftMargin
        val targetTopMargin = targetLayoutParams.topMargin

        // 动画：leftMargin, topMargin
        val animX = ValueAnimator.ofInt(initialLeftMargin, targetLeftMargin)
        animX.addUpdateListener { animation ->
            layoutParams.leftMargin = animation.animatedValue as Int
            currentView.layoutParams = layoutParams
        }

        val animY = ValueAnimator.ofInt(initialTopMargin, targetTopMargin)
        animY.addUpdateListener { animation ->
            layoutParams.topMargin = animation.animatedValue as Int
            currentView.layoutParams = layoutParams
        }

        // 创建宽高变化动画
        val animSize = ValueAnimator.ofFloat(initialWidth, targetWidth)
        animSize.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            layoutParams.width = animatedValue.toInt()
            layoutParams.height = animatedValue.toInt()
            currentView.layoutParams = layoutParams
        }

        // 添加动画到列表
        animatorList.add(animX)
        animatorList.add(animY)
        animatorList.add(animSize)
    }

}