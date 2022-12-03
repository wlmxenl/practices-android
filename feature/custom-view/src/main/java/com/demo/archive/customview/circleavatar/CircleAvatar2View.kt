package com.demo.archive.customview.circleavatar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.demo.archive.customview.CustomViewUtil
import com.demo.archive.customview.R
import com.demo.core.util.dp2pxInt

/**
 * 使用 clipPath 方式实现
 * Created by wangzf on 2022/12/2
 */
class CircleAvatar2View(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmapSize = 200f.dp2pxInt()
    private val bitmap = CustomViewUtil.getBitmap(R.drawable.img_avatar, bitmapSize)
    private val path = Path().apply {
        addCircle(bitmapSize / 2f, bitmapSize / 2f, bitmapSize / 2f, Path.Direction.CW)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(bitmapSize, bitmapSize)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }
}