package com.luyinbros.viewlib.status;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luyinbros.viewlib.R;

import org.weimu.common.view.StatusLayoutController;


public class EmptyPageView extends LinearLayout implements StatusLayoutController.StatusView {
    private TextView tvEmpty;
    private CharSequence mEmptyText;

    public EmptyPageView(Context context) {
        super(context);
        inflate(context, R.layout.layout_empty_default, this);
        tvEmpty = findViewById(R.id.emptyTextView);
    }

    public void setEmptyText(CharSequence text) {
        this.mEmptyText = text;
        if (tvEmpty != null) {
            tvEmpty.setText(mEmptyText);
        }
    }

    @Override
    public int getStatus() {
        return PageStatus.EMPTY;
    }

    @Override
    public void onAttachFromStatusLayoutController(StatusLayoutController controller) {

    }

    @Override
    public void onDetachedFromStatusLayoutController(StatusLayoutController controller) {

    }


}
