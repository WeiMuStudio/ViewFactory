package com.luyinbros.viewlib.dialog.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.luyinbros.viewlib.R;


public class DialogDesignHelper {
    public static final int DEFAULT_STYLE = R.style.CommonDialog;
    public static final int BOTTOM_STYLE = R.style.BottomDialogStyle;


    public static void center(Context context, Dialog dialog) {
        if (context != null && dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.width = (int) (getPhoneScreenParam((Activity) context)[0] * 0.7);
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }

    }

    public static void bottom(@Nullable Context context, @Nullable Dialog dialog) {
        if (context != null && dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            wlp.gravity = Gravity.BOTTOM;
            window.setAttributes(wlp);
        }
    }

    private static int[] getPhoneScreenParam(Activity activity) {
        int[] param = new int[2];
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        param[0] = rect.width();
        param[1] = rect.height();
        return param;
    }
}
