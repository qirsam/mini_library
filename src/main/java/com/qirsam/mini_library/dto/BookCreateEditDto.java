package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.library.Genre;
import lombok.Value;

@Value
public class BookCreateEditDto {
    String title;
    Genre genre;
    Integer authorId;
    String description;
}
