package com.demo.archive.customview.weather

import android.os.Bundle
import com.demo.archive.customview.databinding.WeatherViewsLayoutBinding
import com.demo.core.common.base.BaseFragment

class WeatherViewsFragment : BaseFragment<WeatherViewsLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        binding.ssv1.setRatio(0.4f)
        binding.ssv2.setRatio(0.7f)
    }

}