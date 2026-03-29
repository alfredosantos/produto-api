package com.br.produto_api.model

import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ProdutoTest {

    @Test
    fun `deve criar produto com todos os campos`() {
        val agora = LocalDateTime.now()
        val produto = Produto(
            id = 1L,
            nome = "Teclado",
            descricao = "Teclado Mecanico",
            preco = BigDecimal("199.90"),
            quantidade = 10,
            dataCriacao = agora,
            dataAtualizacao = agora
        )

        assertEquals(1L, produto.id)
        assertEquals("Teclado", produto.nome)
        assertEquals("Teclado Mecanico", produto.descricao)
        assertEquals(BigDecimal("199.90"), produto.preco)
        assertEquals(10, produto.quantidade)
        assertEquals(agora, produto.dataCriacao)
        assertEquals(agora, produto.dataAtualizacao)
    }

    @Test
    fun `deve criar produto com valores padrão`() {
        val produto = Produto()

        assertEquals(null, produto.id)
        assertEquals("", produto.nome)
        assertEquals("", produto.descricao)
        assertEquals(BigDecimal.ZERO, produto.preco)
        assertEquals(0, produto.quantidade)
        assertNotNull(produto.dataCriacao)
        assertNotNull(produto.dataAtualizacao)
    }

    @Test
    fun `deve copiar produto com modificacoes`() {
        val original = Produto(
            id = 1L,
            nome = "Mouse",
            descricao = "Mouse Gamer",
            preco = BigDecimal("99.90"),
            quantidade = 5
        )

        val modificado = original.copy(
            nome = "Mouse Pro",
            preco = BigDecimal("149.90")
        )

        assertEquals("Mouse Pro", modificado.nome)
        assertEquals(BigDecimal("149.90"), modificado.preco)
        assertEquals("Mouse Gamer", modificado.descricao)
        assertEquals(5, modificado.quantidade)
        assertEquals(1L, modificado.id)
    }
}

