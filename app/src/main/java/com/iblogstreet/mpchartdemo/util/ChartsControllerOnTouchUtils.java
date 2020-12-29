package com.iblogstreet.mpchartdemo.util;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.iblogstreet.mpchartdemo.view.StockCombinedChart;

public class ChartsControllerOnTouchUtils {
    private static int currI;
 
    public static void bindWithExpand(final View flMainTouch, final View expandView, final CombinedChart... mCharts) {
        //父布局统一控制集体分发，那么N图高亮联动和缩放等复杂问题则一切解决了，只需要如下微调
        flMainTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                Point touchPoint = new Point(x, y);
 
                if (isTouchPointInView(expandView, touchPoint)) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        expandView.performClick();
                    }
                    return true;
                }
                dispatchToChart(flMainTouch, event, mCharts);
                return true;
            }
        });
    }
 
 
    public static void bind(final View flMainTouch, final CombinedChart... mCharts) {
        //父布局统一控制集体分发，那么N图高亮联动和缩放等复杂问题则一切解决了，只需要如下微调
        flMainTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dispatchToChart(flMainTouch, event, mCharts);
                return true;
            }
        });
    }
    public static void bind(final View flMainTouch, final StockCombinedChart... mCharts) {
        //父布局统一控制集体分发，那么N图高亮联动和缩放等复杂问题则一切解决了，只需要如下微调
        flMainTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dispatchToChart(flMainTouch, event, mCharts);
                return true;
            }
        });
    }
 
    public static void dispatchToChart(final View flMainTouch, MotionEvent event, final CombinedChart... mCharts) {
        for (int i = 0; i < mCharts.length; i++) {
            CombinedChart mChart = mCharts[i];
 
            //1.事件普通传递
            mChart.onTouchEvent(event);
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            boolean touchPointInView = isTouchPointInView(mCharts[i], new Point(x, y));
 
            LineScatterCandleRadarDataSet dataNotBarSet = null;
            BarLineScatterCandleBubbleDataSet barDataSet = null;
 
            if (mChart.getLineData() != null) {
                dataNotBarSet = (LineScatterCandleRadarDataSet) mChart.getLineData().getDataSetByIndex(0);
            } else if (mChart.getScatterData() != null) {
                dataNotBarSet = (LineScatterCandleRadarDataSet) mChart.getScatterData().getDataSetByIndex(0);
            } else if (mChart.getCandleData() != null) {
                dataNotBarSet = (LineScatterCandleRadarDataSet) mChart.getCandleData().getDataSetByIndex(0);
            } else if (mChart.getCombinedData() != null) {
//                        VolBarDataSet cannot be cast to com.github.mikephil.charting.data.LineScatterCandleRadarDataSet
//                        BarDataSet barDataSet ;BarLineScatterCandleBubbleDataSet
                IBarLineScatterCandleBubbleDataSet<? extends Entry> combinedDataSet = mChart.getCombinedData().getDataSetByIndex(0);
                if (combinedDataSet instanceof LineScatterCandleRadarDataSet) {
                    dataNotBarSet = (LineScatterCandleRadarDataSet) combinedDataSet;
                } else {
                    barDataSet = (BarLineScatterCandleBubbleDataSet) combinedDataSet;
                }
            } else if (mChart.getBarData() != null) {
                barDataSet = (BarLineScatterCandleBubbleDataSet) mChart.getBarData().getDataSetByIndex(0);
            } else {
                Log.i("rex", "高亮数据异常" + mChart.toString());
                continue;
            }
 
            if (dataNotBarSet != null) {
                dataNotBarSet.setHighlightEnabled(true);
//                if (SettingsUtil.Companion.isNightTheme()) {
                    dataNotBarSet.setHighLightColor(Color.parseColor("#C0C3CB"));
//                } else {
//                    dataNotBarSet.setHighLightColor(Color.parseColor("#0F3457"));
//                }
            }
            if (barDataSet != null) {
                barDataSet.setHighlightEnabled(true);
//                if (SettingsUtil.Companion.isNightTheme()) {
                    barDataSet.setHighLightColor(Color.parseColor("#C0C3CB"));
//                } else {
//                    barDataSet.setHighLightColor(Color.parseColor("#0F3457"));
//                }
            }
 
            if (touchPointInView) {
                //此处控制高亮线
                if (dataNotBarSet != null) {
                    dataNotBarSet.setDrawHorizontalHighlightIndicator(true);
                }
                if (barDataSet != null) {
                    barDataSet.setHighlightEnabled(true);
                }
 
                if (currI != i) {
                    Log.i("rex", "当前触摸点在第" + (i + 1) + "张图上面");
                    currI = i;
                }
                //此处添加marker
//                mChart.setMarker(new LeftMarkView(flMainTouch.getContext()));
            } else
 
            {
                if (dataNotBarSet != null) {
                    dataNotBarSet.setDrawHorizontalHighlightIndicator(false);
                }
//                if (barDataSet != null) {
//                    barDataSet.setHighlightEnabled(false);
//                }
                mChart.setMarker(null);
            }
        }
 
    }

    public static void dispatchToChart(final View flMainTouch, MotionEvent event, final StockCombinedChart... mCharts) {
        for (int i = 0; i < mCharts.length; i++) {
            StockCombinedChart mChart = mCharts[i];

            //1.事件普通传递
            mChart.onTouchEvent(event);
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            boolean touchPointInView = isTouchPointInView(mCharts[i], new Point(x, y));

            LineScatterCandleRadarDataSet dataNotBarSet = null;
            BarLineScatterCandleBubbleDataSet barDataSet = null;

            if (mChart.getLineData() != null) {
                dataNotBarSet = (LineScatterCandleRadarDataSet) mChart.getLineData().getDataSetByIndex(0);
            } else if (mChart.getScatterData() != null) {
                dataNotBarSet = (LineScatterCandleRadarDataSet) mChart.getScatterData().getDataSetByIndex(0);
            } else if (mChart.getCandleData() != null) {
                dataNotBarSet = (LineScatterCandleRadarDataSet) mChart.getCandleData().getDataSetByIndex(0);
            } else if (mChart.getCombinedData() != null) {
//                        VolBarDataSet cannot be cast to com.github.mikephil.charting.data.LineScatterCandleRadarDataSet
//                        BarDataSet barDataSet ;BarLineScatterCandleBubbleDataSet
                IBarLineScatterCandleBubbleDataSet<? extends Entry> combinedDataSet = mChart.getCombinedData().getDataSetByIndex(0);
                if (combinedDataSet instanceof LineScatterCandleRadarDataSet) {
                    dataNotBarSet = (LineScatterCandleRadarDataSet) combinedDataSet;
                } else {
                    barDataSet = (BarLineScatterCandleBubbleDataSet) combinedDataSet;
                }
            } else if (mChart.getBarData() != null) {
                barDataSet = (BarLineScatterCandleBubbleDataSet) mChart.getBarData().getDataSetByIndex(0);
            } else {
                Log.i("rex", "高亮数据异常" + mChart.toString());
                continue;
            }

            if (dataNotBarSet != null) {
                dataNotBarSet.setHighlightEnabled(true);
//                if (SettingsUtil.Companion.isNightTheme()) {
                dataNotBarSet.setHighLightColor(Color.parseColor("#C0C3CB"));
//                } else {
//                    dataNotBarSet.setHighLightColor(Color.parseColor("#0F3457"));
//                }
            }
            if (barDataSet != null) {
                barDataSet.setHighlightEnabled(true);
//                if (SettingsUtil.Companion.isNightTheme()) {
                barDataSet.setHighLightColor(Color.parseColor("#C0C3CB"));
//                } else {
//                    barDataSet.setHighLightColor(Color.parseColor("#0F3457"));
//                }
            }

            if (touchPointInView) {
                //此处控制高亮线
                if (dataNotBarSet != null) {
                    dataNotBarSet.setDrawHorizontalHighlightIndicator(true);
                }
                if (barDataSet != null) {
                    barDataSet.setHighlightEnabled(true);
                }

                if (currI != i) {
                    Log.i("rex", "当前触摸点在第" + (i + 1) + "张图上面");
                    currI = i;
                }
                //此处添加marker
//                mChart.setMarker(new LeftMarkView(flMainTouch.getContext()));
            } else

            {
                if (dataNotBarSet != null) {
                    dataNotBarSet.setDrawHorizontalHighlightIndicator(false);
                }
//                if (barDataSet != null) {
//                    barDataSet.setHighlightEnabled(false);
//                }
                mChart.setMarker(null);
            }
        }

    }


    // 判断一个具体的触摸点是否在 view 上；
    public static boolean isTouchPointInView(View view, Point point) {
        if (view == null && point == null) {
            throw new NullPointerException();
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (point.x >= left && point.x <= right && point.y >= top && point.y <= bottom) {
            return true;
        }
        return false;
    }
}