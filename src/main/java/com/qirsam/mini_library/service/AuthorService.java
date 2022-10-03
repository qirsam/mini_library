package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.entity.filter.AuthorFilter;
import com.qirsam.mini_library.database.querydsl.QPredicates;
import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.dto.AuthorReadDto;
import com.qirsam.mini_library.mapper.AuthorCreateUpdateMapper;
import com.qirsam.mini_library.mapper.AuthorReadMapper;
import com.qirsam.mini_library.utility.MainUtilityClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.qirsam.mini_library.database.entity.library.QAuthor.author;

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

    public Page<AuthorReadDto> findAll(AuthorFilter filter, int pageNumber) {
        var predicate = QPredicates.builder()
                .add(filter.lastname(), author.lastname::containsIgnoreCase)
                .add(filter.firstname(), author.firstname::containsIgnoreCase)
                .build();

        return authorRepository.findAll(predicate, MainUtilityClass.defaultPageRequest(pageNumber))
                .map(authorReadMapper::map);
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public Optional<AuthorReadDto> update(Integer id, AuthorCreateUpdateDto authorDto) {
     return authorRepository.findById(id)
             .map(author -> authorCreateUpdateMapper.map(authorDto, author))
             .map(authorRepository::saveAndFlush)
             .map(authorReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
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
