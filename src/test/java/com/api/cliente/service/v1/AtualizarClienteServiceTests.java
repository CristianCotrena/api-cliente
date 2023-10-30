package com.api.cliente.service.v1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.base.dto.BaseResultDto;
import com.api.cliente.entity.dtos.AtualizarClienteResponseDto;
import com.api.cliente.entity.dtos.CadastrarClienteResponseDto;
import com.api.cliente.entity.dtos.ClienteAtualizarRequestDto;
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
import org.springframework.http.ResponseEntity;

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

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );
        AtualizarClienteResponseDto atualizarClienteResponseDto = new AtualizarClienteResponseDto(
                clienteModel.getNome().toString(),
                clienteModel.getEmail().toString(),
                clienteModel.getSenhaCatraca().toString()
        );

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals(clienteModel.getNome(), atualizarClienteResponseDto.getNome());
        assertEquals(clienteModel.getEmail(), atualizarClienteResponseDto.getEmail());
        assertEquals(clienteModel.getSenhaCatraca(), atualizarClienteResponseDto.getSenhaCatraca());
        assertEquals("Atualizado com sucesso.", resultado.getBody().getResultado().getDescricao());
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

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );
        AtualizarClienteResponseDto atualizarClienteResponseDto = new AtualizarClienteResponseDto(
                clienteModel.getNome().toString(),
                clienteModel.getEmail().toString(),
                null
        );

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals(clienteModel.getNome(), atualizarClienteResponseDto.getNome());
        assertEquals(clienteModel.getEmail(), atualizarClienteResponseDto.getEmail());
        assertEquals("Atualizado com sucesso.", resultado.getBody().getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Sucesso_ApenasSenha() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        ClienteAtualizarRequestDto clienteAtualizarRequestDtoApenasEmail = new ClienteAtualizarRequestDto();
        clienteAtualizarRequestDtoApenasEmail.setSenhaCatraca("5555");
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(false));
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );
        AtualizarClienteResponseDto atualizarClienteResponseDto = new AtualizarClienteResponseDto(
                clienteModel.getNome().toString(),
                null,
                clienteModel.getSenhaCatraca()
        );

        assertEquals(HttpStatus.ACCEPTED, resultado.getStatusCode());
        assertEquals(clienteModel.getNome(), atualizarClienteResponseDto.getNome());
        assertEquals(clienteModel.getSenhaCatraca(), atualizarClienteResponseDto.getSenhaCatraca());
        assertEquals("Atualizado com sucesso.", resultado.getBody().getResultado().getDescricao());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_IdNaoExistente() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(false);

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("id", resultado.getBody().getErros().get(0).getCampo());
        assertEquals("Dado inexistente.", resultado.getBody().getErros().get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_EmailJaExiste() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        when(clienteRepository.existsByEmail(any(String.class))).thenReturn(Optional.of(true));

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("email", resultado.getBody().getErros().get(0).getCampo());
        assertEquals("Já cadastrado.", resultado.getBody().getErros().get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_EmailIgualAoAnterior() {

        ClienteModel clienteModel = ClienteModelBuilder.build();
        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        clienteAtualizarRequestDto.setEmail(clienteModel.getEmail());

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("email", resultado.getBody().getErros().get(0).getCampo());
        assertEquals("Deve ser diferente do anterior.", resultado.getBody().getErros().get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_SenhaIgualAoAnterior() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        clienteAtualizarRequestDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("senhaCatraca", resultado.getBody().getErros().get(0).getCampo());
        assertEquals("Deve ser diferente do anterior.", resultado.getBody().getErros().get(0).getMensagem());
    }

    @Test
    public void testeCadastrarCliente_Fracasso_EmailESenhaIgualAoAnterior() {

        ClienteModel clienteModel = ClienteModelBuilder.build();

        when(clienteRepository.existsById(clienteModel.getId())).thenReturn(true);
        when(clienteRepository.findById(clienteModel.getId())).thenReturn(Optional.of(clienteModel));
        clienteAtualizarRequestDto.setEmail(clienteModel.getEmail());
        clienteAtualizarRequestDto.setSenhaCatraca(clienteModel.getSenhaCatraca());

        ResponseEntity<BaseDto<ClienteModel>> resultado = atualizarClienteService.atualizarCliente(
                clienteModel.getId(),
                clienteAtualizarRequestDto
        );

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("email", resultado.getBody().getErros().get(0).getCampo());
        assertEquals("Deve ser diferente do anterior.", resultado.getBody().getErros().get(0).getMensagem());
        assertEquals("senhaCatraca", resultado.getBody().getErros().get(1).getCampo());
        assertEquals("Deve ser diferente do anterior.", resultado.getBody().getErros().get(1).getMensagem());
    }
}