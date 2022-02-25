package com.semihbkgr.example.springcloud.jwt.productservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ProductRepository : ReactiveMongoRepository<Product, String> {

    fun findAllByOwner(owner:String): Flux<Product>

}