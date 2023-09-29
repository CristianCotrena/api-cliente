package com.api.cliente.controllers.V1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.dtos.InserirDadosClienteDto;
import com.api.cliente.models.ClienteModel;
import com.api.cliente.services.CadastrarClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cliente")
public class ClienteController {

    CadastrarClienteService cadastrarClienteService;

    public ClienteController(CadastrarClienteService cadastrarClienteService) {
        this.cadastrarClienteService = cadastrarClienteService;
    }

    @PostMapping
    public ResponseEntity<BaseDto<ClienteModel>> cadastrarCliente(@RequestBody InserirDadosClienteDto inserirDadosClienteDto) {
        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        return ResponseEntity.status(baseDto.getResultado().getStatus()).body(baseDto);
    }
}
