package com.springboot.book.config.shiro;

import com.springboot.book.dao.UserDao;
import com.springboot.book.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;


    /**
     *授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User Dbuser=userDao.selectByEmail(user.getEmail());
        info.addStringPermission(Dbuser.getRole());
        return info;
    }
    /*
    认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
//        此处编写登录认证逻辑
        UsernamePasswordToken token= (UsernamePasswordToken)authenticationToken;
        User user=userDao.selectByEmail(token.getUsername());
        if(user==null)
        {
            System.out.println("认证结果为：用户不存在");
            return null;

        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");

    }

}
