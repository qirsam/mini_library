package com.qirsam.mini_library.database.repository;

import com.qirsam.mini_library.database.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
