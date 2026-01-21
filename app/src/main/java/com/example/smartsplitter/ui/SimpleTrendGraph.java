package com.example.smartsplitter.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrendGraph extends View {

    private Paint linePaint, pointPaint, axisPaint, fillPaint;
    private List<Float> dataPoints = new ArrayList<>();

    public SimpleTrendGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        float density = getResources().getDisplayMetrics().density;

        linePaint = new Paint();
        linePaint.setColor(0xFFCDDC39); // Lime
        linePaint.setStrokeWidth(3f * density);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(0xFFFFFFFF);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);

        axisPaint = new Paint();
        axisPaint.setColor(0x44FFFFFF);
        axisPaint.setStrokeWidth(1f * density);

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
    }

    public void setData(List<Float> data) {
        this.dataPoints = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataPoints == null || dataPoints.size() < 2)
            return;

        float density = getResources().getDisplayMetrics().density;
        float width = getWidth();
        float height = getHeight();
        float padding = 16f * density;

        float availableWidth = width - 2 * padding;
        float availableHeight = height - 2 * padding;

        // Find max
        float max = 0;
        for (float v : dataPoints)
            max = Math.max(max, v);
        if (max == 0)
            max = 100;

        Path path = new Path();
        Path fillPath = new Path();

        float xStep = availableWidth / (dataPoints.size() - 1);

        fillPath.moveTo(padding, height - padding); // Start bottom-left

        for (int i = 0; i < dataPoints.size(); i++) {
            float val = dataPoints.get(i);
            float x = padding + i * xStep;
            float y = height - padding - (val / max * availableHeight);

            if (i == 0) {
                path.moveTo(x, y);
                fillPath.lineTo(x, y);
            } else {
                // Bezier curve for smoothness
                float prevVal = dataPoints.get(i - 1);
                float prevX = padding + (i - 1) * xStep;
                float prevY = height - padding - (prevVal / max * availableHeight);
                float cp1x = prevX + xStep / 2;
                float cp1y = prevY;
                float cp2x = x - xStep / 2;
                float cp2y = y;
                path.cubicTo(cp1x, cp1y, cp2x, cp2y, x, y);
                fillPath.cubicTo(cp1x, cp1y, cp2x, cp2y, x, y);
            }

            canvas.drawCircle(x, y, 4f * density, pointPaint);
        }

        fillPath.lineTo(padding + (dataPoints.size() - 1) * xStep, height - padding);
        fillPath.close();

        // Gradient Fill
        fillPaint.setShader(new LinearGradient(0, 0, 0, height, 0x55CDDC39, 0x00CDDC39, Shader.TileMode.CLAMP));
        canvas.drawPath(fillPath, fillPaint);

        canvas.drawPath(path, linePaint);

        // Draw Axis
        canvas.drawLine(padding, height - padding, width - padding, height - padding, axisPaint); // X
    }
}
