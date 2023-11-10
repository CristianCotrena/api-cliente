package com.api.cliente.entity.dtos;

import java.util.List;

public class PaginaListagemDto {

    private List<ListarClienteResponseDto> resultados;
    private int paginaAtual;
    private int totalPaginas;

    public PaginaListagemDto(List<ListarClienteResponseDto> resultados, int paginaAtual, int totalPaginas) {
        this.resultados = resultados;
        this.paginaAtual = paginaAtual;
        this.totalPaginas = totalPaginas;
    }

    public List<ListarClienteResponseDto> getResultados() {
        return resultados;
    }

    public void setResultados(List<ListarClienteResponseDto> resultados) {
        this.resultados = resultados;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
}
