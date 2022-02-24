package com.semihbkgr.springcloud.authorization.productservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.semihbkgr.springcloud.authorization.jwt.JwtComponent
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.stream.Collectors

class JwtFilter(val jwtComponent: JwtComponent, val objectMapper: ObjectMapper) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val auth = exchange.request.headers.getFirst("Authorization")
        if (auth != null) {
            if (auth.startsWith("Bearer ")) {
                if (jwtComponent.validate(auth.substring(7))) {
                    var claims = jwtComponent.getClaims(auth.substring(7))
                    var authorities = objectMapper.readValue(claims["authorities"], Set::class.java).stream().map { i ->
                        i as String
                    }.map { i ->
                        SimpleGrantedAuthority(i)
                    }.collect(Collectors.toList())
                    var authentication = UsernamePasswordAuthenticationToken(claims["sub"], claims, authorities)
                    ReactiveSecurityContextHolder.withAuthentication(authentication)
                    return chain.filter(exchange)
                }
                return Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"))
            }
            return Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect token format"))
        }
        return Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication token is not available"))
    }

}