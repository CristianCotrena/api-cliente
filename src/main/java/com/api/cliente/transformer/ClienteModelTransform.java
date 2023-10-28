package com.api.cliente.transformer;

import com.api.cliente.entity.dtos.AtualizarClienteRequestDto;
import com.api.cliente.entity.dtos.BuscarClienteDto;
import com.api.cliente.entity.dtos.CadastrarClienteRequestDto;
import com.api.cliente.entity.models.ClienteModel;

import java.util.Optional;

public class ClienteModelTransform {

    public ClienteModel transformerCadastrarCliente(CadastrarClienteRequestDto dto) {
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(dto.getNome());
        clienteModel.setDataNascimento(dto.getDataNascimento());
        clienteModel.setEmail(dto.getEmail());
        clienteModel.setCpf(dto.getCpf());
        clienteModel.setSenhaCatraca(dto.getSenhaCatraca());
        clienteModel.setStatus(1);
        return clienteModel;
    }

    public ClienteModel transformerAtualizarCliente(AtualizarClienteRequestDto dto, ClienteModel clienteModel) {
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            clienteModel.setEmail(dto.getEmail());
        } else {
            dto.setEmail(clienteModel.getEmail());
        }
        if (dto.getSenhaCatraca() != null && !dto.getSenhaCatraca().isEmpty()) {
            clienteModel.setSenhaCatraca(dto.getSenhaCatraca());
        } else {
            dto.setSenhaCatraca(clienteModel.getSenhaCatraca());
        }
        return clienteModel;
    }

    public BuscarClienteDto transformerBuscarCliente (Optional<ClienteModel> optionalClienteModel) {
        BuscarClienteDto buscarClienteDto = new BuscarClienteDto();
        buscarClienteDto.setId(optionalClienteModel.get().getId());
        buscarClienteDto.setNome(optionalClienteModel.get().getNome());
        buscarClienteDto.setDataNascimento(optionalClienteModel.get().getDataNascimento());
        buscarClienteDto.setEmail(optionalClienteModel.get().getEmail());
        buscarClienteDto.setCpf(optionalClienteModel.get().getCpf());
        buscarClienteDto.setSenhaCatraca(optionalClienteModel.get().getSenhaCatraca());
        return buscarClienteDto;
    }
}
