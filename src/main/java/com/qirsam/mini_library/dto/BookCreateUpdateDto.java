package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.validation.UniqueBook;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotEmpty;

@Value
@UniqueBook
@FieldNameConstants
public class BookCreateUpdateDto {
    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.title}")
    String title;

    Integer authorId;

    Genre genre;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.description}")
    String description;
}
