package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Author;
import com.qirsam.mini_library.database.entity.library.Book;
import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.web.dto.BookCreateUpdateDto;
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
        copy(object, book);
        return book;
    }

    @Override
    public Book map(BookCreateUpdateDto fromObject, Book toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(BookCreateUpdateDto fromObject, Book toObject) {
        toObject.setTitle(fromObject.getTitle());
        toObject.setAuthor(getAuthor(fromObject.getAuthorId()));
        toObject.setGenre(fromObject.getGenre());
        toObject.setDescription(fromObject.getDescription());
    }

    private Author getAuthor(Integer authorId) {
        return Optional.ofNullable(authorId)
                .flatMap(authorRepository::findById)
                .orElse(null);
    }
}
