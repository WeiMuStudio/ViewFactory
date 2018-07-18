package com.luyinbros.viewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
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

import org.weimu.common.view.Dimens;

/**
 * View:
 * <p>
 * --------------------------------------------------------
 * Icon  FormName                           ValueName Arrow
 * --------------------------------------------------------
 */
public class FormItemView extends ViewGroup {
    private ImageView mMenuIconImageView;
    private TextView mMenuNameTextView;
    private ImageView mArrowImageView;
    private TextView mHintTextView;
    private final int padding;
    private int valueTextColor;


    public FormItemView(Context context) {
        super(context);
        padding = Dimens.px(context, 15);
    }

    public FormItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public FormItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormItemView);
        setNameText(a.getString(R.styleable.FormItemView_form_name));
        setIcon(a.getDrawable(R.styleable.FormItemView_form_icon));
        setValueText(a.getString(R.styleable.FormItemView_form_value));
        setShowArrow(a.getBoolean(R.styleable.FormItemView_form_isShowArrow, true));
        setValueTextColor(a.getColor(R.styleable.FormItemView_form_value_color, 0xFF1A1A1A));
        a.recycle();
        padding = Dimens.px(context, 15);
    }

    public void setNameText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            if (mMenuNameTextView == null) {
                mMenuNameTextView = new TextView(getContext());
                mMenuNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                mMenuNameTextView.setTextColor(0xFF333333);
            }
            addView(mMenuNameTextView);
        } else {
            if (mMenuNameTextView != null) {
                removeView(mMenuNameTextView);
            }
        }
        if (mMenuNameTextView != null) {
            mMenuNameTextView.setText(text);
        }
    }

    public void setNameText(@StringRes int res) {
        setNameText(getContext().getResources().getString(res));
    }

    public void setIcon(@DrawableRes int res) {
        setIcon(ContextCompat.getDrawable(getContext(), res));
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            if (mMenuIconImageView == null) {
                mMenuIconImageView = new ImageView(getContext());
            }
            if (!isChildOrHidden(mMenuIconImageView)) {
                addView(mMenuIconImageView);
            }
        } else {
            if (mMenuIconImageView != null) {
                removeView(mMenuIconImageView);
            }
        }
        if (mMenuIconImageView != null) {
            mMenuIconImageView.setImageDrawable(drawable);
        }
    }

    public void setShowArrow(boolean isShow) {
        if (isShow) {
            if (mArrowImageView == null) {
                mArrowImageView = new ImageView(getContext());
                mArrowImageView.setImageResource(R.drawable.ic_right_arrow);
            }
            if (!isChildOrHidden(mArrowImageView)) {
                addView(mArrowImageView);
            }
        } else {
            if (mArrowImageView != null) {
                removeView(mArrowImageView);
            }
        }
    }


    public void setValueTextColor(@ColorInt int color) {
        this.valueTextColor = color;
        if (mHintTextView != null) {
            mHintTextView.setTextColor(valueTextColor);
        }
    }

    public void setValueText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            if (mHintTextView == null) {
                mHintTextView = new TextView(getContext());
                mHintTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                mHintTextView.setTextColor(valueTextColor);
            }
            if (!isChildOrHidden(mHintTextView)) {
                addView(mHintTextView);
            }
        } else {
            if (mHintTextView != null) {
                removeView(mHintTextView);
            }
        }
        if (mHintTextView != null) {
            mHintTextView.setText(text);
        }
    }

    public void setValueText(@StringRes int res) {
        setValueText(getContext().getResources().getString(res));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                px(50));
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentWidth = getMeasuredWidth();
        if (shouldLayout(mMenuIconImageView)) {
            mMenuIconImageView.layout(
                    padding,
                    getViewTop(mMenuIconImageView),
                    padding + mMenuIconImageView.getMeasuredWidth(),
                    getViewTop(mMenuIconImageView) + mMenuIconImageView.getMeasuredHeight()
            );
        }
        if (shouldLayout(mMenuNameTextView)) {
            int left = padding;
            if (shouldLayout(mMenuIconImageView)) {
                left = padding + mMenuIconImageView.getMeasuredWidth() + px(10);
            }
            mMenuNameTextView.layout(left,
                    getViewTop(mMenuNameTextView),
                    left + mMenuNameTextView.getMeasuredWidth(),
                    getViewTop(mMenuNameTextView) + mMenuNameTextView.getMeasuredHeight());
        }

        if (shouldLayout(mArrowImageView)) {
            mArrowImageView.layout(parentWidth - mArrowImageView.getMeasuredWidth() - padding,
                    getViewTop(mArrowImageView),
                    parentWidth - padding,
                    getViewTop(mArrowImageView) + mArrowImageView.getMeasuredHeight());
        }
        if (shouldLayout(mHintTextView)) {
            int right = parentWidth;
            if (shouldLayout(mArrowImageView)) {
                right = right - mArrowImageView.getMeasuredWidth() - padding;
            }
            mHintTextView.layout(right - padding - mHintTextView.getMeasuredWidth(),
                    getViewTop(mHintTextView),
                    right - padding,
                    getViewTop(mHintTextView) + mHintTextView.getMeasuredHeight());
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

    private boolean isChildOrHidden(View child) {
        return child.getParent() == this;
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
