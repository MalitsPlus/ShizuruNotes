package com.github.malitsplus.shizurunotes.ui.calendar

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.github.malitsplus.shizurunotes.R
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView

class CustomMonthView(context: Context) : MonthView(context) {

    private val dayTextSize = dipToPx(context, 12f).toFloat()
    private val schemeHeight = dipToPx(context, 11f).toFloat()
    private val schemeTextSize = dipToPx(context, 9f).toFloat()
    private val schemeTextDiff = (schemeHeight - (schemeHeight - schemeTextSize) / 2.0 * 1.5).toFloat()

    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mSchemeBasicPaint = Paint()

    init {
        //日期边框
        mRectPaint.style = Paint.Style.STROKE
        mRectPaint.strokeWidth = dipToPx(context, 0.5f).toFloat()
        mRectPaint.color = context.getColor(R.color.black_500)




        mSchemeBasicPaint.isAntiAlias = true
        mSchemeBasicPaint.style = Paint.Style.FILL
        mSchemeBasicPaint.textAlign = Paint.Align.CENTER
        mSchemeBasicPaint.isFakeBoldText = false

        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint)
        //4.0以上硬件加速会导致无效
        mSelectedPaint.maskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.SOLID)
    }

    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return true 则绘制onDrawScheme，因为这里背景色不是是互斥的
     */
    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar?,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        canvas.drawRect(
            x.toFloat(),
            y.toFloat(),
            x + mItemWidth.toFloat(),
            y + mItemHeight.toFloat(),
            mSelectedPaint
        )
        return true
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    override fun onDrawScheme(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int
    ) {
        mSchemeBasicPaint.color = calendar.schemeColor
        mSchemeTextPaint.apply {
            textSize = schemeTextSize
            textAlign = Paint.Align.LEFT
        }

        val schemes = calendar.schemes
        if (schemes == null || schemes.size == 0) {
            return
        }
        val space = dipToPx(context, 1f)
        var indexY = y + dayTextSize * 2f

        for (scheme in schemes) {
            mSchemePaint.color = scheme.shcemeColor
            canvas.drawRect(
                x.toFloat() + space,
                indexY,
                x + mItemWidth.toFloat() - space,
                indexY + schemeHeight,
                mSchemePaint
            )
            canvas.drawText(
                scheme.scheme, x.toFloat() + space, indexY + schemeTextDiff, mSchemeTextPaint
            )
            indexY += space + schemeHeight
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        canvas.drawRect(
            x.toFloat(),
            y.toFloat(),
            x + mItemWidth.toFloat(),
            y + mItemHeight.toFloat(),
            mRectPaint
        )
        val centerX = (x + mItemWidth / 2).toFloat()
        val top = y - mItemHeight / 2 + dayTextSize
        val textCenterY = y - mItemHeight / 2 + dayTextSize / 2 + 5f

        mCurDayTextPaint.textSize = dayTextSize
        mCurDayTextPaint.color = context.getColor(R.color.black_800)
        mCurMonthTextPaint.textSize = dayTextSize
        mOtherMonthTextPaint.textSize = dayTextSize
        val mCurrentDayBackgroundPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = context.getColor(R.color.blue_100)
        }

        if (calendar.isCurrentDay) {
            canvas.drawCircle(centerX, mTextBaseLine + textCenterY, dayTextSize * 3 / 4, mCurrentDayBackgroundPaint)
            canvas.drawText(
                calendar.day.toString(), centerX, mTextBaseLine + top, mCurDayTextPaint
            )
            return
        }

        canvas.drawText(
            calendar.day.toString(), centerX, mTextBaseLine + top,
            if (calendar.isCurrentMonth) mCurMonthTextPaint else mOtherMonthTextPaint
        )
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private fun dipToPx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}