package com.api.cliente.entity.dtos;

public class CadastrarClienteDto {

    private String clienteId;

    public CadastrarClienteDto(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
}
