package com.example.sistema_vendas;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    
    @OneToMany(mappedBy = "produto")
    @JsonIgnore
    private List<ItemVenda> itensVenda;
}