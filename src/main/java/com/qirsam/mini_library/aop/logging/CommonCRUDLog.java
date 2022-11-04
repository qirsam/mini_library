package com.qirsam.mini_library.aop.logging;

import com.qirsam.mini_library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommonCRUDLog {

    private final UserService userService;

    @Pointcut("execution(public * com.qirsam.mini_library.service.*Service.create(*))")
    public void anyCreateServiceMethod() {
    }

    @Before("com.qirsam.mini_library.aop.CommonPointcuts.isServiceLayer() " +
            "&& !target(com.qirsam.mini_library.service.UserService) " +
            "&& anyCreateServiceMethod() " +
            "&& args(dto)")
    public void addLoggingBeforeCreate(Object dto) { //включает в себя все сервисы, кроме UserService для безопасности
//        String nameUser = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails principal = userService.getPrincipal();
        log.info("{} creating {}", principal.getUsername(), dto);

    }
}



