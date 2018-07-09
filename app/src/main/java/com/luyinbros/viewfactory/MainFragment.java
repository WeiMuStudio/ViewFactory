package com.luyinbros.viewfactory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luyinbros.viewlib.dialog.AlertDialogBuilder;
import com.luyinbros.viewlib.dialog.core.DialogBundle;
import com.luyinbros.viewlib.dialog.core.DialogResultReceiver;


public class MainFragment extends Fragment implements DialogResultReceiver {

    private static final String TAG = "MainFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChildFragment childFragment = new ChildFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.child_container, childFragment)
                .show(childFragment)
                .commitNow();

        view.findViewById(R.id.action_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialogBuilder(MainFragment.this)
                        .setMessage("第一Fragment消息")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .show(1);
            }
        });
    }


    @Override
    public void onDialogResult(int requestCode, @NonNull DialogBundle data) {
        Log.d(TAG, "onAlertDialogResult: " + requestCode);
    }
}
