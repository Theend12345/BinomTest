package com.yarets.binom.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import com.yarets.binom.R

@SuppressLint("UseCompatLoadingForDrawables")
fun createMarkerIcon(context: Context, imageId: Int): Bitmap {
    val background: Drawable = context.getDrawable(R.drawable.ic_tracker_75dp) ?: throw IllegalStateException()
    val foreground: Drawable = context.getDrawable(imageId) ?: throw IllegalStateException()

    val width = background.intrinsicWidth
    val height = background.intrinsicHeight
    val mergedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(mergedBitmap)

    background.setBounds(0, 0, width, height)
    background.draw(canvas)

    val foregroundWidth = (width * 0.7).toInt()
    val foregroundHeight = (height * 0.7).toInt()
    val left = (width - foregroundWidth) / 2
    val top = ((height - foregroundHeight) / 2) - 15

    val roundedBitmap = Bitmap.createBitmap(foregroundWidth, foregroundHeight, Bitmap.Config.ARGB_8888)
    val roundedCanvas = Canvas(roundedBitmap)

    foreground.setBounds(0, 0, foregroundWidth, foregroundHeight)
    foreground.draw(roundedCanvas)

    val paint = Paint().apply {
        isAntiAlias = true
        shader = BitmapShader(roundedBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    val path = Path().apply {
        addOval(RectF(0f, 0f, foregroundWidth.toFloat(), foregroundHeight.toFloat()), Path.Direction.CW)
    }

    canvas.save()
    canvas.translate(left.toFloat(), top.toFloat())
    canvas.drawPath(path, paint)
    canvas.restore()

    return mergedBitmap
}