package com.api.cliente.service.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.base.dto.BaseResultDto;
import com.api.cliente.entity.dtos.ClienteAtualizarRequestDto;
import com.api.cliente.entity.dtos.ClienteResponseDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.mock.ClienteAtualizarRequestDtoBuilder;
import com.api.cliente.mock.ClienteModelBuilder;
import com.api.cliente.repositories.ClienteRepository;
import com.api.cliente.services.v1.AtualizarClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarClienteServiceTests {

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private AtualizarClienteService atualizarClienteService;

    private ClienteAtualizarRequestDto clienteAtualizarRequestDto;

    @BeforeEach
    public void setup() {
        clienteRepository = mock(ClienteRepository.class);
        atualizarClienteService =  new AtualizarClienteService(clienteRepository);

        clienteAtualizarRequestDto = new ClienteAtualizarRequestDto();
        clienteAtualizarRequestDto = ClienteAtualizarRequestDtoBuilder.build();
    }

    @Test
    public void testeCadastrarCliente_Sucesso() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(false));

        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDto);
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto(
                clienteModel.getNome().toString(),
                clienteModel.getEmail().toString(),
                clienteModel.getSenhaCatraca().toString()
        );
        BaseResultDto atualizadoComSucesso = new BaseResultDto(
                baseDto.getResultado().getStatus(),
                baseDto.getResultado().getDescricao()
        );

        assertEquals(HttpStatus.ACCEPTED.value(), baseDto.getResultado().getStatus());
        assertEquals("Atualizado com sucesso.", baseDto.getResultado().getDescricao());
        assertEquals(HttpStatus.ACCEPTED.value(), atualizadoComSucesso.getStatus());
        assertEquals(clienteModel.getNome(), clienteResponseDto.getNome());
        assertEquals(clienteModel.getEmail(), clienteResponseDto.getEmail());
        assertEquals(clienteModel.getSenhaCatraca(), clienteResponseDto.getSenhaCatraca());
        assertEquals("Atualizado com sucesso.", atualizadoComSucesso.getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Sucesso_ApenasEmail() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        ClienteAtualizarRequestDto clienteAtualizarRequestDtoApenasEmail = new ClienteAtualizarRequestDto();
        clienteAtualizarRequestDtoApenasEmail.setEmail("atualizandoapenasemail@gmail.com");

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(false));

        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDtoApenasEmail);
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto(
                clienteModel.getNome().toString(),
                clienteModel.getEmail().toString(),
                null
        );
        BaseResultDto atualizadoComSucesso = new BaseResultDto(
                baseDto.getResultado().getStatus(),
                baseDto.getResultado().getDescricao()
        );

        assertEquals(HttpStatus.ACCEPTED.value(), baseDto.getResultado().getStatus());
        assertEquals("Atualizado com sucesso.", baseDto.getResultado().getDescricao());
        assertEquals(HttpStatus.ACCEPTED.value(), atualizadoComSucesso.getStatus());
        assertEquals(clienteModel.getNome(), clienteResponseDto.getNome());
        assertEquals(clienteModel.getEmail(), clienteResponseDto.getEmail());
        assertEquals("Atualizado com sucesso.", atualizadoComSucesso.getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Sucesso_ApenasSenha() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        ClienteAtualizarRequestDto clienteAtualizarRequestDtoApenasSenha = new ClienteAtualizarRequestDto();
        clienteAtualizarRequestDtoApenasSenha.setSenhaCatraca("5555");

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));

        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDtoApenasSenha);
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto(
                clienteModel.getNome().toString(),
                null,
                clienteModel.getSenhaCatraca().toString()
        );
        BaseResultDto atualizadoComSucesso = new BaseResultDto(
                baseDto.getResultado().getStatus(),
                baseDto.getResultado().getDescricao()
        );

        assertEquals(HttpStatus.ACCEPTED.value(), baseDto.getResultado().getStatus());
        assertEquals("Atualizado com sucesso.", baseDto.getResultado().getDescricao());
        assertEquals(HttpStatus.ACCEPTED.value(), atualizadoComSucesso.getStatus());
        assertEquals(clienteModel.getNome(), clienteResponseDto.getNome());
        assertEquals(clienteModel.getSenhaCatraca(), clienteResponseDto.getSenhaCatraca());
        assertEquals("Atualizado com sucesso.", atualizadoComSucesso.getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_IdNaoExistente() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(false);

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("Id Cliente.", listaErros.get(0).getCampo());
        assertEquals("Dado inexistente.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_EmailJaExiste() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(true));

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("E-mail.", listaErros.get(0).getCampo());
        assertEquals("Já cadastrado.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_EmailIgualAoAnterior() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        clienteAtualizarRequestDto.setEmail(clienteModel.getEmail());

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("E-mail.", listaErros.get(0).getCampo());
        assertEquals("Deve ser diferente do anterior.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_SenhaIgualAoAnterior() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        clienteAtualizarRequestDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("Senha da catraca.", listaErros.get(0).getCampo());
        assertEquals("Deve ser diferente do anterior.", listaErros.get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_EmailESenhaIgualAoAnterior() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        clienteAtualizarRequestDto.setEmail(clienteModel.getEmail());
        clienteAtualizarRequestDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        BaseDto baseDto = atualizarClienteService.atualizarCliente(clienteModel.getId(), clienteAtualizarRequestDto);
        List<BaseErrorDto> listaErros = baseDto.getErros();

        assertEquals(HttpStatus.BAD_REQUEST.value(), baseDto.getResultado().getStatus());
        assertEquals("Bad Request", baseDto.getResultado().getDescricao());
        assertEquals("E-mail.", listaErros.get(0).getCampo());
        assertEquals("Deve ser diferente do anterior.", listaErros.get(0).getMensagem());
        assertEquals("Senha da catraca.", listaErros.get(1).getCampo());
        assertEquals("Deve ser diferente do anterior.", listaErros.get(1).getMensagem());
    }
}