package com.demo.archive.customview.couponview

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.demo.core.util.dp2pxFloat
import com.demo.core.util.dp2pxInt
import com.demo.core.util.sp2pxFloat

class PaymentPromotionView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var text = "返现:3.99元"
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textBounds = Rect()
    private val startPadding = 10f.dp2pxInt()
    private val endPadding = 5f.dp2pxInt()
    private val textVerticalPadding = 5f.dp2pxInt()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgPath = Path()
    private val strokeWidth = 1f.dp2pxFloat()

    private var bgStrokeColor = ColorUtils.string2Int("#666666")
    private var bgSolidColor = Color.WHITE
    private var textColor = ColorUtils.string2Int("#F5BE2F")

    init {
        textPaint.textSize = 13f.sp2pxFloat()
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.RIGHT
        textPaint.color = textColor

        bgPaint.color = bgSolidColor
        bgPaint.style = Paint.Style.FILL
        bgPaint.pathEffect = CornerPathEffect(2f.dp2pxFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!TextUtils.isEmpty(text)) {
            textPaint.getTextBounds(text, 0, text.length, textBounds)
            setMeasuredDimension(textBounds.width() + startPadding + (endPadding * 1.8f).toInt(),
                textBounds.height() + textVerticalPadding * 2)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (TextUtils.isEmpty(text)) return
        // 底层
        resetPathByHierarchy(true)
        bgPaint.color = bgStrokeColor
        canvas.drawPath(bgPath, bgPaint)
        // 次底层
        resetPathByHierarchy(false)
        bgPaint.color = bgSolidColor
        canvas.drawPath(bgPath, bgPaint)
        // 文本
        textPaint.color = textColor
        canvas.drawText(text, (width - endPadding).toFloat(),
            height / 2f - textBounds.centerY(), textPaint)
    }

    private fun resetPathByHierarchy(isBottomLevel: Boolean) {
        val diff = if (isBottomLevel) 0f else strokeWidth.toFloat()
        bgPath.reset()
        bgPath.moveTo(0f + diff * 1.3f, height / 2f)
        bgPath.lineTo(startPadding.toFloat() + diff / 2f, 0f + diff)
        bgPath.lineTo(width.toFloat() - diff, 0f + diff)
        bgPath.lineTo(width.toFloat() - diff, height.toFloat() - diff)
        bgPath.lineTo(startPadding.toFloat() + diff / 2f, height.toFloat() - diff)
        bgPath.close()
    }

    fun setData(text: String, strokeColor: String, solidColor: String, textColor: String) {
        this.text = text
        this.bgSolidColor = ColorUtils.string2Int(solidColor)
        this.bgStrokeColor = ColorUtils.string2Int(strokeColor)
        this.textColor = ColorUtils.string2Int(textColor)
        requestLayout()
    }

}