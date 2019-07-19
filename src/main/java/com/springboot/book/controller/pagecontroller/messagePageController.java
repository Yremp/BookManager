package com.springboot.book.controller.pagecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class messagePageController {
    @Autowired
    @RequestMapping("/noauthoritypage")
    public String noauthoritypage(){
        return "noauthoritypage";
    }
}
