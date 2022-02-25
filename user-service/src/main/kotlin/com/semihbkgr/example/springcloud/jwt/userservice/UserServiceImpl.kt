package com.semihbkgr.example.springcloud.jwt.userservice

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {

    override fun save(user: User): Mono<User> = repository.save(user)

    override fun findByUsername(username: String): Mono<User> = repository.findByUsername(username)

    override fun findByEmail(email: String): Mono<User> = repository.findByEmail(email)

}