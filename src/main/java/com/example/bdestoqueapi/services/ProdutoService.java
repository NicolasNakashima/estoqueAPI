package com.example.bdestoqueapi.services;

import com.example.bdestoqueapi.models.Produto;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.bdestoqueapi.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeLikeIgnoreCase(nome);
    }

    public List<Produto> buscarPorNomeEPreco(String nome, Double preco){
        return produtoRepository.findByNomeLikeIgnoreCaseAndPrecoLessThan(nome, preco);
    }
    public List<Produto> buscarTodosProdutos(){
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id){
        return produtoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Produto não encontrado com o id " + id));
    }

    @Transactional
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto excluirProduto(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
        return produto;
    }

    @Transactional
    public Produto salvarProduto(Long id){
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.save(produto);
        return produto;
    }

}//fim do PublicService

