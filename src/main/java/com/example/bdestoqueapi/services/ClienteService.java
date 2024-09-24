package com.example.bdestoqueapi.services;

import com.example.bdestoqueapi.models.Cliente;
import com.example.bdestoqueapi.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;


import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    // READ & UPDATE
    public List<Cliente> buscarTodosOsClientes() {
        return repository.findAll();
    }

    public Cliente buscarClientePorId(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado!" + id));
    }

    // DELETE
    @Transactional
    public Cliente excluirClientePorId(long id) {
        Cliente cliente = buscarClientePorId(id);
        repository.delete(cliente);
        return cliente;
    }
}
