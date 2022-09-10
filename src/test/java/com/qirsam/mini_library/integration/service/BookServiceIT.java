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

    private static final Integer AUTHOR_1 = 1;
    private static final Long BOOK_1 = 1L;
    private final BookService bookService;

    @Test
    void create() {
        var bookDto = new BookCreateUpdateDto(
                "Хоббит, или Туда и обратно",
                AUTHOR_1,
                Genre.FAIRYTALE,
                """
                        Повесть английского писателя Джона Р. Р. Толкина.\040
                        Впервые опубликована в 1937 году издательством\040
                        George Allen & Unwin, став со временем классикой детской литературы.\040
                        В основе сюжета - путешествие хоббита Бильбо Бэггинса,\040
                        волшебника Гэндальфа и тринадцати гномов во главе с\040
                        Торином Дубощитом. Их путь лежит к Одинокой Горе,\040
                        где находятся гномьи сокровища, охраняемые драконом Смаугом.
                        """
        );
        var result = bookService.create(bookDto);

        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthorId(), result.getAuthor().getId());
        assertEquals(bookDto.getGenre(), result.getGenre());
        assertEquals(bookDto.getDescription(), result.getDescription());
    }

    @Test
    void findById() {
        var result = bookService.findById(BOOK_1);
       assertTrue(result.isPresent());
       result.ifPresent(book -> assertEquals(book.getTitle(),"Властелин колец"));

    }

    @Test
    void findAll() {
        var filter = new BookFilter("Властелин", null, null);
        var pageRequest = PageRequest.of(0, 2, Sort.unsorted());
        var result = bookService.findAll(filter, pageRequest);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }
}