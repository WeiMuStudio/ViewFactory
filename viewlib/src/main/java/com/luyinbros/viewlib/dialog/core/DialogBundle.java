package com.luyinbros.viewlib.dialog.core;

import java.util.HashMap;
import java.util.Map;

public class DialogBundle {
    private Map<String, Object> data;
    /**
     * 对话按钮类型
     *
     * @see DialogBundle
     */
    public static final String KEY_BUTTON_ACTION_TYPE = "button.action.type";

    public DialogBundle() {
        data = new HashMap<>();
    }


    /**
     * 存储对话框操作类型
     *
     * @param action 当前对话框操作类型
     * @see DialogAction
     */
    public void putDialogAction(int action) {
        data.put(KEY_BUTTON_ACTION_TYPE, action);
    }


    public void putInt(String key, int value) {
        data.put(key, value);
    }

    public void putString(String key, String value) {
        data.put(key, value);
    }

    /**
     * 获取对话框操作类型
     *
     * @return 如果无操作 则返回{{@link DialogAction#NONE}}
     */
    public int getDialogAction() {
        Object action = data.get(KEY_BUTTON_ACTION_TYPE);
        if (action instanceof Integer) {
            return (int) action;
        } else {
            return DialogAction.NONE;
        }
    }

    public int getInt(String key, int defaultValue) {
        Object value = data.get(key);
        if (value instanceof Integer) {
            return (int) value;
        } else {
            return defaultValue;
        }
    }

    public String getString(String key, String defaultValue) {
        Object value = data.get(key);
        if (value instanceof String) {
            return (String) value;
        } else {
            return defaultValue;
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) data.get(key);
    }


}
