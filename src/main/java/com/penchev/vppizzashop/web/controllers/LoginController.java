package com.penchev.vppizzashop.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginView(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
