package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Author;
import com.qirsam.mini_library.database.entity.library.Book;
import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookCreateUpdateMapper implements Mapper<BookCreateUpdateDto, Book> {
    private final AuthorRepository authorRepository;

    @Override
    public Book map(BookCreateUpdateDto object) {
        var book = new Book();

        book.setTitle(object.getTitle());
        book.setAuthor(getAuthor(object.getAuthorId()));
        book.setGenre(object.getGenre());
        book.setDescription(object.getDescription());

        return book;
    }

    private Author getAuthor(Integer authorId) {
        return Optional.ofNullable(authorId)
                .flatMap(authorRepository::findById)
                .orElse(null);
    }
}
