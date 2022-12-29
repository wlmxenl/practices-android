package com.demo.archive.customview.weather

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.demo.core.util.dp2pxFloat


class SunView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var pathShader: Shader
    private var mPath = Path()
    private val mainColor = Color.parseColor("#fd8008")
    private val mDashPathEffect = DashPathEffect(floatArrayOf(6f.dp2pxFloat(), 4f.dp2pxFloat()), 0f)
    private val mEdgePointRadius = 3f.dp2pxFloat()
    var mPathMeasure = PathMeasure(mPath, false)
    val sunViewPosition = floatArrayOf(0f, 0f)
    val sunViewTanPosition = floatArrayOf(0f, 0f)

    init {
        pathPaint.color = mainColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        pathShader = LinearGradient(w / 2f, 0f, w / 2f, h.toFloat(), mainColor, Color.WHITE, Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas) {
        // 渐变背景
        mPath.moveTo(mEdgePointRadius, height.toFloat() - mEdgePointRadius)
        mPath.quadTo(width / 2f, -height / 2f, width.toFloat() - mEdgePointRadius, height.toFloat() - mEdgePointRadius)
        pathPaint.style = Paint.Style.FILL
        pathPaint.shader = pathShader
        pathPaint.pathEffect = null
        canvas.drawPath(mPath, pathPaint)
        // 背景边框线
        pathPaint.style = Paint.Style.STROKE
        pathPaint.shader = null
        pathPaint.strokeWidth = 1f.dp2pxFloat()
        pathPaint.pathEffect = mDashPathEffect
        canvas.drawPath(mPath, pathPaint)

        // 起点、终点圆点
        pathPaint.style = Paint.Style.FILL
        pathPaint.pathEffect = null
        pathPaint.pathEffect = null
        canvas.drawCircle(mEdgePointRadius, height.toFloat() - mEdgePointRadius, mEdgePointRadius, pathPaint)
        canvas.drawCircle(width.toFloat() - mEdgePointRadius, height.toFloat() - mEdgePointRadius, mEdgePointRadius, pathPaint)

        mPathMeasure.setPath(mPath, false)
        pathPaint.color = Color.RED
        mPathMeasure.getPosTan(0.33f * mPathMeasure.length, sunViewPosition, sunViewTanPosition)
        canvas.drawCircle(sunViewPosition[0], sunViewPosition[1], 10f.dp2pxFloat(), pathPaint)
    }
}