package com.semihbkgr.example.springcloud.jwt.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouterConfig {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route { p ->
                p.path("/authentication/**")
                    .uri("lb://authentication")
            }
            .route { p ->
                p.path("/user/**")
                    .uri("lb://user")
            }
            .route { p ->
                p.path("/product/**")
                    .uri("lb://product")
            }.build()
    }

}