package com.qirsam.mini_library.validation;

import com.qirsam.mini_library.validation.impl.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "{com.qirsam.mini_library.validation.unique.username}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
