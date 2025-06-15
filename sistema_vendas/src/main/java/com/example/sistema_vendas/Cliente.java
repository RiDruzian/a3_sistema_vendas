package com.example.sistema_vendas;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Venda> vendas;
}