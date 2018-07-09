package com.luyinbros.viewlib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.luyinbros.viewlib.dialog.core.DialogConsumer;
import com.luyinbros.viewlib.dialog.core.DialogUtils;

;


public class GeneralDialogBuilder {
    private Context mContext;
    private Bundle mBundle;
    private FragmentManager mFragmentManager;
    private DialogFragment mDialogFragment;
    private String name;

    /**
     * 以context为请求体构造
     *
     * @param context 当前上下稳
     */
    public GeneralDialogBuilder(Context context) {
        mContext = DialogUtils.getDialogContext(context);
        mFragmentManager = DialogUtils.getFragmentManager(context);
        mBundle = new Bundle();
    }

    /**
     * 以fragment为请求体构造
     *
     * @param fragment 请求的fragment
     */
    public GeneralDialogBuilder(Fragment fragment) {
        mContext = DialogUtils.getDialogContext(fragment);
        mFragmentManager = DialogUtils.getFragmentManager(fragment);
        mBundle = new Bundle();
    }

    /**
     * 提供对话框源
     *
     * @param dialogFragment 提供的资源
     * @return 构建体
     */
    public GeneralDialogBuilder source(DialogFragment dialogFragment) {
        this.mDialogFragment = dialogFragment;
        return this;
    }

    /**
     * 添加参数
     *
     * @param bundle 参数
     * @return 构建体
     */
    public GeneralDialogBuilder putAllBundle(Bundle bundle) {
        mBundle.putAll(bundle);
        return this;
    }

    /**
     * 对话框function
     *
     * @param consumer 参数消费者
     * @return 构建体
     */
    public GeneralDialogBuilder forParam(@NonNull DialogConsumer<GeneralDialogBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public GeneralDialogBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        if (mContext != null && mFragmentManager != null && mDialogFragment != null) {
            DialogFactory.showDialog(mDialogFragment, mFragmentManager, mBundle,
                    TextUtils.isEmpty(name) ? mDialogFragment.getClass().getName():name);
        }

    }


}
