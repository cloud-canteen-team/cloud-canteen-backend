package com.s008.smartcanteen.common;

public class UserHolder {
    private static final ThreadLocal<Long> tl = new ThreadLocal<>();

    // 保存当前登录用户ID
    public static void saveUser(Long userId) {
        tl.set(userId);
    }

    // 获取当前登录用户ID
    public static Long getUser() {
        return tl.get();
    }

    // 线程结束前必须移除，防止内存泄漏
    public static void removeUser() {
        tl.remove();
    }
}