package com.api.cliente.services;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.dtos.CadastrarClienteDto;
import com.api.cliente.dtos.InserirDadosClienteDto;
import com.api.cliente.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.validate.CadastrarClienteValidate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CadastrarClienteService {

    private ClienteRepository clienteRepository;

    public CadastrarClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public BaseDto cadastrarCliente(InserirDadosClienteDto inserirDadosClienteDto) {
        List<BaseErrorDto> erros = new CadastrarClienteValidate().validate(inserirDadosClienteDto);
        if (erros.size() > 0) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get().getBody();
        }
        if (!clienteRepository.findByEmail(inserirDadosClienteDto.getEmail()).isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", "Já cadastrado."));
        }
        if (!clienteRepository.findByCpf(inserirDadosClienteDto.getCpf()).isEmpty()) {
            erros.add(new BaseErrorDto("CPF.", "Já cadastrado."));
        }
        if (erros.size() > 0) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get().getBody();
        }

        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setNome(inserirDadosClienteDto.getNome());
        clienteModel.setDataNascimento(inserirDadosClienteDto.getDataNascimento());
        clienteModel.setEmail(inserirDadosClienteDto.getEmail());
        clienteModel.setCpf(inserirDadosClienteDto.getCpf());
        clienteModel.setSenhaCatraca(inserirDadosClienteDto.getSenhaCatraca());

        UUID cadastrarIdCliente = clienteRepository.save(clienteModel).getId();
        return new ResponseSuccessBuilder<CadastrarClienteDto>(
            HttpStatus.CREATED,
            new CadastrarClienteDto(cadastrarIdCliente.toString()),
            "Cliente cadastrado com sucesso.").get().getBody();
    }
}
