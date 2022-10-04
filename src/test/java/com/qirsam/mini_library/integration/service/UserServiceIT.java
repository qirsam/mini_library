package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.repository.UserRepository;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.UserService;
import com.qirsam.mini_library.web.dto.UserCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_ID = 1L;
    private static final String VALID_TEST_USERNAME = "qirsam@gmail.com";
    private static final String INVALID_TEST_USERNAME = "invalidtestusername@gmail.com";

    private static final UserCreateUpdateDto TEST_USER = new UserCreateUpdateDto(
            "test2@gmail.com",
            "{noop}123",
            "test",
            "test",
            LocalDate.now(),
            null
    );
    private final UserService userService;
    private final UserRepository userRepository;




    @Test
    void findById() {
        var mayBeUser = userService.findById(USER_ID);

        assertThat(mayBeUser)
                .isPresent()
                .get()
                .satisfies(user -> assertThat(user.getUsername()).isEqualTo("masha@gmail.com"));
    }

    @Test
    void create() {
        var actualResult = userService.create(TEST_USER);

        assertThat(actualResult)
                .satisfies(user -> assertThat(user.getUsername()).isEqualTo(TEST_USER.getUsername()))
                .satisfies(user -> assertThat(user.getFirstname()).isEqualTo(TEST_USER.getFirstname()))
                .satisfies(user -> assertThat(user.getLastname()).isEqualTo(TEST_USER.getLastname()))
                .satisfies(user -> assertThat(user.getRole()).isEqualTo(Role.USER))
                .satisfies(user -> assertThat(user.getId()).isEqualTo(userRepository.checkMaxUserId()));
    }

    @Test
    void loadUserByUsernameValid() {
        var actualResult = userService.loadUserByUsername(VALID_TEST_USERNAME);

        assertThat(actualResult)
                .hasFieldOrPropertyWithValue("username", VALID_TEST_USERNAME)
                .hasFieldOrPropertyWithValue("authorities", Set.of(Role.ADMIN));
    }

    @Test
    void loadUserByUsernameInvalid() {
        assertThatThrownBy(() -> userService.loadUserByUsername(INVALID_TEST_USERNAME))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Failed to retrieve user: " + INVALID_TEST_USERNAME);
    }

    @Test
    void update() {
        var result = userService.update(USER_ID, TEST_USER);

        assertThat(result)
                .isPresent()
                .get()
                .satisfies(user -> assertThat(user.getUsername()).isEqualTo(TEST_USER.getUsername()))
                .satisfies(user -> assertThat(user.getFirstname()).isEqualTo(TEST_USER.getFirstname()))
                .satisfies(user -> assertThat(user.getLastname()).isEqualTo(TEST_USER.getLastname()))
                .satisfies(user -> assertThat(user.getRole()).isEqualTo(TEST_USER.getRole()))
                .satisfies(user -> assertThat(user.getId()).isEqualTo(USER_ID));
    }

    @Test
    void delete() {
        var result = userService.delete(USER_ID);

        assertThat(result).isEqualTo(true);
    }
}