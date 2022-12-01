package com.demo.archive.customview

import android.os.Bundle
import cn.dripcloud.scaffold.arch.navigation.scaffoldNavigate
import com.blankj.utilcode.util.ToastUtils
import com.demo.archive.customview.databinding.CustomListLayoutBinding
import com.demo.archive.customview.databinding.DemoItemLayoutBinding
import com.demo.core.common.base.BaseFragment
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

        binding.rvList.linear().setup {
            addType<Triple<String, String, Int>>(R.layout.demo_item_layout)
            onBind {
                val itemModel: Triple<String, String, Int> = getModel()
                getBinding<DemoItemLayoutBinding>().item = itemModel
                itemView.setOnClickListener {
                    this@CustomViewListFragment.scaffoldNavigate(itemModel.third)
                }
            }
        }.models = mutableListOf<Any>().apply {
            add(Triple("xfermode", "PorterDuff.Mode 示例", R.id.xfermode_fragment))
        }
    }
}