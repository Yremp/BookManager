package com.springboot.book.controller.pagecontroller;

import com.springboot.book.dao.UserDao;
import com.springboot.book.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class GetUserController {
    @Autowired
    private UserDao userDao;
    private User user;
    @RequestMapping("/login")
    public String login(@RequestParam("useremail") String useremail,
                        @RequestParam("password")String password,
                        Map<String, Object> map, Model model, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(useremail, password);
        System.out.println(useremail+password);
        user=userDao.selectByEmail(useremail);
        try {
            subject.login(token);
            HttpSession session= request.getSession();
            session.setAttribute("user",user);
            return "redirect:background";
        } catch (UnknownAccountException e) {
            map.put("mes", "邮箱不存在");
            return "login";
        } catch (IncorrectCredentialsException e){
            map.put("mes","密码错误");
            return "login";
        }
    }
}
