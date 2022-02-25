package com.semihbkgr.springcloud.authorization.userservice

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService, private val passwordEncoder: PasswordEncoder) {

    @GetMapping("/{value}")
    fun get(@PathVariable value: String, @RequestParam(name = "by", required = false) by: String): Mono<User> =
        when (by) {
            "email" -> userService.findByEmail(value)
            else -> userService.findByUsername(value)
        }

    @PostMapping
    fun create(@RequestBody user: User): Mono<User> {
        user.id = UUID.randomUUID().toString()
        user.password = passwordEncoder.encode(user.password)
        user.authorities = setOf("ROLE_USER")
        return userService.save(user)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody user: User, @PathVariable id: String): Mono<User> {
        user.id = id
        user.password = passwordEncoder.encode(user.password)
        user.authorities = setOf("ROLE_USER")
        return userService.save(user)
    }

}