package com.qirsam.mini_library.config;

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
import java.util.Set;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(urlConfig -> urlConfig
                .antMatchers("/registration", "/login", "/").permitAll()
                .antMatchers( "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .antMatchers("/books", "/books/*", "/authors", "/authors/*").permitAll()
                .anyRequest().authenticated()
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
                        .defaultSuccessUrl("/books")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())));

        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String username = userRequest.getIdToken().getClaim("email");
            UserDetails userDetails;
            try {
                userService.loadUserByUsername(username);
            } catch (UsernameNotFoundException ex) {
                userService.createO2AuthUser(userRequest.getIdToken());
            } finally {
                userDetails = userService.loadUserByUsername(username);
            }
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
