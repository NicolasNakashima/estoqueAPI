package com.example.bdestoqueapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "O nome não pode ser null")
    @Size(min = 0, max = 100, message = "O nome não pode ter mais que 100 caracteres")
    private String nome;
    @NotNull
    @Email
    private String email;
    @Size(min = 0, max = 20, message = "O telefone deve ter no maximo 20 digitos")
    private String telefone;
    @Size(min = 0, max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    private String endereco;

    public Cliente() {};
    public Cliente(long id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

