package com.br.produto_api

import com.br.produto_api.model.Produto
import com.br.produto_api.repository.ProdutoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class ProdutoApiIntegrationTest {

    @Autowired
    private lateinit var repository: ProdutoRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun `deve fazer fluxo completo de CRUD`() {
        // CREATE
        val produto = Produto(
            nome = "Produto Teste",
            descricao = "Descricao do produto",
            preco = BigDecimal("99.99"),
            quantidade = 10
        )
        val salvo = repository.save(produto)
        assertNotNull(salvo.id)

        // READ (por ID)
        val recuperado = repository.findById(salvo.id!!).orElse(null)
        assertNotNull(recuperado)
        assertEquals("Produto Teste", recuperado.nome)

        // READ (listar todos)
        val todos = repository.findAll()
        assertNotNull(todos)

        // COUNT
        val total = repository.count()
        assertEquals(1L, total)

        // UPDATE
        val atualizado = salvo.copy(
            nome = "Produto Teste Atualizado",
            preco = BigDecimal("149.99")
        )
        val salvoAtualizado = repository.save(atualizado)
        assertEquals("Produto Teste Atualizado", salvoAtualizado.nome)

        // SEARCH BY NAME
        val busca = repository.findByNomeContainingIgnoreCase("Produto")
        assertEquals(1, busca.size)

        // DELETE
        repository.deleteById(salvo.id!!)
        val deletado = repository.findById(salvo.id!!).orElse(null)
        kotlin.test.assertNull(deletado)
    }

    @Test
    fun `deve criar multiplos produtos e listar`() {
        val produto1 = repository.save(Produto(
            nome = "Produto 1",
            descricao = "Desc 1",
            preco = BigDecimal("50.00"),
            quantidade = 5
        ))
        val produto2 = repository.save(Produto(
            nome = "Produto 2",
            descricao = "Desc 2",
            preco = BigDecimal("100.00"),
            quantidade = 10
        ))

        val todos = repository.findAll()
        assertEquals(2, todos.size)
    }

    @Test
    fun `deve buscar produtos por nome com diferentes padroes`() {
        repository.save(Produto(
            nome = "Mouse Gamer",
            descricao = "Mouse",
            preco = BigDecimal("99.99"),
            quantidade = 5
        ))
        repository.save(Produto(
            nome = "Teclado Mecanico",
            descricao = "Teclado",
            preco = BigDecimal("199.99"),
            quantidade = 3
        ))

        val busca1 = repository.findByNomeContainingIgnoreCase("mouse")
        val busca2 = repository.findByNomeContainingIgnoreCase("TECLADO")
        val busca3 = repository.findByNomeContainingIgnoreCase("inexistente")

        assertEquals(1, busca1.size)
        assertEquals(1, busca2.size)
        assertEquals(0, busca3.size)
    }
}

