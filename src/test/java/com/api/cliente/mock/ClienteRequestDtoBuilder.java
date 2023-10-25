package com.api.cliente.mock;

import com.api.cliente.entity.dtos.CadastrarClienteRequestDto;

public class ClienteRequestDtoBuilder {

    public static CadastrarClienteRequestDto criar() {

        var request = new CadastrarClienteRequestDto();
        request.setNome("Nome Teste Request Dto");
        request.setDataNascimento("1990-01-01");
        request.setEmail("emailrequestdto@email.com");
        request.setCpf("98765432100");
        request.setSenhaCatraca("9999");
        return request;
    }
}
