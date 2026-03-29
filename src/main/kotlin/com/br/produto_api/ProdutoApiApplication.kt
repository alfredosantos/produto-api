package com.br.produto_api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "Produto API",
        version = "1.0.0",
        description = "API para gerenciamento de produtos"
    )
)
@SpringBootApplication
class ProdutoApiApplication

fun main(args: Array<String>) {
    runApplication<ProdutoApiApplication>(*args)
}