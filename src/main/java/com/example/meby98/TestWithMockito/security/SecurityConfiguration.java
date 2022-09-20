package com.example.meby98.TestWithMockito.security;

import com.example.meby98.TestWithMockito.repository.RoleRepository;
import com.example.meby98.TestWithMockito.repository.UserRepository;
import com.example.meby98.TestWithMockito.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService(roleRepository, userRepository, this.passwordEncoder());
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin().loginProcessingUrl("/api/v1/login").defaultSuccessUrl("/api/v1/users").and()
                .headers().frameOptions().disable().and()
                // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.A).and()
                .authorizeRequests()
                .antMatchers("/api/v1/register").permitAll()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
