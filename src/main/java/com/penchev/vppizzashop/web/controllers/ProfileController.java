package com.penchev.vppizzashop.web.controllers;

import com.penchev.vppizzashop.domain.models.binding.ProfileBindingModel;
import com.penchev.vppizzashop.domain.models.services.EditServiceModel;
import com.penchev.vppizzashop.domain.models.view.EditProfileViewModel;
import com.penchev.vppizzashop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;

@Controller
public class ProfileController {

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Autowired
    public ProfileController(UserService userService, ModelMapper modelMapper, MessageSource messageSource) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @GetMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(@PathVariable String username, ModelAndView modelAndView) {
        EditProfileViewModel model = this.userService.loadEditProfile(username);

        modelAndView.addObject("model", model);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping("/{username}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute ProfileBindingModel model,@PathVariable String username, ModelAndView modelAndView, Locale locale) {

        List<String> errors = userService.validateForm(model);
        if (!StringUtils.isEmpty(userService.checkEmailExists(model.getEmail()))) {
            errors.add(messageSource.getMessage(userService.checkEmailExists(model.getEmail()), null, locale));
        }

        if (errors.isEmpty()) {
           EditServiceModel editServiceModel =  this.modelMapper.map(model, EditServiceModel.class);
           this.userService.editProfile(editServiceModel, username);

            modelAndView.setViewName(String.format("redirect:/%s", username));
        } else {
            modelAndView.addObject("model", model);
            modelAndView.addObject("errors", errors);
        }
        return modelAndView;
    }
}
