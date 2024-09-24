package com.example.bdestoqueapi.repository;

import com.example.bdestoqueapi.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
