package com.demo.archive.customview.fozhu.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.demo.archive.customview.R
import kotlin.math.cos
import kotlin.math.sin

class FoZhu3DView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mSlidingIconIndex = 0
    private var iconBitmap: Bitmap
    private var mSlidingMode = SlidingMode.DOWN
    private var mPivotX = 0f
    private var mPivotY = 0f
    private var mViewWidth = 0f
    private var offsetRadians = 0.0
    private var beadAreaWidth = 0.0
    private var foZhuPointList: List<FoZhuPoint>? = null
    private val tmpIconMap = HashMap<String, Bitmap>()
    private var mSlidingDrawEnabled = false

    private val iconSize by lazy {
        SizeUtils.dp2px(125f)
    }

    companion object {
        private const val mMaxTotal = 12
        private const val mIconTotal = 9
        private const val mAreaWidthOffset = 5
    }

    init {
        this.mSlidingIconIndex = 0
        this.mSlidingMode = SlidingMode.DOWN
        this.iconBitmap = ImageUtils.getBitmap(R.drawable.fozhu_01)
    }

    private fun drawBead(canvas: Canvas, list: List<FoZhuPoint>?, i: Int, z: Boolean, d2: Double) {
        val beadPoint2 = list!![i]
        val beadPoint = if (i == list.size - 1) {
            list[0]
        } else {
            list[i + 1]
        }
        val scale = beadPoint2.scale * this.beadAreaWidth
        val scale2 = scale + (((beadPoint.scale * this.beadAreaWidth) - scale) * d2)
        val x = beadPoint2.x
        val x2 = beadPoint.x
        val y = beadPoint2.y
        val d3 = x + ((x2 - x) * d2)
        val d4 = scale2 / 2.0
        canvas.drawBitmap(
            getIconBitmap(this.iconBitmap, scale2),
            (d3 - d4).toFloat(),
            ((y + ((beadPoint.y - y) * d2)) - d4).toFloat(),
            null
        )
    }

    private fun drawIcon(canvas: Canvas, f2: Float) {
        drawBead(canvas, this.foZhuPointList, 1, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 0, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 2, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 8, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 3, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 7, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 4, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 6, false, f2.toDouble())
        drawBead(canvas, this.foZhuPointList, 5, false, f2.toDouble())
    }

    private val innerBead: List<FoZhuPoint>
        get() {
            val arrayList = ArrayList<FoZhuPoint>()
            val radius = calculateRadius(mViewWidth.toDouble(), this.beadAreaWidth)
            var d2 = this.offsetRadians * 2.0
            for (i in 1..mIconTotal) {
                var x = this.mPivotX + (sin(d2) * radius)
                val y = this.mPivotY - (cos(d2) * radius)
                if (i == 1) {
                    x += 2.0
                }
                arrayList.add(FoZhuPoint(x, y))
                d2 += this.offsetRadians
            }
            setBeadPointLocation(arrayList)
            return arrayList
        }


    private fun calculateRadius(width1: Double, width2: Double): Double {
        return (width1 / 2.0) - (width2 / 2.0)
    }

    private fun setBeadPointLocation(list: List<FoZhuPoint>) {
        list[1].scale = 0.65
        list[1].x -= (this.beadAreaWidth * 0.96f)
        list[1].y -= (this.beadAreaWidth * 0.28f)
        list[2].scale = 0.75
        list[2].x -= (this.beadAreaWidth * 0.33f)
        list[2].y -= (this.beadAreaWidth * 0.18f)
        list[3].scale = 0.85
        list[3].x += (this.beadAreaWidth * 0.45f)
        list[3].y += (this.beadAreaWidth * 0.04f)
        list[4].scale = 0.95
        list[4].x += (this.beadAreaWidth * 1.02f)
        list[4].y += (this.beadAreaWidth * 0.22f)
        list[5].scale = 1.0
        list[5].x += (this.beadAreaWidth * 1.12f)
        list[5].y += (this.beadAreaWidth * 0.32f)
        list[6].scale = 0.95
        list[6].x += (this.beadAreaWidth * 0.68f)
        list[6].y += (this.beadAreaWidth * 0.22f)
        list[7].scale = 0.85
        list[7].x -= (this.beadAreaWidth * 0.1f)
        list[7].y += (this.beadAreaWidth * 0.08f)
        list[8].scale = 0.75
        list[8].x -= (this.beadAreaWidth * 0.84f)
        list[8].y -= (this.beadAreaWidth * 0.1f)
        list[0].scale = 0.65
        list[0].x -= (this.beadAreaWidth * 1.18f)
        list[0].y -= (this.beadAreaWidth * 0.2f)
        for (beadPoint in list) {
            beadPoint.x += (this.beadAreaWidth * 0.1f)
        }
    }

    fun onSlideDownward() {
        this.mSlidingMode = SlidingMode.DOWN
        this.mSlidingDrawEnabled = true
        this.mSlidingIconIndex = 0
        invalidate()
    }

    fun onSlideUpward() {
        this.mSlidingMode = SlidingMode.UP
        this.mSlidingDrawEnabled = true
        this.mSlidingIconIndex = 0
        invalidate()
    }

    private fun getIconBitmap(bitmap: Bitmap, scale: Double): Bitmap {
        val str = scale.toString()
        if (tmpIconMap.containsKey(str)) {
            return tmpIconMap[str]!!
        }
        val width = iconSize
        val height = iconSize
        val matrix = Matrix()
        val f2 = scale.toFloat()
        matrix.postScale(f2 / width, f2 / height)
        tmpIconMap[str] = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        return tmpIconMap[str]!!
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 模拟每颗珠子滚动
        val i = this.mSlidingIconIndex
        if (i < mMaxTotal && this.mSlidingDrawEnabled) {
            var f2 = i / (mMaxTotal * 1f)
            if (this.mSlidingMode == SlidingMode.DOWN) {
                f2 = 1.0f - f2
            }
            drawIcon(canvas, f2)
            mSlidingIconIndex++
            invalidate()
            return
        }
        this.mSlidingDrawEnabled = false
        drawIcon(canvas, 1.0f)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        this.mPivotX = pivotX
        this.mPivotY = pivotY + 10.0f
        this.mViewWidth = width.toFloat()
        this.beadAreaWidth =
            (calculateAreaWidth(mViewWidth.toDouble(), mIconTotal) + mAreaWidthOffset).toDouble()
        this.offsetRadians = calculateOffsetRadians(mIconTotal)
        this.foZhuPointList = this.innerBead
    }

    private fun calculateAreaWidth(width: Double, size: Int): Int {
        val sin = sin(Math.PI / size)
        LogUtils.e(mViewWidth, sin, ((width * sin) / (sin + 1.0)))
        return (((width * sin) / (sin + 1.0)).toInt())
    }

    private fun calculateOffsetRadians(i: Int): Double {
        return Math.PI * 2 / i
    }

    fun setIcon(resDrawableId: Int) {
        this.iconBitmap = ImageUtils.getBitmap(resDrawableId)
        clearIconBitmap()
        invalidate()
    }

    private fun clearIconBitmap() {
        for (bitmap in tmpIconMap.values) {
            bitmap.recycle()
        }
        tmpIconMap.clear()
    }

    fun getTopY() = foZhuPointList?.minOf { it.y.toInt() } ?: 0

}