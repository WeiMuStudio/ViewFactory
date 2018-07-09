package com.luyinbros.viewlib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.luyinbros.viewlib.R;
import com.luyinbros.viewlib.dialog.core.DialogConstants;
import com.luyinbros.viewlib.dialog.core.DialogUtils;
import com.luyinbros.viewlib.dialog.core.DialogView;
import com.luyinbros.viewlib.dialog.view.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class DateDialogBuilder {
    private Context mContext;
    private Bundle mBundle;
    private OnDatePickListener onDatePickListener;
    private long mCurrentDate = 0L;
    private long mMaxDate = 0L;
    private long mMinDate = 0L;
    private Calendar calendar = Calendar.getInstance();

    private FragmentManager mFragmentManager;
    private DatePickerDialog mDatePickerDialog;

    /**
     * 以context为请求体构造
     *
     * @param context 当前上下稳
     */
    public DateDialogBuilder(@Nullable Context context) {
        mContext = DialogUtils.getDialogContext(context);
        mFragmentManager = DialogUtils.getFragmentManager(context);
        mBundle = new Bundle();
        mBundle.putCharSequence(DialogConstants.TITLE_TEXT,
                mContext == null ? "" : mContext.getString(R.string.dialog_date_dialog_title_default));
        mBundle.putCharSequence(DialogConstants.POSITIVE_TEXT,
                mContext == null ? "" : mContext.getString(R.string.dialog_positive_text_default));
        mBundle.putCharSequence(DialogConstants.NEGATIVE_TEXT,
                mContext == null ? "" : mContext.getString(R.string.dialog_negative_text_default));
    }

    /**
     * 以fragment为请求体构造
     *
     * @param fragment 请求的fragment
     */
    public DateDialogBuilder(@Nullable Fragment fragment) {
        mContext = DialogUtils.getDialogContext(fragment);
        mFragmentManager = DialogUtils.getFragmentManager(fragment);
        mBundle = new Bundle();
        mBundle.putCharSequence(DialogConstants.TITLE_TEXT,
                mContext == null ? "" : mContext.getString(R.string.dialog_date_dialog_title_default));
        mBundle.putCharSequence(DialogConstants.POSITIVE_TEXT,
                mContext == null ? "" : mContext.getString(R.string.dialog_positive_text_default));
        mBundle.putCharSequence(DialogConstants.NEGATIVE_TEXT,
                mContext == null ? "" : mContext.getString(R.string.dialog_negative_text_default));
    }


    public DateDialogBuilder setDate(long currentDate) {
        this.mCurrentDate = currentDate;
        return this;
    }

    public DateDialogBuilder setDate(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        this.mCurrentDate = calendar.getTimeInMillis();
        return this;
    }

    public DateDialogBuilder setMaxDate(long maxTime) {
        this.mMaxDate = maxTime;
        return this;
    }

    public DateDialogBuilder setMaxDate(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        this.mMaxDate = calendar.getTimeInMillis();
        return this;
    }

    public DateDialogBuilder setMinDate(long minDate) {
        this.mMinDate = minDate;
        return this;
    }

    public DateDialogBuilder setMinDate(int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        this.mMinDate = calendar.getTimeInMillis();
        return this;
    }

    public DateDialogBuilder setOnDatePickListener(OnDatePickListener onDatePickListener) {
        this.onDatePickListener = onDatePickListener;
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        if (mContext != null && mFragmentManager != null) {
            if (mDatePickerDialog == null) {
                mDatePickerDialog = new DatePickerDialog();
            }
            mBundle.putLong("currentDate", mCurrentDate);
            mBundle.putLong("maxDate", mMaxDate);
            mBundle.putLong("minDate", mMinDate);
            mDatePickerDialog.setOnDatePickListener(onDatePickListener);
            DialogFactory.showDialog(mDatePickerDialog, mFragmentManager, mBundle, DialogFactory.DATE_DIALOG_NAME);
        }
    }
    public interface OnDatePickListener {
        void onDatePick(DialogView dialog,
                        Date date);
    }

}
