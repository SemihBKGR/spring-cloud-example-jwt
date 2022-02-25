package com.semihbkgr.example.springcloud.jwt.productservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ProductRepository : ReactiveMongoRepository<Product, String> {

}