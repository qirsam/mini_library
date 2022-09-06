package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.library.Genre;
import lombok.Value;

@Value
public class BookReadDto {
    Long id;
    String title;
    AuthorReadDto author;
    Genre genre;
    String description;
}
