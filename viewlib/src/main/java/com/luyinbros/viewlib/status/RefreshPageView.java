package com.luyinbros.viewlib.status;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luyinbros.viewlib.R;

import org.weimu.common.view.StatusLayoutController;


public class RefreshPageView extends LinearLayout implements StatusLayoutController.StatusView {
    private TextView tvRefresh;
    private CharSequence mRefreshText;
    private OnPageRefreshListener onPageRefreshListener;

    public RefreshPageView(Context context) {
        super(context);
        inflate(context, R.layout.layout_refresh_default, this);
        tvRefresh = findViewById(R.id.refreshTextView);
    }

    public void setOnPageRefreshListener(OnPageRefreshListener onPageRefreshListener) {
        this.onPageRefreshListener = onPageRefreshListener;
    }

    public void setRefreshText(CharSequence text) {
        this.mRefreshText = text;
        if (tvRefresh != null) {
            tvRefresh.setText(mRefreshText);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public int getStatus() {
        return PageStatus.REFRESH;
    }

    @Override
    public void onAttachFromStatusLayoutController(StatusLayoutController controller) {
        if (onPageRefreshListener != null) {
            onPageRefreshListener.onPageRefresh();
        }
    }

    @Override
    public void onDetachedFromStatusLayoutController(StatusLayoutController controller) {

    }


}
