package com.api.cliente.controllers.V1;


import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.entity.dtos.InserirDadosClienteDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.services.v1.BuscarClienteService;
import com.api.cliente.services.v1.CadastrarClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "API Cliente")
@RestController
@RequestMapping(value = "/v1/cliente")
public class ClienteController {

    CadastrarClienteService cadastrarClienteService;
    BuscarClienteService buscarClienteService;

    public ClienteController(CadastrarClienteService cadastrarClienteService, BuscarClienteService buscarClienteService) {
        this.cadastrarClienteService = cadastrarClienteService;
        this.buscarClienteService = buscarClienteService;
    }

    @Operation(summary = "Cadastra um novo cliente.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Cliente cadastrado com sucesso.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "E-mail já cadastrado.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "E-mail já  cadastrado.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "CPF já cadastrado.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "CPF já cadastrado.")
                    )
            })
    })
    @PostMapping
    public ResponseEntity<BaseDto<ClienteModel>> cadastrarCliente(@RequestBody InserirDadosClienteDto inserirDadosClienteDto) {
        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(inserirDadosClienteDto);
        return ResponseEntity.status(baseDto.getResultado().getStatus()).body(baseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto<ClienteModel>> buscarCliente(@PathVariable("id") UUID id) {
            BaseDto<ClienteModel> baseDto = buscarClienteService.buscarCliente(id);
            return ResponseEntity.ok(baseDto);
    }

}
