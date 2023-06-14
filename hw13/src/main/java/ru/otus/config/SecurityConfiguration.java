package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/login").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/author", "/api/genre", "/api/book").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/api/author/**", "/api/genre/**", "/api/book/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .rememberMe().key("appSecretKey").tokenValiditySeconds(60 * 10)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
