package com.example.bdestoqueapi.repository;
    import com.example.bdestoqueapi.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    //@Modifiying
    // @Query("DELETE FROM Produto p WHERE p.id = :id")
    // void deleteById(@Param("id") Long id);

    List<Produto> findByNomeLikeIgnoreCase(String nome);

    List <Produto> findByNomeLikeIgnoreCaseAndPrecoLessThan(String nome, Double preco);

    int countByQuantidadeEstoqueIsLessThanEqual(Long quant);

    void deleteByQuantidadeEstoqueIsLessThanEqual(Long quant);


}
