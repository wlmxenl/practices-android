package com.demo.archive.customview

import android.os.Bundle
import com.demo.archive.customview.databinding.CustomListLayoutBinding
import com.demo.core.common.base.BaseFragment

/**
 *
 * Created by wangzf on 2022/11/30
 */
class CustomViewListFragment : BaseFragment<CustomListLayoutBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        appBarView?.setup {
            toolbar.title = "CustomView"
        }
    }
}