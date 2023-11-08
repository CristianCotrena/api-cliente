package com.api.cliente.services.v1;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import com.api.cliente.builder.ResponseSuccessBuilder;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.entity.dtos.ListarClienteResponseDto;
import com.api.cliente.entity.dtos.PaginaListagemDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.repositories.specifications.cliente.ClienteSpecifications;
import com.api.cliente.validate.ListarClientesValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarClientesService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ListarClientesService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ResponseEntity listarClientes(String dataInicial, String dataFinal, String pagina) {

        if (pagina == null || pagina.isEmpty()) {
            pagina = "0";
        }

        List<BaseErrorDto> erros = new ListarClientesValidate().validarParametros(dataInicial, dataFinal, pagina);
        if (erros.size() > 0) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, erros);
            return resultado.get();
        }

        Page<ClienteModel> clientes;
        int numero = Integer.parseInt(pagina);
        Pageable paginaSolicitada = PageRequest.of(numero, 10, Sort.by(Sort.Order.asc("dataNascimento")));
        clientes = clienteRepository.findAll(buscaPorData(dataInicial, dataFinal), paginaSolicitada);

        if (clientes.isEmpty()) {
            ResponseErrorBuilder resultado = new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, MensagensErros.DADOS_NAO_ENCONTRADO);
            return resultado.get();
        }
        List<ListarClienteResponseDto> clienteResponseList = clientes.getContent().stream()
                .map(cliente -> new ListarClienteResponseDto(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getDataNascimento(),
                        cliente.getEmail(),
                        cliente.getCpf(),
                        cliente.getSenhaCatraca()
                )).collect(Collectors.toList());

        PaginaListagemDto paginaListagemDto = new PaginaListagemDto(clienteResponseList, (clientes.getNumber() + 1), clientes.getTotalPages());
        return new ResponseSuccessBuilder<>(
                HttpStatus.OK,
                paginaListagemDto,
                "Listagem concluída.").get();
    }

    private Specification<ClienteModel> buscaPorData(
            String dataInicial,
            String dataFinal) {
        Specification<ClienteModel> specificationDataInicial = ClienteSpecifications.dataInicialMaiorOuIgual(dataInicial);
        Specification<ClienteModel> specificationDataFinal = ClienteSpecifications.dataFinalMenorOuIgual(dataFinal);

        Specification<ClienteModel> buscar = Specification.where(specificationDataInicial).and(specificationDataFinal);
        return buscar;
    }
}
