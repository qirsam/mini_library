package com.qirsam.mini_library.mapper;

import com.qirsam.mini_library.database.entity.library.Author;
import com.qirsam.mini_library.database.entity.library.Book;
import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.util.ImageUtil;
import com.qirsam.mini_library.web.dto.BookCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class BookCreateUpdateMapper implements Mapper<BookCreateUpdateDto, Book> {
    private final AuthorRepository authorRepository;
    private final ImageUtil imageUtil;
    @Override
    public Book map(BookCreateUpdateDto object) {
        var book = new Book();
        copy(object, book, imageUtil.getBookImageFilename());
        return book;
    }

    @Override
    public Book map(BookCreateUpdateDto fromObject, Book toObject) {
        copy(fromObject, toObject, toObject.getId() + ".jpg");
        return toObject;
    }

    private void copy(BookCreateUpdateDto fromObject, Book toObject, String imageFileName) {
        toObject.setTitle(fromObject.getTitle());
        toObject.setAuthor(getAuthor(fromObject.getAuthorId()));
        toObject.setGenre(fromObject.getGenre());
        toObject.setDescription(fromObject.getDescription());

        Optional.ofNullable(fromObject.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> toObject.setImage(imageFileName));
    }

    private Author getAuthor(Integer authorId) {
        return Optional.ofNullable(authorId)
                .flatMap(authorRepository::findById)
                .orElse(null);
    }
}
