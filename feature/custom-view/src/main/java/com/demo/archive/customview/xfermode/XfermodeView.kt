package com.demo.archive.customview.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils

/**
 *
 * Created by wangzf on 2022/11/30
 */
class XfermodeView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mWidth = SizeUtils.dp2px(90f)
    private val mHeight = SizeUtils.dp2px(140f)
    private val bitmapSize = SizeUtils.dp2px(75f)
    private val srcBitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888) // 矩形
    private val destBitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888) // 圆形
    private val bitmapBounds = RectF()
    private val bitmapPadding = (mWidth - bitmapSize) / 2f

    private var xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
    private var modeName = PorterDuff.Mode.SRC_OUT.name

    init {
        val canvas = Canvas(srcBitmap)
        paint.color = Color.parseColor("#2196f3")
        canvas.drawRect(0f, SizeUtils.dp2px(25f).toFloat(), SizeUtils.dp2px(50f).toFloat(), bitmapSize.toFloat(), paint)
        canvas.setBitmap(destBitmap)
        paint.color = Color.parseColor("#e91e63")
        canvas.drawOval(SizeUtils.dp2px(25f).toFloat(), 0f, bitmapSize.toFloat(), SizeUtils.dp2px(50f).toFloat(), paint)

        bitmapBounds.set(bitmapPadding, (mHeight - bitmapSize).toFloat() - bitmapPadding,
            bitmapPadding + bitmapSize, mHeight - bitmapPadding)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        // 绘制边界线
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#999999")
        paint.strokeWidth = SizeUtils.dp2px(1.5f).toFloat()
        canvas.drawRect(paint.strokeWidth / 2f, paint.strokeWidth / 2f,
            width.toFloat() - paint.strokeWidth / 2f, height.toFloat() - paint.strokeWidth / 2f, paint)

        // xfermode 类型
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = SizeUtils.sp2px(14f).toFloat()
        paint.color = Color.parseColor("#333333")
        canvas.drawText(modeName, bitmapBounds.centerX(), (mHeight.toFloat() - bitmapBounds.top) / 2 - 20f, paint)

        val layerCount = canvas.saveLayer(bitmapBounds, null)
        canvas.drawBitmap(destBitmap, bitmapPadding, bitmapBounds.top, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(srcBitmap, bitmapPadding, bitmapBounds.top, paint)
        paint.xfermode = null
        canvas.restoreToCount(layerCount)
    }

    fun setPorterDuffMode(mode: PorterDuff.Mode) {
        xfermode = PorterDuffXfermode(mode)
        modeName = mode.name
        // invalidate()
    }
}