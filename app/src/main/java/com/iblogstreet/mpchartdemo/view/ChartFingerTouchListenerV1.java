package com.iblogstreet.mpchartdemo.view;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * 图表长按及滑动手指监听
 */
public class ChartFingerTouchListenerV1 implements View.OnTouchListener {

    private GestureDetector mDetector;
    private HighlightListener mListener;
    private boolean mIsLongPress = false;
    private CombinedChart[] combinedCharts;

    public ChartFingerTouchListenerV1(HighlightListener listener, Context context, CombinedChart... charts) {
        this.combinedCharts = charts;
        mListener = listener;

        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                mIsLongPress = true;

                CombinedChart combinedChart = findViewByPoint(e);
                if (mListener != null) {
                    mListener.enableHighlight(combinedChart);
                }

                Highlight h = combinedChart.getHighlightByTouchPoint(e.getX(), e.getY());
                Log.e("GestureDetector", "onLongPress" + e.getX() + ":" + e.getY());
                if (h != null) {
                    h.setDraw(e.getX(), e.getY() - combinedChart.getTop());
                    combinedChart.highlightValue(h, true);
                    combinedChart.disableScroll();
                }

            }

        });
    }

    private CombinedChart findViewByPoint(MotionEvent event) {
        for (CombinedChart mChart : combinedCharts) {
            //1.事件普通传递
            mChart.onTouchEvent(event);
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            boolean touchPointInView = isTouchPointInView(mChart, new Point(x, y));
            if (touchPointInView) {
                return mChart;
            }
        }
        return null;
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        for (CombinedChart combinedChart : combinedCharts) {

            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            boolean touchPointInView = isTouchPointInView(combinedChart, new Point(x, y));
            if (touchPointInView) {
                combinedChart.onTouchEvent(event);
            }
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                mIsLongPress = false;
                combinedChart.highlightValue(null, true);
                if (mListener != null) {
                    mListener.disableHighlight();
                }
                combinedChart.enableScroll();
            }
            if (mIsLongPress && touchPointInView && event.getAction() == MotionEvent.ACTION_MOVE) {
                if (mListener != null) {
                    mListener.enableHighlight(combinedChart);
                }
                Highlight h = combinedChart.getHighlightByTouchPoint(event.getX(), event.getY());
                if (h != null) {
                    h.setDraw(event.getX(), event.getY() - combinedChart.getTop());
                    combinedChart.highlightValue(h, true);
                    combinedChart.disableScroll();
                }
            }
        }
        return true;
    }

    public interface HighlightListener {
        void enableHighlight(CombinedChart mChart);

        void disableHighlight();
    }
}
