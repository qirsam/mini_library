package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.database.entity.user.Status;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class UserBookServiceIT extends IntegrationTestBase {
    private static final Long TEST_USER_ID = 3L;
    private static final Long TEST_BOOK_ID_ONE = 4L;
    private static final Long TEST_BOOK_ID_TWO = 2L;
    private final UserBookService userBookService;


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
    void createStatus() {
        var actualResult = userBookService.updateStatus(TEST_BOOK_ID_TWO, Status.PLANNED);
        assertThat(actualResult.get())
                .satisfies(userBookReadDto ->
                        userBookReadDto.getStatus().equals(Status.PLANNED)
                );
    }

    @Test
    void findByPrincipalUserIdAndBookId() {
        var actualResult = userBookService.findByPrincipalUserIdAndBookId(TEST_BOOK_ID_ONE);
        assertThat(actualResult.get())
                .satisfies(userBookReadDto ->
                        userBookReadDto.getStatus().equals(Status.READING));
    }

    @Test
    void updateStatus() {
        var actualResult = userBookService.updateStatus(TEST_BOOK_ID_ONE, Status.COMPLETED);
        assertThat(actualResult.get())
                .satisfies(userBookReadDto ->
                        userBookReadDto.getStatus().equals(Status.COMPLETED));
    }

    @Test
    void findUserBookAnonymous() {
        var securityContext = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
        securityContext.setAuthentication(new AnonymousAuthenticationToken("key","anonymous",authorities));
        SecurityContextHolder.setContext(securityContext);

        var actualResult = userBookService.findUserBook(TEST_BOOK_ID_ONE);
        assertThat(actualResult).isEqualTo(Optional.empty());
    }

}