package com.qirsam.mini_library.database.repository.filter;

import com.qirsam.mini_library.database.entity.filter.BookFilter;
import com.qirsam.mini_library.database.entity.library.Book;
import com.qirsam.mini_library.database.querydsl.QPredicates;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.qirsam.mini_library.database.entity.library.QBook.book;

@RequiredArgsConstructor
public class BookFilterRepositoryImpl implements BookFilterRepository {

    private final EntityManager entityManager;

    @Override
    public List<Book> findAllByFilter(BookFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.title(), book.title::containsIgnoreCase)
                .add(filter.authorLastname(), book.author.lastname::containsIgnoreCase)
                .add(filter.genre(), book.genre::eq)
                .build();

        return new JPAQuery<Book>(entityManager)
                .select(book)
                .from(book)
                .where(predicate)
                .fetch();
    }
}
