package com.semihbkgr.springcloud.authorization.productservice

import com.semihbkgr.springcloud.authorization.jwt.JwtComponent
import com.semihbkgr.springcloud.authorization.jwt.JwtComponentImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity, jwtFilter: JwtFilter): SecurityWebFilterChain? {
        return http.authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()
            .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
            .build()
    }

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService? {
        return MapReactiveUserDetailsService()
    }

    @Bean
    fun jwtFilter(jwtComponent: JwtComponent): JwtFilter {
        return JwtFilter(jwtComponent)
    }

    @Bean
    fun jwtComponent(@Value("\${jwt.secret}") secret: String): JwtComponent {
        return JwtComponentImpl(secret)
    }

}