package com.mas.controller;

import com.mas.Messages;
import com.mas.domain.User;
import com.mas.service.SocialNetworksLoginService;
import com.mas.service.UserUtil;
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
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    SocialNetworksLoginService socialNetworksLoginService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private Messages messages;
    @Autowired
    private UserUtil userUtis;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() throws Exception {
        ModelAndView model = new ModelAndView();
        model.setViewName(Links.HOME);
        return model;
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public ModelAndView facebookSignIn(HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView model = new ModelAndView();
        if (userUtis.isAuthenticated() && principal != null) {
            User user = socialNetworksLoginService.fbLogin(auth);
            if (user == null) {
                SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
                ctxLogOut.logout(request, response, auth);
                model.getModel().put("errorMessage", messages.get("errorMessage.user.social.network.null"));
                user = new User();
                model.addObject("user", user);
                model.setViewName(Links.REGISTRATION);
            } else {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                model.setViewName(Links.HOME);
            }
        }
        return model;
    }
}