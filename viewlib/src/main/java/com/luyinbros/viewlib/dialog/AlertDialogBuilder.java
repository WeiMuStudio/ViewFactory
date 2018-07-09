package com.luyinbros.viewlib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.luyinbros.viewlib.R;
import com.luyinbros.viewlib.dialog.core.DialogConstants;
import com.luyinbros.viewlib.dialog.core.DialogConsumer;
import com.luyinbros.viewlib.dialog.core.DialogUtils;
import com.luyinbros.viewlib.dialog.core.OnDialogButtonClickListener;
import com.luyinbros.viewlib.dialog.view.AlertDialog;

/**
 * 提示对话框
 * 支持
 * {@link DialogConstants#MESSAGE_TEXT}
 * {@link DialogConstants#DIALOG_REQUEST_CODE}
 * {@link DialogConstants#TITLE_TEXT}
 * {@link DialogConstants#NEGATIVE_TEXT}
 * {@link DialogConstants#POSITIVE_TEXT}
 */
public final class AlertDialogBuilder {
    private Context mContext;
    private Bundle mBundle;
    private OnDialogButtonClickListener onPositiveClickListener;
    private OnDialogButtonClickListener onNegativeClickListener;

    private FragmentManager mFragmentManager;
    private AlertDialog mAlertDialog;

    /**
     * 以context为请求体构造
     *
     * @param context 当前上下稳
     */
    public AlertDialogBuilder(@Nullable Context context) {
        mContext = DialogUtils.getDialogContext(context);
        mFragmentManager = DialogUtils.getFragmentManager(context);
        mBundle = new Bundle();
    }

    /**
     * 以fragment为请求体构造
     *
     * @param fragment 请求的fragment
     */
    public AlertDialogBuilder(@Nullable Fragment fragment) {
        mContext = DialogUtils.getDialogContext(fragment);
        mFragmentManager = DialogUtils.getFragmentManager(fragment);
        mBundle = new Bundle();
    }

    /**
     * 提供对话框源，用于自我业务回调
     *
     * @param alertDialog 提供的资源
     * @return 构建体
     */
    public AlertDialogBuilder source(@Nullable AlertDialog alertDialog) {
        this.mAlertDialog = alertDialog;
        return this;
    }

    /**
     * 添加参数
     *
     * @param bundle 参数
     * @return 构建体
     */
    public AlertDialog putAllBundle(@Nullable Bundle bundle) {
        if (bundle != null) {
            mBundle.putAll(bundle);
        }
        return mAlertDialog;
    }

    /**
     * 对话框function
     *
     * @param consumer 参数消费者
     * @return 构建体
     */
    public AlertDialogBuilder provide(@NonNull DialogConsumer<AlertDialogBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    /**
     * 设置消息
     *
     * @param res 消息资源文件
     * @return 构建体
     */
    public AlertDialogBuilder setMessage(@StringRes int res) {
        return mContext == null ? setMessage("") : setMessage(mContext.getString(res));
    }

    /**
     * 设置为消息
     *
     * @param message 消息
     * @return 构建体
     */
    public AlertDialogBuilder setMessage(@Nullable CharSequence message) {
        mBundle.putCharSequence(DialogConstants.MESSAGE_TEXT, message);
        return this;
    }

    /**
     * 设置为标题
     *
     * @param res 标题资源文件
     * @return 构建体
     */
    public AlertDialogBuilder setTitle(int res) {
        return mContext == null ? setTitle("") : setTitle(mContext.getString(res));
    }

    /**
     * 设置为标题
     *
     * @param title 标题
     * @return 构建体
     */
    public AlertDialogBuilder setTitle(@Nullable CharSequence title) {
        mBundle.putCharSequence(DialogConstants.TITLE_TEXT, title);
        return this;
    }

    /**
     * 设置积极按钮，调用此方法按钮默认为资源文件{@link R.string.dialog_positive_text_default}
     *
     * @param onClickListener 按钮点击事件 如果null默认为dismiss
     * @return 构建体
     */
    public AlertDialogBuilder setPositiveButton(@Nullable OnDialogButtonClickListener onClickListener) {
        return setPositiveButton(mContext == null ? "" : mContext.getString(R.string.dialog_positive_text_default), onClickListener);
    }

    /**
     * 设置积极按钮
     *
     * @param res             积极按钮资源文本
     * @param onClickListener 按钮点击事件 如果null默认为dismiss
     * @return 构建体
     */
    public AlertDialogBuilder setPositiveButton(@StringRes int res,
                                                @Nullable OnDialogButtonClickListener onClickListener) {
        return setPositiveButton(mContext == null ? "" : mContext.getString(res), onClickListener);
    }

    /**
     * 设置积极按钮
     *
     * @param text            按钮文本
     * @param onClickListener 按钮点击事件 如果null默认为dismiss
     * @return 构建体
     */
    public AlertDialogBuilder setPositiveButton(@Nullable CharSequence text,
                                                @Nullable OnDialogButtonClickListener onClickListener) {
        mBundle.putCharSequence(DialogConstants.POSITIVE_TEXT, text);
        this.onPositiveClickListener = onClickListener;
        return this;
    }

    /**
     * 设置消极按钮，调用此方法按钮默认为资源文件 dialog_negative_text_default
     *
     * @param onClickListener 按钮点击事件 如果null默认为dismiss
     * @return 构建体
     */
    public AlertDialogBuilder setNegativeButton(@Nullable OnDialogButtonClickListener onClickListener) {
        return setNegativeButton(mContext == null ? "" : mContext.getString(R.string.dialog_negative_text_default), onClickListener);
    }

    /**
     * 设置消极按钮
     *
     * @param res             消极按钮资源文本
     * @param onClickListener 按钮点击事件 如果null默认为dismiss
     * @return 构建体
     */
    public AlertDialogBuilder setNegativeButton(@StringRes int res,
                                                @Nullable OnDialogButtonClickListener onClickListener) {
        return setNegativeButton(mContext == null ? "" : mContext.getString(res), onClickListener);
    }

    /**
     * 设置消极按钮
     *
     * @param text            消极按钮文本
     * @param onClickListener 按钮点击事件 如果null默认为dismiss
     * @return 构建体
     */
    public AlertDialogBuilder setNegativeButton(@Nullable CharSequence text,
                                                @Nullable OnDialogButtonClickListener onClickListener) {
        mBundle.putCharSequence(DialogConstants.NEGATIVE_TEXT, text);
        this.onNegativeClickListener = onClickListener;
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        show(-1);
    }

    /**
     * 显示，如果请求体实现{@link com.luyinbros.viewlib.dialog.core.DialogResultReceiver},则会回调
     *
     * @param requestCode 请求码
     */
    public void show(int requestCode) {
        if (mContext != null && mFragmentManager != null) {
            if (mAlertDialog == null) {
                mAlertDialog = new AlertDialog();
            }
            mBundle.putInt(DialogConstants.DIALOG_REQUEST_CODE, requestCode);
            mAlertDialog.setOnNegativeClickListener(onNegativeClickListener);
            mAlertDialog.setOnPositiveClickListener(onPositiveClickListener);
            DialogFactory.showDialog(mAlertDialog, mFragmentManager, mBundle, DialogFactory.ALERT_DIALOG_NAME);
        }
    }


}
