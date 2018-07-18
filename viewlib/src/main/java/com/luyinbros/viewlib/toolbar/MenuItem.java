package com.luyinbros.viewlib.toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

public interface MenuItem {

    int TYPE_TEXT = 0;
    int TYPE_IMAGE = 1;
    int TYPE_SELF = 2;

    /**
     * 为menu设置图片
     *
     * @param drawable menu
     * @return 对应的menuItem
     */
    MenuItem setImageDrawable(@NonNull Drawable drawable);

    /**
     * 为menu设置图片
     *
     * @param res drawable res
     * @return 对应的menuItem
     */
    MenuItem setImageResource(@DrawableRes int res);

    /**
     * 获取一个menu Id
     *
     * @return 如果menu id未设置，则返回-1
     */
    @IdRes
    int getMenuId();

    /**
     * 设置一个menu Id
     *
     * @param id 对应的id ,从资源文件获取
     * @return 返回对应的Menu
     */
    MenuItem setMenuId(@IdRes int id);

    /**
     * 为menu设置文字
     *
     * @param res text res
     * @return 对应的menuItem
     */
    MenuItem setMenuText(@StringRes int res);

    /**
     * 为menu设置文字
     *
     * @param text menu text
     * @return 对应的menuItem
     */
    MenuItem setMenuText(CharSequence text);


    /**
     * 设置menu类型
     *
     * @param type menu type
     * @return 对应的menuItem
     * @see #TYPE_IMAGE
     * @see #TYPE_TEXT
     * @see #TYPE_SELF
     */
    MenuItem setType(int type);

    /**
     * 设置menu是否可见
     *
     * @param visible true为可见
     */
    void setVisible(boolean visible);

    /**
     * 当前menu是否可见
     *
     * @return true为可见
     */
    boolean isVisible();

    /**
     * 设置当前menu 是否能用
     *
     * @param enabled true为可用,false 则点击事件、焦点都不可用
     * @return 对应的menuItem
     */
    MenuItem setEnabled(boolean enabled);

    /**
     * 设置自定义视图
     *
     * @param resId layout view resource
     * @return 对应的menuItem
     */
    MenuItem setActionView(@LayoutRes int resId);

    /**
     * 获取当前的menu对应的视图
     *
     * @return menu对应的view
     */
    View getActionView();

    /**
     * 设置menu 跳转的意图
     * 此intent 会调用context.startActivity
     *
     * @param intent 跳转的intent
     * @return menu对应的view
     */
    MenuItem setIntent(@Nullable Intent intent);

    /**
     * 获取menu对应的intent
     *
     * @return 对应的Intent，可能为空
     */
    @Nullable
    Intent getIntent();


}
