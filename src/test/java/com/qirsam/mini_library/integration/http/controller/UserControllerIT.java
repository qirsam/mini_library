package com.qirsam.mini_library.integration.http.controller;

import com.qirsam.mini_library.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.qirsam.mini_library.dto.UserCreateUpdateDto.Fields.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private static final String DUPLICATE_EMAIL = "test@gmail.com";
    private final MockMvc mockMvc;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("user"),
                        view().name("user/registration")
                );
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/registration")
                        .param(username, "testcontroller@gmail.com")
                        .param(rawPassword, "test")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(birthDate, "2000-01-01")
                        .with(csrf())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }

    @Test
    void createWithDuplicateEmail() throws Exception {
        mockMvc.perform(post("/registration")
                        .param(username, DUPLICATE_EMAIL)
                        .param(rawPassword, "test")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(birthDate, "2000-01-01")
                        .with(csrf())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        flash().attributeExists("errors", "user"),
                        redirectedUrl("/registration")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/user/" + PRINCIPLE_USER_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("user"),
                        view().name("user/user")
                );
    }

    @Test
    void findUserBooks() throws Exception {
        mockMvc.perform(get("/user/" + PRINCIPLE_USER_ID + "/my_books"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        model().attributeExists("userBooks"),
                        view().name("user/myBooks")

                );
    }
}