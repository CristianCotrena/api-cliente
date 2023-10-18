package com.api.cliente.entity.dtos;

import java.util.UUID;

public class BuscarClienteDto {
    private UUID id;
    private String email;
    private String cpf;

    public BuscarClienteDto(UUID id, String email, String cpf) {
        this.id = id;
        this.email = email;
        this.cpf = cpf;
    }

    public BuscarClienteDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
