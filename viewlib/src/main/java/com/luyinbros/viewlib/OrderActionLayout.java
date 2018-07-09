package com.luyinbros.viewlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.weimu.common.view.Dimens;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Andy Hong on 1/2/2018.
 */

public class OrderActionLayout extends LinearLayout {
    private List<ButtonInfo> buttonInfoList = new ArrayList<>();
    private OnOrderActionClickListener onOrderActionClickListener;


    public OrderActionLayout(Context context) {
        super(context);
        init();
    }

    public OrderActionLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrderActionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        setLayoutParams(layoutParams);
        clear();
    }

    public void setOnOrderActionClickListener(OnOrderActionClickListener onOrderActionClickListener) {
        this.onOrderActionClickListener = onOrderActionClickListener;
    }

    public void clear() {
        buttonInfoList.clear();
        removeAllViews();
    }

    public void addActionDefaultButton(int action, String actionName) {
        TextView actionView = new TextView(getContext());
        setActionTextLayoutParam(actionView);
        actionView.setTextColor(0xFF666666);
        ViewCompat.setBackground(actionView, ContextCompat.getDrawable(getContext(), R.drawable.bg_order_action_button));
        actionView.setText(actionName);
        actionView.setPadding(Dimens.px(getContext(), 5),
                Dimens.px(getContext(), 5),
                Dimens.px(getContext(), 5),
                Dimens.px(getContext(), 5));
        addActionButton(action, actionView);
    }

    public void addActionAttractiveButton(int action, String actionName) {
        TextView actionView = new TextView(getContext());
        setActionTextLayoutParam(actionView);
        actionView.setTextColor(0xFF333333);
        ViewCompat.setBackground(actionView, ContextCompat.getDrawable(getContext(), R.drawable.bg_order_action_attractive_button));
        actionView.setText(actionName);
        actionView.setPadding(Dimens.px(getContext(), 5),
                Dimens.px(getContext(), 5),
                Dimens.px(getContext(), 5),
                Dimens.px(getContext(), 5));
        addActionButton(action, actionView);
    }

    private void setActionTextLayoutParam(TextView textView) {
        textView.setGravity(Gravity.CENTER);
        textView.setMinEms(5);
        textView.setMaxEms(8);
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addActionButton(final int action, View view) {
        if (buttonInfoList.size() != 0) {
            View lastView = buttonInfoList.get(buttonInfoList.size() - 1).getView();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) lastView.getLayoutParams();
            marginLayoutParams.rightMargin = Dimens.px(getContext(), Dimens.px(getContext(), 5));
            lastView.setLayoutParams(marginLayoutParams);
        }
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderActionClickListener != null) {
                    onOrderActionClickListener.onActionClick(action);
                }
            }
        });
        addView(view);
        buttonInfoList.add(new ButtonInfo(action,
                buttonInfoList.size() - 1,
                view));
    }

    public View getButton(int index) {
        return buttonInfoList.get(index).getView();
    }

    public View getButtonBy(int action) {
        for (ButtonInfo buttonInfo : buttonInfoList) {
            if (action == buttonInfo.getAction()) {
                return buttonInfo.getView();
            }
        }
        return null;
    }

    public interface OnOrderActionClickListener {
        void onActionClick(int action);
    }

    static class ButtonInfo {
        private final int action;
        private final int index;
        private View view;

        public ButtonInfo(int action, int index, View view) {
            this.action = action;
            this.index = index;
            this.view = view;
        }

        public View getView() {
            return view;
        }

        public int getIndex() {
            return index;
        }

        public int getAction() {
            return action;
        }
    }
}
