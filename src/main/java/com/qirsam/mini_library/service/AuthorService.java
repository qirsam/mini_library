package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.dto.AuthorReadDto;
import com.qirsam.mini_library.mapper.AuthorCreateUpdateMapper;
import com.qirsam.mini_library.mapper.AuthorReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
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

    @Transactional
    public AuthorReadDto create(AuthorCreateUpdateDto authorDto) {
        return Optional.of(authorDto)
                .map(authorCreateUpdateMapper::map)
                .map(authorRepository::save)
                .map(authorReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AuthorReadDto> update(Integer id, AuthorCreateUpdateDto authorDto) {
     return authorRepository.findById(id)
             .map(author -> authorCreateUpdateMapper.map(authorDto, author))
             .map(authorRepository::saveAndFlush)
             .map(authorReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id){
        return authorRepository.findById(id)
                .map(author -> {
                    authorRepository.delete(author);
                    authorRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
