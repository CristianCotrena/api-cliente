package com.api.cliente.entity.dtos;

public class CadastrarClienteResponseDto {

    private String clienteId;

    public CadastrarClienteResponseDto() {
    }

    public CadastrarClienteResponseDto(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
}
