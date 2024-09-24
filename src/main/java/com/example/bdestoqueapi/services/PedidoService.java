package com.example.bdestoqueapi.services;
import com.example.bdestoqueapi.models.Pedido;
import com.example.bdestoqueapi.repository.ClienteRepository;
import com.example.bdestoqueapi.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Pedido> searchAll () { return pedidoRepository.findAll(); }

    public Pedido savePedidos ( Pedido pedido ) {
        boolean validator = false;
        if(clienteRepository.findById(pedido.getClienteId()).isPresent() || pedido.getStatus() != "Concluído"){
            validator = true;
        }
        return validator ? pedidoRepository.save((pedido)) : null;
    }

    @Transactional
    public Pedido deletePedido ( Long id ) {
        Pedido pedido = searchPedidosById(id);
        pedidoRepository.delete(pedido);
        return pedido;
    }

    public Pedido searchPedidosById ( Long id ) {
        return pedidoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Pedido não encontrado.")
        );
    }

    public List<Pedido> searchPedidosByClienteId ( int clienteId ) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> searchPedidosByDataPedido ( LocalDateTime dataPedido ) {
        return pedidoRepository.findByDate(dataPedido);
    }

    public List<Pedido> searchPedidosByStatus ( String status ) {
        return pedidoRepository.findByStatusLikeIgnoreCase( status );
    }

    public List<Pedido> searchPedidosByTotal ( BigDecimal totalPedido ) {
        return pedidoRepository.findByTotalPedido( totalPedido );
    }

}
