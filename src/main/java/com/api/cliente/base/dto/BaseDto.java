package com.api.cliente.base.dto;

import java.util.List;

public class BaseDto<T> {

    private T dados;
    private List<BaseErrorDto> erros;
    private BaseResultDto resultado;

    public BaseDto(T dados) {
        this.dados = dados;
        this.erros = erros;
        this.resultado = resultado;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }

    public List<BaseErrorDto> getErros() {
        return erros;
    }

    public void setErros(List<BaseErrorDto> erros) {
        this.erros = erros;
    }

    public BaseResultDto getResultado() {
        return resultado;
    }

    public void setResultado(BaseResultDto resultado) {
        this.resultado = resultado;
    }
}
