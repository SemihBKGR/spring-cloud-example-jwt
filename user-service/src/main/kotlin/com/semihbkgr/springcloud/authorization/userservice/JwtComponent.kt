package com.semihbkgr.springcloud.authorization.userservice

interface JwtComponent {

    fun validate(token: String): Map<String, Any>

}