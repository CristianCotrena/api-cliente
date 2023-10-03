package com.api.cliente.controllers.V1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.builder.ResponseErrorBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cliente/v1")
@Tag(name = "API Cliente")
public class ClienteControllerTeste {

    @GetMapping
    public ResponseEntity<BaseDto<Void>> olaMundoPaginaCliente() {

//        ClienteModel cliente = new ClienteModel();
//        cliente.setNome("Fulano");
//        cliente.setEmail("fulano@nt.com");
//        cliente.setDataNascimento("1992-01-31");
//        return new ResponseSuccessBuilder<ClienteModel>(HttpStatus.ACCEPTED, cliente).get();

        List<BaseErrorDto> erros = new ArrayList<>();
        erros.add(new BaseErrorDto("CPF", "Campo invalido."));
        erros.add(new BaseErrorDto("E-mail", "Campo obrigatório."));
        return new ResponseErrorBuilder(HttpStatus.BAD_REQUEST, "Não foi possível buscar o cliente.", erros).get();
    }
}
