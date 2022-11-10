package com.qirsam.mini_library.aop.logging;

import com.qirsam.mini_library.service.UserService;
import com.qirsam.mini_library.web.dto.UserCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CreateUpdateDeleteLogs {

    private final UserService userService;
    private final HttpServletRequest request;


    @Pointcut("execution(public * com.qirsam.mini_library.service.*Service.create(*))")
    public void anyCreateServiceMethod() {
    }

    @Pointcut("execution(public * com.qirsam.mini_library.service.*Service.update*(*,*))")
    public void anyUpdateServiceMethod() {
    }

    @Before("com.qirsam.mini_library.aop.CommonPointcuts.isServiceLayer() " +
            "&& !target(com.qirsam.mini_library.service.UserService) " +
            "&& anyCreateServiceMethod() " +
            "&& args(dto)")
    public void addLoggingBeforeCreate(Object dto) { //включает в себя все сервисы, кроме UserService для безопасности
        UserDetails principal = userService.getPrincipal();
        log.info("{} (ip: {}) creating {}", principal.getUsername(), request.getRemoteAddr(), dto);

    }

    @Before("com.qirsam.mini_library.aop.CommonPointcuts.isServiceLayer() " +
            "&& target(com.qirsam.mini_library.service.UserService) " +
            "&& anyCreateServiceMethod() " +
            "&& args(dto)")
    public void addLoggingForUserCreate(UserCreateUpdateDto dto) {
        log.info("ip: {} creating {} user", request.getRemoteAddr(), dto.getUsername());
    }

    @Before(value = "com.qirsam.mini_library.aop.CommonPointcuts.isServiceLayer() " +
            "&& !target(com.qirsam.mini_library.service.UserService) " +
            "&& anyUpdateServiceMethod() " +
            "&& args(id, data)")
    public void addLoggingBeforeUpdateEntity(Long id, Object data) {
        UserDetails principal = userService.getPrincipal();
        log.info("{} (ip: {}) update id {} :: {}", principal.getUsername(), request.getRemoteAddr(), id, data);
    }


}



