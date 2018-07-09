package com.luyinbros.viewlib.dialog.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyinbros.viewlib.drawable.MaterialProgressDrawable;
import com.luyinbros.viewlib.R;
import com.luyinbros.viewlib.dialog.core.DialogConstants;


public final class LoadingDialog extends DialogFragment {
    private ImageView ivLoading;
    private TextView tvLoading;
    private MaterialProgressDrawable mDrawable;

    private CharSequence mLoadingText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CommonDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivLoading = view.findViewById(R.id.iv_loading);
        tvLoading = view.findViewById(R.id.tv_loading);
        mDrawable = new MaterialProgressDrawable(view.getContext(),ivLoading);
        mDrawable.setAlpha(255);
        mDrawable.setStartEndTrim(0f, 0.8f);
        mDrawable.setArrowScale(1f); //0~1之间
        mDrawable.setProgressRotation(1);
        mDrawable.showArrow(true);
        ivLoading.setImageDrawable(mDrawable);
        Bundle bundle = getArguments();
        if (bundle != null) {
            setLoadingText(bundle.getCharSequence(DialogConstants.LOADING_TEXT));
        }
        tvLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DialogDesignHelper.center(getContext(), getDialog());
    }


    protected void setLoadingText(CharSequence text) {
        mLoadingText = text;
        if (tvLoading != null) {
            tvLoading.setText(String.format("%s...", mLoadingText));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mDrawable.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDrawable.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
