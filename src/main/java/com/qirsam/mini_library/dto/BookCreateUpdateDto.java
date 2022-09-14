package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.library.Genre;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class BookCreateUpdateDto {
    String title;
    Integer authorId;
    Genre genre;
    String description;
}
