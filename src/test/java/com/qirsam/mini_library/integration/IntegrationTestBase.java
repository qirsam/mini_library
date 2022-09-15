package com.qirsam.mini_library.integration;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@IT
@Sql({
        "classpath:sql/data.sql"
})
public abstract class IntegrationTestBase {

    protected static final Long PRINCIPLE_USER_ID = 5L;

    @BeforeEach
    void init() {
        List<GrantedAuthority> roles = List.of(Role.ADMIN, Role.USER);
        var testUser = new User("test@gmail.com", "test", "test", "test", LocalDate.of(2000, 1, 1), Role.ADMIN, new ArrayList<>());
        testUser.setId(PRINCIPLE_USER_ID);
        var authenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);

        var securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.5").withReuse(true);

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

}
