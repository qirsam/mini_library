package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Author;
import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorCreateUpdateMapper implements  Mapper<AuthorCreateUpdateDto, Author> {

    @Override
    public Author map(AuthorCreateUpdateDto object) {
        return new Author(
                object.getFirstname(),
                object.getLastname(),
                object.getBirthDate(),
                object.getDescription()
        );
    }
}