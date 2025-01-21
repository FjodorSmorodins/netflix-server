package com.example.netflix.config;

import com.example.netflix.security.IpAddressFilter;
import com.example.netflix.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IpAddressFilter ipAddressFilter;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers( "/","/index.html", "/register");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/register",
                                "/api/series-view-count/**","/api/users/login",
                                "/api/languages/**", "/api/genres/**",
                                "/api/users/register", "/api/users/activate",
                                "/api/users/request-password-reset", "/api/users/reset-password",
                                "/api/movies/**", "/api/movie-view-count/**",
                                "/api/series/**", "/api/users/**",
                                "/api/movie-watchlist/**", "/api/series-watchlist/**",
                                "/api/series/**", "/api/profiles/**",
                                "/api/profiles/watch-movie", "/api/profiles/watch-series",
                                "/api/genre-for-series/**", "/api/preferences/**",
                                "/api/payments", "/api/users/invite",
                                "/api/admin/endpoints", "/error",
                                "/css/**",     // Allow access to CSS files
                                "/js/**",
                                "/img/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .logout(LogoutConfigurer::permitAll
                )
                .addFilterBefore(ipAddressFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}