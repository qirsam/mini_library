package com.qirsam.mini_library.validation.impl;

import com.qirsam.mini_library.database.repository.BookRepository;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.validation.UniqueBook;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueBookValidator implements ConstraintValidator<UniqueBook, BookCreateUpdateDto> {

    private final BookRepository bookRepository;

    @Override
    public boolean isValid(BookCreateUpdateDto value, ConstraintValidatorContext context) {
        if (bookRepository.findByTitleAndAuthor_Id(value.getTitle(), value.getAuthorId()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.qirsam.mini_library.validation.unique.book}")
                    .addPropertyNode("title").addConstraintViolation();
            return false;
        }
        return true;
    }
}
