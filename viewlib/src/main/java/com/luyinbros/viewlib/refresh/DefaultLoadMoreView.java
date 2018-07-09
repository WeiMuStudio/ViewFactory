package com.luyinbros.viewlib.refresh;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luyinbros.viewlib.R;


public class DefaultLoadMoreView extends LinearLayout implements PullUpRefreshView {
    private TextView tvText;
    private ProgressBar progressBar;
    private OnLoadMoreRefreshListener onLoadMoreRefreshListener;
    private int mStatus;


    public DefaultLoadMoreView(Context context) {
        super(context);
        inflate(context, R.layout.layout_loadmore, this);
        tvText = findViewById(R.id.tv_load_more_text);
        progressBar = findViewById(R.id.pb_refresh);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadMoreRefreshListener != null && mStatus == STATUS_FAILURE) {
                    notifyPullUpStatusChanged(STATUS_REFRESH);
                }
            }
        });
    }

    @Override
    public void setOnLoadMoreRefreshListener(OnLoadMoreRefreshListener onLoadMoreRefreshListener) {
        this.onLoadMoreRefreshListener = onLoadMoreRefreshListener;
    }

    @Override
    public int getLoadMoreStatus() {
        return mStatus;
    }

    @Override
    public void notifyPullUpStatusChanged(int status) {
        if (mStatus != status) {
            this.mStatus = status;
            if (mStatus == STATUS_IDLE) {
                tvText.setText("上拉加载更多");
                progressBar.setVisibility(View.GONE);
            } else if (mStatus == STATUS_REFRESH) {
                tvText.setText("加载中");
                progressBar.setVisibility(View.VISIBLE);
                if (onLoadMoreRefreshListener != null) {
                    onLoadMoreRefreshListener.onLoadMoreRefresh();
                }
            } else if (mStatus == STATUS_NO_MORE) {
                tvText.setText("没有更多了");
                progressBar.setVisibility(View.GONE);
            } else if (mStatus == STATUS_FAILURE) {
                tvText.setText("上拉加载错误，点击重新刷新");
                progressBar.setVisibility(View.GONE);
            }
        }

    }
}
