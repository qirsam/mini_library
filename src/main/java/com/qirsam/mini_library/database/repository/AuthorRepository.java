package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.library.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
