package com.semihbkgr.springcloud.authorization.productservice

interface JwtComponent {

    fun validate(token: String): Map<String, Any>

}