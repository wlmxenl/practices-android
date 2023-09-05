package com.demo.archive.customview

import com.demo.core.common.base.BaseNavHostActivity

/**
 *
 * Created by wangzf on 2022/11/29
 */
class CustomViewActivity : BaseNavHostActivity() {

    override fun getNavGraphResId() = R.navigation.nav_graph_custom_view

    override fun getCustomStartDestination(): Int {
        return R.id.clip_view_fragment
    }
}