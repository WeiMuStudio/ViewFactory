package com.luyinbros.viewfactory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.luyinbros.viewlib.dialog.DateDialogBuilder;
import com.luyinbros.viewlib.dialog.DialogFactory;
import com.luyinbros.viewlib.dialog.core.DialogBundle;
import com.luyinbros.viewlib.dialog.core.DialogResultReceiver;
import com.luyinbros.viewlib.dialog.core.DialogView;
import com.luyinbros.viewlib.dialog.core.OnDialogButtonClickListener;
import com.luyinbros.viewlib.notification.NotificationFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DialogActivity extends AppCompatActivity implements DialogResultReceiver {
    private static final String TAG = "DialogActivity";
    private Button btnShowWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.child_container, fragment)
                .show(fragment)
                .commitNow();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onNotification(View v) {
        NotificationFactory.showMessage(this);
    }

    public void onDate(View v) {
        new DateDialogBuilder(this)
                .setDate(2008, 10, 12)
                .setMaxDate(2010, 10, 12)
                .setMinDate(2008, 10, 12)
                .setOnDatePickListener(new DateDialogBuilder.OnDatePickListener() {
                    @Override
                    public void onDatePick(DialogView dialog, Date date) {
                        Log.d(TAG, new SimpleDateFormat("YYYY-MM-dd").format(date));
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public void onAlert(View v) {
        DialogFactory.showAlertDialog(this, "这是一条消息",
                new OnDialogButtonClickListener() {
                    @Override
                    public void onClick(DialogView dialog) {
                        showToast("确定");
                        dialog.dismiss();
                    }
                },
                new OnDialogButtonClickListener() {
                    @Override
                    public void onClick(DialogView dialog) {
                        showToast("取消");
                        dialog.dismiss();
                    }
                });
    }

    public void onLoading(View v) {
        DialogFactory.showLoadingDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDialogResult(int requestCode, @NonNull DialogBundle data) {
        Log.d(TAG, "onAlertDialogResult: " + requestCode);
    }


}
