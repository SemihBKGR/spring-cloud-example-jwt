package com.semihbkgr.springcloud.authorization.userservice

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(private var userService: UserService) {

    @GetMapping("/{value}")
    fun get(@PathVariable value: String, @RequestParam(name = "by", required = false) by: String): Mono<User> =
        when (by) {
            "email" -> userService.findByEmail(value)
            else -> userService.findByUsername(value)
        }

    @PostMapping
    fun create(@RequestBody user: User): Mono<User> {
        user.id = UUID.randomUUID().toString()
        user.authorities = setOf("ROLE_USER")
        return userService.save(user)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody user: User, @PathVariable id: String): Mono<User> {
        user.id = id
        user.authorities = setOf("ROLE_USER")
        return userService.save(user)
    }

}