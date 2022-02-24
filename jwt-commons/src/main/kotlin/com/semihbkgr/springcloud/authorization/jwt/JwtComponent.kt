package com.semihbkgr.springcloud.authorization.jwt

interface JwtComponent {

    fun validate(token: String): Boolean

    fun containsSubject(token: String): Boolean

    fun getClaims(token: String): Map<String, String>

    fun getSubject(token: String): String

    fun getClaim(token: String, name: String): String

}