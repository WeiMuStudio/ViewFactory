package com.luyinbros.viewlib.dialog.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.luyinbros.viewlib.R;
import com.luyinbros.viewlib.dialog.DateDialogBuilder;
import com.luyinbros.viewlib.dialog.core.DialogConstants;
import com.luyinbros.viewlib.dialog.core.DialogView;

import java.util.Calendar;
import java.util.Date;


public class DatePickerDialog extends DialogFragment implements DialogView {
    private TextView mTitleTextView;
    private DatePicker mDatePicker;
    private TextView mPositiveTextView;
    private TextView mNegativeTextView;

    private CharSequence mTitle;
    private CharSequence mPositiveText = "";
    private CharSequence mNegativeText = "";
    private long mCurrentDate = -1;
    private long mMaxDate = -1;
    private long mMinDate = -1;
    private DateDialogBuilder.OnDatePickListener onDatePickListener;
    private Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DatePickerDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_date_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleTextView = view.findViewById(R.id.tv_title);
        mDatePicker = view.findViewById(R.id.mDatePicker);
        mPositiveTextView = view.findViewById(R.id.action_confirm);
        mNegativeTextView = view.findViewById(R.id.action_cancel);
        Bundle bundle = getArguments();
        if (bundle != null) {
            setTitle(bundle.getCharSequence(DialogConstants.TITLE_TEXT));
            setPositiveText(bundle.getCharSequence(DialogConstants.POSITIVE_TEXT));
            setNegativeText(bundle.getCharSequence(DialogConstants.NEGATIVE_TEXT));
            setDate(bundle.getLong("currentDate", -1));
            setMaxDate(bundle.getLong("maxDate", -1));
            setMinDate(bundle.getLong("minDate", -1));
        }

    }

    protected void setDate(long currentDate) {
        mCurrentDate = currentDate;
        calendar.setTime(new Date(currentDate));
        mDatePicker.updateDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }


    protected void setMaxDate(long maxDate) {
        if (maxDate > 0) {
            this.mMaxDate = maxDate;
            mDatePicker.setMaxDate(mMaxDate);
        }

    }

    protected void setMinDate(long minDate) {
        if (minDate > 0) {
            this.mMinDate = minDate;
            mDatePicker.setMinDate(mMinDate);
        }
    }


    protected void setTitle(CharSequence title) {
        this.mTitle = title;
        if (mTitleTextView != null) {
            if (TextUtils.isEmpty(mTitle)) {
                mTitleTextView.setVisibility(View.GONE);
            } else {
                mTitleTextView.setText(mTitle);
                mTitleTextView.setVisibility(View.VISIBLE);
            }
        }

    }

    protected void setNegativeText(CharSequence negativeText) {
        this.mNegativeText = negativeText;
        if (mNegativeTextView != null) {
            if (TextUtils.isEmpty(mNegativeText)) {
                mNegativeTextView.setVisibility(View.GONE);
                mNegativeTextView.setOnClickListener(null);
            } else {
                mNegativeTextView.setText(mNegativeText);
                mNegativeTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                mPositiveTextView.setVisibility(View.VISIBLE);
            }
        }

    }


    protected void setPositiveText(CharSequence positiveText) {
        this.mPositiveText = positiveText;
        if (mPositiveTextView != null) {
            if (TextUtils.isEmpty(mPositiveText)) {
                mPositiveTextView.setVisibility(View.GONE);
                mPositiveTextView.setOnClickListener(null);
            } else {
                mPositiveTextView.setText(mPositiveText);
                mPositiveTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onDatePickListener != null) {
                            calendar.set(Calendar.YEAR, mDatePicker.getYear());
                            calendar.set(Calendar.MONTH, mDatePicker.getMonth());
                            calendar.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
                            onDatePickListener.onDatePick(DatePickerDialog.this,
                                    new Date(calendar.getTimeInMillis()));
                        }
                        dismiss();
                    }
                });
                mPositiveTextView.setVisibility(View.VISIBLE);
            }
        }

    }

    public void setOnDatePickListener(DateDialogBuilder.OnDatePickListener onDatePickListener) {
        this.onDatePickListener = onDatePickListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DialogDesignHelper.bottom(getContext(), getDialog());
    }

}
