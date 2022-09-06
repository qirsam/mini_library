package com.qirsam.mini_library.database.entity.filter;

import com.qirsam.mini_library.database.entity.library.Genre;

public record BookFilter(String title,
                         String authorLastname,
                         Genre genre) {
}
