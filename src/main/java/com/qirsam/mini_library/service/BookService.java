package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.entity.filter.BookFilter;
import com.qirsam.mini_library.database.querydsl.QPredicates;
import com.qirsam.mini_library.database.repository.BookRepository;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.dto.BookReadDto;
import com.qirsam.mini_library.mapper.BookCreateUpdateMapper;
import com.qirsam.mini_library.mapper.BookReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.qirsam.mini_library.database.entity.library.QBook.book;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookCreateUpdateMapper bookCreateUpdateMapper;
    private final BookReadMapper bookReadMapper;
    private final BookRepository bookRepository;

    public Optional<BookReadDto> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookReadMapper::map);
    }

    public Page<BookReadDto> findAll(BookFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.title(), book.title::containsIgnoreCase)
                .add(filter.authorLastname(), book.author.lastname::containsIgnoreCase)
                .add(filter.genre(), book.genre::eq).build();

        return bookRepository.findAll(predicate, pageable).map(bookReadMapper::map);
    }

    @Transactional
    public BookReadDto create(BookCreateUpdateDto bookDto) {
        return Optional.of(bookDto)
                .map(bookCreateUpdateMapper::map)
                .map(bookRepository::save)
                .map(bookReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public Optional<BookReadDto> update(Long id, BookCreateUpdateDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> bookCreateUpdateMapper.map(bookDto, book))
                .map(bookRepository::saveAndFlush)
                .map(bookReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public boolean delete(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    bookRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
