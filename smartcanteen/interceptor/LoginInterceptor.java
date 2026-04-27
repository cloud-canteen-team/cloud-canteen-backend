package com.s008.smartcanteen.interceptor;

import com.s008.smartcanteen.common.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头中获取用户ID（暂时的模拟方案）
        String userIdStr = request.getHeader("userId");

        // 2. 判断是否为空
        if (userIdStr == null || userIdStr.isEmpty()) {
            // 如果没有传userId，返回401未登录错误码
            response.setStatus(401);
            return false;
        }

        // 3. 存入 ThreadLocal
        UserHolder.saveUser(Long.parseLong(userIdStr));

        // 4. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束时，务必清理 ThreadLocal，防止内存占用
        UserHolder.removeUser();
    }
}