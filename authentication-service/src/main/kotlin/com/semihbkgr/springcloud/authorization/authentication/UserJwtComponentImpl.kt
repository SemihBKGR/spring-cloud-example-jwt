package com.semihbkgr.springcloud.authorization.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class UserJwtComponentImpl(
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.expiration}") val expirationDuration: Duration,
    val objectMapper: ObjectMapper
) : UserJwtComponent {

    private final val hmacKey: Key

    init {
        hmacKey = SecretKeySpec(
            Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName()
        )
    }

    override fun generate(user: User): String {
        val now = Instant.now();
        return Jwts.builder()
            .setSubject(user.username)
            .claim("email", user.email)
            .claim("authorities", objectMapper.writeValueAsString(user.authorities))
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusMillis(expirationDuration.toMillis())))
            .signWith(SignatureAlgorithm.HS256, hmacKey)
            .compact()
    }

}