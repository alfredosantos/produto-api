package com.br.produto_api.repository

import com.br.produto_api.model.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long> {
    fun findByNomeContainingIgnoreCase(nome: String): List<Produto>
}

