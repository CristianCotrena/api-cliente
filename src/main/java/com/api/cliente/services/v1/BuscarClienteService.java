package com.api.cliente.services.v1;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.entity.dtos.BuscarClienteDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.transformer.ClienteModelTransform;
import com.api.cliente.validate.BuscarClienteValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuscarClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    public BuscarClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ResponseEntity buscarCliente(String id, String email, String cpf) {

        List<BaseErrorDto> erros = new BuscarClienteValidate().validarParametros(id, email, cpf);
        if (erros.size() > 0) {
            ResponseErrorBuilder responseErrorBuilder = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return responseErrorBuilder.get();
        }

        UUID uuid = null;
        if (id != null && !id.isEmpty()) {
            uuid = UUID.fromString(id);
        }
        BuscarClienteDto buscarClienteDto = new BuscarClienteDto();
        if ((id != null && !id.isEmpty()) && (email != null && !email.isEmpty()) && (cpf != null && !cpf.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findById(uuid);
            if (!(clienteModelOptional.isPresent()
                    && clienteModelOptional.get().getEmail().equals(email)
                    && clienteModelOptional.get().getCpf().equals(cpf))) {
                erros.add(new BaseErrorDto("id, email, cpf", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        } else if ((id != null && !id.isEmpty()) && (email != null && !email.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findById(uuid);
            if (!(clienteModelOptional.isPresent()
                    && clienteModelOptional.get().getEmail().equals(email))) {
                erros.add(new BaseErrorDto("id, email", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        } else if ((id != null && !id.isEmpty()) && (cpf != null && !cpf.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findById(uuid);
            if (!(clienteModelOptional.isPresent()
                    && clienteModelOptional.get().getCpf().equals(cpf))) {
                erros.add(new BaseErrorDto("id, cpf", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        } else if ((email != null && !email.isEmpty()) && (cpf != null && !cpf.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findByEmail(email);
            if (!(clienteModelOptional.isPresent()
                    && clienteModelOptional.get().getCpf().equals(cpf))) {
                erros.add(new BaseErrorDto("email, cpf", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        } else if ((id != null && !id.isEmpty()) && (email == null || email.isEmpty()) && (cpf == null || cpf.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findById(uuid);
            if (!(clienteModelOptional.isPresent())) {
                erros.add(new BaseErrorDto("id", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        } else if ((email != null && !email.isEmpty()) && (id == null || id.isEmpty()) && (cpf == null || cpf.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findByEmail(email);
            if (!(clienteModelOptional.isPresent())) {
                erros.add(new BaseErrorDto("email", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        } else if ((cpf != null && !cpf.isEmpty()) && (id == null || id.isEmpty()) && (email == null || email.isEmpty())) {
            Optional<ClienteModel> clienteModelOptional = clienteRepository.findByCpf(cpf);
            if (!(clienteModelOptional.isPresent())) {
                erros.add(new BaseErrorDto("cpf", MensagensErros.DADOS_NAO_ENCONTRADO));
            } else {
                buscarClienteDto = new ClienteModelTransform().transformerBuscarCliente(clienteModelOptional);
            }
        }
        if (erros.size() > 0) {
            ResponseErrorBuilder responseErrorBuilder = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return responseErrorBuilder.get();
        }

        ResponseSuccessBuilder clienteEncontrado = new ResponseSuccessBuilder<BuscarClienteDto>(
                HttpStatus.OK,
                buscarClienteDto,
                "Cliente encontrado com sucesso."
        );
        return clienteEncontrado.get();
    }
}
