package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.dto.AuthorReadDto;
import com.qirsam.mini_library.mapper.AuthorCreateUpdateMapper;
import com.qirsam.mini_library.mapper.AuthorReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorReadMapper authorReadMapper;
    private final AuthorCreateUpdateMapper authorCreateUpdateMapper;

    public List<AuthorReadDto> findAll() {
        return authorRepository.findAllByOrderByLastnameAsc().stream()
                .map(authorReadMapper::map)
                .toList();
    }

    public Optional<AuthorReadDto> findById(Integer id) {
        return authorRepository.findById(id)
                .map(authorReadMapper::map);
    }

    public AuthorReadDto create(AuthorCreateUpdateDto authorDto) {
        return Optional.of(authorDto)
                .map(authorCreateUpdateMapper::map)
                .map(authorRepository::save)
                .map(authorReadMapper::map)
                .orElseThrow();
    }
}
