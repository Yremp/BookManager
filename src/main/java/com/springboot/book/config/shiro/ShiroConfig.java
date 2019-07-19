package com.springboot.book.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /*
    关联SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean= new ShiroFilterFactoryBean();
//        设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> Map =new LinkedHashMap<String, String>();
//        参数含义
//user-------->rememberme 时使用
//role-------->得到对应的角色权限才可以访问

//-anon------->不需要认证就可以访问，下面设置不需要登录就可以访问的页面
        Map.put("/","anon");
        Map.put("/index.html","anon");
        Map.put("login.html","anon");
        Map.put("/css/**","anon");
        Map.put("/webjars/**","anon");
        Map.put("/login","anon");
//authc------>需要登录认证才能访问的资源，下面设置登录之后才能访问的页面
        Map.put("/book/**","authc");
        Map.put("/lookbook","authc");
        Map.put("/booklist.html","authc");
        Map.put("/background","authc");
        Map.put("/rend/**","authc");
        Map.put("/userinfo/**","authc");
//perms------->得到资源权限才可以访问,下面设置管理才能访问的页面
        Map.put("/user/**","perms[admin]");
        Map.put("/book/**","perms[admin]");
        Map.put("/lookbook","perms[admin]");
        Map.put("/booklist.html","perms[admin]");
//        设置认证跳转界面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
//        设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauthoritypage");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(Map);
        return  shiroFilterFactoryBean;
    }

    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
    DefaultWebSecurityManager defaultWebSecurityManager =new DefaultWebSecurityManager();
    /*
    关联一个Realm
     */
    defaultWebSecurityManager.setRealm(userRealm);
    return defaultWebSecurityManager;
}
    @Bean(name ="userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /*
    配置一个ShiroDialect，用于Thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect()
    {
        return new ShiroDialect();
    }
}
