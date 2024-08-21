package com.demo.archive

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.demo.archive.customview.CustomViewActivity
import com.demo.archive.databinding.ActivityMainBinding
import com.demo.core.common.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onPageViewCreated(savedInstanceState: Bundle?) {
        appBarView?.setup {
            toolbar.navigationIcon = null
            toolbar.title = "DemoList"
        }

        mutableListOf<Pair<String, Class<*>>>().apply {
            add("CustomView" to CustomViewActivity::class.java)
        }.let {
            val referenceIds = IntArray(it.size)
            for ((index, item) in it.withIndex()) {
                val btn = AppCompatButton(this).apply {
                    text = item.first
                    id = View.generateViewId()
                    setOnClickListener {
                        startActivity(Intent(this@MainActivity, item.second))
                    }
                }
                referenceIds[index] = btn.id
                binding.ctlRootLayout.addView(btn)
            }
            binding.ctlFlowLayout.referencedIds = referenceIds
            binding.ctlRootLayout.run { requestLayout() }
        }

        startActivity(Intent(this, CustomViewActivity::class.java))
    }

}