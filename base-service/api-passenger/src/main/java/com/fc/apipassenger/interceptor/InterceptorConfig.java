package com.fc.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 初始化拦截器，则在拦截器里 @Autowired 的对象才不会为空
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())   //用初始化拦截器的方法，而不是new一个
                //拦截的路径
                .addPathPatterns("/**")
                //不拦截的路径
                .excludePathPatterns("/verification-code")  //放行 获取验证码
                .excludePathPatterns("/verification-code-check")   //放行 校验验证码
                .excludePathPatterns("/token-refresh")  //放行 验证码刷新


                //for test
                .excludePathPatterns("/noAuthTest");

    }
}
