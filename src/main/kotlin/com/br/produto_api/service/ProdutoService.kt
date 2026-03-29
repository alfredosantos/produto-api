package com.br.produto_api.service

import com.br.produto_api.model.Produto
import com.br.produto_api.repository.ProdutoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProdutoService(
    private val produtoRepository: ProdutoRepository
) {
    
    fun listarTodos(): List<Produto> {
        return produtoRepository.findAll()
    }
    
    fun contarProdutos(): Long {
        return produtoRepository.count()
    }
    
    fun buscarPorId(id: Long): Produto? {
        return produtoRepository.findById(id).orElse(null)
    }
    
    fun buscarPorNome(nome: String): List<Produto> {
        return produtoRepository.findByNomeContainingIgnoreCase(nome)
    }
    
    fun criar(produto: Produto): Produto {
        return produtoRepository.save(produto)
    }
    
    fun atualizar(id: Long, produtoAtualizado: Produto): Produto? {
        val produtoExistente = produtoRepository.findById(id).orElse(null) ?: return null
        
        val produtoModificado = produtoExistente.copy(
            nome = produtoAtualizado.nome,
            descricao = produtoAtualizado.descricao,
            preco = produtoAtualizado.preco,
            quantidade = produtoAtualizado.quantidade,
            dataAtualizacao = LocalDateTime.now()
        )
        
        return produtoRepository.save(produtoModificado)
    }
    
    fun deletar(id: Long): Boolean {
        return if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}

