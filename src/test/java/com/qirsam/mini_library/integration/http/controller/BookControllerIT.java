package com.qirsam.mini_library.integration.http.controller;

import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.database.entity.user.Status;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class BookControllerIT extends IntegrationTestBase {

    private static final Long TEST_BOOK_ID = 1L;
    private final MockMvc mockMvc;


    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/books/" + TEST_BOOK_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("book", "statuses", "genres", "authors"),
                        view().name("book/book")
                );
    }

    @Test
    void setStatus() throws Exception {
        mockMvc.perform(post("/books/" + TEST_BOOK_ID + "/status")
                        .param("id", TEST_BOOK_ID.toString())
                        .param("status", Status.COMPLETED.name())
                        .with(csrf())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/books/" + TEST_BOOK_ID)

                );
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("books", "genres", "filter"),
                        view().name("book/books")
                );
    }

    @Test
    void addBook() throws Exception {
        mockMvc.perform(get("/books/add-book"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("book", "genres", "authors"),
                        view().name("book/add-book")
                );
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/books/add-book")
                .param("title", "Сильмариллион")
                .param("authorId", "1")
                .param("genre", Genre.FANTASY.name())
                .param("description", "123")
                .with(csrf())
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/books/{\\d+}")
                );
    }
}