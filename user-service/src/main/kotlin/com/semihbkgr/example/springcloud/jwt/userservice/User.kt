package com.semihbkgr.example.springcloud.jwt.userservice

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    @Id
    var id: String?,
    @Indexed(unique = true)
    var username: String?,
    var password: String?,
    @Indexed(unique = true)
    var email: String?,
    var authorities: Set<String>?
)
