package com.luyinbros.viewlib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.luyinbros.viewlib.R;
import com.luyinbros.viewlib.dialog.core.DialogConstants;
import com.luyinbros.viewlib.dialog.core.DialogUtils;
import com.luyinbros.viewlib.dialog.view.LoadingDialog;

/**
 * 加载对话框
 */
public final class LoadingDialogBuilder {
    private Context mContext;
    private Bundle bundle;
    private FragmentManager mFragmentManager;

    private LoadingDialog loadingDialog;

    /**
     * 以context为请求体构造
     *
     * @param context 当前上下稳
     */
    public LoadingDialogBuilder(@Nullable Context context) {
        mContext = DialogUtils.getDialogContext(context);
        mFragmentManager = DialogUtils.getFragmentManager(context);
        bundle = new Bundle();
        setLoadingText(R.string.dialog_loading_message_default);
    }

    /**
     * 以fragment为请求体构造
     *
     * @param fragment 请求的fragment
     */
    public LoadingDialogBuilder(@Nullable Fragment fragment) {
        mContext = DialogUtils.getDialogContext(fragment);
        mFragmentManager = DialogUtils.getFragmentManager(fragment);
        bundle = new Bundle();
        setLoadingText(R.string.dialog_loading_message_default);
    }


    /**
     * 设置加载对话框文本
     *
     * @param loadingText 载入对话框文本
     * @return 构建体
     */
    public LoadingDialogBuilder setLoadingText(@Nullable CharSequence loadingText) {
        bundle.putCharSequence(DialogConstants.LOADING_TEXT, loadingText);
        return this;
    }

    /**
     * 设置加载对话框文本
     *
     * @param res 载入对话框文本资源
     * @return 构建体
     */
    public LoadingDialogBuilder setLoadingText(@StringRes int res) {
        return setLoadingText(mContext != null ? mContext.getString(res) : "");
    }


    public void show() {
        if (mContext != null && mFragmentManager != null) {
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog();
            }
            DialogFactory.showDialog(loadingDialog, mFragmentManager, bundle, DialogFactory.LOADING_DIALOG_NAME);
        }
    }

}
