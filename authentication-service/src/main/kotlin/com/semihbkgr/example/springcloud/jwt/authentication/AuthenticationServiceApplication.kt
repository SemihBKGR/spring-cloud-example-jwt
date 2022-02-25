package com.semihbkgr.example.springcloud.jwt.authentication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AuthenticationServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthenticationServiceApplication>(*args)
}
