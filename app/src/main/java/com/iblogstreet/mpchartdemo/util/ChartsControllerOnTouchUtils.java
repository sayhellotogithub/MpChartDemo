package com.iblogstreet.mpchartdemo.util;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;

public class ChartsControllerOnTouchUtils {
    private int currI;
    private HighlightListener highlightListener;

    public void bindWithExpand(final View flMainTouch, final View expandView, final CombinedChart... mCharts) {
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
                dispatchToChart(event, mCharts);
                return true;
            }
        });
    }


    public void bind(final View flMainTouch, final Chart... mCharts) {
        //父布局统一控制集体分发，那么N图高亮联动和缩放等复杂问题则一切解决了，只需要如下微调
        flMainTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dispatchToChart(event, mCharts);
                return true;
            }
        });
    }


    public void bind(HighlightListener highlightListener, final View flMainTouch, final Chart... mCharts) {
        this.highlightListener = highlightListener;
        bind(flMainTouch, mCharts);
    }

    public void dispatchToChart(MotionEvent event, final Chart... charts) {
        for (int i = 0; i < charts.length; i++) {
            Chart mChart = charts[i];
            mChart.onTouchEvent(event);

            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            //1.事件普通传递
            boolean touchPointInView = isTouchPointInView(charts[i], new Point(x, y));

            if (touchPointInView) {

                if (currI != i) {
                    Log.i("rex", "当前触摸点在第" + (i + 1) + "张图上面");
                    currI = i;
                }
                if(highlightListener!=null){
                    highlightListener.enableHighlight();
                }
            } else {
                mChart.setMarker(null);
                if(highlightListener!=null){
                    highlightListener.disableHighlight();
                }
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

    public interface HighlightListener {
        void enableHighlight();

        void disableHighlight();
    }
}