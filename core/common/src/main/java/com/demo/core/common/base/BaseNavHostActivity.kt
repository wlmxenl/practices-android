package com.demo.core.common.base

import cn.dripcloud.scaffold.arch.navigation.SimpleNavHostActivity
import com.blankj.utilcode.util.BarUtils

/**
 *
 * Created by wangzf on 2022/11/29
 */
abstract class BaseNavHostActivity : SimpleNavHostActivity() {

    override fun onBeforeSetContentView() {
        super.onBeforeSetContentView()
        BarUtils.transparentStatusBar(this)
    }
}