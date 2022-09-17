package com.qirsam.mini_library.service;

import com.qirsam.mini_library.database.entity.filter.UserFilter;
import com.qirsam.mini_library.database.entity.user.QUser;
import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.database.entity.user.User;
import com.qirsam.mini_library.database.querydsl.QPredicates;
import com.qirsam.mini_library.database.repository.UserRepository;
import com.qirsam.mini_library.dto.UserCreateUpdateDto;
import com.qirsam.mini_library.dto.UserReadDto;
import com.qirsam.mini_library.mapper.UserCreateUpdateMapper;
import com.qirsam.mini_library.mapper.UserReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserCreateUpdateMapper userCreateUpdateMapper;
    private final UserReadMapper userReadMapper;
    private final UserRepository userRepository;

    public Optional<UserReadDto> findById(Long id) {
        var principal = getPrincipal();
        return userRepository.findById(id)
                .map(userReadMapper::map)
                .filter(user -> user.getUsername().equals(principal.getUsername())
                                || principal.getAuthorities().contains(Role.ADMIN)
                                || principal.getAuthorities().contains(Role.MODERATOR));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.username(), QUser.user.username::containsIgnoreCase)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateUpdateDto userDto) {
        return Optional.of(userDto)
                .map(userCreateUpdateMapper::map)
                .map(user -> {
                    user.setRole(Role.USER);
                    return userRepository.save(user);
                })
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateUpdateDto userDto) {
        var principal = getPrincipal();
        return userRepository.findById(id)
                .filter(user -> user.getUsername().equals(principal.getUsername())
                                || principal.getAuthorities().contains(Role.ADMIN)
                                || principal.getAuthorities().contains(Role.MODERATOR))
                .map(user -> {
                    user.setPassword(userRepository.findById(id).get().getPassword()); // TODO: 17.09.2022 нормальная смена пароля, костыль
                    return userCreateUpdateMapper.map(userDto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

}
