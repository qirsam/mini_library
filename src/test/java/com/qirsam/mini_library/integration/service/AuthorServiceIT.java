package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
class AuthorServiceIT extends IntegrationTestBase {

    private static final Integer TEST_AUTHOR_ID = 1;
    private final AuthorService authorService;


    @Test
    void findAll() {
        var result = authorService.findAll();
        assertThat(result.get(0).getLastname()).isEqualTo("Зыков");
    }

    @Test
    void findById() {
        var result = authorService.findById(TEST_AUTHOR_ID);

        assertThat(result)
                .isPresent()
                .get()
                .satisfies(author -> assertThat(author.getLastname()).isEqualTo("Толкин"));
    }

    @Test
    void create() {
        var uncleBob = new AuthorCreateUpdateDto(
                "Роберт",
                "Мартин",
                LocalDate.of(1952, 12, 5),
                """
                        Роберт С. Мартин, он же Дядя Боб (Uncle Bob),\040
                        — профессиональный программист, начавший карьеру в 1970 году,\040
                        сооснователь компании cleancoders.com,\040
                        основатель компании Uncle Bob Consulting LLC,\040
                        которая оказывает консультационные услуги и занимается обучением\040
                        персонала крупных корпораций. Бывший главный редактор\040
                        журнала C++ Report и первый председатель группы Agile Alliance.\040
                        Автор бестселлеров «Чистый код», «Идеальный программист»,\040
                        «Чистая архитектура» и «Чистый Agile».
                        """
        );
        var result = authorService.create(uncleBob);

        assertThat(result)
                .satisfies(author -> assertThat(author.getLastname()).isEqualTo(uncleBob.getLastname()))
                .satisfies(author -> assertThat(author.getFirstname()).isEqualTo(uncleBob.getFirstname()))
                .satisfies(author -> assertThat(author.getBirthDate()).isEqualTo(uncleBob.getBirthDate()))
                .satisfies(author -> assertThat(author.getDescription()).isEqualTo(uncleBob.getDescription()));
    }
}