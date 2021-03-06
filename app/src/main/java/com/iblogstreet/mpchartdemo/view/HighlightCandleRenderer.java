package com.iblogstreet.mpchartdemo.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 自定义CandleStickChart渲染器 绘制高亮  -- 绘制方式和自定义LineChart渲染器相同
 * 使用方法: 1.先设置渲染器 {@link CombinedChart#setRenderer(DataRenderer)}
 * 传入自定义渲染器 将其中Candle图的渲染器替换成此渲染器
 * 2.设置数据时 调用 {@link CandleEntry#CandleEntry(float, float, float, float, float, Object)}
 * 传入String类型的data 以绘制x的值  -- 如未设置 则只绘制竖线
 */
public class HighlightCandleRenderer extends CandleStickChartRenderer implements HighlightTouchListener {

    private float highlightSize;//图表高亮文字大小 单位:px
    private DecimalFormat format = new DecimalFormat("0.0000");
    private Highlight[] indices;
    private boolean isTouchOn;

    public HighlightCandleRenderer(CandleDataProvider chart, ChartAnimator animator,
                                   ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    public HighlightCandleRenderer setHighlightSize(float textSize) {
        highlightSize = textSize;
        return this;
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
        this.indices = indices;
    }

    protected float getYPixelForValues(float x, float y) {
        MPPointD pixels = mChart.getTransformer(YAxis.AxisDependency.LEFT).getPixelForValues(x, y);
        return (float) pixels.y;
    }


    @Override
    public void drawValues(Canvas c) {

        // if values are drawn
//        if (isDrawingValuesAllowed(mChart)) {

        List<ICandleDataSet> dataSets = mChart.getCandleData().getDataSets();

        for (int i = 0; i < dataSets.size(); i++) {

            ICandleDataSet dataSet = dataSets.get(i);

            if (!shouldDrawValues(dataSet) || dataSet.getEntryCount() < 1)
                continue;

            // apply the text-styling defined by the DataSet
            applyValueTextStyle(dataSet);

            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

            mXBounds.set(mChart, dataSet);

            float[] positions = trans.generateTransformedValuesCandle(
                    dataSet, mAnimator.getPhaseX(), mAnimator.getPhaseY(), mXBounds.min, mXBounds.max);

            float yOffset = Utils.convertDpToPixel(5f);

            ValueFormatter formatter = dataSet.getValueFormatter();

            MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
            iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
            iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);

            for (int j = 0; j < positions.length; j += 2) {

                float x = positions[j];
                float y = positions[j + 1];

                if (!mViewPortHandler.isInBoundsRight(x))
                    break;

                if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                    continue;

                CandleEntry entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min);

                Object data = entry.getData();

                //如果data是true或false
                //todo 处理 显示买卖点
                if (data == null || !(data instanceof Integer)) {
                    if (dataSet.isDrawValuesEnabled()) {
                        // drawValue(c, formatter.getCandleLabel(entry), x, y - yOffset, dataSet.getValueTextColor(j / 2));
                    }

                } else {
                    if ((int) data == 1) {
                        drawValue(c, "B", x, y - yOffset, dataSet.getValueTextColor(j / 2));
                    } else if ((int) data == 2) {
                        drawValue(c, "S", x, y - yOffset, dataSet.getValueTextColor(j / 2));
                    }
                }

//                if (dataSet.isDrawValuesEnabled()) {
//                    drawValue(c, formatter.getCandleLabel(entry), x, y - yOffset, dataSet.getValueTextColor(j / 2));
//                }

                if (entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {

                    Drawable icon = entry.getIcon();

                    Utils.drawImage(
                            c,
                            icon,
                            (int) (x + iconsOffset.x),
                            (int) (y + iconsOffset.y),
                            icon.getIntrinsicWidth(),
                            icon.getIntrinsicHeight());
                }
            }

            MPPointF.recycleInstance(iconsOffset);
        }
//        }
    }


    @Override
    protected void drawHighlightLines(Canvas c, float x, float y, ILineScatterCandleRadarDataSet set) {

    }

    @Override
    public void drawExtras(Canvas c) {
        if (indices == null) {
            return;
        }

        CandleData candleData = mChart.getCandleData();
        for (Highlight high : indices) {
            ICandleDataSet set = candleData.getDataSetByIndex(high.getDataSetIndex());
            if (set == null || !set.isHighlightEnabled())
                continue;

            CandleEntry e = set.getEntryForXValue(high.getX(), high.getY());
            if (!isInBoundsX(e, set))
                continue;

            float lowValue = e.getLow() * mAnimator.getPhaseY();
            float highValue = e.getHigh() * mAnimator.getPhaseY();
            MPPointD pix = mChart.getTransformer(set.getAxisDependency())
                    .getPixelForValues(e.getX(), (lowValue + highValue) / 2f);
            float xp = (float) pix.x;

            mHighlightPaint.setColor(set.getHighLightColor());
            mHighlightPaint.setStrokeWidth(set.getHighlightLineWidth());
            mHighlightPaint.setTextSize(highlightSize);

            float xMin = mViewPortHandler.contentLeft();
            float xMax = mViewPortHandler.contentRight();
            float contentBottom = mViewPortHandler.contentBottom();
            //画竖线
            int halfPaddingVer = 5;//竖向半个padding
            int halfPaddingHor = 8;
            float textXHeight = 0;

            String textX;//高亮点的X显示文字
            Object data = e.getData();
            if (data != null && data instanceof String) {
                textX = (String) data;
            } else {
                textX = e.getX() + "";
            }
            if (!TextUtils.isEmpty(textX)) {//绘制x的值
                //先绘制文字框
                mHighlightPaint.setStyle(Paint.Style.STROKE);
                int width = Utils.calcTextWidth(mHighlightPaint, textX);
                int height = Utils.calcTextHeight(mHighlightPaint, textX);
                float left = Math.max(xMin, xp - width / 2F - halfPaddingVer);//考虑间隙
                float right = left + width + halfPaddingHor * 2;
                if (right > xMax) {
                    right = xMax;
                    left = right - width - halfPaddingHor * 2;
                }
                textXHeight = height + halfPaddingVer * 2;
                RectF rect = new RectF(left, 0, right, textXHeight);
                c.drawRect(rect, mHighlightPaint);
                //再绘制文字
                mHighlightPaint.setStyle(Paint.Style.FILL);
                Paint.FontMetrics metrics = mHighlightPaint.getFontMetrics();
                float baseY = (height + halfPaddingVer * 2 - metrics.top - metrics.bottom) / 2;
                c.drawText(textX, left + halfPaddingHor, baseY, mHighlightPaint);
            }
            //绘制竖线
            c.drawLine(xp, textXHeight, xp, mChart.getHeight(), mHighlightPaint);

            //判断是否画横线
            float y = high.getDrawY();
            float yMaxValue = mChart.getYChartMax();
            float yMinValue = mChart.getYChartMin();
            float yMin = getYPixelForValues(xp, yMaxValue);
            float yMax = getYPixelForValues(xp, yMinValue);
            Log.e("HighlightCandleRenderer",y+":"+contentBottom);
//            if (y >= 0 && y <= contentBottom) {//在区域内即绘制横线
            if(isTouchOn){
                //先绘制文字框
                mHighlightPaint.setStyle(Paint.Style.STROKE);
                float yValue = (yMax - y) / (yMax - yMin) * (yMaxValue - yMinValue) + yMinValue;
                String text = format.format(yValue);
                int width = Utils.calcTextWidth(mHighlightPaint, text);
                int height = Utils.calcTextHeight(mHighlightPaint, text);
                float top = Math.max(0, y - height / 2F - halfPaddingVer);//考虑间隙
                float bottom = top + height + halfPaddingVer * 2;
                if (bottom > contentBottom) {
                    bottom = contentBottom;
                    top = bottom - height - halfPaddingVer * 2;
                }
                RectF rect = new RectF(xMax - width - halfPaddingHor * 2, top, xMax, bottom);
                c.drawRect(rect, mHighlightPaint);
                //再绘制文字
                mHighlightPaint.setStyle(Paint.Style.FILL);
                Paint.FontMetrics metrics = mHighlightPaint.getFontMetrics();
                float baseY = (top + bottom - metrics.top - metrics.bottom) / 2;
                c.drawText(text, xMax - width - halfPaddingHor, baseY, mHighlightPaint);
                //绘制横线
                c.drawLine(0, y, xMax - width - halfPaddingHor * 2, y, mHighlightPaint);
            }
        }
        indices = null;
    }

    @Override
    public void onTouch(boolean isTouchOn) {
        this.isTouchOn = isTouchOn;
    }
}
