package onlineclass.config;

import onlineclass.interceptor.CorsInterceptor;
import onlineclass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器配置类
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    CorsInterceptor corsInterceptor(){
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");//拦截全部跨域请求并放行

        registry.addInterceptor(loginInterceptor()).addPathPatterns("/pri/*/*/**")
                .excludePathPatterns("/pri/user/login","/pri/user/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
