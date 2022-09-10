package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getFirstname(),
                object.getLastname(),
                object.getBirthDate(),
                object.getRole(),
                object.getUserBooks()
        );
    }
}
