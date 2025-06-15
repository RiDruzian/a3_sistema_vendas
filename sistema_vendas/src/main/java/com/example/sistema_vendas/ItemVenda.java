package com.example.sistema_vendas;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantidade;
    private Double precoUnitario;
    private Double subTotal;
    
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;
    
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}