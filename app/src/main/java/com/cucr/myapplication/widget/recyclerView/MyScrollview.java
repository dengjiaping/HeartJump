package com.cucr.myapplication.widget.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.cucr.myapplication.utils.MyLogger;

/**
 * Created by 911 on 2017/7/10.
 * 嵌套 recyclerView 的 scorllView
 */


public class MyScrollview extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private boolean isLoading;

    public MyScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private int i;

    //自定义上拉加载
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY && !isLoading && scrollY > 0) {
            i++;
            MyLogger.jLog().i("调用了..."+i);

            mListener.onLoadMore();
        }
    }

    public void setIsLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    public void setListener(LoadMoreListener listener) {
        mListener = listener;
    }

    private LoadMoreListener mListener;

    public interface LoadMoreListener {
        void onLoadMore();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
