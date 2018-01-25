package com.mas.controller;

import com.mas.Messages;
import com.mas.util.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    Messages messages;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin() {
        return Links.LOGIN;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView signIn() {

        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.getModel().put("successMessage", messages.get("successMessage.login.success", auth.getName()));
        }
        if (auth.getName().equals("anonymousUser")) {
            model.getModel().put("errorMessage", messages.get("errorMessage.invalid.credentials.provided"));
        }
        model.setViewName(Links.HOME);
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser")) {
            model.getModel().put("infoMessage", messages.get("infoMessage.logged.out"));
        }
        model.setViewName(Links.HOME);
        return model;
    }

}