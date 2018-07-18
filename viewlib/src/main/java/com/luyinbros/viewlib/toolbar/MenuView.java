package com.luyinbros.viewlib.toolbar;

public interface MenuView {

    /**
     * 添加一个新的menu 按钮
     *
     * @param menuItem 你的标题栏的menu
     * @return 返回你添加的menu
     */
    MenuItem addMenu(MenuItem menuItem);

    /**
     * 添加一个新的menu 按钮
     *
     * @param menuItem 你的标题栏的menu
     * @param index    menu放置的位置
     * @return 返回你添加的menu
     */
    MenuItem addMenu(MenuItem menuItem, int index);

    /**
     * 移除指定的menu
     *
     * @param menuItem 移除的menu项
     */
    void removeMenu(MenuItem menuItem);

    /**
     * 移除指定位置的menu
     *
     * @param index menu目标位置
     */
    void removeMenu(int index);

    /**
     * 获取位置的index
     *
     * @param index menu目标位置
     * @return 返回当前index上的MenuItem
     */
    MenuItem getMenu(int index);

    interface OnMenuItemClickListener {
        void onItemClick(MenuItem item);
    }

}
