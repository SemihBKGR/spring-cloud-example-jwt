package com.semihbkgr.example.springcloud.jwt.userservice

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec


@Component
class JwtComponentImpl(@Value("\${jwt.secret}") private val secret: String) : JwtComponent {

    private final val hmacKey: Key

    init {
        hmacKey = SecretKeySpec(
            Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName()
        )
    }

    override fun validate(token: String): Map<String, Any> {
        val jwt: Jws<Claims> = Jwts.parser()
            .setSigningKey(hmacKey)
            .parseClaimsJws(token)
        if (jwt.body != null) {
            val map = HashMap<String, Any>()
            jwt.body.forEach { c ->
                map[c.key] = c.value
            }
            return map
        }
        return Collections.emptyMap()
    }
}