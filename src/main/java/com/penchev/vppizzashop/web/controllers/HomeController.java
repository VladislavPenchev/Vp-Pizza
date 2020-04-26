package com.penchev.vppizzashop.web.controllers;

import com.penchev.vppizzashop.domain.models.view.LoginViewModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Principal principal){
        String currentUserName = principal.getName();

        LoginViewModel model = new LoginViewModel();
        model.setUsername(currentUserName);

        modelAndView.addObject("model", model);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
