package com.semihbkgr.springcloud.authorization.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class UserJwtComponentImpl(
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.expiration}") val expirationDuration: Duration,
    val objectMapper: ObjectMapper
) : UserJwtComponent {

    override fun generate(user: User): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secret)
            .setSubject(user.username)
            .claim("email", user.email)
            .claim("authorities", objectMapper.writeValueAsString(user.authorities))
            .setExpiration(Date(System.currentTimeMillis() + expirationDuration.toMillis()))
            .compact()
    }

}