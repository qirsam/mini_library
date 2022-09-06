package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.library.Genre;
import lombok.Value;

@Value
public class BookCreateUpdateDto {
    String title;
    Integer authorId;
    Genre genre;
    String description;
}
