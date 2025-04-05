package com.api.cliente.mock;

import com.api.cliente.entity.dtos.AtualizarClienteRequestDto;

public class ClienteAtualizarRequestDtoBuilder {

    public static AtualizarClienteRequestDto build() {

        var request = new AtualizarClienteRequestDto();
        request.setEmail("emailrequestdto@email.com");
        request.setSenhaCatraca("9999");
        return request;
    }
}
