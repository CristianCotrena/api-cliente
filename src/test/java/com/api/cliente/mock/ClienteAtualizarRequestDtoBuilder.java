package com.api.cliente.mock;

import com.api.cliente.entity.dtos.ClienteAtualizarRequestDto;
import com.api.cliente.entity.dtos.ClienteRequestDto;

public class ClienteAtualizarRequestDtoBuilder {

    public static ClienteAtualizarRequestDto build() {

        var request = new ClienteAtualizarRequestDto();
        request.setEmail("emailrequestdto@email.com");
        request.setSenhaCatraca("9999");
        return request;
    }
}
