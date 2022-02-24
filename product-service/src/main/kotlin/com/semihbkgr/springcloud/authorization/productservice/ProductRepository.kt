package com.semihbkgr.springcloud.authorization.productservice

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ProductRepository : ReactiveMongoRepository<Product, String> {

}