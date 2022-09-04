package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select b from Book b " +
                   "where b.title = :title")
    Optional<Book> findByTitle(String title);
}
