package com.semihbkgr.springcloud.authorization.productservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(
    @Id
    val id: String,
    var owner: String?,
    val name: String,
    val price: Float,
    val description: String?
)
