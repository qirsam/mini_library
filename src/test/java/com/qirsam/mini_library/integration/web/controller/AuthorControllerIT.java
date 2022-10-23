package com.qirsam.mini_library.integration.web.controller;

import com.qirsam.mini_library.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.qirsam.mini_library.web.dto.AuthorCreateUpdateDto.Fields.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class AuthorControllerIT extends IntegrationTestBase {

    private static final Integer TEST_AUTHOR_ID = 1;

    private final MockMvc mockMvc;

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/authors/" + TEST_AUTHOR_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("author"),
                        view().name("book/author")
                );
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("authors"),
                        view().name("book/authors")
                );
    }

    @Test
    void addAuthor() throws Exception {
        mockMvc.perform(get("/authors/add-author"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("author"),
                        view().name("book/add-author")
                );
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(multipart("/authors/add-author")
                        .file(image, new byte[0])
                        .param(lastname, "Уоллс")
                        .param(firstname, "Крейг")
                        .param(birthDate, "1972-01-10")
                        .param(description, """
                                Крейг Уоллс (Craig Walls) работает старшим инженером-разработчиком в VMware.\040
                                Он настойчиво продвигает Spring Framework, часто выступает на встречах в местных группах\040
                                пользователей и конференциях и пишет о Spring. Когда Крейг не пишет программный код, он
                                обычно планирует свою следующую поездку в Диснейленд и проводит все свободное время\040
                                со своей супругой, двумя дочерьми, тремя собаками и попугаем.
                                """)
                        .with(csrf())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/authors/{\\d+}"),
                        model().attributeExists("id")
                );
    }
}