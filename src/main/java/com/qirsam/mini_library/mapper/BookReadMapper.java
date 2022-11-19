package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Book;
import com.qirsam.mini_library.web.dto.BookReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookReadMapper implements Mapper<Book, BookReadDto> {

    private final AuthorReadMapper authorReadMapper;

    @Override
    public BookReadDto map(Book object) {
        var author = Optional.of(object.getAuthor())
                .map(authorReadMapper::map)
                .orElseThrow();

        return new BookReadDto(
                object.getId(),
                object.getTitle(),
                author,
                object.getGenre(),
                object.getDescription()
        );
    }
}