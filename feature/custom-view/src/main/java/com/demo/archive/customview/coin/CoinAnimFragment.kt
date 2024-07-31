package com.demo.archive.customview.coin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.blankj.utilcode.util.LogUtils
import com.demo.archive.customview.R
import com.demo.archive.customview.databinding.CoinAnimFragmentLayoutBinding
import com.demo.core.common.base.BaseFragment
import kotlin.random.Random


class CoinAnimFragment : BaseFragment<CoinAnimFragmentLayoutBinding>() {
    private lateinit var coinImage: ImageView

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        coinImage = binding.coinImage
        binding.coinImage.setOnClickListener {
            flipCoin()
        }
    }

    private fun flipCoin() {
        // 确定动画结束时是正面还是反面
        val showHeads: Boolean = Random.nextBoolean()
        // 确保旋转的度数最终使硬币朝向正确
        val spins = 4 // 基础旋转圈数
        val finalRotation = spins * 360f + (if (showHeads) 0 else 180) // 如果是反面，额外加180度

        val animator = ObjectAnimator.ofFloat(coinImage, "rotationY", 0f, finalRotation)
        animator.setDuration(3000) // 动画持续时间为3000毫秒，即3秒
        animator.interpolator = DecelerateInterpolator() // 使用减速插值器

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // 动画结束时根据随机结果显示硬币的正面或反面
                coinImage.setImageResource(if (showHeads) R.drawable.coin_head else R.drawable.coin_tail)
            }
        })

        animator.addUpdateListener { animation: ValueAnimator ->
            val rotation = animation.animatedValue as Float % 360
            LogUtils.e(animation.animatedValue)
            // 在动画过程中切换正反面以增加真实感
            if (rotation > 90 && rotation < 270) {
                coinImage.setImageResource(R.drawable.coin_tail)
            } else {
                if (animation.animatedValue as Float > 89f) {
                    coinImage.setImageResource(R.drawable.coin_head)
                }
            }
        }

        animator.start()
    }
}