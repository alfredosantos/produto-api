package com.br.produto_api.controller

import com.br.produto_api.dto.CriarProdutoRequest
import com.br.produto_api.dto.AtualizarProdutoRequest
import com.br.produto_api.model.Produto
import com.br.produto_api.service.ProdutoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações de gerenciamento de produtos")
class ProdutoController(
    private val produtoService: ProdutoService
) {

    @Operation(summary = "Lista todos os produtos")
    @GetMapping
    fun listarTodos(): ResponseEntity<List<Produto>> {
        val produtos = produtoService.listarTodos()
        return ResponseEntity.ok(produtos)
    }

    @Operation(summary = "Conta o total de produtos cadastrados")
    @GetMapping("/contar")
    fun contarProdutos(): ResponseEntity<Long> {
        val total = produtoService.contarProdutos()
        return ResponseEntity.ok(total)
    }

    @Operation(summary = "Busca um produto pelo ID")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produto encontrado"),
        ApiResponse(responseCode = "404", description = "Produto não encontrado")
    )
    @GetMapping("/{id}")
    fun buscarPorId(
        @Parameter(description = "ID do produto") @PathVariable id: Long
    ): ResponseEntity<Produto> {
        val produto = produtoService.buscarPorId(id)
        return if (produto != null) ResponseEntity.ok(produto)
        else ResponseEntity.notFound().build()
    }

    @Operation(summary = "Busca produtos pelo nome")
    @GetMapping("/buscar/{nome}")
    fun buscarPorNome(
        @Parameter(description = "Nome ou parte do nome") @PathVariable nome: String
    ): ResponseEntity<List<Produto>> {
        val produtos = produtoService.buscarPorNome(nome)
        return ResponseEntity.ok(produtos)
    }

    @Operation(summary = "Cria um novo produto")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @PostMapping
    fun criar(@RequestBody request: CriarProdutoRequest): ResponseEntity<Produto> {
        val produto = Produto(
            nome = request.nome,
            descricao = request.descricao,
            preco = request.preco,
            quantidade = request.quantidade,
            dataCriacao = LocalDateTime.now(),
            dataAtualizacao = LocalDateTime.now()
        )
        val produtoCriado = produtoService.criar(produto)
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado)
    }

    @Operation(summary = "Atualiza um produto existente")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Produto atualizado"),
        ApiResponse(responseCode = "404", description = "Produto não encontrado")
    )
    @PutMapping("/{id}")
    fun atualizar(
        @Parameter(description = "ID do produto") @PathVariable id: Long,
        @RequestBody request: AtualizarProdutoRequest
    ): ResponseEntity<Produto> {
        val produtoAtualizado = Produto(
            nome = request.nome,
            descricao = request.descricao,
            preco = request.preco,
            quantidade = request.quantidade
        )
        val produto = produtoService.atualizar(id, produtoAtualizado)
        return if (produto != null) ResponseEntity.ok(produto)
        else ResponseEntity.notFound().build()
    }

    @Operation(summary = "Remove um produto")
    @ApiResponses(
        ApiResponse(responseCode = "204", description = "Produto removido"),
        ApiResponse(responseCode = "404", description = "Produto não encontrado")
    )
    @DeleteMapping("/{id}")
    fun deletar(
        @Parameter(description = "ID do produto") @PathVariable id: Long
    ): ResponseEntity<Unit> {
        return if (produtoService.deletar(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }
}
