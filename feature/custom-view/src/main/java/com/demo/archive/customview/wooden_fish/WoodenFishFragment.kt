package com.demo.archive.customview.wooden_fish

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import com.demo.archive.customview.databinding.WoodenFishLayoutBinding
import com.demo.core.common.base.BaseFragment

class WoodenFishFragment : BaseFragment<WoodenFishLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {

        binding.ivWoodenHammer.post {
            binding.ivWoodenHammer.pivotX = (binding.ivWoodenHammer.width / 2).toFloat()
            binding.ivWoodenHammer.pivotY = binding.ivWoodenHammer.height.toFloat()
            binding.ivWoodenHammer.rotation = -45f
        }


        binding.ivWoodenFish.setOnClickListener {
            playHitAnimation()
        }
    }

    private fun playHitAnimation() {
        val hammerDown = ObjectAnimator.ofFloat(binding.ivWoodenHammer, "rotation", -45f, -65f)
        hammerDown.duration = 150

        val hammerUp = ObjectAnimator.ofFloat(binding.ivWoodenHammer, "rotation", -65f, -45f)
        hammerUp.duration = 150

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(hammerDown, hammerUp)
        animatorSet.start()
    }
}