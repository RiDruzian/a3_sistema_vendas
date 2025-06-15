package com.example.sistema_vendas;

import java.time.LocalDateTime;
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
@RequestMapping("/vendas")
public class VendaController {
    
    @Autowired
    private VendaRepository vendaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ItemVendaRepository itemVendaRepository;
    
    @GetMapping
    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Venda buscarPorId(@PathVariable Long id) {
        return vendaRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Venda adicionar(@RequestBody VendaRequest vendaRequest) {
        Cliente cliente = clienteRepository.findById(vendaRequest.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setDataVenda(LocalDateTime.now());
        venda.setValorTotal(0.0);
        
        Venda vendaSalva = vendaRepository.save(venda);
        
        double valorTotal = 0;
        
        for (ItemVendaRequest itemReq : vendaRequest.getItens()) {
            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemReq.getProdutoId()));
            
            if (produto.getQuantidadeEstoque() < itemReq.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            
            ItemVenda item = new ItemVenda();
            item.setVenda(vendaSalva);
            item.setProduto(produto);
            item.setQuantidade(itemReq.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setSubTotal(produto.getPreco() * itemReq.getQuantidade());
            
            itemVendaRepository.save(item);
            
            // Atualiza estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemReq.getQuantidade());
            produtoRepository.save(produto);
            
            valorTotal += item.getSubTotal();
        }
        
        vendaSalva.setValorTotal(valorTotal);
        return vendaRepository.save(vendaSalva);
    }
    
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Venda venda = vendaRepository.findById(id).orElse(null);
        if (venda != null) {
            // Devolve os produtos ao estoque
            for (ItemVenda item : venda.getItens()) {
                Produto produto = item.getProduto();
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
                produtoRepository.save(produto);
            }
            vendaRepository.deleteById(id);
        }
    }
    
    // Classes auxiliares para a requisição
    public static class VendaRequest {
        private Long clienteId;
        private List<ItemVendaRequest> itens;
        
        // getters e setters
        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
        public List<ItemVendaRequest> getItens() { return itens; }
        public void setItens(List<ItemVendaRequest> itens) { this.itens = itens; }
    }
    
    public static class ItemVendaRequest {
        private Long produtoId;
        private Integer quantidade;
        
        // getters e setters
        public Long getProdutoId() { return produtoId; }
        public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
        public Integer getQuantidade() { return quantidade; }
        public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    }
}