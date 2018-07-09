package com.luyinbros.viewlib.status;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.luyinbros.viewlib.R;

import org.weimu.common.view.StatusLayoutController;


public class NetworkFailurePageView extends LinearLayout implements StatusLayoutController.StatusView {
    private StatusLayoutController mStatusLayoutController;

    public NetworkFailurePageView(Context context) {
        super(context);
        inflate(context, R.layout.layout_network_failure_default, this);
    }

    public void setClickRefreshEnable() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatusLayoutController != null && mStatusLayoutController.hasStatus(PageStatus.REFRESH)) {
                    mStatusLayoutController.notifyStatus(PageStatus.REFRESH);
                }
            }
        });
    }

    @Override
    public int getStatus() {
        return PageStatus.NETWORK_FAILURE;
    }

    @Override
    public void onAttachFromStatusLayoutController(StatusLayoutController controller) {
        this.mStatusLayoutController = controller;
    }

    @Override
    public void onDetachedFromStatusLayoutController(StatusLayoutController controller) {
        this.mStatusLayoutController = null;
    }


}
