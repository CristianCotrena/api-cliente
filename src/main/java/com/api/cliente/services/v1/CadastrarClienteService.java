package com.api.cliente.services.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.constants.MensagensSucessos;
import com.api.cliente.entity.dtos.CadastrarClienteResponseDto;
import com.api.cliente.entity.dtos.CadastrarClienteRequestDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.transformer.ClienteModelTransform;
import com.api.cliente.validate.CadastrarClienteValidate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CadastrarClienteService {

    private ClienteRepository clienteRepository;

    public CadastrarClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ResponseEntity cadastrarCliente(CadastrarClienteRequestDto cadastrarClienteRequestDto) {
        List<BaseErrorDto> erros = new CadastrarClienteValidate().validate(cadastrarClienteRequestDto);
        if (erros.size() > 0) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get();
        }
        if (clienteRepository.existsByEmail(cadastrarClienteRequestDto.getEmail()).orElse(false)) {
            erros.add(new BaseErrorDto("e-mail", MensagensErros.DADO_JA_CADASTRADO));
        }
        if (clienteRepository.existsByCpf(cadastrarClienteRequestDto.getCpf()).orElse(false)) {
            erros.add(new BaseErrorDto("cpf", MensagensErros.DADO_JA_CADASTRADO));
        }
        if (erros.size() > 0) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get();
        }

        ClienteModel clienteModel = new ClienteModelTransform().transformerCadastrarCliente(cadastrarClienteRequestDto);
        UUID cadastrarIdCliente = clienteRepository.save(clienteModel).getId();

        ResponseSuccessBuilder cadastradoComSucesso = new ResponseSuccessBuilder<CadastrarClienteResponseDto>(
                HttpStatus.CREATED,
                new CadastrarClienteResponseDto(cadastrarIdCliente.toString()),
                MensagensSucessos.CADASTRADO_COM_SUCESSO
        );
        return cadastradoComSucesso.get();
    }
}
