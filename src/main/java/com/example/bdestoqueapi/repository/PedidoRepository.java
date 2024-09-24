package com.example.bdestoqueapi.repository;

import com.example.bdestoqueapi.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(int clienteId);
    List<Pedido> findByDate(LocalDateTime dtPedido);
    List<Pedido> findByStatusLikeIgnoreCase(String status);
    List<Pedido> findByTotalPedido (BigDecimal totalPedido);
}
