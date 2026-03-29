package com.br.produto_api.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "produtos")
data class Produto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    val nome: String = "",
    
    @Column(nullable = false, columnDefinition = "TEXT")
    val descricao: String = "",
    
    @Column(nullable = false)
    val preco: BigDecimal = BigDecimal.ZERO,
    
    @Column(nullable = false)
    val quantidade: Int = 0,
    
    @Column(name = "data_criacao")
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "data_atualizacao")
    val dataAtualizacao: LocalDateTime = LocalDateTime.now()
)

