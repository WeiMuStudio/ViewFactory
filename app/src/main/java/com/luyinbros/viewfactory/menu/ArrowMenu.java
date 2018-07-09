package com.luyinbros.viewfactory.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyinbros.viewfactory.R;

public class ArrowMenu extends ViewGroup {
    private ImageView menuImageView;
    private TextView menuNameTextView;
    private ImageView arrowImageView;
    private TextView hintTextView;
    private final int padding;

    public ArrowMenu(Context context) {
        super(context);
        padding = px(10);
    }

    public ArrowMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ArrowMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArrowMenu);
        setMenuNameText(a.getString(R.styleable.ArrowMenu_arrowmenu_menuName));
        setMenuImage(a.getDrawable(R.styleable.ArrowMenu_arrowmenu_menuImage));
        setHintText(a.getString(R.styleable.ArrowMenu_arrowmenu_menuhint));
        setShowArrow(a.getBoolean(R.styleable.ArrowMenu_arrowmenu_isShowArrow, true));
        a.recycle();
        padding = px(10);
    }

    public void setMenuNameText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            if (menuNameTextView == null) {
                menuNameTextView = new TextView(getContext());
                menuNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                menuNameTextView.setTextColor(0xFF333333);
            }
            addView(menuNameTextView);
        } else {
            if (menuNameTextView != null) {
                removeView(menuNameTextView);
            }
        }
        if (menuNameTextView != null) {
            menuNameTextView.setText(text);
        }
    }

    public void setMenuNameText(@StringRes int res) {
        setMenuNameText(getContext().getResources().getString(res));
    }

    public void setMenuImage(@DrawableRes int res) {
        setMenuImage(ContextCompat.getDrawable(getContext(), res));
    }

    public void setMenuImage(Drawable drawable) {
        if (drawable != null) {
            if (menuImageView == null) {
                menuImageView = new ImageView(getContext());
            }
            addView(menuImageView);
        } else {
            if (menuImageView != null) {
                removeView(menuImageView);
            }
        }

        if (menuImageView != null) {
            menuImageView.setImageDrawable(drawable);
        }

    }

    public void setShowArrow(boolean isShow) {
        if (isShow) {
            if (arrowImageView == null) {
                arrowImageView = new ImageView(getContext());
                arrowImageView.setImageResource(R.mipmap.ic_launcher_round);
            }
            addView(arrowImageView);
        } else {
            if (arrowImageView != null) {
                removeView(arrowImageView);
            }
        }
    }


    public void setHintText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            if (hintTextView == null) {
                hintTextView = new TextView(getContext());
                hintTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                hintTextView.setTextColor(0xFF333333);
            }
            addView(hintTextView);
        } else {
            if (hintTextView != null) {
                removeView(hintTextView);
            }
        }
        if (hintTextView != null) {
            hintTextView.setText(text);
        }
    }

    public void setHintText(@StringRes int res) {
        setHintText(getContext().getResources().getString(res));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                px(100));
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentWidth = getMeasuredWidth();
        if (shouldLayout(menuImageView)) {
            menuImageView.layout(
                    padding,
                    getViewTop(menuImageView),
                    padding + menuImageView.getMeasuredWidth(),
                    getViewTop(menuImageView) + menuImageView.getMeasuredHeight()
            );
        }
        if (shouldLayout(menuNameTextView)) {
            int left = padding;
            if (shouldLayout(menuNameTextView)) {
                left = padding + menuImageView.getMeasuredWidth() + px(10);
            }
            menuNameTextView.layout(left,
                    getViewTop(menuNameTextView),
                    left + menuNameTextView.getMeasuredWidth(),
                    getViewTop(menuNameTextView) + menuNameTextView.getMeasuredHeight());
        }

        if (shouldLayout(arrowImageView)) {
            arrowImageView.layout(parentWidth - arrowImageView.getMeasuredWidth() - padding,
                    getViewTop(arrowImageView),
                    parentWidth - padding,
                    getViewTop(arrowImageView) + arrowImageView.getMeasuredHeight());
        }
        if (shouldLayout(hintTextView)) {
            int right = parentWidth;
            if (shouldLayout(arrowImageView)) {
                right = right - arrowImageView.getMeasuredWidth() - padding;
            }
            hintTextView.layout(right - padding - hintTextView.getMeasuredWidth(),
                    getViewTop(hintTextView),
                    right - padding,
                    getViewTop(hintTextView) + hintTextView.getMeasuredHeight());
        }

    }

    private int px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    private int getViewTop(View view) {
        final int parentHeight = getMeasuredHeight();
        return parentHeight / 2 - view.getMeasuredHeight() / 2;
    }

    private boolean shouldLayout(View view) {
        return view != null && view.getParent() == this && view.getVisibility() != GONE;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof MarginLayoutParams) {
            return new LayoutParams(p);
        } else {
            return new LayoutParams(p);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
    }


    private static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
