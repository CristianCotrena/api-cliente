package com.api.cliente.services.v1;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.entity.dtos.AtualizarClienteResponseDto;
import com.api.cliente.entity.dtos.ClienteAtualizarRequestDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.transformer.ClienteModelTransform;
import com.api.cliente.validate.AtualizarClienteValidate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AtualizarClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public AtualizarClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ResponseEntity atualizarCliente(
            String idCliente,
            ClienteAtualizarRequestDto clienteAtualizarRequestDto) {

        List<BaseErrorDto> erros = new AtualizarClienteValidate().validate(idCliente, clienteAtualizarRequestDto);
        ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
        if (erros.size() > 0) {
            return resultado.get();
        }

        UUID uuid = UUID.fromString(idCliente);
        if (!clienteRepository.existsById(uuid)) {
            erros.add(new BaseErrorDto("id", MensagensErros.INEXISTENTE));
            return resultado.get();
        }

        Optional<ClienteModel> clienteExistente = clienteRepository.findById(uuid);
        ClienteModel clienteModel = clienteExistente.get();

        if (clienteAtualizarRequestDto.getEmail() != null && !clienteAtualizarRequestDto.getEmail().isEmpty()) {
            if (clienteExistente.get().getEmail().equals(clienteAtualizarRequestDto.getEmail())) {
                erros.add(new BaseErrorDto("email", MensagensErros.CAMPO_DIFERENTE_ANTERIOR));
            } else if (clienteRepository.existsByEmail(clienteAtualizarRequestDto.getEmail()).orElse(false)) {
                erros.add(new BaseErrorDto("email", MensagensErros.DADO_JA_CADASTRADO));
            }
        }
        if (clienteAtualizarRequestDto.getSenhaCatraca() != null && !clienteAtualizarRequestDto.getSenhaCatraca().isEmpty()) {
            if (clienteExistente.get().getSenhaCatraca().equals(clienteAtualizarRequestDto.getSenhaCatraca())) {
                erros.add(new BaseErrorDto("senhaCatraca", MensagensErros.CAMPO_DIFERENTE_ANTERIOR));
            }
        }
        if (erros.size() > 0) {
            return resultado.get();
        }
        if (erros.size() > 0) {
            resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get();
        }
        clienteModel = new ClienteModelTransform().transformerAtualizarCliente(clienteAtualizarRequestDto, clienteModel);
        
        ClienteModel atualizarCliente = clienteRepository.save(clienteModel);

        AtualizarClienteResponseDto resposta = new AtualizarClienteResponseDto(
                atualizarCliente.getNome(),
                atualizarCliente.getEmail(),
                atualizarCliente.getSenhaCatraca()
        );

        ResponseSuccessBuilder atualizado = new ResponseSuccessBuilder<AtualizarClienteResponseDto>(
                HttpStatus.ACCEPTED,
                resposta,
                "Atualizado com sucesso."
        );
        return atualizado.get();
    }
}
