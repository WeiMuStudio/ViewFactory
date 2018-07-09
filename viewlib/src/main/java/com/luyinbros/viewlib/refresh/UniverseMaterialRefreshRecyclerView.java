package com.luyinbros.viewlib.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luyinbros.viewlib.status.EmptyPageView;
import com.luyinbros.viewlib.status.NetworkFailurePageView;
import com.luyinbros.viewlib.status.OnPageRefreshListener;
import com.luyinbros.viewlib.status.PageStatus;
import com.luyinbros.viewlib.status.RefreshPageView;

import org.luyinbros.widget.recyclerview.CellSpan;
import org.luyinbros.widget.recyclerview.RecyclerViewCell;
import org.luyinbros.widget.recyclerview.ViewCellAdapter;
import org.weimu.common.view.StatusLayoutController;

/**
 * 上拉和下拉的冲突策略 只能当前界面手动取消
 */

public final class UniverseMaterialRefreshRecyclerView extends MaterialRefreshLayout implements PullDownRefreshView, PullUpRefreshView {
    private RecyclerView mRecyclerView;
    private StatusLayoutController mStatusController;
    private RecyclerView.Adapter mAdapter;
    private PullUpRefreshView mPullUpRefreshView;
    private PullDownRefreshView mPullDownRefreshView;
    private StatusLayoutController.StatusView mRefreshPageView;
    private StatusLayoutController.StatusView mEmptyPageView;
    private StatusLayoutController.StatusView mFailurePageView;
    private StatusLayoutController.StatusView mNetworkFailureView;
    private boolean isLoadMoreEnable = false;
    private OnPageRefreshListener onPageRefreshListener;
    private RecyclerView.LayoutManager mLayoutManager;

    public UniverseMaterialRefreshRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public UniverseMaterialRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRecyclerView = new RecyclerView(context);
        mStatusController = new StatusLayoutController(this);
        mPullDownRefreshView = new PullDownRefreshView() {
            @Override
            public void setOnRefreshListener(OnPullDownRefreshListener onRefreshListener) {
                UniverseMaterialRefreshRecyclerView.super.setOnRefreshListener(onRefreshListener);
            }

            @Override
            public void notifyPullDownRefresh() {
                UniverseMaterialRefreshRecyclerView.super.notifyPullDownRefresh();
            }

            @Override
            public void notifyPullDownRefreshComplete() {
                UniverseMaterialRefreshRecyclerView.super.notifyPullDownRefreshComplete();
            }

            @Override
            public void setRefreshEnable(boolean isEnable) {
                UniverseMaterialRefreshRecyclerView.super.setRefreshEnable(isEnable);
            }
        };
        addView(mRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 配置默认的状态视图，如果当前状态视图没有要求的话
     * 1、空白视图
     * 2、网络错误视图
     * 3、刷新视图
     */
    public void setDefaultStatusPage() {
        mEmptyPageView = new EmptyPageView(getContext());
        addStatusView(mEmptyPageView);

        mNetworkFailureView = new NetworkFailurePageView(getContext());
        addStatusView(mNetworkFailureView);

        mRefreshPageView = new RefreshPageView(getContext());
        addStatusView(mRefreshPageView);
    }

    /**
     * 添加状态视图
     *
     * @param statusView 当前需要添加的状态视图
     */
    public void addStatusView(StatusLayoutController.StatusView statusView) {
        int status = statusView.getStatus();
        switch (status) {
            case PageStatus.EMPTY:
                mEmptyPageView = statusView;
                break;
            case PageStatus.FAILURE:
                mFailurePageView = statusView;
                ((View) mFailurePageView).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyRefreshPage();
                    }
                });
                break;
            case PageStatus.REFRESH:
                mRefreshPageView = statusView;
                break;
            case PageStatus.NETWORK_FAILURE:
                mNetworkFailureView = statusView;
                ((View) mNetworkFailureView).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyRefreshPage();
                    }
                });
                break;
        }
        mStatusController.addStatusView(statusView);
    }

    /**
     * 改变状态视图
     *
     * @param status 当前视图状态
     */
    public void notifyStatusPage(int status) {
        setLoadMoreVisibleIfEnable(false);
        mStatusController.notifyStatus(status);
    }

    /**
     * 错误的页面视图
     */
    public void notifyFailurePage() {
        if (mFailurePageView != null) {
            setRefreshing(false);
            setLoadMoreVisibleIfEnable(false);
            setRefreshEnable(false);
            mStatusController.notifyStatus(PageStatus.FAILURE);
        }
    }

    /**
     * 网络错误视图
     */
    public void notifyNetworkFailurePage() {
        if (mNetworkFailureView != null) {
            setRefreshing(false);
            setLoadMoreVisibleIfEnable(false);
            setRefreshEnable(false);
            mStatusController.notifyStatus(PageStatus.NETWORK_FAILURE);
        }

    }

    /**
     * 空视图
     */
    private void notifyEmptyPage() {
        if (mEmptyPageView != null) {
            setRefreshEnable(true);
            setRefreshing(false);
            setLoadMoreVisibleIfEnable(true);
            mStatusController.notifyStatus(PageStatus.EMPTY);
        }

    }

    /**
     * 更新recyclerView ，如果adapter{@link RecyclerView.Adapter#getItemCount()}为0，则自动变成空白视图
     */
    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            if (mAdapter.getItemCount() == 0) {
                notifyEmptyPage();
            } else {
                notifyPageRefreshComplete();
            }
        }
    }

    /**
     * 通知为刷新视图
     */
    public void notifyRefreshPage() {
        if (mRefreshPageView != null) {
            setLoadMoreVisibleIfEnable(false);
            setRefreshEnable(false);
            if (onPageRefreshListener != null) {
                onPageRefreshListener.onPageRefresh();
            }
            mStatusController.notifyStatus(PageStatus.REFRESH);
        }

    }

    /**
     * 通知页面刷新完成
     */
    public void notifyPageRefreshComplete() {
        setRefreshEnable(true);
        setLoadMoreVisibleIfEnable(true);
        notifyPullDownRefreshComplete();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        mStatusController.notifyContent();
    }

    /**
     * 通知下拉刷新
     */
    @Override
    public void notifyPullDownRefresh() {
        mPullDownRefreshView.notifyPullDownRefresh();
    }

    /**
     * 下拉刷新完成
     */
    @Override
    public void notifyPullDownRefreshComplete() {
        mPullDownRefreshView.notifyPullDownRefreshComplete();
    }

    /**
     * 设置下拉刷新监听事件
     *
     * @param onRefreshListener 下拉刷新监听事件
     */
    @Override
    public void setOnRefreshListener(OnPullDownRefreshListener onRefreshListener) {
        mPullDownRefreshView.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 设置页面刷新的监听事件
     *
     * @param onPageRefreshListener 页面刷新
     */
    public void setOnPageRefreshListener(OnPageRefreshListener onPageRefreshListener) {
        this.onPageRefreshListener = onPageRefreshListener;
    }


    /**
     * 下拉刷新是否生效
     *
     * @param isEnable 如果为false则没有下拉刷新
     */
    @Override
    public void setRefreshEnable(boolean isEnable) {
        mPullDownRefreshView.setRefreshEnable(isEnable);
    }

    /**
     * 设置加载更多是否可见
     *
     * @param isVisible 如果为false就不可见
     */
    public void setLoadMoreVisibleIfEnable(boolean isVisible) {
        if (isLoadMoreEnable) {
            if (mAdapter != null) {
                if (mAdapter instanceof ViewCellAdapter) {
                    if (isVisible) {
                        ((ViewCellAdapter) mAdapter).setBottomCell((RecyclerViewCell) mPullUpRefreshView);
                    } else {
                        notifyPullUpStatusChanged(PullUpRefreshView.STATUS_IDLE);
                        ((ViewCellAdapter) mAdapter).removeBottomCell();
                    }

                }
            }
        }
    }

    /**
     * 上拉刷新是否可用
     *
     * @param enable 如果为false则不可用
     */
    public void setLoadMoreEnable(boolean enable) {
        isLoadMoreEnable = enable;
        if (mPullUpRefreshView == null) {
            mPullUpRefreshView = new LoadMoreCell(new DefaultLoadMoreView(getContext()));
        }
        notifyPullUpStatusChanged(LoadMoreCell.STATUS_IDLE);
    }


    /**
     * 设置上拉刷新监听事件
     *
     * @param onLoadMoreRefreshListener 上拉刷新监听器
     */
    @Override
    public void setOnLoadMoreRefreshListener(OnLoadMoreRefreshListener onLoadMoreRefreshListener) {
        if (mPullUpRefreshView != null) {
            mPullUpRefreshView.setOnLoadMoreRefreshListener(onLoadMoreRefreshListener);
        }
    }

    /**
     * 上拉刷新错误
     */
    public void notifyLoadMoreFailure() {
        notifyPullUpStatusChanged(PullUpRefreshView.STATUS_FAILURE);
    }

    /**
     * 没有更多了
     */
    public void notifyNoMore() {
        notifyPullUpStatusChanged(PullUpRefreshView.STATUS_NO_MORE);
    }

    public void notifyLoadMoreIdle() {
        notifyPullUpStatusChanged(PullUpRefreshView.STATUS_IDLE);
    }

    /**
     * 改变上拉刷新的状态，请不要调用
     */
    @Override
    public void notifyPullUpStatusChanged(int status) {
        if (isLoadMoreEnable && mPullUpRefreshView != null) {
            mPullUpRefreshView.notifyPullUpStatusChanged(status);
        }
    }

    @Override
    public int getLoadMoreStatus() {
        return mPullUpRefreshView != null ? mPullUpRefreshView.getLoadMoreStatus() : PullUpRefreshView.STATUS_INVALID;
    }

    /**
     * 为recyclerView设置适配器
     *
     * @param adapter 当前适配器
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter != null && mAdapter instanceof ViewCellAdapter) {
            ((ViewCellAdapter) mAdapter).setLayoutManager(mLayoutManager);
        }
    }

    /**
     * 获取recyclerView适配器
     *
     * @return 如果recyclerView有配置，返回
     */
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置recyclerView布局管理器
     *
     * @param layout 当前管理器
     */
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        this.mLayoutManager = layout;

        if (mAdapter == null || !(mAdapter instanceof ViewCellAdapter)) {
            mRecyclerView.setLayoutManager(layout);
        } else {
            ((ViewCellAdapter) mAdapter).setLayoutManager(mLayoutManager);
        }

    }

    /**
     * 获取recyclerView布局管理
     *
     * @return 返回当前recyclerView的布局管理
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    /**
     * 添加 recyclerView 装饰者
     *
     * @param decor 当前装饰者
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    /**
     * 添加滚动监听事件
     *
     * @param listener 当前监听事件
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
    }

    /**
     * 移除滚动监听事件
     *
     * @param listener 当前需要移除的监听事件
     */
    public void removeOnScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.removeOnScrollListener(listener);
    }

    /**
     * 滚动到指定位置
     *
     * @param position 滚动的重点
     */
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    /**
     * 平滑的滚动到指定位置
     *
     * @param position 当前位置
     */
    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    /**
     * 获取当前recyclerView
     *
     * @return 返回recyclerView，且不可能为空
     */
    @NonNull
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    private static class LoadMoreCell extends RecyclerViewCell<RecyclerView.ViewHolder> implements PullUpRefreshView, CellSpan {
        private PullUpRefreshView mPullUpRefreshView;
        private int mStatus;

        public LoadMoreCell(PullUpRefreshView pullUpRefreshView) {
            this.mPullUpRefreshView = pullUpRefreshView;
            mStatus = mPullUpRefreshView.getLoadMoreStatus();
        }

        @Override
        public void setOnLoadMoreRefreshListener(OnLoadMoreRefreshListener onLoadMoreRefreshListener) {
            mPullUpRefreshView.setOnLoadMoreRefreshListener(onLoadMoreRefreshListener);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder((View) mPullUpRefreshView) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mPullUpRefreshView.notifyPullUpStatusChanged(mStatus);
        }

        @Override
        public void notifyPullUpStatusChanged(int status) {
            this.mStatus = status;
            notifyDataSetChanged();
        }

        @Override
        public int getLoadMoreStatus() {
            return mStatus;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.addOnScrollListener(mOnScrollChangeListener);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            recyclerView.removeOnScrollListener(mOnScrollChangeListener);
        }

        @Override
        public void onDetachedFromAdapter() {
            super.onDetachedFromAdapter();
            mPullUpRefreshView.notifyPullUpStatusChanged(PullUpRefreshView.STATUS_IDLE);
        }

        @Override
        public int getItemCount() {
            return 1;
        }

        private RecyclerView.OnScrollListener mOnScrollChangeListener = new RecyclerView.OnScrollListener() {
            private int lastVisibleItemPosition;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                if (layoutManager instanceof LinearLayoutManager) {
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    final int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findLastPosition(lastPositions);
                }
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (mStatus == STATUS_IDLE) {
                    final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager == null) {
                        return;
                    }
                    if (layoutManager.getChildCount() > 0
                            && newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastVisibleItemPosition == layoutManager.getItemCount() - 1) {
                        notifyPullUpStatusChanged(STATUS_REFRESH);
                    }
                }

            }

            private int findLastPosition(int[] lastPositions) {
                int max = lastPositions[0];
                for (int value : lastPositions) {
                    if (value > max) {
                        max = value;
                    }
                }
                return max;
            }
        };

        @Override
        public int getSpanSize(int position, int spanTotalCount) {
            return spanTotalCount;
        }

    }
}
