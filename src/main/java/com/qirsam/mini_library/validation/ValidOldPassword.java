package com.qirsam.mini_library.validation;

import com.qirsam.mini_library.validation.impl.ValidOldPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidOldPasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOldPassword {
    String message() default "{com.qirsam.mini_library.validation.password.ValidOldPassword}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
