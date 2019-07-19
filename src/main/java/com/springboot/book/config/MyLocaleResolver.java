package com.springboot.book.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String L=httpServletRequest.getParameter("l");
        Locale locale=null;
        if(!StringUtils.isEmpty(L)){
            String [] split= L.split("_");
            locale=new Locale(split[0],split[1]);
        }
        else
        {
            locale=new Locale("zh","CN");
        }

        return  locale;
    }
}
