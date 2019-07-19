package com.springboot.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class myMvcConfigs extends WebMvcConfigurerAdapter {
//    视图映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("background.html").setViewName("background");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/booklist.html").setViewName("booklist");
        registry.addViewController("/signup.html").setViewName("signup");

    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
//                excludePathPatterns("/user/login","/webjars/**","/index.html","login.html","/css/**","/","/admin/**","/login");
////        以上为没有限制访问（不登录也可以访问）的path
//    }
    @Bean
 public LocaleResolver localeResolver(){
        return  new MyLocaleResolver();
 }


}
