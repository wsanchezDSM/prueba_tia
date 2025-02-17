package com.tia.principal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final ExternalServersConfig externalServersConfig;

    @Autowired
    public SecurityConfig(ExternalServersConfig externalServersConfig) {
        this.externalServersConfig = externalServersConfig;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/","/**").permitAll().anyRequest().
                hasRole("ADMIN").and().csrf().disable();
        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins(externalServersConfig.getCors()).
                allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }
}
