package com.qirsam.mini_library.validation;

import com.qirsam.mini_library.validation.impl.UniqueBookValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueBookValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueBook {
    String message() default "{com.qirsam.mini_library.validation.unique.book}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
