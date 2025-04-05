package com.api.cliente.service.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.services.v1.ListarClientesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarClientesServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ListarClientesService listarClientesService;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        listarClientesService = new ListarClientesService(clienteRepository);
    }

    @Test
    public void testeListarClientes_Sucesso() {
        List<ClienteModel> clientes = new ArrayList<>();
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());
        clientes.add(new ClienteModel());

        Pageable pageable = PageRequest.of(0, 10);
        Page<ClienteModel> clientePagina = new PageImpl<>(clientes, pageable, clientes.size());

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(clientePagina);
        ResponseEntity<BaseDto> responseEntity = listarClientesService.listarClientes(null, null, "0");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Listagem concluída.", responseEntity.getBody().getResultado().getDescricao());
    }

    @Test
    public void testeListarClientes_FracassoListaVazia() {
        List<ClienteModel> clientes = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 10);
        Page<ClienteModel> clientePagina = new PageImpl<>(clientes, pageable, clientes.size());

        when(clienteRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(clientePagina);
        ResponseEntity<BaseDto> responseEntity = listarClientesService.listarClientes(null, null, "0");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Dados não encontrados.", responseEntity.getBody().getResultado().getDescricao());
    }
}