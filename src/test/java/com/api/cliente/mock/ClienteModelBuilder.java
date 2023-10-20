package com.api.cliente.mock;

import com.api.cliente.entity.models.ClienteModel;

import java.util.UUID;

public class ClienteModelBuilder {

    public static ClienteModel build() {
        
        ClienteModel resultado = new ClienteModel();
        resultado.setId(UUID.randomUUID());
        resultado.setNome("Mateus Candiolli");
        resultado.setDataNascimento("1992-01-31");
        resultado.setEmail("mateuscandiolli.1909@gmail.com");
        resultado.setCpf("12345678912");
        resultado.setSenhaCatraca("1234");
        resultado.setStatus(1);

        return resultado;
    }
}
