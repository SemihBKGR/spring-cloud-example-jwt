package com.semihbkgr.example.springcloud.jwt.userservice

interface JwtComponent {

    fun validate(token: String): Map<String, Any>

}