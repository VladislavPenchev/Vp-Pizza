package com.penchev.vppizzashop.web.controllers;

import com.penchev.vppizzashop.domain.models.binding.RegisterBindingModel;
import com.penchev.vppizzashop.domain.models.services.RegisterServiceModel;
import com.penchev.vppizzashop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;

@Controller
public class RegisterController {

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Autowired
    public RegisterController(UserService userService, ModelMapper modelMapper, MessageSource messageSource) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerView(@ModelAttribute RegisterBindingModel model, ModelAndView modelAndView) {
        modelAndView.addObject("model", model);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute RegisterBindingModel model, ModelAndView modelAndView, Locale locale) {
        List<String> errors = userService.validateForm(model);
        if (!StringUtils.isEmpty(userService.checkEmailExists(model.getEmail()))) {
            errors.add(messageSource.getMessage(userService.checkEmailExists(model.getEmail()), null, locale));
        }
        if (!StringUtils.isEmpty(userService.checkUsernameExists(model.getUsername()))) {
            errors.add(messageSource.getMessage(userService.checkUsernameExists(model.getUsername()), null, locale));
        }
        if (errors.isEmpty()) {
            RegisterServiceModel registerServicemodel = this.modelMapper.map(model, RegisterServiceModel.class);
            this.userService.register(registerServicemodel);

            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject("model", model);
            modelAndView.addObject("errors", errors);
        }
        return modelAndView;
    }

}
