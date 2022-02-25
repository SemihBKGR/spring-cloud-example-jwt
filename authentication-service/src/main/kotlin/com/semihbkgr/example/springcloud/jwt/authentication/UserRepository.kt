package com.semihbkgr.example.springcloud.jwt.authentication

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveMongoRepository<User, Int> {

    fun findByUsername(username: String): Mono<User>

}