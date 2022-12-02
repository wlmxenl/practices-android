package com.demo.archive.customview.mixtextview

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.demo.archive.customview.R
import com.demo.core.util.dp2pxFloat
import com.demo.core.util.dp2pxInt
import com.demo.core.util.sp2pxFloat

/**
 *
 * Created by wangzf on 2022/12/2
 */
class MixTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    // https://www.lipsum.com/
    private val text = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium" +
            " doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis " +
            "et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia " +
            "voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui " +
            "ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor " +
            "sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt " +
            "ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, " +
            "quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea " +
            "commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse " +
            "quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16f.sp2pxFloat()
    }
    private val fontMetrics = Paint.FontMetrics()
    private val bitmap = ImageUtils.getBitmap(R.drawable.img_avatar)
    private val bitmapMargin = 10f.dp2pxFloat()
    private val bitmapTopOffset = 60f.dp2pxInt()

    override fun onDraw(canvas: Canvas) {
        paint.getFontMetrics(fontMetrics)
        canvas.drawBitmap(bitmap, (width - bitmap.width).toFloat() - bitmapMargin, bitmapMargin + bitmapTopOffset, paint)
        // 绘制换行文本
        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float
        while (start < text.length) {
            maxWidth = if (verticalOffset + fontMetrics.bottom < bitmapMargin + bitmapTopOffset ||
                verticalOffset + fontMetrics.top > bitmapTopOffset + bitmapMargin * 2 + bitmap.height) {
                width.toFloat()
            } else {
                (width - bitmap.width).toFloat() - bitmapMargin * 2
            }
            count = paint.breakText(text, start, text.length, true, maxWidth, null)
            canvas.drawText(text, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
    }
}