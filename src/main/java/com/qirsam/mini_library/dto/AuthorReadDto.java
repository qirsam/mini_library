package com.qirsam.mini_library.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AuthorReadDto {
    Integer id;
    String firstname;
    String lastname;
    LocalDate birthDate;
    String description;
}
