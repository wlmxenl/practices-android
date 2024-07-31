package com.demo.archive.customview.muyu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import com.demo.archive.customview.R
import com.demo.archive.customview.databinding.MuyuAnimFragmentLayoutBinding
import com.demo.archive.customview.fozhu.view.FoZhuTextHelper
import com.demo.archive.customview.fozhu.view.SoundPlayer
import com.demo.core.common.base.BaseFragment

class MuYuAnimFragment  : BaseFragment<MuyuAnimFragmentLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {

        binding.clickView.setOnClickListener {
            playWoodenFishAnimation()
        }
        SoundPlayer.loadSound(R.raw.wooden_fish_sound)
    }

    private fun playWoodenFishAnimation() {
        // X 和 Y 轴缩放动画
        val pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.85f, 1.0f)
        val pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.85f, 1.0f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.ivWoodenFish, pvhScaleX, pvhScaleY)
        animator.duration = 200

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                SoundPlayer.play(R.raw.wooden_fish_sound)
                FoZhuTextHelper.animBottom2Top(requireActivity(), "功德 +1", binding.flAnimTextLayout)
            }
        })
        animator.start()
    }
}