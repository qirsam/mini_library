package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
class AuthorServiceIT extends IntegrationTestBase {

        private final AuthorService authorService;

    @Test
    void findAll() {
        var result = authorService.findAll();
        assertThat(result.get(0).getLastname()).isEqualTo("Толкин");
    }
}