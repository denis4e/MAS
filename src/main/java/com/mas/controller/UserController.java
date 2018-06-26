package com.mas.controller;

import com.mas.Messages;
import com.mas.dao.repository.RoleRepository;
import com.mas.dao.repository.UserRepository;
import com.mas.domain.Role;
import com.mas.domain.User;
import com.mas.service.EmailService;
import com.mas.service.UserUtil;
import com.mas.util.Links;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private EmailService sendingMailService;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Messages messages;
    @Autowired
    UserUtil userUtils;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(Links.REGISTRATION);
        return modelAndView;
    }

    @RequestMapping(value = "/sendPassword", method = RequestMethod.GET)
    public ModelAndView sendPasswordForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Links.SEND_PASSWORD);
        return modelAndView;
    }

    @RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) throws MessagingException, IOException, TemplateException {
        ModelAndView modelAndView = new ModelAndView();
        User userEmailExists = userRepository.findUserByEmail(user.getEmail());
        User userLoginExists = userRepository.findUserByLogin(user.getLogin());

        if (userEmailExists != null) {
            bindingResult
                    .rejectValue("email", "errorMessage.userEmail.exists", "");
        }
        if (userLoginExists != null) {
            bindingResult
                    .rejectValue("login", "errorMessage.userLogin.exists", "");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(Links.REGISTRATION);
        } else {
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            Role userRole = roleRepository.findRoleByRoleName("ROLE_USER");
            userRole.getUsers().add(user);
            user.getRoles().add(userRole);
            userRepository.save(user);
            modelAndView.addObject("successMessage", messages.get("successMessage.check.email"));
            modelAndView.addObject("user", new User());
            modelAndView.setViewName(Links.HOME);
            sendingMailService.sendMessage(user, messages.get("email.userRegistered.subject"), "userRegistered");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/sendPasswordToEmail", method = RequestMethod.GET)
    public ModelAndView sendPasswordToEmail(@RequestParam("email") String email) throws MessagingException, IOException, TemplateException {
        User user = userRepository.findUserByEmail(email);
        ModelAndView modelAndView = new ModelAndView();
        if (user != null) {
            Map<String, Object> model = new HashMap<>();
            model.put("userName", user.getUserName());
            model.put("lastName", user.getLastName());
            UUID uuid = UUID.randomUUID();
            String password = uuid.toString().replace("-", "");
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(user);
            model.put("password", password);
            sendingMailService.sendMessage(user, messages.get("email.userPassword.subject"), "userPassword", model);
            modelAndView.addObject("successMessage", messages.get("successMessage.check.email"));
            modelAndView.setViewName(Links.HOME);
        } else {
            modelAndView.addObject("errorMessage", messages.get("errorMessage.user.not.found"));
            modelAndView.setViewName(Links.SEND_PASSWORD);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public ModelAndView userProfile() {
        ModelAndView modelAndView = new ModelAndView();
        User user = userUtils.getCurrentUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(Links.USER_PROFILE);
        return modelAndView;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateUser(@RequestParam("userId") int userId, @RequestParam("phone") String phone,
                                   @RequestParam("email") String email, @RequestParam("userName") String userName, @RequestParam("lastName") String lastName) {
        ModelAndView modelAndView = new ModelAndView();
        int result = userRepository.updateUser(userId, phone, email, userName, lastName);
        if (result > 0) {
            modelAndView.addObject("successMessage", messages.get("successMessage.user.updated"));
        } else {
            modelAndView.addObject("errorMessage", messages.get("errorMessage.user.not.updated"));
        }
        User user = userUtils.getCurrentUser();
        modelAndView.addObject("user", user);
        modelAndView.setViewName(Links.USER_PROFILE);
        return modelAndView;
    }
}
