package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.user.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserCreateUpdateDto {

    String email;

    String rawPassword;

    String firstname;

    String lastname;

    LocalDate birthDate;

    Role role;
}
