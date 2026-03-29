package com.br.produto_api.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CriarProdutoRequest(
    val nome: String,
    val descricao: String,
    val preco: BigDecimal,
    val quantidade: Int
)

data class AtualizarProdutoRequest(
    val nome: String,
    val descricao: String,
    val preco: BigDecimal,
    val quantidade: Int
)

data class ProdutoResponse(
    val id: Long?,
    val nome: String,
    val descricao: String,
    val preco: BigDecimal,
    val quantidade: Int,
    val dataCriacao: LocalDateTime,
    val dataAtualizacao: LocalDateTime
)

