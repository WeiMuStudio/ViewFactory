package com.luyinbros.viewlib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.luyinbros.viewlib.dialog.core.DialogUtils;
import com.luyinbros.viewlib.dialog.core.OnDialogButtonClickListener;

public class DialogFactory {
    public static final String ALERT_DIALOG_NAME = "AlertDialog";
    public static final String LOADING_DIALOG_NAME = "LoadingDialog";
    public static final String DATE_DIALOG_NAME = "dateDialog";

    /**
     * 显示Alert对话框
     * 带有积极按钮和消极按钮
     *
     * @param context                 当前上下文
     * @param message                 消息资源文件
     * @param onPositiveClickListener 积极点击事件  为空默认为dismiss
     * @param onNegativeClickListener 消极点击事件  为空默认为dismiss
     */
    public static void showAlertDialog(@Nullable Context context,
                                       @StringRes int message,
                                       @Nullable OnDialogButtonClickListener onPositiveClickListener,
                                       @Nullable OnDialogButtonClickListener onNegativeClickListener) {
        new AlertDialogBuilder(context)
                .setMessage(message)
                .setPositiveButton(onPositiveClickListener)
                .setNegativeButton(onNegativeClickListener)
                .show();
    }

    /**
     * 显示Alert对话框
     * 带有积极按钮和消极按钮
     *
     * @param context                 当前上下文
     * @param message                 消息资源文件
     * @param onPositiveClickListener 积极点击事件  为空默认为dismiss
     * @param onNegativeClickListener 消极点击事件  为空默认为dismiss
     */
    public static void showAlertDialog(@Nullable Context context,
                                       String message,
                                       @Nullable OnDialogButtonClickListener onPositiveClickListener,
                                       @Nullable OnDialogButtonClickListener onNegativeClickListener) {
        new AlertDialogBuilder(context)
                .setMessage(message)
                .setPositiveButton(onPositiveClickListener)
                .setNegativeButton(onNegativeClickListener)
                .show();
    }

    /**
     * 显示默认的加载对话框
     *
     * @param context 上下文
     */
    public static void showLoadingDialog(@Nullable Context context) {
        new LoadingDialogBuilder(context).show();
    }

    /**
     * 显示加载对话框
     *
     * @param context 上下文
     * @param res     加载对话框默认文本资源文件
     */
    public static void showLoadingDialog(@Nullable Context context, @StringRes int res) {
        new LoadingDialogBuilder(context).setLoadingText(res).show();
    }

    /**
     * 隐藏加载对话框
     *
     * @param context 上下文
     */
    public static void dismissLoadingDialog(@Nullable Context context) {
        dismissDialog(DialogUtils.getFragmentManager(context), DialogFactory.LOADING_DIALOG_NAME);
    }

    /**
     * 隐藏对话框
     *
     * @param fragmentManager 管理器
     * @param name            对话框名字
     */
    public static void dismissDialog(@Nullable FragmentManager fragmentManager,
                                     String name) {
        if (fragmentManager != null) {
            DialogFragment currentDialog = (DialogFragment) fragmentManager.findFragmentByTag(name);
            if (currentDialog != null) {
                currentDialog.dismissAllowingStateLoss();
            }
        }
    }

    /**
     * 显示对话框
     *
     * @param dialogFragment  对话框对象
     * @param fragmentManager 碎片管理器
     * @param bundle          携带参数
     * @param name            对话框名
     */
    public static void showDialog(@Nullable DialogFragment dialogFragment,
                                  @Nullable FragmentManager fragmentManager,
                                  @Nullable Bundle bundle,
                                  @NonNull String name) {
        if (fragmentManager != null) {
            DialogFragment currentDialog = (DialogFragment) fragmentManager.findFragmentByTag(name);
            if (currentDialog != null) {
                currentDialog.dismiss();
            }
            if (dialogFragment != null) {
                dialogFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(dialogFragment, name);
                transaction.commitNowAllowingStateLoss();
                transaction.show(dialogFragment);
            }

        }

    }
}
