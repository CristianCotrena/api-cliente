package com.api.cliente.services.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.constants.MensagensSucessos;
import com.api.cliente.entity.dtos.CadastrarClienteDto;
import com.api.cliente.entity.dtos.InserirDadosClienteDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.transformer.ClienteModelTransform;
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
        if (clienteRepository.existsByEmail(inserirDadosClienteDto.getEmail()).orElse(false)) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.DADO_JA_CADASTRADO));
        }
        if (clienteRepository.existsByCpf(inserirDadosClienteDto.getCpf()).orElse(false)) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.DADO_JA_CADASTRADO));
        }
        if (erros.size() > 0) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get().getBody();
        }

        ClienteModel clienteModel = new ClienteModelTransform().transformarParaClienteModel(inserirDadosClienteDto);

        UUID cadastrarIdCliente = clienteRepository.save(clienteModel).getId();
        return new ResponseSuccessBuilder<CadastrarClienteDto>(
                HttpStatus.CREATED,
                new CadastrarClienteDto(cadastrarIdCliente.toString()), MensagensSucessos.CADASTRADO_COM_SUCESSO).get().getBody();
    }
}