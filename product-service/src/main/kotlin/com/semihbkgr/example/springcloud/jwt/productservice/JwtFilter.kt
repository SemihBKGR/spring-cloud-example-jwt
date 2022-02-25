package com.semihbkgr.example.springcloud.jwt.productservice

import com.fasterxml.jackson.databind.ObjectMapper
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

class JwtFilter(private val jwtComponent: JwtComponent, private val objectMapper: ObjectMapper) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = exchange.request.headers.getFirst("Authorization")
        if (token != null) {
            if (token.startsWith("Bearer ")) {
                try {
                    val cliams = jwtComponent.validate(token.substring(7))
                    val sub = cliams["sub"]
                    val authorities = cliams["authorities"] as String
                    val authoritySet = objectMapper.readValue(authorities, Set::class.java)
                        .stream()
                        .map { a ->
                            a as String
                        }.map { a ->
                            SimpleGrantedAuthority(a)
                        }.collect(Collectors.toList())
                    val authentication = UsernamePasswordAuthenticationToken(sub, cliams, authoritySet)
                    ReactiveSecurityContextHolder.withAuthentication(authentication)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"))
                }
            }
        }
        return chain.filter(exchange)
    }

}