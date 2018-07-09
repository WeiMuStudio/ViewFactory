package com.luyinbros.viewfactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.luyinbros.viewlib.window.EnhancePopupWindow;

public class ViewTestActivity extends AppCompatActivity {
    private SelfPopupWindow selfPopupWindow;
    private Button btnShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        selfPopupWindow = new SelfPopupWindow(this);
        btnShow = findViewById(R.id.btn_show);
        Log.d("ddd", getIntent().getIntExtra("ddd",0)+"");
    }

    public void onShowPopupWindow(View view) {

        Snackbar.make(findViewById(android.R.id.content), "还行", Snackbar.LENGTH_SHORT).show();
        // selfPopupWindow.showAtLocation(btnShow, Gravity.BOTTOM);

//        selfPopupWindow.showAtAnchorView(btnShow, EnhancePopupWindow.VerticalGravity.BELOW,
//                EnhancePopupWindow.HorizontalGravity.CENTER);
    }

    @Override
    public void onBackPressed() {
        if (selfPopupWindow.isShowing()) {
            selfPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    public static class SelfPopupWindow extends EnhancePopupWindow {

        public SelfPopupWindow(Context context) {
            super(context);
            setContentView(LayoutInflater.from(context).inflate(R.layout.window_self, null, false));
            setFocusable(true);
            setOutsideTouchable(true);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            setHeight(WindowManager.LayoutParams.MATCH_PARENT);
            setAnimationStyle(R.style.popupWindowDefaultAnimation);
            setBackgroundDrawable(new ColorDrawable(Color.argb(153, 0, 0, 0)));
            setContentAnimation(R.anim.bottom_in, R.anim.bottom_out);
            setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("setTouchInterceptor", event.getX() + " " + event.getY());
                    return false;
                }
            });
            update();
        }

    }
}
