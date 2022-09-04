package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.dto.AuthorReadDto;
import com.qirsam.mini_library.mapper.AuthorReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorReadMapper authorReadMapper;

    public List<AuthorReadDto> findAll() {
        return authorRepository.findAll().stream()
                .map(authorReadMapper::map)
                .toList();
    }
}
