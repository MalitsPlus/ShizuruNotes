package com.github.nyanfantasia.shizurunotes.ui.base

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.ResourceManager.Companion.get

class BackgroundSpan(private val type: Int) : ReplacementSpan() {
    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fontMetricsInt: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {

        //先填充文字
        paint.style = Paint.Style.FILL
        canvas.drawText(text, start, end, x, y.toFloat(), paint)

        //设置边框粗细
        paint.strokeWidth = 3.0f
        //设置绘制矩形范围
        val rectF = RectF(
            x,
            (top + 5).toFloat(),
            x + measureText(paint, text, start, end),
            (bottom - 5).toFloat()
        )
        when (type) {
            BORDER_RECT -> drawBorderRect(canvas, paint, rectF)
            BACKGROUND_RECT -> drawBackgroundRect(canvas, paint, rectF)
        }
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }

    private fun drawBorderRect(canvas: Canvas, paint: Paint, rectF: RectF) {
        val cornerRadius = 10

        //画无边框的纯填充色方框背景
        paint.color = Color.TRANSPARENT
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)

        //画边框线
        paint.style = Paint.Style.STROKE
        paint.color = Color.GRAY
        paint.isAntiAlias = true
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)
    }

    private fun drawBackgroundRect(canvas: Canvas, paint: Paint, rectF: RectF) {
        val cornerRadius = 10

        //画无边框的纯填充色方框背景
        paint.color = get()
            .getColor(R.color.red_200_beta)
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)

        //画边框线
        paint.style = Paint.Style.STROKE
        paint.color = Color.GRAY
        paint.isAntiAlias = true
        canvas.drawRoundRect(rectF, cornerRadius.toFloat(), cornerRadius.toFloat(), paint)
    }

    companion object {
        const val BORDER_RECT = 1
        const val BACKGROUND_RECT = 2
    }
}