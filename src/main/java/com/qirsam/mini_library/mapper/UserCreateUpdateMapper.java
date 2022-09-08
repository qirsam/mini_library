package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.dto.UserCreateUpdateDto;
import org.springframework.stereotype.Component;

@Component
public class UserCreateUpdateMapper implements Mapper<UserCreateUpdateDto, User> {
    @Override
    public User map(UserCreateUpdateDto object) {
        var user = new User();
        copy(object, user, Role.USER);
        return user;
    }

    @Override
    public User map(UserCreateUpdateDto fromObject, User toObject) {
        copy(fromObject, toObject, toObject.getRole());
        return toObject;
    }

    private static void copy(UserCreateUpdateDto object, User user, Role role) {
        user.setEmail(object.getEmail());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(role);
        user.setPassword(object.getRawPassword());
    }
}
