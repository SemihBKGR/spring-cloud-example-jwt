package com.semihbkgr.example.springcloud.jwt.productservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(
    @Id
    var id: String?,
    var owner: String?,
    val name: String?,
    val price: Float?,
    val description: String?
)
