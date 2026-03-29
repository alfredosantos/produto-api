package com.br.produto_api.controller

import com.br.produto_api.dto.AtualizarProdutoRequest
import com.br.produto_api.dto.CriarProdutoRequest
import com.br.produto_api.model.Produto
import com.br.produto_api.service.ProdutoService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProdutoControllerTest {

    private lateinit var service: ProdutoService
    private lateinit var controller: ProdutoController

    @BeforeEach
    fun setUp() {
        service = mockk()
        controller = ProdutoController(service)
    }

    @Test
    fun `listarTodos deve retornar status 200 com lista de produtos`() {
        val produtos = listOf(
            Produto(id = 1L, nome = "Produto 1", descricao = "Desc 1", preco = BigDecimal("10.00"), quantidade = 5),
            Produto(id = 2L, nome = "Produto 2", descricao = "Desc 2", preco = BigDecimal("20.00"), quantidade = 10)
        )
        every { service.listarTodos() } returns produtos

        val response = controller.listarTodos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body!!.size)
        verify { service.listarTodos() }
    }

    @Test
    fun `contarProdutos deve retornar status 200 com contagem`() {
        every { service.contarProdutos() } returns 5L

        val response = controller.contarProdutos()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(5L, response.body!!)
        verify { service.contarProdutos() }
    }

    @Test
    fun `buscarPorId deve retornar status 200 quando produto existe`() {
        val produto = Produto(
            id = 1L,
            nome = "Mouse",
            descricao = "Mouse Gamer",
            preco = BigDecimal("99.90"),
            quantidade = 5
        )
        every { service.buscarPorId(1L) } returns produto

        val response = controller.buscarPorId(1L)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Mouse", response.body!!.nome)
        verify { service.buscarPorId(1L) }
    }

    @Test
    fun `buscarPorId deve retornar status 404 quando produto nao existe`() {
        every { service.buscarPorId(999L) } returns null

        val response = controller.buscarPorId(999L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify { service.buscarPorId(999L) }
    }

    @Test
    fun `buscarPorNome deve retornar status 200 com lista de produtos`() {
        val produtos = listOf(
            Produto(id = 1L, nome = "Mouse Gamer", descricao = "Desc", preco = BigDecimal("99.90"), quantidade = 5)
        )
        every { service.buscarPorNome("mouse") } returns produtos

        val response = controller.buscarPorNome("mouse")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body!!.size)
        verify { service.buscarPorNome("mouse") }
    }

    @Test
    fun `buscarPorNome deve retornar lista vazia quando nenhum encontrado`() {
        every { service.buscarPorNome("inexistente") } returns emptyList()

        val response = controller.buscarPorNome("inexistente")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(0, response.body!!.size)
    }

    @Test
    fun `criar deve retornar status 201 com produto criado`() {
        val request = CriarProdutoRequest(
            nome = "Novo Produto",
            descricao = "Descricao",
            preco = BigDecimal("100.00"),
            quantidade = 1
        )
        val produtoCriado = Produto(
            id = 1L,
            nome = request.nome,
            descricao = request.descricao,
            preco = request.preco,
            quantidade = request.quantidade,
            dataCriacao = LocalDateTime.now(),
            dataAtualizacao = LocalDateTime.now()
        )
        every { service.criar(any()) } returns produtoCriado

        val response = controller.criar(request)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(1L, response.body!!.id)
        assertEquals("Novo Produto", response.body!!.nome)
        verify { service.criar(any()) }
    }

    @Test
    fun `atualizar deve retornar status 200 com produto atualizado`() {
        val request = AtualizarProdutoRequest(
            nome = "Nome Atualizado",
            descricao = "Desc Atualizada",
            preco = BigDecimal("150.00"),
            quantidade = 8
        )
        val produtoAtualizado = Produto(
            id = 1L,
            nome = request.nome,
            descricao = request.descricao,
            preco = request.preco,
            quantidade = request.quantidade
        )
        every { service.atualizar(1L, any()) } returns produtoAtualizado

        val response = controller.atualizar(1L, request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Nome Atualizado", response.body!!.nome)
        verify { service.atualizar(1L, any()) }
    }

    @Test
    fun `atualizar deve retornar status 404 quando produto nao existe`() {
        val request = AtualizarProdutoRequest(
            nome = "Nome",
            descricao = "Desc",
            preco = BigDecimal("100.00"),
            quantidade = 1
        )
        every { service.atualizar(999L, any()) } returns null

        val response = controller.atualizar(999L, request)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify { service.atualizar(999L, any()) }
    }

    @Test
    fun `deletar deve retornar status 204 quando produto existe`() {
        every { service.deletar(1L) } returns true

        val response = controller.deletar(1L)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify { service.deletar(1L) }
    }

    @Test
    fun `deletar deve retornar status 404 quando produto nao existe`() {
        every { service.deletar(999L) } returns false

        val response = controller.deletar(999L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        verify { service.deletar(999L) }
    }
}

