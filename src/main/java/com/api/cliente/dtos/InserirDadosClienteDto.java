package com.api.cliente.dtos;

public class CadastrarClienteDto {

    private String nome;
    private String dataNascimento;
    private String email;
    private String cpf;
    private String senhaCatraca;

    public CadastrarClienteDto() {

    }

    public CadastrarClienteDto(
            String nome,
            String dataNascimento,
            String email,
            String cpf,
            String senhaCatraca) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.cpf = cpf;
        this.senhaCatraca = senhaCatraca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getSenhaCatraca() {
        return senhaCatraca;
    }

    public void setSenhaCatraca(String senhaCatraca) {
        this.senhaCatraca = senhaCatraca;
    }
}
