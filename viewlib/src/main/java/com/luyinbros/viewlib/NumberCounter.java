package com.luyinbros.viewlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * View:
 * <p>
 * ----------------
 * - number +
 * ----------------
 */

public class NumberCounter extends LinearLayout {
    private TextView tvSub;
    private TextView tvAdd;
    private TextView tvCurrentNumber;
    private int minNumber = 0;
    private int maxNumber = Integer.MAX_VALUE;
    private int currentNumber = 0;
    private OnNumberChangeListener onNumberChangeListener;

    public NumberCounter(Context context) {
        super(context);
        init(context);
    }

    public NumberCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.widget_number_counter, this);
        tvSub = (TextView) findViewById(R.id.tv_sub);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvCurrentNumber = (TextView) findViewById(R.id.tv_num);
        tvSub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNumberChangeListener != null) {
                    if (currentNumber > minNumber) {
                        onNumberChangeListener.onNumberChange(currentNumber - 1, false);
                    }
                }
            }
        });
        tvAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNumberChangeListener != null) {
                    if (maxNumber > currentNumber) {
                        onNumberChangeListener.onNumberChange(currentNumber + 1, true);
                    }
                }
            }
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        tvSub.setClickable(enabled);
        tvAdd.setClickable(enabled);
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
        tvCurrentNumber.setText(String.valueOf(currentNumber));

    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public interface OnNumberChangeListener {
        /**
         * 数量变化，但是不更新到视图上
         *
         * @param futureNumber 变化后的数据
         */
        void onNumberChange(int futureNumber, boolean isAdd);
    }
}
