package com.penchev.vppizzashop.domain.models.binding;

import com.penchev.vppizzashop.utils.validator.NotSamePassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NotSamePassword.List({
        @NotSamePassword(
                password = "password",
                confirmPassword = "confirmPassword",
                message = "{password.match.confirmPassword}"
        )
})
public class RegisterBindingModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    @NotBlank(message = "{username.empty}")
    @Size.List({
            @Size(min = 6, message = "{username.size.min}"),
            @Size(max = 30, message = "{username.size.max}")
    })
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "{password.empty}")
    @Size.List({
            @Size(min = 8, message = "{password.size.min}"),
            @Size(max = 30, message = "{password.size.max}")
    })
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "{confirm.password.empty}")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
}
