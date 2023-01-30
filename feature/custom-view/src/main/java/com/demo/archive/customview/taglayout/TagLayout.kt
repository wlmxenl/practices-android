package com.demo.archive.customview.taglayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private val childrenBounds = arrayListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        var heightUsed = 0
        var lineMaxWidth = 0
        var lineWidthUsed = 0 // 已使用的行宽度
        var lineMaxHeight = 0 // 行最大高度
        for ((index, child) in children.withIndex()) {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            // 换行
            if (specWidthMode != MeasureSpec.UNSPECIFIED && child.measuredWidth + lineWidthUsed > specWidthSize) {
                lineWidthUsed = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            childrenBounds[index].set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight)
            lineWidthUsed += child.measuredWidth
            lineMaxWidth = maxOf(lineMaxWidth, lineWidthUsed)
            lineMaxHeight = maxOf(lineMaxHeight, child.measuredHeight)
        }
        setMeasuredDimension(lineMaxWidth, lineMaxHeight + heightUsed)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val bounds = childrenBounds[index]
            child.layout(bounds.left, bounds.top, bounds.right, bounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}