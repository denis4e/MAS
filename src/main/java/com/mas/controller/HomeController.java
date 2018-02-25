package com.mas.controller;

import com.mas.Messages;
import com.mas.domain.User;
import com.mas.service.SocialNetworksLoginService;
import com.mas.util.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    @Autowired
    SocialNetworksLoginService socialNetworksLoginService;

    @Autowired
    Messages messages;

    @Autowired
    UserDetailsService userDetailsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.setViewName(Links.HOME);
        if (auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            User user = socialNetworksLoginService.fbLogin(auth);
            if (user == null) {
                //TODO update logic and send FB params to registration form
                SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler(); // concern you
                ctxLogOut.logout(request, response, auth); // concern you
                model.getModel().put("errorMessage", messages.get("errorMessage.user.social.network.null"));
                user = new User();
                model.addObject("user", user);
                model.setViewName(Links.REGISTRATION);
            } else {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        return model;
    }
}