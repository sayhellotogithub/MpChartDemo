package com.iblogstreet.mpchartdemo.view;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 自定义CombinedChartRenderer 把Candle图、Line图 的渲染器替换成自定义渲染器
 */
public class HighlightCombinedRenderer extends CombinedChartRenderer {

    private float highlightSize;//图表高亮文字大小 单位:px
    private float barOffset;
    private float highLightWidth;


    public HighlightCombinedRenderer(CombinedChart chart, ChartAnimator animator,
                                     ViewPortHandler viewPortHandler, float highlightSize, float barOffset) {
        super(chart, animator, viewPortHandler);
        this.highlightSize = highlightSize;
        this.barOffset = barOffset;
    }

    public HighlightCombinedRenderer(CombinedChart chart, ChartAnimator animator,
                                     ViewPortHandler viewPortHandler, float highlightSize) {
        super(chart, animator, viewPortHandler);
        this.highlightSize = highlightSize;
        this.barOffset = 0;
    }

    public HighlightCombinedRenderer(CombinedChart chart, ChartAnimator animator,
                                     ViewPortHandler viewPortHandler, float highlightSize, float barOffset, float highLightWidth) {
        this(chart, animator, viewPortHandler, highlightSize, barOffset);
        this.highLightWidth = highLightWidth;
    }

    public void changeTouch(boolean isOnTouch) {
        for (DataRenderer renderer : mRenderers) {
            if (renderer instanceof HighlightTouchListener) {
                ((HighlightTouchListener) renderer).onTouch(isOnTouch);
            }
        }
    }


    @Override
    public void createRenderers() {
        mRenderers.clear();
        CombinedChart chart = (CombinedChart) mChart.get();
        if (chart == null)
            return;
        DrawOrder[] orders = chart.getDrawOrder();
        for (DrawOrder order : orders) {
            switch (order) {
                case BAR:
                    if (chart.getBarData() != null)
                        mRenderers.add(new HighlightBarRenderer(chart, mAnimator, mViewPortHandler, barOffset).setHighlightSize(highlightSize).setHighLineWidth(highLightWidth));
                    break;
                case BUBBLE:
                    if (chart.getBubbleData() != null)
                        mRenderers.add(new BubbleChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case LINE:
                    if (chart.getLineData() != null)
                        mRenderers.add(new HighlightLineRenderer(chart, mAnimator, mViewPortHandler)
                                .setHighlightSize(highlightSize));
                    break;
                case CANDLE:
                    if (chart.getCandleData() != null)
                        mRenderers.add(new HighlightCandleRenderer(chart, mAnimator, mViewPortHandler)
                                .setHighlightSize(highlightSize));
                    break;
                case SCATTER:
                    if (chart.getScatterData() != null)
                        mRenderers.add(new ScatterChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
            }
        }
    }
}
