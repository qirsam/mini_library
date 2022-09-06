package com.qirsam.mini_library.dto;

import lombok.Value;

@Value
public class AuthorReadDto {
    Integer id;
    String firstname;
    String lastname;
    String description;
}
