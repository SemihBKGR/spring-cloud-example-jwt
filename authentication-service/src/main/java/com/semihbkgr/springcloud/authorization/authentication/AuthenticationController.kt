package com.semihbkgr.springcloud.authorization.authentication

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/authentication")
class AuthenticationController(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val userTokenComponent: UserJwtComponent
) {

    @PostMapping("/jwt")
    fun jwtToken(@RequestBody user: User, res: ServerResponse): Mono<User> {
        return userRepository.findByUsername(user.username)
            .filter { userFromDB ->
                passwordEncoder.matches(user.password, userFromDB.password)
            }
            .doOnNext { u ->
                val jwt = userTokenComponent.generate(u)
                res.headers().add("Authorization", jwt)
            }
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials")))
    }

    @PostMapping
    fun signup(@RequestBody user: User, res: ServerResponse): Mono<User> {
        return userRepository.save(user)
            .doOnNext { u ->
                val jwt = userTokenComponent.generate(u)
                res.headers().add("Authorization", jwt)
            }
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials")))
    }

}