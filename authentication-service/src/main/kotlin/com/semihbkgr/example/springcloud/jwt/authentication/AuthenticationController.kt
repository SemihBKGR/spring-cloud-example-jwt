package com.semihbkgr.example.springcloud.jwt.authentication

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/authentication")
class AuthenticationController(
    val userRepository: com.semihbkgr.example.springcloud.jwt.authentication.UserRepository,
    val passwordEncoder: PasswordEncoder,
    val userTokenComponent: com.semihbkgr.example.springcloud.jwt.authentication.UserJwtComponent
) {

    @PostMapping
    fun jwtToken(
        @RequestBody user: com.semihbkgr.example.springcloud.jwt.authentication.User,
        exchange: ServerWebExchange
    ): Mono<com.semihbkgr.example.springcloud.jwt.authentication.User> {
        return userRepository.findByUsername(user.username)
            .filter { userFromDB ->
                passwordEncoder.matches(user.password, userFromDB.password)
            }
            .doOnNext { u ->
                val jwt = userTokenComponent.generate(u)
                exchange.response.headers.add("Authorization", "Bearer " + jwt)
            }
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials")))
    }

}