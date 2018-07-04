package com.mas.controller;

import com.mas.Messages;
import com.mas.dao.repository.RoleRepository;
import com.mas.dao.repository.UserRepository;
import com.mas.domain.User;
import com.mas.service.EmailService;
import com.mas.service.SocialNetworksLoginService;
import com.mas.service.UserUtil;
import com.mas.util.Links;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationTrustResolver;
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
public class LoginController {
    @Autowired
    SocialNetworksLoginService socialNetworksLoginService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private Messages messages;
    @Autowired
    private UserUtil userUtils;
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin() {
        return Links.LOGIN;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView signIn() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!authenticationTrustResolver.isAnonymous(auth)) {
            model.getModel().put("successMessage", messages.get("successMessage.login.success", auth.getName()));
        } else {
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

    @RequestMapping(value = "/login/facebook", method = RequestMethod.GET)
    public ModelAndView facebookSignIn(HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView model = new ModelAndView();
        model.setViewName(Links.HOME);
        if (userUtils.isAuthenticated() && principal != null) {
            JSONObject userData = socialNetworksLoginService.fbLogin(auth);
            String faceBookId = userData.get("id").toString();
            String faceBookUserName = userData.get("first_name").toString();
            String faceBookLastName = userData.get("last_name").toString();
            String faceBookEmail = userData.get("email").toString();
            User user = userRepository.findUserByFaceBookId(faceBookId);
            if (user == null) {
                SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
                ctxLogOut.logout(request, response, auth);
                model.getModel().put("errorMessage", messages.get("errorMessage.user.social.network.facebook.null"));
                user = new User();
                user.setFbId(faceBookId);
                user.setLastName(faceBookLastName);
                user.setUserName(faceBookUserName);
                user.setEmail(faceBookEmail);
                user.setEnabled(true);
                model.addObject("user", user);
                model.setViewName(Links.REGISTRATION);
            } else {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        return model;
    }

    @RequestMapping(value = "/login/google", method = RequestMethod.GET)
    public ModelAndView googleSignIn(HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView model = new ModelAndView();
        model.setViewName(Links.HOME);
        if (userUtils.isAuthenticated() && principal != null) {
            JSONObject userData = socialNetworksLoginService.googleLogin(auth);
            JSONArray googleEmailArray = (JSONArray) userData.get("emails");
            JSONObject googleEmailObj = (JSONObject) googleEmailArray.get(0);
            String googleEmail = googleEmailObj.get("value").toString();

            JSONObject googleNameObject =  (JSONObject) userData.get("name");
            String googleLastName = googleNameObject.get("familyName").toString();
            String googleUserName = googleNameObject.get("givenName").toString();
            String googleId = userData.get("id").toString();

            User user = userRepository.findUserByGoogleId(googleId);
            if (user == null) {
                SecurityContextLogoutHandler ctxLogOut = new SecurityContextLogoutHandler();
                ctxLogOut.logout(request, response, auth);
                model.getModel().put("errorMessage", messages.get("errorMessage.user.social.network.google.null"));
                user = new User();
                user.setGoogleId(googleId);
                user.setUserName(googleUserName);
                user.setLastName(googleLastName);
                user.setEmail(googleEmail);
                user.setEnabled(true);
                model.addObject("user", user);
                model.setViewName(Links.REGISTRATION);
            } else {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        return model;
    }
}