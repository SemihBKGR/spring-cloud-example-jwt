package com.semihbkgr.example.springcloud.jwt.userservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveMongoRepository<User, Int> {

    fun findByUsername(username: String): Mono<User>

    fun findByEmail(email: String): Mono<User>

}