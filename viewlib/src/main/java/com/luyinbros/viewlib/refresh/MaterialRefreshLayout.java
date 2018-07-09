package com.luyinbros.viewlib.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

public class MaterialRefreshLayout extends SwipeRefreshLayout implements PullDownRefreshView {

    private OnPullDownRefreshListener mOnRefreshListener;

    public MaterialRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public MaterialRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
    @Override
    public void notifyPullDownRefresh() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onPullDownRefresh();
        }
        setRefreshing(true);
    }

    @Override
    public void notifyPullDownRefreshComplete() {
        setRefreshing(false);
    }

    @Override
    public void setRefreshEnable(boolean isEnable) {
        setEnabled(isEnable);
    }

    @Override
    public void setOnRefreshListener(OnPullDownRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        super.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onPullDownRefresh();
                }
            }
        });
    }
}
