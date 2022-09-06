package com.qirsam.mini_library.integration.service;

import com.qirsam.mini_library.database.entity.library.Genre;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.integration.IntegrationTestBase;
import com.qirsam.mini_library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookServiceIT extends IntegrationTestBase {

    private static final Integer AUTHOR_1 = 1;
    @Autowired
    private BookService bookService;

    @Test
    void create() {
        var bookDto = new BookCreateUpdateDto(
                "Хоббит, или Туда и обратно",
                AUTHOR_1,
                Genre.FAIRYTALE,
                """
                        Повесть английского писателя Джона Р. Р. Толкина. 
                        Впервые опубликована в 1937 году издательством 
                        George Allen & Unwin, став со временем классикой детской литературы. 
                        В основе сюжета - путешествие хоббита Бильбо Бэггинса, 
                        волшебника Гэндальфа и тринадцати гномов во главе с 
                        Торином Дубощитом. Их путь лежит к Одинокой Горе, 
                        где находятся гномьи сокровища, охраняемые драконом Смаугом.
                        """
        );
        var result = bookService.create(bookDto);

        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthorId(), result.getAuthor().getId());
        assertEquals(bookDto.getGenre(), result.getGenre());
        assertEquals(bookDto.getDescription(), result.getDescription());
    }
}