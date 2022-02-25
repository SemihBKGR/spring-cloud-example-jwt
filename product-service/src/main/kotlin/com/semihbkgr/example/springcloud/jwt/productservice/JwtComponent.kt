package com.semihbkgr.example.springcloud.jwt.productservice

interface JwtComponent {

    fun validate(token: String): Map<String, Any>

}