package com.demo.archive.customview.clip

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.demo.core.util.dp2pxFloat

class ClipPathView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFDF3F")
        style = Paint.Style.FILL
    }
    private val path = Path()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(500, 200)
    }

    override fun onDraw(canvas: Canvas) {
        path.reset()
        path.moveTo(20f.dp2pxFloat(), 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat() - 20f.dp2pxFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()
        canvas.clipPath(path)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }
}