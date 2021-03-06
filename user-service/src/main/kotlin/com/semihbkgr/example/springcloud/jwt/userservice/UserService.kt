package com.semihbkgr.example.springcloud.jwt.userservice

import reactor.core.publisher.Mono

interface UserService {

    fun save(user: User): Mono<User>

    fun findByUsername(username: String): Mono<User>

    fun findByEmail(email: String): Mono<User>

}