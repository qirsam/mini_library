package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.user.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateUpdateDto {

    String username;

    String rawPassword;

    String firstname;

    String lastname;

    LocalDate birthDate;

    Role role;
}
