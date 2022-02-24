package com.semihbkgr.springcloud.authorization.jwt

import io.jsonwebtoken.Jwts

class JwtComponentImpl(val secret: String) : JwtComponent{

    override fun validate(token: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsSubject(token: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getClaims(token: String): Map<String, String> {
        TODO("Not yet implemented")
    }

    override fun getSubject(token: String): String {
        TODO("Not yet implemented")
    }

    override fun getClaim(token: String, name: String): String {
        TODO("Not yet implemented")
    }

}