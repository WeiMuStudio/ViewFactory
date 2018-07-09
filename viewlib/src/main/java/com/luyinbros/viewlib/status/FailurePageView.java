package com.luyinbros.viewlib.status;

import android.content.Context;
import android.widget.LinearLayout;

import com.luyinbros.viewlib.R;

import org.weimu.common.view.StatusLayoutController;



public class FailurePageView extends LinearLayout implements StatusLayoutController.StatusView {

    public FailurePageView(Context context) {
        super(context);
        inflate(context, R.layout.layout_failure_default, this);
    }


    @Override
    public int getStatus() {
        return  PageStatus.FAILURE;
    }

    @Override
    public void onAttachFromStatusLayoutController(StatusLayoutController controller) {

    }

    @Override
    public void onDetachedFromStatusLayoutController(StatusLayoutController controller) {

    }


}
