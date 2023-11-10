package com.api.cliente.services.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeletarClienteService {

    private final ClienteRepository clienteRepository;

    public DeletarClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteModel> listarClientesAtivos() {
        return clienteRepository.findByStatus(1);
    }

    public ResponseEntity<? extends BaseDto<? extends Object>> inativarCliente(UUID id) {
        Optional<ClienteModel> encontrarCliente = clienteRepository.findByid(id);
        if (encontrarCliente.isPresent()) {
            // Atualize o status para 0 (inativo)
            ClienteModel cliente = encontrarCliente.get();
            cliente.setStatus(0);
            clienteRepository.save(cliente);

            return new ResponseSuccessBuilder<String>(HttpStatus.OK, "Cliente marcado como inativo com sucesso.").get();
        } else {
            // Trata o caso em que o cliente com o ID fornecido não existe
            List<BaseErrorDto> errors = new ArrayList<>();
            errors.add(new BaseErrorDto("Cliente", "Cliente não encontrado."));
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.NOT_FOUND, errors);
            return resultado.get();
        }
    }
}


