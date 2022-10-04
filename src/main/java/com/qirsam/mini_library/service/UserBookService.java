package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.entity.user.Status;
import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.database.entity.user.UserBook;
import com.qirsam.mini_library.database.repository.BookRepository;
import com.qirsam.mini_library.database.repository.UserBookRepository;
import com.qirsam.mini_library.mapper.UserBookReadMapper;
import com.qirsam.mini_library.web.dto.UserBookReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserBookService {

    private final UserBookReadMapper userBookReadMapper;
    private final UserBookRepository userBookRepository;
    private final UserService userService;
    private final BookRepository bookRepository;

    public Page<UserBookReadDto> findByUserId(Long id, Pageable pageable) {
        var allByUserId = userBookRepository
                .findAllByUserId(id)
                .stream()
                .map(userBookReadMapper::map)
                .toList();
        return new PageImpl<>(allByUserId, pageable, allByUserId.size());
    }

    public Optional<UserBookReadDto> findByPrincipalUserIdAndBookId(Long bookId) {
        return findUserBook(bookId)
                .map(userBookReadMapper::map);
    }

    public Optional<UserBook> findUserBook(Long bookId) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        } else {
            var principal = userService.getPrincipal();
            return userBookRepository.findByUser_UsernameAndAndBook_Id(principal.getUsername(), bookId);
        }
    }

    @Transactional
    public UserBookReadDto create(UserBook userBookDto) {
        return Optional.of(userBookDto)
                .map(userBookRepository::save)
                .map(userBookReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserBookReadDto> updateStatus(Long bookId, Status status) {
        var thisUser = (User) userService.loadUserByUsername(userService.getPrincipal().getUsername());
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return Optional.ofNullable(findUserBook(bookId)
                .map(userBook -> {
                    userBook.setStatus(status);
                    return userBook;
                })
                .map(userBookRepository::saveAndFlush)
                .map(userBookReadMapper::map)
                .orElseGet(() -> create(new UserBook(
                        thisUser,
                        book,
                        status))));
    }
}
