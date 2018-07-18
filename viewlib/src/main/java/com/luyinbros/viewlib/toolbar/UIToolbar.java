package com.luyinbros.viewlib.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyinbros.viewlib.R;


public class UIToolbar extends ViewGroup {
    private TextView mTitleTextView;
    private ImageView mNavButtonView;
    @ColorInt
    private int mTitleColor = -1;
    private int mTitleTextSize;
    private final int maxHeight;
    private final int mMenuTextColor;
    private final int mMenuTextSize;
    private ActionMenuLayout mMenuLayout;

    private OnClickListener mOnNavigateClickListener;


    public UIToolbar(Context context) {
        this(context, null);
    }

    public UIToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIToolbar);
        maxHeight = a.getDimensionPixelOffset(R.styleable.UIToolbar_uiToolbar_maxHeight, px(51));
        mMenuTextColor = a.getColor(R.styleable.UIToolbar_uiToolbar_menuTextColor, 0xFF363636);
        mMenuTextSize = a.getDimensionPixelSize(R.styleable.UIToolbar_uiToolbar_menuTextSize, 0);
        setTitleText(a.getString(R.styleable.UIToolbar_uiToolbar_title));
        setNavigationIcon(a.getDrawable(R.styleable.UIToolbar_uiToolbar_navigateIcon));
        setTitleColor(a.getColor(R.styleable.UIToolbar_uiToolbar_titleColor,
                0xFF363636));
        setTitleTextSize(a.getDimensionPixelOffset(R.styleable.UIToolbar_uiToolbar_titleSize, textPx(16)));
        ViewCompat.setBackground(this, a.getDrawable(R.styleable.UIToolbar_uiToolbar_background));
        a.recycle();
    }

    /**
     * 设置标题栏文本
     *
     * @param res 标题文本资源文件
     */
    public void setTitleText(@StringRes int res) {
        setTitleText(getContext().getString(res));
    }

    /**
     * 设置标题栏文本
     *
     * @param title 文本
     */
    public void setTitleText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                final Context context = getContext();
                mTitleTextView = new AppCompatTextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                setTitleTextSize(mTitleTextSize);
                setTitleColor(mTitleColor);
            }
            if (!isChildOrHidden(mTitleTextView)) {
                addView(mTitleTextView);
            }
        } else {
            removeView(mTitleTextView);
        }
        if (mTitleTextView != null) {
            if (TextUtils.isEmpty(title)) {
                mTitleTextView.setText("");
            } else {
                if (title.length() > 10) {
                    StringBuilder stringBuilder = new StringBuilder(title);
                    stringBuilder.delete(10, stringBuilder.length());
                    stringBuilder.append("...");
                    mTitleTextView.setText(stringBuilder);
                } else {
                    mTitleTextView.setText(title);
                }
            }
        }
    }

    /**
     * 设置标题栏尺寸
     *
     * @param size 标题栏尺寸
     */
    public void setTitleTextSize(@Px int size) {
        this.mTitleTextSize = size;
        if (mTitleTextView != null) {
            mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    /**
     * 设置标题栏颜色
     *
     * @param color 目标颜色
     */
    public void setTitleColor(int color) {
        this.mTitleColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    /**
     * 设置导航点击事件
     *
     * @param onClickListener 点击事件
     */
    public void setOnNavigateClickListener(OnClickListener onClickListener) {
        this.mOnNavigateClickListener = onClickListener;
        if (mNavButtonView != null) {
            mNavButtonView.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置导航栏图标
     *
     * @param icon 导航栏图标
     */
    public void setNavigationIcon(Drawable icon) {
        if (icon != null) {
            if (mNavButtonView == null) {
                mNavButtonView = new AppCompatImageView(getContext());
                final LayoutParams lp = new LayoutParams(maxHeight,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                mNavButtonView.setLayoutParams(lp);
                mNavButtonView.setScaleType(ImageView.ScaleType.CENTER);
                mNavButtonView.setOnClickListener(mOnNavigateClickListener);
            }
            if (!isChildOrHidden(mNavButtonView)) {
                addView(mNavButtonView);
            }
        } else {
            if (mNavButtonView != null) {
                mNavButtonView.setImageDrawable(null);
                removeView(mNavButtonView);
            }
        }

        if (mNavButtonView != null) {
            mNavButtonView.setImageDrawable(icon);
        }
    }

    /**
     * 设置Menu点击事件
     *
     * @param menuItemClickListener
     */
    public void setOnMenuItemClickListener(MenuView.OnMenuItemClickListener menuItemClickListener) {
        prepareMenuLayout();
        mMenuLayout.setOnMenuItemClickListener(menuItemClickListener);
    }


    /**
     * 获取Menu视图
     *
     * @return menu对应的视图
     */
    public MenuView getMenuView() {
        prepareMenuLayout();
        return mMenuLayout;
    }

    /**
     * 创建一个新的Menu
     *
     * @return 一个新的Menu
     */
    public MenuItem createMenu() {
        prepareMenuLayout();
        return mMenuLayout.createMenu();
    }

    /**
     * 添加一个menu
     *
     * @param menuItem 目标menu
     * @return 返回添加后的menu
     */
    public MenuItem addMenu(MenuItem menuItem) {
        prepareMenuLayout();
        return mMenuLayout.addMenu(menuItem);
    }


    /**
     * 添加一个文本式menu
     *
     * @param res menu文本
     * @return 返回添加后的menu
     */
    public MenuItem addTextMenu(@StringRes int res) {
        prepareMenuLayout();
        return mMenuLayout.addTextMenu(res);
    }

    /**
     * 添加一个图片式menu
     *
     * @param res menu 图片
     * @return 返回添加后的menu
     */
    public MenuItem addImageMenu(@DrawableRes int res) {
        prepareMenuLayout();
        return mMenuLayout.addImageMenu(res);
    }


    private void prepareMenuLayout() {
        if (mMenuLayout == null) {
            mMenuLayout = new ActionMenuLayout(getContext());
            mMenuLayout.setParam(mMenuTextColor, mMenuTextSize);
            if (!isChildOrHidden(mMenuLayout)) {
                addView(mMenuLayout, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxHeight);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentHeight = getMeasuredHeight();
        final int parentWidth = getMeasuredWidth();

        if (shouldLayout(mTitleTextView)) {
            int titleLeft = parentWidth / 2 - mTitleTextView.getMeasuredWidth() / 2;
            int titleTop = parentHeight / 2 - mTitleTextView.getMeasuredHeight() / 2;
            mTitleTextView.layout(titleLeft,
                    titleTop,
                    titleLeft + mTitleTextView.getMeasuredWidth(), titleTop + mTitleTextView.getMeasuredHeight());
        }
        if (shouldLayout(mNavButtonView)) {
            mNavButtonView.layout(0, 0, mNavButtonView.getMeasuredWidth(), mNavButtonView.getMeasuredHeight());
        }

        if (shouldLayout(mMenuLayout)) {
            mMenuLayout.layout(parentWidth - mMenuLayout.getMeasuredWidth(), 0, parentWidth, mMenuLayout.getMeasuredHeight());
        }
    }

    private boolean isChildOrHidden(View child) {
        return child.getParent() == this;
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

    private int textPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private int px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
