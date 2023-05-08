package com.demo.archive.customview.couponview

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.demo.archive.customview.databinding.CouponListLayoutBinding
import com.demo.archive.customview.databinding.SportViewLayoutBinding
import com.demo.core.common.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * Created by wangzf on 2022/12/1
 */
class CouponViewFragment : BaseFragment<CouponListLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {

        binding.view1.setData("返现:2.99元", "#666666", "#ffffff", "#666666")
        binding.view2.setData("返现:2.99元", "#434343", "#434343", "#F5BE2F")
    }
}