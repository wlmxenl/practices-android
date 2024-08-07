package com.demo.archive.customview

import android.graphics.Color
import android.os.Bundle
import cn.dripcloud.scaffold.arch.navigation.scaffoldNavigate
import com.blankj.utilcode.util.ToastUtils
import com.demo.archive.customview.databinding.CustomListLayoutBinding
import com.demo.archive.customview.databinding.DemoItemLayoutBinding
import com.demo.core.common.base.BaseFragment
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

/**
 *
 * Created by wangzf on 2022/11/30
 */
class CustomViewListFragment : BaseFragment<CustomListLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        appBarView?.setup {
            toolbar.title = "CustomView"
        }

        binding.rvList
            .linear()
            .divider {
                setColor(Color.parseColor("#dedede"))
                setDivider(1, true)
            }
            .setup {
                addType<Triple<String, String, Int>>(R.layout.demo_item_layout)
                onBind {
                    val itemModel: Triple<String, String, Int> = getModel()
                    getBinding<DemoItemLayoutBinding>().item = itemModel
                    itemView.setOnClickListener {
                        this@CustomViewListFragment.scaffoldNavigate(itemModel.third)
                    }
                }
            }
            .models = mutableListOf<Any>().apply {
                add(Triple("CouponView", "CouponView", R.id.coupon_fragment))
                add(Triple("佛珠", "3d", R.id.fozhu_fragment))
                add(Triple("TagLayout", "自定义 ViewGroup 示例", R.id.tag_layout_fragment))
                add(Triple("天气相关View", "PathMeasure", R.id.weather_views_fragment))
                add(Triple("CircleAvatarView", "Canvas.clipPath、PorterDuff.Mode", R.id.circle_avatar_fragment))
                add(Triple("MixTextView", "Paint.breakText", R.id.mix_textview_fragment))
                add(Triple("SportView", "文本居中绘制、Paint.Cap", R.id.sport_view_fragment))
                add(Triple("xfermode", "PorterDuff.Mode", R.id.xfermode_fragment))
            }
    }
}