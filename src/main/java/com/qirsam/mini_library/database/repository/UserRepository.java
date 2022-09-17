package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findByUsername(String username);


    @Query("SELECT MAX(u.id) FROM User u")
    Long checkMaxUserId();
}
