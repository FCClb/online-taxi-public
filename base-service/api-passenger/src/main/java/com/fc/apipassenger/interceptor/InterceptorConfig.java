package com.fc.apipassenger.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //不拦截的路径
                .excludePathPatterns("/noAuthTest")
                .excludePathPatterns("/verification-code")  //放行 获取验证码
                .excludePathPatterns("/verification-code-check");   //放行 校验验证码

    }
}
