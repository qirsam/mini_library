package com.qirsam.mini_library.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookService {
//
//    @Transactional
//    public BookReadDto create(BookCreateEditDto bookDto){
//
//    }
}
