package com.qirsam.mini_library.config;

import com.qirsam.mini_library.database.entity.user.Role;
import com.qirsam.mini_library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Set;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration{

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(urlConfig -> urlConfig
                .antMatchers("/books/add-book").hasAnyAuthority(Role.MODERATOR.getAuthority(), Role.ADMIN.getAuthority())
                .antMatchers("/registration", "/login", "/", "/api/v1/**", "/images/**", "/css/**").permitAll()
                .antMatchers( "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .antMatchers("/books", "/books/{\\d+}", "/authors", "/authors/{\\d+}").permitAll()
                .anyRequest().authenticated() //todo Обновление и удаление для админа и модератора
        );


        http
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/books"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))

                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/check-users")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                        .failureUrl("/registration"));

        return http.build();
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String username = userRequest.getIdToken().getClaim("email");
            try {
                userService.loadUserByUsername(username);
            } catch (UsernameNotFoundException ex) {
                return new DefaultOidcUser(Collections.singleton(Role.USER_GOOGLE), userRequest.getIdToken());
            }
            UserDetails userDetails = userService.loadUserByUsername(username);

            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            var userDetailsMethods = Set.of(UserDetails.class.getMethods());
            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }
}
