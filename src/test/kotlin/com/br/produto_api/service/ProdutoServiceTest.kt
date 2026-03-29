package com.br.produto_api.service

import com.br.produto_api.model.Produto
import com.br.produto_api.repository.ProdutoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ProdutoServiceTest {

    private lateinit var repository: ProdutoRepository
    private lateinit var service: ProdutoService

    @BeforeEach
    fun setUp() {
        repository = mockk()
        service = ProdutoService(repository)
    }

    @Test
    fun `listarTodos deve retornar lista de produtos`() {
        val produtos = listOf(
            Produto(id = 1L, nome = "Produto 1", descricao = "Desc 1", preco = BigDecimal("10.00"), quantidade = 5),
            Produto(id = 2L, nome = "Produto 2", descricao = "Desc 2", preco = BigDecimal("20.00"), quantidade = 10)
        )
        every { repository.findAll() } returns produtos

        val resultado = service.listarTodos()

        assertEquals(2, resultado.size)
        assertEquals("Produto 1", resultado[0].nome)
        verify { repository.findAll() }
    }

    @Test
    fun `listarTodos deve retornar lista vazia quando nao ha produtos`() {
        every { repository.findAll() } returns emptyList()

        val resultado = service.listarTodos()

        assertTrue(resultado.isEmpty())
        verify { repository.findAll() }
    }

    @Test
    fun `contarProdutos deve retornar total de produtos`() {
        every { repository.count() } returns 5L

        val resultado = service.contarProdutos()

        assertEquals(5L, resultado)
        verify { repository.count() }
    }

    @Test
    fun `contarProdutos deve retornar zero quando nao ha produtos`() {
        every { repository.count() } returns 0L

        val resultado = service.contarProdutos()

        assertEquals(0L, resultado)
    }

    @Test
    fun `buscarPorId deve retornar produto existente`() {
        val produto = Produto(
            id = 1L,
            nome = "Mouse",
            descricao = "Mouse Gamer",
            preco = BigDecimal("99.90"),
            quantidade = 5
        )
        every { repository.findById(1L) } returns Optional.of(produto)

        val resultado = service.buscarPorId(1L)

        assertNotNull(resultado)
        assertEquals("Mouse", resultado!!.nome)
        verify { repository.findById(1L) }
    }

    @Test
    fun `buscarPorId deve retornar null quando produto nao existe`() {
        every { repository.findById(999L) } returns Optional.empty()

        val resultado = service.buscarPorId(999L)

        assertNull(resultado)
        verify { repository.findById(999L) }
    }

    @Test
    fun `buscarPorNome deve retornar produtos que contem o nome`() {
        val produtos = listOf(
            Produto(id = 1L, nome = "Mouse Gamer", descricao = "Desc", preco = BigDecimal("99.90"), quantidade = 5),
            Produto(id = 2L, nome = "Mouse Basico", descricao = "Desc", preco = BigDecimal("29.90"), quantidade = 10)
        )
        every { repository.findByNomeContainingIgnoreCase("mouse") } returns produtos

        val resultado = service.buscarPorNome("mouse")

        assertEquals(2, resultado.size)
        assertTrue(resultado.all { it.nome.contains("Mouse", ignoreCase = true) })
        verify { repository.findByNomeContainingIgnoreCase("mouse") }
    }

    @Test
    fun `buscarPorNome deve retornar lista vazia quando nenhum produto encontrado`() {
        every { repository.findByNomeContainingIgnoreCase("inexistente") } returns emptyList()

        val resultado = service.buscarPorNome("inexistente")

        assertTrue(resultado.isEmpty())
    }

    @Test
    fun `criar deve salvar e retornar o produto`() {
        val produto = Produto(
            nome = "Novo Produto",
            descricao = "Descricao",
            preco = BigDecimal("100.00"),
            quantidade = 1
        )
        val produtoSalvo = produto.copy(id = 1L)
        every { repository.save(any()) } returns produtoSalvo

        val resultado = service.criar(produto)

        assertEquals(1L, resultado.id)
        assertEquals("Novo Produto", resultado.nome)
        verify { repository.save(any()) }
    }

    @Test
    fun `atualizar deve modificar e retornar o produto`() {
        val produtoExistente = Produto(
            id = 1L,
            nome = "Nome Antigo",
            descricao = "Desc Antiga",
            preco = BigDecimal("50.00"),
            quantidade = 5,
            dataCriacao = LocalDateTime.now(),
            dataAtualizacao = LocalDateTime.now()
        )
        val produtoAtualizado = Produto(
            nome = "Nome Novo",
            descricao = "Desc Nova",
            preco = BigDecimal("75.00"),
            quantidade = 10
        )
        every { repository.findById(1L) } returns Optional.of(produtoExistente)
        every { repository.save(any()) } answers { firstArg() }

        val resultado = service.atualizar(1L, produtoAtualizado)

        assertNotNull(resultado)
        assertEquals("Nome Novo", resultado!!.nome)
        assertEquals(BigDecimal("75.00"), resultado.preco)
        assertEquals(10, resultado.quantidade)
        verify { repository.findById(1L) }
        verify { repository.save(any()) }
    }

    @Test
    fun `atualizar deve retornar null quando produto nao existe`() {
        val produtoAtualizado = Produto(
            nome = "Nome Novo",
            descricao = "Desc Nova",
            preco = BigDecimal("75.00"),
            quantidade = 10
        )
        every { repository.findById(999L) } returns Optional.empty()

        val resultado = service.atualizar(999L, produtoAtualizado)

        assertNull(resultado)
        verify { repository.findById(999L) }
    }

    @Test
    fun `deletar deve retornar true quando produto existe`() {
        every { repository.existsById(1L) } returns true
        every { repository.deleteById(1L) } returns Unit

        val resultado = service.deletar(1L)

        assertTrue(resultado)
        verify { repository.existsById(1L) }
        verify { repository.deleteById(1L) }
    }

    @Test
    fun `deletar deve retornar false quando produto nao existe`() {
        every { repository.existsById(999L) } returns false

        val resultado = service.deletar(999L)

        assertFalse(resultado)
        verify { repository.existsById(999L) }
    }
}

