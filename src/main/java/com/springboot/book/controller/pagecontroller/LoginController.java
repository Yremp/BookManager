package com.springboot.book.controller.pagecontroller;

import com.springboot.book.dao.BookDao;
import com.springboot.book.dao.UserDao;
import com.springboot.book.model.Book;
import com.springboot.book.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired

    private BookDao bookDao;
    private UserDao userDao;
    private String Password;
    private User user;
/*
默认打开首页
 */
    @RequestMapping({"/","/index.html"})
    public String queryAll(Model model){
        List<Book> books =bookDao.selectAll();
        model.addAttribute("books" ,books);
        return "index";
    }


/*
登录后打开后台界面
 */
    @RequestMapping({"/background"})
    public String background()
    {
        return "background";
    }
/*
用户退出
 */
    @RequestMapping("/logout")
    public String Logout(Model model){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("mes","您已经退出登录");
        return "login";
    }

    @RequestMapping("/index1")
    public String Logout2(Model model){

        return "index1";
    }

}
