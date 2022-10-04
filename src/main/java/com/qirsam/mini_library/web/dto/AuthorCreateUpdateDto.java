package com.qirsam.mini_library.web.dto;

import com.qirsam.mini_library.validation.UniqueAuthor;
import com.qirsam.mini_library.validation.groups.CreateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UniqueAuthor(groups = CreateAction.class)
public class AuthorCreateUpdateDto {

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.firstname}")
    String firstname;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.lastname}")
    String lastname;

    @NotNull(message = "{com.qirsam.mini_library.validation.notNull.birthDate}")
    LocalDate birthDate;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.description}")
    String description;
}
