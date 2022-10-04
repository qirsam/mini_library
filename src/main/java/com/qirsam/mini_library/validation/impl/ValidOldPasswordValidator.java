package com.qirsam.mini_library.validation.impl;

import com.qirsam.mini_library.service.UserService;
import com.qirsam.mini_library.validation.ValidOldPassword;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ValidOldPasswordValidator implements ConstraintValidator<ValidOldPassword, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!userService.checkIfValidOldPassword(value)) {
            context.buildConstraintViolationWithTemplate("{com.qirsam.mini_library.validation.password.ValidOldPassword}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
