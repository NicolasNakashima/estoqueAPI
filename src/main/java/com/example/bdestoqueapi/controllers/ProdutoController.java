package com.example.bdestoqueapi.controllers;

import com.example.bdestoqueapi.models.Produto;
import com.example.bdestoqueapi.services.ProdutoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RestController
@RequestMapping("api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    private final Validator validator;

    @Autowired
    public ProdutoController(ProdutoService produtoService, Validator validator) {
        this.produtoService = produtoService;
        this.validator = validator;
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        List<Produto> listaProdutos = produtoService.buscarPorNome(nome);
        if(!listaProdutos.isEmpty()) {
            return ResponseEntity.ok(listaProdutos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produtos não encontrados");

        }
    }

    @GetMapping("/buscarPorNomeEPreco")
    public ResponseEntity<?> buscarPorNomeEPreco(@RequestParam String nome, @RequestParam Double preco) {
        List<Produto> listaProdutos = produtoService.buscarPorNomeEPreco(nome, preco);
        if(!listaProdutos.isEmpty()) {
            return ResponseEntity.ok(listaProdutos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produtos não encontrados");
        }
    }

    @GetMapping("/selecionar")
    public List<Produto> listarProdutos() {
        return produtoService.buscarTodosProdutos();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirProduto(@Valid @RequestBody Produto produto) {
        produtoService.salvarProduto(produto);
        return ResponseEntity.ok("Produto inserido com sucesso");
    }

    @DeleteMapping("excluir/{id}")
    public ResponseEntity<String> excluirProduto(@Valid @PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        produtoService.excluirProduto(id);
        return ResponseEntity.ok("Produto excluído com sucesso");
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<String> atualizarProduto(@Valid @PathVariable Long id, @RequestBody Produto produtoAtualizado) {
//        Optional<Produto> produtoExistente = produtoRepository.findById(id);
//        if (produtoExistente.isPresent()) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
        produtoService.salvarProduto(produto);
        return ResponseEntity.ok("Produto atualizado com sucesso");

//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

    @PatchMapping("/atualizarParcial/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Produto produto = produtoService.buscarProdutoPorId(id);

        // atualiza apenas aqueles que estão presentes no corpo da requisição
        if (updates.containsKey("nome")) {
            produto.setNome((String) updates.get("nome"));
        }
        if (updates.containsKey("preco")) {
            String preco = updates.get("preco").toString();
            produto.setPreco(Double.parseDouble(preco));
        }
        if (updates.containsKey("descricao")) {
            produto.setDescricao((String) updates.get("descricao"));
        }
        if (updates.containsKey("quantidadeEstoque")) {
            produto.setQuantidadeEstoque((int) updates.get("quantidadeEstoque"));
        }

        //validar
        DataBinder binder = new DataBinder(produto);
        binder.setValidator(validator);
        binder.validate();
        BindingResult result = binder.getBindingResult();
//        Map<String, String> erros = validarProduto(result);
        if(result.hasErrors()){
            Map errors = validarProduto(result);
            return ResponseEntity.badRequest().body(errors);
        }

        produtoService.salvarProduto(produto);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }

    //método validar produto que o professor mostrou em aula
    public Map<String, String> validarProduto(BindingResult resultado) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : resultado.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException re) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST ).body(errors);
    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleBadRequestException(MethodArgumentNotValidException mane){
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mane.getBindingResult().getFieldError().getDefaultMessage());
//    }





}//fim do controller
