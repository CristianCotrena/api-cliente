package com.api.cliente.service.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.entity.dtos.CadastrarClienteRequestDto;
import com.api.cliente.entity.dtos.CadastrarClienteResponseDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.mock.ClienteModelBuilder;
import com.api.cliente.mock.ClienteRequestDtoBuilder;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.services.v1.CadastrarClienteService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CadastrarClienteServiceTests {

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private CadastrarClienteService cadastrarClienteService;

    private CadastrarClienteRequestDto cadastrarClienteRequestDto;
    private ClienteModel clienteModel;

    @BeforeEach
    public void setup() {
        clienteRepository = mock(ClienteRepository.class);
        cadastrarClienteService = new CadastrarClienteService(clienteRepository);

        cadastrarClienteRequestDto = ClienteRequestDtoBuilder.criar();
    }

    @Test
    public void testeCadastrarCliente_Sucesso() {

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.empty());
        clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);
        ResponseEntity<BaseDto<ClienteModel>> resultado = cadastrarClienteService.cadastrarCliente(cadastrarClienteRequestDto);
        CadastrarClienteResponseDto cadastrarClienteResponseDto = new CadastrarClienteResponseDto(clienteModel.getId().toString());

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(clienteModel.getId().toString(), cadastrarClienteResponseDto.getClienteId());
        assertEquals("Cadastrado com sucesso.", resultado.getBody().getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Erro_EmailJaExiste() {

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(true));
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.empty());
        clienteModel = ClienteModelBuilder.build();

        ResponseEntity<BaseDto<ClienteModel>> responseEntity = cadastrarClienteService.cadastrarCliente(cadastrarClienteRequestDto);
        List<BaseErrorDto> listaErros = responseEntity.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("e-mail", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Erro_CpfJaExiste() {

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.of(true));
        clienteModel = ClienteModelBuilder.build();

        ResponseEntity<BaseDto<ClienteModel>> responseEntity = cadastrarClienteService.cadastrarCliente(cadastrarClienteRequestDto);
        List<BaseErrorDto> listaErros = responseEntity.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("cpf", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Erro_EmailCpfJaExiste() {

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(true));
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.of(true));
        clienteModel = ClienteModelBuilder.build();

        ResponseEntity<BaseDto<ClienteModel>> responseEntity = cadastrarClienteService.cadastrarCliente(cadastrarClienteRequestDto);
        List<BaseErrorDto> listaErros = responseEntity.getBody().getErros();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("e-mail", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
        assertEquals("cpf", listaErros.get(1).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(1).getMensagem());
    }
}