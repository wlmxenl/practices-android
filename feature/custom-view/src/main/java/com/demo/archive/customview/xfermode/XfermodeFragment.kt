package com.demo.archive.customview.xfermode

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import com.demo.archive.customview.databinding.XfermodeLayoutBinding
import com.demo.core.common.base.BaseFragment

/**
 *
 * doc: [PorterDuff.Mode](https://developer.android.google.cn//reference/android/graphics/PorterDuff.Mode)
 *
 * Created by wangzf on 2022/11/30
 */
class XfermodeFragment : BaseFragment<XfermodeLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        val xferModeList = PorterDuff.Mode.values()
        val referenceIds = IntArray(xferModeList.size)
        xferModeList.forEachIndexed { index, mode ->
            val btn = XfermodeView(requireActivity()).apply {
                id = View.generateViewId()
                setPorterDuffMode(mode)
            }
            referenceIds[index] = btn.id
            binding.ctlRootLayout.addView(btn)
        }
        binding.ctlFlowLayout.referencedIds = referenceIds
        binding.ctlRootLayout.run { requestLayout() }
    }
}