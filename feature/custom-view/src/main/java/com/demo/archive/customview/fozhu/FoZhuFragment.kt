package com.demo.archive.customview.fozhu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.demo.archive.customview.R
import com.demo.archive.customview.databinding.FozhuFragmentLayoutBinding
import com.demo.archive.customview.fozhu.view.FoZhuTextHelper
import com.demo.archive.customview.fozhu.view.SoundPlayer
import com.demo.core.common.base.BaseFragment
import kotlin.math.abs


/**
 *
 * Created by wangzf on 2024/07/01
 */
class FoZhuFragment : BaseFragment<FozhuFragmentLayoutBinding>(), GestureDetector.OnGestureListener, View.OnTouchListener {
    private lateinit var gestureDetector: GestureDetector

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        gestureDetector = GestureDetector(requireActivity(), this)
        binding.clickView.setOnTouchListener(this)
        binding.clickView.setOnClickListener {
            binding.fz3dView.onSlideDownward()
            x()
        }
        prepare()
    }

    private fun prepare() {
        SoundPlayer.loadSound(R.raw.fozhu_audio)
        binding.fz3dView.post {
            binding.flAnimTextLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topMargin = binding.fz3dView.getTopY() - SizeUtils.dp2px(140f)
            }
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val y1 = e1?.y ?: 0f
        val y2 = e2.y
        LogUtils.e(y1, y2)
        if (y1 - y2 > 50.0f && abs(velocityY) > 0.0f) {
            binding.fz3dView.onSlideUpward()
            x()
            return true
        }
        if (y2 - y1 <= 50.0f || abs(velocityY) <= 0.0f) {
            return false
        }
        binding.fz3dView.onSlideDownward()
        LogUtils.e("down")
        x()
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun x() {
        SoundPlayer.play(R.raw.fozhu_audio)
        FoZhuTextHelper.animBottom2Top(requireActivity(), "功德 +1", binding.flAnimTextLayout)
    }
}