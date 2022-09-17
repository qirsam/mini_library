package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.library.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

    @Query(value = "select b from Book b " +
                   "where b.title = :title")
    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleAndAuthor_Id(String title, Integer authorId);

    List<Book> findAllByAuthor_Id(Integer authorId);
}
