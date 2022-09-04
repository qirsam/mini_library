package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorServiceIT extends IntegrationTestBase {

    @Autowired
    private AuthorService authorService;

    @Test
    void findAll() {
        var result = authorService.findAll();
        assertThat(result.get(0).getLastname()).isEqualTo("Толкин");
    }
}