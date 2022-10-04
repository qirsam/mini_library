package com.qirsam.mini_library.validation;

import com.qirsam.mini_library.validation.impl.ValidChangePasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidChangePasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidChangePassword {
    String message() default "{com.qirsam.mini_library.validation.password.ValidRepeatPassword}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
