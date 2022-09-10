package com.qirsam.mini_library.dto;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.entity.user.UserBook;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class UserReadDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    Role role;
    List<UserBook> userBooks;
}
