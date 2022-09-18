package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.user.UserBook;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends
        JpaRepository<UserBook, Long>,
        QuerydslPredicateExecutor<UserBook> {

    @EntityGraph(attributePaths = {"book.author.lastname", "book.author.firstname", "book.id", "book.title"})
    List<UserBook> findAllByUserId(Long Id);

    @EntityGraph(attributePaths = {"book", "user"})
    Optional<UserBook> findByUser_UsernameAndAndBook_Id(String username, Long bookId);


}
