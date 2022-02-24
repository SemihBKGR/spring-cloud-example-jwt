package com.semihbkgr.springcloud.authorization.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route { p ->
                p.path("/authentication/**")
                    .uri("lb://authentication")
                p.path("/user/**")
                    .uri("lb://user")
            }.build()
    }

}