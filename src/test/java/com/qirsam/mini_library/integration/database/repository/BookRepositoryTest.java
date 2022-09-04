package com.qirsam.mini_library.integration.database.repository;

import com.qirsam.mini_library.database.repository.BookRepository;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;






class BookRepositoryTest extends IntegrationTestBase {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void findByTitle() {
        var mayBeBook = bookRepository.findByTitle("Властелин колец");
        assertThat(mayBeBook).isPresent();
        mayBeBook.ifPresent(book -> assertThat(book.getTitle()).isEqualTo("Властелин колец"));

    }
}