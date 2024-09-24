package com.example.bdestoqueapi.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cliente_id")
    private long clienteId;


    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @NotNull
    @Size(max = 20, message = "O campo status deve ter no máximo 20 caracteres")
    private String status;

    @Column(name = "total_pedido", precision = 10, scale = 2)
    private double totalPedido;


    public Pedido() {
    }


    public Pedido(long id, long clienteId, LocalDateTime dataPedido, String status, double totalPedido) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
        this.status = status;
        this.totalPedido = totalPedido;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }
}
