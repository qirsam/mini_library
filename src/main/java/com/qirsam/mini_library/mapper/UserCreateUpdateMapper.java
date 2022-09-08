package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.dto.UserCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateUpdateMapper implements Mapper<UserCreateUpdateDto, User> {

    private final PasswordEncoder passwordEncoder;

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

    private void copy(UserCreateUpdateDto object, User user, Role role) {
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(role);

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}
