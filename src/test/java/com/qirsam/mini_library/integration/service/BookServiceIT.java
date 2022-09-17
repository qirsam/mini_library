package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.database.entity.filter.BookFilter;
import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class BookServiceIT extends IntegrationTestBase {

    private static final Integer TEST_AUTHOR_ID = 1;
    private static final Long TEST_BOOK_ID = 1L;

    private static final BookCreateUpdateDto SILMARILLION = new BookCreateUpdateDto(
            "Сильмариллион",
            TEST_AUTHOR_ID,
            Genre.FANTASY,
            """
                    Произведение английского писателя Дж. Р. Р. Толкина, изданное посмертно его сыном Кристофером.\040
                    «Сильмариллион» представляет собой сборник мифов и легенд Средиземья, описывающих с точки зрения Валар\040
                    и эльфов историю Арды с момента её сотворения.
                    """
    );
    private final BookService bookService;

    @Test
    void findById() {
        var result = bookService.findById(TEST_BOOK_ID);
        assertTrue(result.isPresent());
        result.ifPresent(book -> assertEquals(book.getTitle(), "Властелин колец"));

    }

    @Test
    void findAll() {
        var filter = new BookFilter("Властелин", null, null);
        var pageRequest = PageRequest.of(0, 2, Sort.unsorted());
        var result = bookService.findAll(filter, pageRequest);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    void create() {
        var result = bookService.create(SILMARILLION);

        assertThat(result)
                .satisfies(book -> assertThat(book.getTitle()).isEqualTo(SILMARILLION.getTitle()))
                .satisfies(book -> assertThat(book.getAuthor().getId()).isEqualTo(SILMARILLION.getAuthorId()))
                .satisfies(book -> assertThat(book.getGenre()).isEqualTo(SILMARILLION.getGenre()))
                .satisfies(book -> assertThat(book.getDescription()).isEqualTo(SILMARILLION.getDescription()));
    }

    @Test
    void update() {
        var result = bookService.update(TEST_BOOK_ID, SILMARILLION);

        assertThat(result)
                .isPresent()
                .get()
                .satisfies(book -> assertThat(book.getTitle()).isEqualTo(SILMARILLION.getTitle()))
                .satisfies(book -> assertThat(book.getAuthor().getId()).isEqualTo(SILMARILLION.getAuthorId()))
                .satisfies(book -> assertThat(book.getGenre()).isEqualTo(SILMARILLION.getGenre()))
                .satisfies(book -> assertThat(book.getDescription()).isEqualTo(SILMARILLION.getDescription()));

    }

    @Test
    void delete() {
        var result = bookService.delete(TEST_BOOK_ID);

        assertThat(result).isEqualTo(true);
    }
}