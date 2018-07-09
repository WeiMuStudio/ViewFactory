package com.luyinbros.viewlib.refresh;

public interface PullUpRefreshView {
    int STATUS_INVALID = -1;
    int STATUS_FAILURE = 0;
    int STATUS_NO_MORE = 1;
    int STATUS_IDLE = 2;
    int STATUS_REFRESH = 3;

    void setOnLoadMoreRefreshListener(OnLoadMoreRefreshListener onLoadMoreRefreshListener);

    void notifyPullUpStatusChanged(int status);

    int getLoadMoreStatus();
}
