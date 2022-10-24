package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.library.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer>, QuerydslPredicateExecutor<Author> {

    List<Author> findAllByOrderByLastnameAsc();

    Optional<Author> findByFirstnameAndLastnameAndBirthDate(String firstname, String lastname, LocalDate birthDate);

    @Query(value = "select last_value from author_id_seq", nativeQuery = true)
    Integer getAuthorIdSeq();
}
