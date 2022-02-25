package com.semihbkgr.example.springcloud.jwt.authentication

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    @Id
    var id: String?,
    var username: String,
    var password: String,
    var email: String,
    var authorities: Set<String>?
)
