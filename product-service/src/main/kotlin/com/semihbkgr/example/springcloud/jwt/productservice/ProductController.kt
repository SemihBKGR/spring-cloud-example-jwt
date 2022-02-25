package com.semihbkgr.example.springcloud.jwt.productservice

import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/product")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping
    fun getAll(): Flux<Product> {
        return ReactiveSecurityContextHolder.getContext()
            .flatMapMany { c ->
                productRepository.findAllByOwner(c.authentication.name)
            }
    }

    @PostMapping
    fun create(@RequestBody product: Product): Mono<Product> {
        return ReactiveSecurityContextHolder.getContext().flatMap { c ->
            product.id = UUID.randomUUID().toString()
            product.owner = c.authentication.name
            productRepository.save(product)
        }
    }

    @PutMapping("/{id}")
    fun update(@RequestBody product: Product, @PathVariable id: String): Mono<Product> {
        return ReactiveSecurityContextHolder.getContext().flatMap { c ->
            product.id = id
            product.owner = c.authentication.name
            productRepository.findById(id)
                .flatMap { p ->
                    if (p.name.equals(c.authentication.name))
                        productRepository.save(product)
                    else
                        Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST))
                }
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@RequestBody product: Product, @PathVariable id: String): Mono<Void> {
        return ReactiveSecurityContextHolder.getContext().flatMap { c ->
            productRepository.findById(id)
                .flatMap { p ->
                    if (p.name.equals(c.authentication.name))
                        productRepository.deleteById(id)
                    else
                        Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST))
                }
        }
    }

}