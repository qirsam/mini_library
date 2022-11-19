package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.entity.filter.AuthorFilter;
import com.qirsam.mini_library.database.querydsl.QPredicates;
import com.qirsam.mini_library.database.repository.AuthorRepository;
import com.qirsam.mini_library.mapper.AuthorCreateUpdateMapper;
import com.qirsam.mini_library.mapper.AuthorReadMapper;
import com.qirsam.mini_library.util.ImageUtil;
import com.qirsam.mini_library.util.MainUtilityClass;
import com.qirsam.mini_library.web.dto.AuthorCreateUpdateDto;
import com.qirsam.mini_library.web.dto.AuthorReadDto;
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
    private final ImageService imageService;
    private final ImageUtil imageUtil;

    private static final String authorFolder = "author/";

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
                .map(dto -> {
                    imageService.uploadImageToDisk(dto.getImage(), authorFolder, imageUtil.getAuthorImageFilename());
                    return authorCreateUpdateMapper.map(dto);
                })
                .map(authorRepository::save)
                .map(authorReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public Optional<AuthorReadDto> update(Integer id, AuthorCreateUpdateDto authorDto) {
     return authorRepository.findById(id)
             .map(author -> {
                 imageService.uploadImageToDisk(authorDto.getImage(), authorFolder, author.getId() + ".jpg");
                 return authorCreateUpdateMapper.map(authorDto, author);
             })
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
