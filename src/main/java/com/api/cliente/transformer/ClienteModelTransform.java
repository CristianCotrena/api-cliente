package com.api.cliente.transformer;

import com.api.cliente.entity.dtos.InserirDadosClienteDto;
import com.api.cliente.entity.models.ClienteModel;

public class ClienteModelTransform {

    public ClienteModel transformarParaClienteModel(InserirDadosClienteDto dto) {
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(dto.getNome());
        clienteModel.setDataNascimento(dto.getDataNascimento());
        clienteModel.setEmail(dto.getEmail());
        clienteModel.setCpf(dto.getCpf());
        clienteModel.setSenhaCatraca(dto.getSenhaCatraca());

        if (dto.getStatus() != null) {
            clienteModel.setStatus(dto.getStatus());
        } else {
            clienteModel.setStatus(1);
        }
        return clienteModel;
    }
}
