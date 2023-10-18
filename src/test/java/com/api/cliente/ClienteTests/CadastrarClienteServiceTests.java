package com.api.cliente.ClienteTests;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.base.dto.BaseResultDto;
import com.api.cliente.entity.dtos.CadastrarClienteDto;
import com.api.cliente.entity.dtos.InserirDadosClienteDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.services.v1.CadastrarClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.*;
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

    private InserirDadosClienteDto inserirDadosClienteDto;

    @BeforeEach
    public void setup() {
        clienteRepository = mock(ClienteRepository.class);
        cadastrarClienteService = new CadastrarClienteService(clienteRepository);

        inserirDadosClienteDto = new InserirDadosClienteDto();
        inserirDadosClienteDto.setNome("Nome de Teste");
        inserirDadosClienteDto.setDataNascimento("1992-01-31");
        inserirDadosClienteDto.setEmail("emailteste@teste.com");
        inserirDadosClienteDto.setCpf("12345678912");
        inserirDadosClienteDto.setSenhaCatraca("1234");
    }

    @Test
    public void testeCadastrarCliente_Sucesso() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.empty());
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);

        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        CadastrarClienteDto cadastrarClienteDto = new CadastrarClienteDto(clienteModel.getId().toString());
        BaseResultDto cadastradoComSucesso = new BaseResultDto(
                baseDto.getResultado().getStatus(), baseDto.getResultado().getDescricao());

        assertEquals(HttpStatus.CREATED.value(), baseDto.getResultado().getStatus());
        assertEquals("Cadastrado com sucesso.", baseDto.getResultado().getDescricao());
        assertEquals(HttpStatus.CREATED.value(), cadastradoComSucesso.getStatus());
        assertEquals(clienteModel.getId().toString(), cadastrarClienteDto.getClienteId());
        assertEquals("Cadastrado com sucesso.", cadastradoComSucesso.getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Sucesso_StatusZero() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.empty());
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);
        clienteModel.setStatus(0);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        CadastrarClienteDto cadastrarClienteDto = new CadastrarClienteDto(clienteModel.getId().toString());
        BaseResultDto cadastradoComSucesso = new BaseResultDto(baseDto.getResultado().getStatus(), baseDto.getResultado().getDescricao());

        assertEquals(HttpStatus.CREATED.value(), baseDto.getResultado().getStatus());
        assertEquals("Cadastrado com sucesso.", baseDto.getResultado().getDescricao());
        assertEquals(HttpStatus.CREATED.value(), cadastradoComSucesso.getStatus());
        assertEquals(clienteModel.getId().toString(), cadastrarClienteDto.getClienteId());
        assertEquals("Cadastrado com sucesso.", cadastradoComSucesso.getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Erro_EmailJaExiste() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(true));
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.empty());
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("E-mail.", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Erro_CpfJaExiste() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.of(true));
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("CPF.", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Erro_EmailCpfJaExiste() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(true));
        when(clienteRepository.existsByCpf(any(String.class))).thenReturn(Optional.of(true));
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("E-mail.", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
        assertEquals("CPF.", listaErros.get(1).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(1).getMensagem());
    }
}