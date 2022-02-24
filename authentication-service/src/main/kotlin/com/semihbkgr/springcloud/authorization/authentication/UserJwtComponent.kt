package com.semihbkgr.springcloud.authorization.authentication

interface UserJwtComponent {

    fun generate(user: User): String

}