package com.qirsam.mini_library.validation.impl;

import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.validation.UniqueAuthor;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueAuthorValidator implements ConstraintValidator<UniqueAuthor, AuthorCreateUpdateDto> {

    private final AuthorRepository authorRepository;

    @Override
    public boolean isValid(AuthorCreateUpdateDto value, ConstraintValidatorContext context) {
        if (authorRepository
                .findByFirstnameAndLastnameAndBirthDate(value.getFirstname(),value.getLastname(),value.getBirthDate())
                .isPresent()) {
            context.buildConstraintViolationWithTemplate("{com.qirsam.mini_library.validation.unique.author}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
