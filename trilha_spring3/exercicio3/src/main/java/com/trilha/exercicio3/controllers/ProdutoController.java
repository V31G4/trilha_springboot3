package com.trilha.exercicio3.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trilha.exercicio3.produtos.Produto;
import com.trilha.exercicio3.produtos.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Produto> listarProdutos(){
		return produtoRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Produto adicionarProduto(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
		return produtoRepository.findById(id)
				.map(produto -> {
					produto.setNome(produtoAtualizado.getNome());
					produto.setPreco(produtoAtualizado.getPreco());
					Produto produtoSalvo = produtoRepository.save(produto);
					return ResponseEntity.ok().body(produtoSalvo);
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> removerProduto(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(produto -> {
					produtoRepository.delete(produto);
					return ResponseEntity.ok().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	
	
	
}
