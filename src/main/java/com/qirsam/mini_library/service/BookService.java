package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.entity.filter.BookFilter;
import com.qirsam.mini_library.database.repository.BookRepository;
import com.qirsam.mini_library.dto.BookCreateUpdateDto;
import com.qirsam.mini_library.dto.BookReadDto;
import com.qirsam.mini_library.mapper.BookCreateUpdateMapper;
import com.qirsam.mini_library.mapper.BookReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookCreateUpdateMapper bookCreateUpdateMapper;
    private final BookReadMapper bookReadMapper;
    private final BookRepository bookRepository;

    public List<BookReadDto> findAll(BookFilter filter){
        return bookRepository.findAll().stream()
//                .filter(book -> book.)
                .map(bookReadMapper::map)
                .toList();
    }

    @Transactional
    public BookReadDto create(BookCreateUpdateDto bookDto) {
        return Optional.of(bookDto)
                .map(bookCreateUpdateMapper::map)
                .map(bookRepository::save)
                .map(bookReadMapper::map)
                .orElseThrow();
    }

}
