package com.github.malitsplus.shizurunotes.ui.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.malitsplus.shizurunotes.R;
import com.github.malitsplus.shizurunotes.common.ResourceManager;

public class BackgroundSpan extends ReplacementSpan {

    public static final int BORDER_RECT = 1;
    public static final int BACKGROUND_RECT = 2;

    private int type;

    public BackgroundSpan(int type) {
        this.type = type;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fontMetricsInt) {
        return (int) paint.measureText(text, start, end);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {

        //先填充文字
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, start, end, x, y, paint);

        //设置边框粗细
        paint.setStrokeWidth(3.0f);
        //设置绘制矩形范围
        RectF rectF = new RectF(x, top + 5, x + measureText(paint, text, start, end), bottom - 5);

        switch (type){
            case BORDER_RECT:
                drawBorderRect(canvas, paint, rectF);
                break;
            case BACKGROUND_RECT:
                drawBackgroundRect(canvas, paint, rectF);
                break;
        }
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }

    private void drawBorderRect(Canvas canvas, Paint paint, RectF rectF) {
        int cornerRadius = 10;

        //画无边框的纯填充色方框背景
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        //画边框线
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
    }

    private void drawBackgroundRect(Canvas canvas, Paint paint, RectF rectF) {
        int cornerRadius = 10;

        //画无边框的纯填充色方框背景
        paint.setColor(ResourceManager.Companion.get().getColor(R.color.red_200_beta));
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        //画边框线
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
    }
}
