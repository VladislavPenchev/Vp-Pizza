package com.penchev.vppizzashop.domain.models.binding;

import com.penchev.vppizzashop.utils.validator.NotSamePassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//@NotSamePassword.List({
//        @NotSamePassword(
//                password = "newPassword",
//                confirmPassword = "confirmPassword",
//                message = "{password.match.confirmPassword}"
//        )
//})
public class ProfileBindingModel {
    private String firstName;
    private String surName;
    private String email;
//    private String currentPassword;
//    private String newPassword;
//    private String confirmPassword;

    @NotBlank(message = "First name empty")
    @Size.List({
            @Size(min = 6, message = "{username.size.min}"),
            @Size(max = 30, message = "{username.size.max}")
    })
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank(message = "Sur name empty")
    @Size.List({
            @Size(min = 6, message = "{username.size.min}"),
            @Size(max = 30, message = "{username.size.max}")
    })
    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    @Email(message = "{email.valid}")
    @NotBlank(message = "{email.empty}")
    @Size.List({
            @Size(min = 6, message = "{email.size.min}"),
            @Size(max = 60, message = "{email.size.max}")
    })
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @Size.List({
//            @Size(min = 8, message = "{password.size.min}"),
//            @Size(max = 30, message = "{password.size.max}")
//    })
//    public String getCurrentPassword() {
//        return currentPassword;
//    }
//
//    public void setCurrentPassword(String currentPassword) {
//        this.currentPassword = currentPassword;
//    }
//
//    @Size.List({
//            @Size(min = 8, message = "{password.size.min}"),
//            @Size(max = 30, message = "{password.size.max}")
//    })
//    public String getNewPassword() {
//        return newPassword;
//    }
//
//    public void setNewPassword(String newPassword) {
//        this.newPassword = newPassword;
//    }
//
//    @Size.List({
//            @Size(min = 8, message = "{password.size.min}"),
//            @Size(max = 30, message = "{password.size.max}")
//    })
//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
}
