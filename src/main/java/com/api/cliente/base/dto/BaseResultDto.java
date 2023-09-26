package com.api.cliente.base.dto;

public class BaseResultDto {

    private Integer status;
    private String descricao;

    public BaseResultDto(Integer status, String descricao) {
        this.status = status;
        this.descricao = descricao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
