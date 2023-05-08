package com.demo.archive.customview.weather

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.drawable.toBitmap
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ImageUtils
import com.demo.archive.customview.R
import com.demo.core.util.dp2pxFloat
import com.demo.core.util.dp2pxInt
import kotlin.math.cos
import kotlin.math.sin


class SunriseSunsetView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val mSunRadius = 10f.dp2pxFloat()
    private lateinit var mSunBitmap: Bitmap
    private val mRectF = RectF()
    private var mLineRadius = 0f
    private val mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mShadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLineDashPathEffect = DashPathEffect(floatArrayOf(4f.dp2pxFloat(), 4f.dp2pxFloat()), 0f)
    private var mRatio = 0.7f // 0 未日出, 1 日落
    private val mShadowPath = Path()

    init {
        mLinePaint.strokeWidth = 1f.dp2pxFloat()
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.pathEffect = mLineDashPathEffect

        mShadowPaint.style = Paint.Style.FILL_AND_STROKE

        context.withStyledAttributes(attrs, R.styleable.SunriseSunsetView) {
            mLinePaint.color = getColor(R.styleable.SunriseSunsetView_ssv_line_color, ColorUtils.string2Int("#FFC536"))
            mShadowPaint.color = getColor(R.styleable.SunriseSunsetView_ssv_shadow_color, ColorUtils.string2Int("#FFF9EB"))
            mSunBitmap = getDrawableOrThrow(R.styleable.SunriseSunsetView_ssv_icon)
                .toBitmap((mSunRadius * 2).toInt(), (mSunRadius * 2).toInt())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        val rectHeight = (widthSpecSize - mSunRadius * 2f) / 2f
        mLineRadius = rectHeight
        mRectF.set(mSunRadius, mSunRadius, widthSpecSize - mSunRadius, mSunRadius + rectHeight)
        setMeasuredDimension(widthSpecSize.toInt(), (rectHeight + mSunRadius * 2f).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        // 阴影进度
        drawShadow(canvas)
        // 外层半圆线圈
        canvas.drawArc(mRectF.left, mRectF.top, mRectF.right, mRectF.bottom + mRectF.height(),
            180f, 180f, true, mLinePaint)
        // 太阳图标
        if (mRatio > 0f && mRatio < 1f) {
            drawSunView(canvas)
        }
    }

    private fun drawShadow(canvas: Canvas) {
        mShadowPath.reset()
        val endY: Float = mRectF.bottom
        val curPointX: Float = mRectF.left + mLineRadius - mLineRadius * cos(Math.PI * mRatio).toFloat()
        mShadowPath.moveTo(0f, endY)
        mShadowPath.arcTo(mRectF.left, mRectF.top, mRectF.right, mRectF.bottom + mRectF.height(), 180f, 180f * mRatio, false)
        mShadowPath.lineTo(curPointX, endY)
        mShadowPath.close()
        canvas.drawPath(mShadowPath, mShadowPaint)
    }

    private fun drawSunView(canvas: Canvas) {
        val curPointX: Float = mRectF.left + mLineRadius - mLineRadius * cos(Math.PI * mRatio).toFloat()
        val curPointY: Float = mRectF.bottom - mLineRadius * sin(Math.PI * mRatio).toFloat()
        canvas.drawBitmap(mSunBitmap, curPointX - mSunBitmap.width / 2f, curPointY - mSunBitmap.height / 2f, null)
    }

    fun setRatio(ratio: Float) {
        this.mRatio = ratio
        invalidate()
    }
}