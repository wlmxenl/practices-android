package com.demo.archive.customview.circleavatar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.demo.archive.customview.CustomViewUtil
import com.demo.archive.customview.R
import com.demo.core.util.dp2pxInt

/**
 * 使用 xfermode 方式实现
 * Created by wangzf on 2022/12/2
 */
class CircleAvatar1View(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val bitmapSize = 200f.dp2pxInt()
    private val bitmap = CustomViewUtil.getBitmap(R.drawable.img_avatar, bitmapSize)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val dstRect = RectF(0f, 0f, bitmapSize.toFloat(), bitmapSize.toFloat())

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(bitmapSize, bitmapSize)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawOval(dstRect, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(bitmap, null, dstRect, paint)
        paint.xfermode = null
    }
}