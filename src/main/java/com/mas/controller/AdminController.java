package com.mas.controller;

import com.mas.util.Messages;
import com.mas.dao.repository.UserRepository;
import com.mas.domain.User;
import com.mas.util.Links;
import com.mas.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Messages messages;

    @RequestMapping(value = "/usersList", method = RequestMethod.GET)
    public ModelAndView registeredUsers(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView();
        Page<User> page = userRepository.findAll(pageable);
        if (page != null) {
            modelAndView.addObject("userList", page.getContent());
            Pagination.addPaginationParams(modelAndView, page, "usersList");
        } else {
            modelAndView.addObject("errorMessage", messages.get("errorMessage.user.list.null"));
            modelAndView.setViewName(Links.USERS_LIST);
        }
        return modelAndView;
    }


}

