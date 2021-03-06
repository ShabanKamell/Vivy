package com.vivy.app.shared.util.picasso

/*
  Created by @TallShahawi on 12/08/2018
 */

import android.graphics.*
import com.squareup.picasso.Transformation
import com.vivy.app.shared.util.CrashlyticsUtil
import com.vivy.app.shared.util.io.BitmapUtil

class CircleTransform : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        try {

            val size = Math.min(source.width, source.height)

            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)

            if (squaredBitmap == null) {
                source.recycle()
                return BitmapUtil.empty()
            }

            if (squaredBitmap != source) {
                source.recycle()
            }

            val bitmap = Bitmap.createBitmap(size, size, source.config)

            if (bitmap == null) {
                source.recycle()
                return BitmapUtil.empty()
            }

            val canvas = Canvas(bitmap)

            val paint = Paint()
            val shader = BitmapShader(
                    squaredBitmap,
                    Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP
            )
            paint.shader = shader
            paint.isAntiAlias = true

            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)

            squaredBitmap.recycle()

            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            CrashlyticsUtil.log(e)
            source.recycle()
            return BitmapUtil.empty()
        }

    }

    override fun key(): String {
        return "circle"
    }
}
