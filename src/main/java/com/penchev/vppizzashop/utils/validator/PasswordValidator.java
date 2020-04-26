package com.penchev.vppizzashop.utils.validator;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<NotSamePassword, Object> {

    private String password;
    private String confirmPassword;

    public void initialize(NotSamePassword constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String fieldValue = (String) new BeanWrapperImpl(value).getPropertyValue(password);
        String fieldMatchValue = (String) new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

        if (StringUtils.hasText(fieldValue)) {
            return fieldValue.equals(fieldMatchValue);
        }
        return true;
    }
}
