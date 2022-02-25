package com.semihbkgr.example.springcloud.jwt.productservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(security: ServerHttpSecurity, jwtFilter: JwtFilter): SecurityWebFilterChain? {
        return security.authorizeExchange()
            .anyExchange()
            .permitAll()
            .and()
            .csrf()
            .disable()
            .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
            .build()
    }

    @Bean
    fun userDetailsService(): ReactiveUserDetailsService {
        return ReactiveUserDetailsService {
            Mono.error(ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @Bean
    fun jwtFilter(jwtComponent: JwtComponent, objectMapper: ObjectMapper): JwtFilter {
        return JwtFilter(jwtComponent, objectMapper)
    }

}