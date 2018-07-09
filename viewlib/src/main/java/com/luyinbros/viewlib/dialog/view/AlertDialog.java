package com.luyinbros.viewlib.dialog.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luyinbros.viewlib.R;
import com.luyinbros.viewlib.dialog.core.DialogAction;
import com.luyinbros.viewlib.dialog.core.DialogBundle;
import com.luyinbros.viewlib.dialog.core.DialogConstants;
import com.luyinbros.viewlib.dialog.core.DialogResultReceiver;
import com.luyinbros.viewlib.dialog.core.DialogUtils;
import com.luyinbros.viewlib.dialog.core.DialogView;
import com.luyinbros.viewlib.dialog.core.OnDialogButtonClickListener;

public class AlertDialog extends DialogFragment implements DialogView {
    private static final String TAG = "AlertDialog";
    private TextView mTitleTextView;
    private TextView mMessageTextView;
    private TextView mPositiveTextView;
    private TextView mNegativeTextView;

    private int requestCode = -1;
    private CharSequence mTitle = "";
    private CharSequence mMessage = "";
    private CharSequence mPositiveText = "";
    private CharSequence mNegativeText = "";
    private OnDialogButtonClickListener onPositiveClickListener;
    private OnDialogButtonClickListener onNegativeClickListener;
    private DialogResultReceiver mOnResultReceiver;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnResultReceiver = DialogUtils.getReceiver(this);
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, DialogDesignHelper.DEFAULT_STYLE);
        if (getArguments() != null) {
            requestCode = getArguments().getInt(DialogConstants.DIALOG_REQUEST_CODE, -1);
        }
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_alert_default, container, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleTextView = view.findViewById(R.id.tv_dialog_title);
        mMessageTextView = view.findViewById(R.id.tv_dialog_message);
        mPositiveTextView = view.findViewById(R.id.dialog_action_positive);
        mNegativeTextView = view.findViewById(R.id.dialog_action_negative);
        Bundle bundle = getArguments();
        if (bundle != null) {
            setTitle(bundle.getCharSequence(DialogConstants.TITLE_TEXT));
            setMessage(bundle.getCharSequence(DialogConstants.MESSAGE_TEXT));
            setPositiveText(bundle.getCharSequence(DialogConstants.POSITIVE_TEXT));
            setNegativeText(bundle.getCharSequence(DialogConstants.NEGATIVE_TEXT));
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DialogDesignHelper.center(getContext(), getDialog());
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

    protected void setMessage(CharSequence message) {
        this.mMessage = message;
        if (mMessageTextView != null) {
            if (TextUtils.isEmpty(mMessage)) {
                mMessageTextView.setVisibility(View.GONE);
            } else {
                mMessageTextView.setText(mMessage);
                mMessageTextView.setVisibility(View.VISIBLE);
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
                        if (onNegativeClickListener != null) {
                            onNegativeClickListener.onClick(AlertDialog.this);
                        } else {
                            if (DialogUtils.isSupportCallBack(requestCode, mOnResultReceiver)) {
                                if (mOnResultReceiver != null) {
                                    DialogBundle dialogBundle = new DialogBundle();
                                    dialogBundle.putDialogAction(DialogAction.NEGATIVE);
                                    mOnResultReceiver.onDialogResult(requestCode, onAlterDialogResult(dialogBundle));
                                }
                            }

                            dismiss();
                        }
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
                        if (onPositiveClickListener != null) {
                            onPositiveClickListener.onClick(AlertDialog.this);
                        } else {
                            if (DialogUtils.isSupportCallBack(requestCode, mOnResultReceiver)) {
                                if (mOnResultReceiver != null) {
                                    DialogBundle dialogBundle = new DialogBundle();
                                    dialogBundle.putDialogAction(DialogAction.POSITIVE);
                                    mOnResultReceiver.onDialogResult(requestCode, onAlterDialogResult(dialogBundle));
                                }
                            }
                            dismiss();
                        }
                    }
                });
                mPositiveTextView.setVisibility(View.VISIBLE);
            }
        }

    }


    @NonNull
    protected DialogBundle onAlterDialogResult(DialogBundle dialogBundle) {
        return dialogBundle;
    }

    @Override
    public void onCancel(android.content.DialogInterface dialog) {
        super.onCancel(dialog);
        if (DialogUtils.isSupportCallBack(requestCode, mOnResultReceiver)) {
            DialogBundle dialogBundle = new DialogBundle();
            dialogBundle.putDialogAction(DialogAction.CANCEL);
            mOnResultReceiver.onDialogResult(requestCode, onAlterDialogResult(dialogBundle));
        }
    }

    public void setOnNegativeClickListener(OnDialogButtonClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }

    public void setOnPositiveClickListener(OnDialogButtonClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

}
