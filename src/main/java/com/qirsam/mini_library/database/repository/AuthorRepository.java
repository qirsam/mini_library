package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.library.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer>, QuerydslPredicateExecutor<Author> {

    List<Author> findAllByOrderByLastnameAsc();

}
