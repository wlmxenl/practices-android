package com.demo.core.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cn.dripcloud.scaffold.page.BasePageActivity
import cn.dripcloud.scaffold.page.IPageStateLayout
import com.blankj.utilcode.util.BarUtils
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 *
 * Created by wangzf on 2022/11/29
 */
abstract class BaseActivity<VB : ViewBinding> : BasePageActivity<VB, CustomAppBarLayout>() {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): VB {
        return ViewBindingUtil.inflateWithGeneric(this, inflater)
    }

    override fun onBeforeSetContentView() {
        BarUtils.transparentStatusBar(this)
    }

    override fun onCreateAppBarView(): View? = CustomAppBarLayout(this).apply {
        setup {
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun onCreatePageStateView(): IPageStateLayout? = null

    override fun loadData() {
    }

}