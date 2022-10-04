package com.qirsam.mini_library.web.dto;

import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.validation.UniqueBook;
import com.qirsam.mini_library.validation.groups.CreateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotEmpty;

@Value
@UniqueBook(groups = CreateAction.class)
@FieldNameConstants
public class BookCreateUpdateDto {
    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.title}")
    String title;

    Integer authorId;

    Genre genre;

    @NotEmpty(message = "{com.qirsam.mini_library.validation.notEmpty.description}")
    String description;
}
