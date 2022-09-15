package com.qirsam.mini_library.validation;

import com.qirsam.mini_library.validation.impl.UniqueAuthorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueAuthorValidator.class)
public @interface UniqueAuthor {
    String message() default "{com.qirsam.mini_library.validation.unique.author}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
