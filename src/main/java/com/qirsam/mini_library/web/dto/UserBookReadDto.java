package com.qirsam.mini_library.web.dto;

import com.qirsam.mini_library.database.entity.library.Book;
import com.qirsam.mini_library.database.entity.user.Status;
import com.qirsam.mini_library.database.entity.user.User;
import lombok.Value;

@Value
public class UserBookReadDto {
    Long id;
    Book book;
    User user;
    Status status;
}
