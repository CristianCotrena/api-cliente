package com.api.cliente.mock;

import com.api.cliente.entity.dtos.ClienteRequestDto;

public class ClienteRequestDtoBuilder {

    public static ClienteRequestDto criar() {

        var request = new ClienteRequestDto();
        request.setNome("Nome Teste Request Dto");
        request.setDataNascimento("1990-01-01");
        request.setEmail("emailrequestdto@email.com");
        request.setCpf("98765432100");
        request.setSenhaCatraca("9999");
        return request;
    }

    public static ClienteRequestDto atualizar() {

        var request = new ClienteRequestDto();
        request.setEmail("emailrequestdto@email.com");
        request.setSenhaCatraca("9999");
        return request;
    }
}
