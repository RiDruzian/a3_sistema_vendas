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
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Cliente adicionar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}