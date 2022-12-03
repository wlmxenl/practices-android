package com.demo.archive.customview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.blankj.utilcode.util.Utils

/**
 *
 * Created by wangzf on 2022/12/2
 */
object CustomViewUtil {

    fun getBitmap(resourceId: Int, wh: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(Utils.getApp().resources, resourceId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = wh
        return BitmapFactory.decodeResource(Utils.getApp().resources, resourceId, options)
    }
}