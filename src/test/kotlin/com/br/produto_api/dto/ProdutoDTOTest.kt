package com.br.produto_api.dto

import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ProdutoDTOTest {

    @Test
    fun `deve criar CriarProdutoRequest com todos os campos`() {
        val request = CriarProdutoRequest(
            nome = "Monitor",
            descricao = "Monitor 4K",
            preco = BigDecimal("1299.90"),
            quantidade = 3
        )

        assertEquals("Monitor", request.nome)
        assertEquals("Monitor 4K", request.descricao)
        assertEquals(BigDecimal("1299.90"), request.preco)
        assertEquals(3, request.quantidade)
    }

    @Test
    fun `deve criar AtualizarProdutoRequest com todos os campos`() {
        val request = AtualizarProdutoRequest(
            nome = "Monitor 4K Pro",
            descricao = "Monitor 4K com suporte HDR",
            preco = BigDecimal("1599.90"),
            quantidade = 2
        )

        assertEquals("Monitor 4K Pro", request.nome)
        assertEquals("Monitor 4K com suporte HDR", request.descricao)
        assertEquals(BigDecimal("1599.90"), request.preco)
        assertEquals(2, request.quantidade)
    }

    @Test
    fun `deve criar ProdutoResponse com todos os campos`() {
        val dataCriacao = LocalDateTime.now()
        val dataAtualizacao = dataCriacao.plusMinutes(5)

        val response = ProdutoResponse(
            id = 1L,
            nome = "Headset",
            descricao = "Headset Gamer",
            preco = BigDecimal("299.90"),
            quantidade = 8,
            dataCriacao = dataCriacao,
            dataAtualizacao = dataAtualizacao
        )

        assertEquals(1L, response.id)
        assertEquals("Headset", response.nome)
        assertEquals("Headset Gamer", response.descricao)
        assertEquals(BigDecimal("299.90"), response.preco)
        assertEquals(8, response.quantidade)
        assertEquals(dataCriacao, response.dataCriacao)
        assertEquals(dataAtualizacao, response.dataAtualizacao)
    }
}

