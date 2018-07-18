package com.luyinbros.viewlib.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ActionMenuLayout extends ViewGroup implements MenuView {
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private int mMenuTextColor;
    private int mMenuTextSize;

    ActionMenuLayout(Context context) {
        super(context);
    }

    public void setParam(int menuTextColor, int menuTextSize) {
        this.mMenuTextColor = menuTextColor;
        this.mMenuTextSize = menuTextSize;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public MenuItem addTextMenu(@StringRes int res) {
        MenuItem menuItem = createMenu();
        menuItem.setMenuText(res);
        menuItem.setType(MenuItem.TYPE_TEXT);
        addMenu(menuItem);
        return menuItem;
    }

    public MenuItem addImageMenu(@DrawableRes int res) {
        MenuItem menuItem = createMenu();
        menuItem.setImageResource(res);
        menuItem.setType(MenuItem.TYPE_IMAGE);
        addMenu(menuItem);
        return menuItem;
    }

    public MenuItem createMenu() {
        return new GeneralMenuItemImp(getContext(), this);
    }

    @Override
    public MenuItem addMenu(MenuItem menuItem) {
        return addMenu(menuItem, menuItems.size());
    }

    @Override
    public MenuItem addMenu(MenuItem menuItem, int index) {
        menuItems.add(index, menuItem);
        adjustMenu();
        return menuItem;
    }

    @Override
    public void removeMenu(MenuItem menuItem) {
        menuItems.remove(menuItem);
        adjustMenu();
    }

    @Override
    public void removeMenu(int index) {
        menuItems.remove(index);
        adjustMenu();
    }

    @Override
    public MenuItem getMenu(int index) {
        return menuItems.get(index);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalWidth = 0;
        final int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                if (child instanceof ImageView) {
                    child.measure(MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.EXACTLY));
                } else {
                    measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
                }
                totalWidth += getChildAt(i).getMeasuredWidth();
            } else {
                measureChild(getChildAt(i), MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY));
            }
        }
        setMeasuredDimension(totalWidth, parentHeight);
    }

    private void adjustMenu() {
        removeAllViews();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getActionView() != null) {
                addView(menuItem.getActionView());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int right = getMeasuredWidth();
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            child.layout(right - child.getMeasuredWidth(), 0, right, child.getMeasuredHeight());
            right -= child.getMeasuredWidth();
        }
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
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
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


    private static Context findRealContext(Context context) {
        Context tempContext = context;
        while (tempContext instanceof ContextWrapper) {
            if (tempContext instanceof Activity) {
                break;
            }
            tempContext = ((ContextWrapper) context).getBaseContext();
        }
        if (tempContext == null) {
            tempContext = context;
        }
        return tempContext;
    }


    private static class GeneralMenuItemImp implements MenuItem {
        private AppCompatTextView mMenuTextView;
        private AppCompatImageView mMenuIconView;
        private View mView;
        @IdRes
        private int mId;
        private ActionMenuLayout mParentView;
        private Intent intent;
        private int type = -1;
        private boolean visible = true;
        private boolean enable = true;
        private Context mContext;
        private OnClickListener onMenuClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intent != null) {
                    mContext.startActivity(intent);
                } else {
                    if (mParentView.mOnMenuItemClickListener != null) {
                        mParentView.mOnMenuItemClickListener.onItemClick(GeneralMenuItemImp.this);
                    }
                }
            }
        };

        private GeneralMenuItemImp(Context context, ActionMenuLayout parentView) {
            this.mContext = findRealContext(context);
            this.mParentView = parentView;
        }

        @Override
        public int getMenuId() {
            return mId;
        }

        @Override
        public MenuItem setMenuId(int id) {
            mId = id;
            return this;
        }

        @Override
        public MenuItem setImageDrawable(Drawable drawable) {
            prepareImageView();
            mMenuIconView.setImageDrawable(drawable);
            return this;
        }

        @Override
        public MenuItem setImageResource(int res) {
            prepareImageView();
            mMenuIconView.setImageResource(res);
            return this;
        }

        private void prepareImageView() {
            if (mMenuIconView == null) {
                mMenuIconView = new AppCompatImageView(mContext);
                mMenuIconView.setScaleType(ImageView.ScaleType.CENTER);
                mMenuIconView.setOnClickListener(onMenuClickListener);
            }
        }

        @Override
        public MenuItem setMenuText(int res) {
            return setMenuText(mContext.getString(res));
        }

        @Override
        public MenuItem setMenuText(CharSequence text) {
            if (mMenuTextView == null) {
                mMenuTextView = new AppCompatTextView(mContext);
                mMenuTextView.setGravity(Gravity.CENTER);
                mMenuTextView.setSingleLine();
                mMenuTextView.setPadding(10,
                        0, 10, 0);
                mMenuTextView.setTextColor(mParentView.mMenuTextColor);
                mMenuTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mParentView.mMenuTextSize);
                mMenuTextView.setOnClickListener(onMenuClickListener);
            }
            mMenuTextView.setText(text);
            return this;
        }

        @Override
        public MenuItem setType(int type) {
            this.type = type;
            setVisible(visible);
            setEnabled(enable);
            mParentView.adjustMenu();
            return this;
        }

        @Override
        public void setVisible(boolean visible) {
            this.visible = visible;
            if (getActionView() != null) {
                getActionView().setVisibility(visible ? VISIBLE : GONE);
            }
        }

        @Override
        public boolean isVisible() {
            return visible;
        }

        @Override
        public MenuItem setEnabled(boolean enabled) {
            this.enable = enabled;
            if (getActionView() != null) {
                getActionView().setEnabled(enable);
            }
            return this;
        }

        @Override
        public MenuItem setActionView(int resId) {
            mView = LayoutInflater.from(mContext).inflate(resId, mParentView, false);
            if (mView != null) {
                mView.setOnClickListener(onMenuClickListener);
            }
            return this;
        }

        @Override
        public View getActionView() {
            if (type == TYPE_TEXT) {
                return mMenuTextView;
            } else if (type == TYPE_IMAGE) {
                return mMenuIconView;
            } else if (type == TYPE_SELF) {
                return mView;
            }
            return null;
        }

        @Override
        public MenuItem setIntent(Intent intent) {
            this.intent = intent;
            if (!(mContext instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            return this;
        }

        @Override
        public Intent getIntent() {
            return intent;
        }

    }


}
