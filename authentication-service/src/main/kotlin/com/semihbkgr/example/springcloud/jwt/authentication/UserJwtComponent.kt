package com.semihbkgr.example.springcloud.jwt.authentication

interface UserJwtComponent {

    fun generate(user: User): String

}