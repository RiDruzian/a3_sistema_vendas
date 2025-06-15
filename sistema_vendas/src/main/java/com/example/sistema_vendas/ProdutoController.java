package com.example.sistema_vendas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }
    
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }
}