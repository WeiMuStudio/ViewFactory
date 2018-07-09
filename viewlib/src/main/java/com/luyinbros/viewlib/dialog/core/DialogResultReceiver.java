package com.luyinbros.viewlib.dialog.core;

import android.support.annotation.NonNull;

public interface DialogResultReceiver {
    void onDialogResult(int requestCode, @NonNull DialogBundle data);
}
