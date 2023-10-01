package com.api.cliente.ClienteTests;

import com.api.cliente.base.dto.BaseDto;
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

import java.util.Optional;
import java.util.UUID;

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

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);

        assertEquals(HttpStatus.CREATED.value(), baseDto.getResultado().getStatus());
        assertEquals("Cliente cadastrado com sucesso.", baseDto.getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Sucesso_StatusZero() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);
        clienteModel.setStatus(0);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);

        assertEquals(HttpStatus.CREATED.value(), baseDto.getResultado().getStatus());
        assertEquals("Cliente cadastrado com sucesso.", baseDto.getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Erro_EmailJaExiste() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new ClienteModel()));
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);
        //when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Erro_CpfJaExiste() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(new ClienteModel()));
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);
        //when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Erro_EmailCpfJaExiste() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new ClienteModel()));
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(new ClienteModel()));
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId(idCliente);
        //when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals(2, baseDto.getErros().size());
    }
}