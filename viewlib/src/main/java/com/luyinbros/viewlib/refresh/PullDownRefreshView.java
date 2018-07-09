package com.luyinbros.viewlib.refresh;

public interface PullDownRefreshView {

    void setOnRefreshListener(OnPullDownRefreshListener onPullDownRefreshListener);

    void notifyPullDownRefresh();

    void notifyPullDownRefreshComplete();

    void setRefreshEnable(boolean isEnable);
}
