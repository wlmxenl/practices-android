package com.demo.archive.customview.sportview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.demo.core.util.dp2pxFloat
import com.demo.core.util.dp2pxInt
import com.demo.core.util.sp2pxFloat

/**
 *
 * Created by wangzf on 2022/12/1
 */
class SportView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ringWidth = 10f.dp2pxFloat()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(200f.dp2pxInt(), 200f.dp2pxInt())
    }

    override fun onDraw(canvas: Canvas) {
        // 圆环
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#EEEEEE")
        paint.strokeWidth = ringWidth
        canvas.drawCircle(width / 2f, height / 2f, (width - ringWidth) / 2f, paint)

        // 进度条
        paint.color = Color.parseColor("#D32D25")
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            ringWidth / 2f, ringWidth / 2f,
            width.toFloat() - ringWidth / 2f, height.toFloat() - ringWidth / 2f,
            -90f, 225f, false, paint
        )

        // 绘制辅助线
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 1f.dp2pxFloat()
        canvas.drawLine(0f, height / 2f, width.toFloat(),  height / 2f, paint)

        // 绘制文字
        paint.style = Paint.Style.FILL
        paint.textSize = 32f.sp2pxFloat()
        paint.color = Color.parseColor("#D32D25")
        paint.textAlign = Paint.Align.CENTER

        val text = "SPORT"
        val fontMetrics = paint.fontMetrics
        val baseline = height / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2
        canvas.drawText(text, width / 2f, baseline, paint)
    }
}