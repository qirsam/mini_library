package com.qirsam.mini_library.web.dto;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.validation.UniqueEmail;
import com.qirsam.mini_library.validation.groups.CreateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UniqueEmail(groups = CreateAction.class)
public class UserCreateUpdateDto {

    @Email
    String username;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.password}", groups = CreateAction.class)
    String rawPassword;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.firstname}")
    String firstname;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.lastname}")
    String lastname;

    @Past(message = "{com.qirsam.mini_library.validation.Past.birthDate}")
    @NotNull(message = "{com.qirsam.mini_library.validation.notNull.birthDate}")
    LocalDate birthDate;

    Role role;
}
