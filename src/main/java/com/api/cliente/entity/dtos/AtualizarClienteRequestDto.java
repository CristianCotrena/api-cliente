package com.api.cliente.entity.dtos;

public class AtualizarClienteRequestDto {

    private String email;
    private String senhaCatraca;

    public AtualizarClienteRequestDto() {
    }

    public AtualizarClienteRequestDto(String email, String senhaCatraca) {
        this.email = email;
        this.senhaCatraca = senhaCatraca;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaCatraca() {
        return senhaCatraca;
    }

    public void setSenhaCatraca(String senhaCatraca) {
        this.senhaCatraca = senhaCatraca;
    }
}
