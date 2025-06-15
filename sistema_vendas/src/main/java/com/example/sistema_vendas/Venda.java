package com.example.sistema_vendas;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dataVenda;
    private Double valorTotal;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemVenda> itens;
}