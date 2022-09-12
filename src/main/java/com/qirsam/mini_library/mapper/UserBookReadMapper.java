package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.user.UserBook;
import com.qirsam.mini_library.dto.UserBookReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserBookReadMapper implements Mapper<UserBook, UserBookReadDto> {

    @Override
    public UserBookReadDto map(UserBook object) {
        return new UserBookReadDto(
                object.getId(),
                object.getBook(),
                object.getUser(),
                object.getStatus()
        );
    }
}
