package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Author;
import com.qirsam.mini_library.dto.AuthorReadDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorReadMapper implements Mapper<Author, AuthorReadDto> {
    @Override
    public AuthorReadDto map(Author object) {
        return new AuthorReadDto(
                object.getId(),
                object.getFirstname(),
                object.getLastname(),
                object.getDescription()
        );
    }
}
