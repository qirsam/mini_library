package com.qirsam.mini_library.aop.logging;

import com.qirsam.mini_library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecurityLogs {

    private final UserService userService;
    private final HttpServletRequest request;

    @Pointcut("execution(public void com.qirsam.mini_library.service.UserService.changeUserPassword(*))")
    public void isChangeUserPasswordMethod() {
    }


    @After("com.qirsam.mini_library.aop.CommonPointcuts.isServiceLayer() " +
            "&& target(com.qirsam.mini_library.service.UserService) " +
            "&& isChangeUserPasswordMethod()")
    public void doChangePassword() {
        UserDetails principal = userService.getPrincipal();
        log.info("user {} (ip: {}) change password", principal.getUsername(), request.getRemoteAddr());
    }


    @AfterThrowing(pointcut = "execution(* org.springframework.security.authentication.ProviderManager.authenticate(..)) " +
            "&& args(authentication) " +
            "&& target(org.springframework.security.authentication.ProviderManager)"
            , throwing = "ex")
    public void enteredBadCredentials(Throwable ex, Authentication authentication) {
        log.info("Bad Credentials (ip : {}) entered username: {}  ex: {}", request.getRemoteAddr(), authentication.getPrincipal(), ex.getMessage());
    }

}
