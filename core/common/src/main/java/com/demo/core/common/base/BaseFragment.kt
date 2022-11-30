package com.demo.core.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cn.dripcloud.scaffold.page.BasePageFragment
import cn.dripcloud.scaffold.page.IPageStateLayout
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 *
 * Created by wangzf on 2022/11/29
 */
abstract class BaseFragment<VB : ViewBinding> : BasePageFragment<VB, CustomAppBarLayout>() {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): VB {
        return ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
    }

    override fun onCreateAppBarView(): View? = CustomAppBarLayout(requireActivity()).apply {
        setup {
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    override fun onCreatePageStateView(): IPageStateLayout? = null

    override fun loadData() {
    }
}