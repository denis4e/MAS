package com.mas.controller

import com.mas.repository.UserRepository
import com.mas.repository.RoleRepository
import com.mas.domain.User
import com.mas.service.EmailService
import com.mas.service.UserUtil
import com.mas.util.Links
import com.mas.util.Messages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/profile")
class ProfileController {
    @Autowired
    private EmailService sendingMailService

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository

    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    private Messages messages

    @Autowired
    UserUtil userUtils

    @RequestMapping(path = "/userProfile", method = RequestMethod.GET)
    def userProfile() {
        ModelAndView modelAndView = new ModelAndView()
        User user = userUtils.getCurrentUser()
        def sampleMap = [color:'Blue', shape:'Circle']
        sampleMap << [color:'Blue2']
        sampleMap.each { println it }
        modelAndView.addObject("user", user)
        modelAndView.setViewName(Links.USER_PROFILE)
        return modelAndView
    }

    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    def updateUser(@RequestParam("userId") int userId, @RequestParam("phone") String phone,
                   @RequestParam("email") String email,
                   @RequestParam("userName") String userName, @RequestParam("lastName") String lastName) {
        ModelAndView modelAndView = new ModelAndView()
        def result = userRepository.updateUser(userId, phone, email, userName, lastName)
        if (result > 0) {
            modelAndView.addObject("successMessage", messages.get("successMessage.user.updated"))
        } else {
            modelAndView.addObject("errorMessage", messages.get("errorMessage.user.not.updated"))
        }
        User user = userUtils.getCurrentUser()
        modelAndView.addObject("user", user)
        modelAndView.setViewName(Links.USER_PROFILE)
        return modelAndView
    }
}
