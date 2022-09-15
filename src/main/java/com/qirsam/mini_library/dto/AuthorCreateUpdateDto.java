package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.validation.UniqueAuthor;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
@UniqueAuthor
public class AuthorCreateUpdateDto {
    String firstname;
    String lastname;
    LocalDate birthDate;
    String description;
}
