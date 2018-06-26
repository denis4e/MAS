package com.mas.util;

import com.mas.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.ModelAndView;

public class Pagination {

    public static void addPaginationParams(ModelAndView modelAndView, Page<User> page, String link) {
        int current = page.getNumber() + 1;
        int begin = 1;
        int end = page.getTotalPages();
        modelAndView.addObject("beginIndex", begin);
        modelAndView.addObject("endIndex", end);
        modelAndView.addObject("currentIndex", current);
        modelAndView.addObject("link", link);
    }
}
