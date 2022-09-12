package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.database.entity.user.Status;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UserBookServiceIT extends IntegrationTestBase {

    private final UserBookService userBookService;
    private static final Long TEST_USER_ID = 3L;
    private static final Long TEST_BOOK_ID_ONE = 1L;
    private static final Long TEST_BOOK_ID_TWO = 2L;

    @Test
    void findByUserId() {
        var actualResult = userBookService.findByUserId(TEST_USER_ID, Pageable.unpaged());

        assertThat(actualResult)
                .anySatisfy(userBookReadDto -> {
                    assertThat(userBookReadDto.getBook().getId()).isEqualTo(TEST_BOOK_ID_ONE);
                    assertThat(userBookReadDto.getStatus()).isEqualTo(Status.READING);
                })
                .anySatisfy(userBookReadDto -> {
                    assertThat(userBookReadDto.getBook().getId()).isEqualTo(TEST_BOOK_ID_TWO);
                    assertThat(userBookReadDto.getStatus()).isEqualTo(Status.COMPLETED);
                });
    }

    @Test
    void findByPrincipalUserIdAndBookId() {
    }
}