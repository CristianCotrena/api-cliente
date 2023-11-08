package com.api.cliente.service.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.entity.dtos.BuscarClienteDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.mock.ClienteModelBuilder;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.services.v1.BuscarClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarClienteServiceTests {

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private BuscarClienteService buscarClienteService;

    private BuscarClienteDto buscarClienteDto = new BuscarClienteDto();

    @BeforeEach
    public void setup() {
        clienteRepository = mock(ClienteRepository.class);
        buscarClienteService = new BuscarClienteService(clienteRepository);
    }

    @Test
    public void testeBuscarCliente_SucessoBuscaPorId() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(clienteModel));
        // when(clienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(clienteModel));
        ResponseEntity resultado = buscarClienteService.buscarCliente(
                clienteModel.getId().toString(),
                null,
                null
        );
        buscarClienteDto.setId(clienteModel.getId());
        buscarClienteDto.setNome(clienteModel.getNome());
        buscarClienteDto.setDataNascimento(clienteModel.getDataNascimento());
        buscarClienteDto.setEmail(clienteModel.getEmail());
        buscarClienteDto.setCpf(clienteModel.getCpf());
        buscarClienteDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(clienteModel.getId(), buscarClienteDto.getId());
        assertEquals(clienteModel.getNome(), buscarClienteDto.getNome());
        assertEquals(clienteModel.getDataNascimento(), buscarClienteDto.getDataNascimento());
        assertEquals(clienteModel.getEmail(), buscarClienteDto.getEmail());
        assertEquals(clienteModel.getCpf(), buscarClienteDto.getCpf());
        assertEquals(clienteModel.getSenhaCatraca(), buscarClienteDto.getSenhaCatraca());
    }

    @Test
    public void testeBuscarCliente_SucessoBuscaPorEmail() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.of(clienteModel));
        ResponseEntity resultado = buscarClienteService.buscarCliente(
                null,
                clienteModel.getEmail(),
                null
        );
        buscarClienteDto.setId(clienteModel.getId());
        buscarClienteDto.setNome(clienteModel.getNome());
        buscarClienteDto.setDataNascimento(clienteModel.getDataNascimento());
        buscarClienteDto.setEmail(clienteModel.getEmail());
        buscarClienteDto.setCpf(clienteModel.getCpf());
        buscarClienteDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(clienteModel.getId(), buscarClienteDto.getId());
        assertEquals(clienteModel.getNome(), buscarClienteDto.getNome());
        assertEquals(clienteModel.getDataNascimento(), buscarClienteDto.getDataNascimento());
        assertEquals(clienteModel.getEmail(), buscarClienteDto.getEmail());
        assertEquals(clienteModel.getCpf(), buscarClienteDto.getCpf());
        assertEquals(clienteModel.getSenhaCatraca(), buscarClienteDto.getSenhaCatraca());
    }

    @Test
    public void testeBuscarCliente_SucessoBuscaPorCpf() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(clienteModel));
        ResponseEntity resultado = buscarClienteService.buscarCliente(
                null,
                null,
                clienteModel.getCpf()
        );
        buscarClienteDto.setId(clienteModel.getId());
        buscarClienteDto.setNome(clienteModel.getNome());
        buscarClienteDto.setDataNascimento(clienteModel.getDataNascimento());
        buscarClienteDto.setEmail(clienteModel.getEmail());
        buscarClienteDto.setCpf(clienteModel.getCpf());
        buscarClienteDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(clienteModel.getId(), buscarClienteDto.getId());
        assertEquals(clienteModel.getNome(), buscarClienteDto.getNome());
        assertEquals(clienteModel.getDataNascimento(), buscarClienteDto.getDataNascimento());
        assertEquals(clienteModel.getEmail(), buscarClienteDto.getEmail());
        assertEquals(clienteModel.getCpf(), buscarClienteDto.getCpf());
        assertEquals(clienteModel.getSenhaCatraca(), buscarClienteDto.getSenhaCatraca());
    }

    @Test
    public void testeBuscarCliente_FracassoTodosOsParametros() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity<BaseDto> resultado = buscarClienteService.buscarCliente(
                clienteModel.getId().toString(),
                clienteModel.getEmail(),
                clienteModel.getCpf()
        );
        List<BaseErrorDto> erros = resultado.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("id, email, cpf", erros.get(0).getCampo());
        assertEquals("Dados não encontrados.", erros.get(0).getMensagem());
    }

    @Test
    public void testeBuscarCliente_FracassoPorId() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity<BaseDto> resultado = buscarClienteService.buscarCliente(
                clienteModel.getId().toString(),
                null,
                null
        );
        List<BaseErrorDto> erros = resultado.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("id", erros.get(0).getCampo());
        assertEquals("Dados não encontrados.", erros.get(0).getMensagem());
    }

    @Test
    public void testeBuscarCliente_FracassoEmail() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<BaseDto> resultado = buscarClienteService.buscarCliente(
                null,
                clienteModel.getEmail(),
                null
        );
        List<BaseErrorDto> erros = resultado.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("email", erros.get(0).getCampo());
        assertEquals("Dados não encontrados.", erros.get(0).getMensagem());
    }

    @Test
    public void testeBuscarCliente_FracassoCpf() {

        ClienteModel clienteModel = new ClienteModel();
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity<BaseDto> resultado = buscarClienteService.buscarCliente(
                null,
                null,
                clienteModel.getCpf()
        );
        List<BaseErrorDto> erros = resultado.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("cpf", erros.get(0).getCampo());
        assertEquals("Dados não encontrados.", erros.get(0).getMensagem());
    }
}
