package com.demo.core.common.base

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import com.blankj.utilcode.util.BarUtils
import com.demo.core.common.R
import com.google.android.material.appbar.MaterialToolbar

/**
 * 顶部应用栏
 * Created by wangzf on 2022/11/29
 */
class CustomAppBarLayout(context: Context) : FrameLayout(context) {
    val toolbar: MaterialToolbar

    init {
        setPadding(0, BarUtils.getActionBarHeight(), 0, 0)
        setBackgroundColor(Color.parseColor("#455A64"))
        addView(MaterialToolbar(context).apply {
            setTitleTextColor(Color.WHITE)
            setNavigationIcon(R.drawable.ic_action_back)
        }.also {
            toolbar = it
        })
    }

    fun setup(block: CustomAppBarLayout.() -> Unit) {
        block.invoke(this)
    }
}