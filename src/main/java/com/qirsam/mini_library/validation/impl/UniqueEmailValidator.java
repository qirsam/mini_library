package com.qirsam.mini_library.validation.impl;

import com.qirsam.mini_library.database.repository.UserRepository;
import com.qirsam.mini_library.dto.UserCreateUpdateDto;
import com.qirsam.mini_library.validation.UniqueEmail;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserCreateUpdateDto> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserCreateUpdateDto value, ConstraintValidatorContext context) {
        if (userRepository.findByUsername(value.getUsername()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.qirsam.mini_library.validation.unique.username}")
                    .addPropertyNode("username").addConstraintViolation();
            return false;
        }
        return true;
    }
}
