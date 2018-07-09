package com.luyinbros.viewlib;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

public class ShortMessageBar {

    public void show(@Nullable View view, int res) {
        SnackBarUtils.show(view, res);
    }

    public void show(@Nullable View view, String text) {
        SnackBarUtils.show(view, text);
    }

    private static class SnackBarUtils {

        private static void show(@Nullable View view, int res) {
            if (view != null) {
                Snackbar.make(view, res, Snackbar.LENGTH_SHORT).show();
            }
        }

        private static void show(@Nullable View view, String text) {
            if (view != null) {
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
            }
        }

    }


}
