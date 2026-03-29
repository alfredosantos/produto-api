package com.br.produto_api.repository

import com.br.produto_api.model.Produto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@SpringBootTest
class ProdutoRepositoryTest {

    @Autowired
    private lateinit var repository: ProdutoRepository

    private lateinit var produto1: Produto
    private lateinit var produto2: Produto

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
        produto1 = Produto(
            nome = "Mouse Gamer",
            descricao = "Mouse com RGB",
            preco = BigDecimal("99.90"),
            quantidade = 10
        )
        produto2 = Produto(
            nome = "Teclado Mecanico",
            descricao = "Teclado com switches",
            preco = BigDecimal("199.90"),
            quantidade = 5
        )
    }

    @Test
    fun `deve salvar e recuperar produto por ID`() {
        val salvo = repository.save(produto1)

        assertNotNull(salvo.id)
        val recuperado = repository.findById(salvo.id!!).orElse(null)

        assertNotNull(recuperado)
        assertEquals("Mouse Gamer", recuperado.nome)
    }

    @Test
    fun `deve listar todos os produtos`() {
        repository.save(produto1)
        repository.save(produto2)

        val todos = repository.findAll()

        assertEquals(2, todos.size)
    }

    @Test
    fun `deve contar todos os produtos`() {
        val countAntes = repository.count()

        repository.save(produto1)
        repository.save(produto2)

        val countDepois = repository.count()
        assertEquals(countAntes + 2, countDepois)
    }

    @Test
    fun `deve buscar produtos por nome ignorando case`() {
        repository.save(produto1)
        repository.save(produto2)

        val resultado = repository.findByNomeContainingIgnoreCase("mouse")

        assertEquals(1, resultado.size)
        assertEquals("Mouse Gamer", resultado[0].nome)
    }

    @Test
    fun `deve buscar produtos por nome com diferentes cases`() {
        repository.save(produto1)

        val resultadoUpperCase = repository.findByNomeContainingIgnoreCase("MOUSE")
        val resultadoLowerCase = repository.findByNomeContainingIgnoreCase("mouse")
        val resultadoMixedCase = repository.findByNomeContainingIgnoreCase("Mouse")

        assertEquals(1, resultadoUpperCase.size)
        assertEquals(1, resultadoLowerCase.size)
        assertEquals(1, resultadoMixedCase.size)
    }

    @Test
    fun `deve retornar lista vazia quando nenhum produto encontrado`() {
        val resultado = repository.findByNomeContainingIgnoreCase("inexistente")

        assertTrue(resultado.isEmpty())
    }

    @Test
    fun `deve atualizar um produto existente`() {
        val salvo = repository.save(produto1)
        val atualizado = salvo.copy(nome = "Mouse Ultra Gamer", preco = BigDecimal("129.90"))

        repository.save(atualizado)

        val recuperado = repository.findById(salvo.id!!).orElse(null)
        assertNotNull(recuperado)
        assertEquals("Mouse Ultra Gamer", recuperado.nome)
        assertEquals(BigDecimal("129.90"), recuperado.preco)
    }

    @Test
    fun `deve deletar um produto`() {
        val salvo = repository.save(produto1)
        val idSalvo = salvo.id!!

        repository.deleteById(idSalvo)

        val deletado = repository.findById(idSalvo).orElse(null)
        assertNull(deletado)
    }

    @Test
    fun `deve verificar se produto existe`() {
        val salvo = repository.save(produto1)

        val existe = repository.existsById(salvo.id!!)
        val naoExiste = repository.existsById(999L)

        assertTrue(existe)
        assertFalse(naoExiste)
    }

    @Test
    fun `deve buscar por nome com caracteres especiais`() {
        val produtoEspecial = Produto(
            nome = "Monitor 4K - Ultra",
            descricao = "Monitor",
            preco = BigDecimal("999.90"),
            quantidade = 2
        )

        repository.save(produtoEspecial)

        val resultado = repository.findByNomeContainingIgnoreCase("4K")

        assertEquals(1, resultado.size)
    }
}

