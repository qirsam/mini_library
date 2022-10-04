package com.qirsam.mini_library.validation.impl;

import com.qirsam.mini_library.validation.ValidChangePassword;
import com.qirsam.mini_library.web.dto.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidChangePasswordValidator implements ConstraintValidator<ValidChangePassword, PasswordDto> {

    @Override
    public boolean isValid(PasswordDto value, ConstraintValidatorContext context) {
        if (!value.getNewPassword().equals(value.getRepeatPassword())) {
            context.buildConstraintViolationWithTemplate("{com.qirsam.mini_library.validation.password.ValidRepeatPassword}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
