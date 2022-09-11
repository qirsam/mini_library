package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.repository.UserRepository;
import com.qirsam.mini_library.dto.UserCreateUpdateDto;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;
    private final UserRepository userRepository;
    private static final Long USER_ID = 1L;
    private static final String VALID_TEST_USERNAME = "qirsam@mail.com";
    private static final String INVALID_TEST_USERNAME = "invalidtestusername@mail.com";


    @Test
    void findById() {

        var mayBeUser = userService.findById(USER_ID);

        assertThat(mayBeUser)
                .isPresent()
                .get()
                .satisfies(user -> assertThat(user.getUsername()).isEqualTo("masha@mail.com"));
    }

    @Test
    void create() {
        var userDto = new UserCreateUpdateDto(
                "test@gmail.com",
                "{noop}123",
                "test",
                "test",
                LocalDate.now(),
                null
        );
        var actualResult = userService.create(userDto);

        assertThat(actualResult)
                .satisfies(user -> assertThat(user.getUsername()).isEqualTo("test@gmail.com"))
                .satisfies(user -> assertThat(user.getFirstname()).isEqualTo("test"))
                .satisfies(user -> assertThat(user.getLastname()).isEqualTo("test"))
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
}