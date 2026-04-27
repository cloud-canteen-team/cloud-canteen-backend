package com.s008.smartcanteen.config;

import com.s008.smartcanteen.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**") // 拦截所有接口
                .excludePathPatterns(
                        "/api/user/login",      // 排除登录接口
                        "/api/common/upload",   // 排除上传（可选）
                        "/uploads/**"           // 排除静态资源
                );
    }
}