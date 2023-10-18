package com.api.cliente.controllers.V1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.entity.dtos.ClienteAtualizarRequestDto;
import com.api.cliente.entity.dtos.ClienteRequestDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.services.v1.AtualizarClienteService;
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
    AtualizarClienteService atualizarClienteService;

    public ClienteController(
            CadastrarClienteService cadastrarClienteService,
            AtualizarClienteService atualizarClienteService) {
        this.cadastrarClienteService = cadastrarClienteService;
        this.atualizarClienteService = atualizarClienteService;
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
    public ResponseEntity<BaseDto<ClienteModel>> cadastrarCliente(@RequestBody ClienteRequestDto clienteRequestDto) {
        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(clienteRequestDto);
        return ResponseEntity.status(baseDto.getResultado().getStatus()).body(baseDto);
    }

    @Operation(summary = "Atualizar um cliente já existente.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cliente atualizado com sucesso.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Cliente atualizado com sucesso.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "E-mail já cadastrado.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "E-mail já  cadastrado.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Senha da catraca já cadastrado.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Senha da catraca já cadastrado.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "E-mail deve ser diferente do anterior.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "E-mail deve ser diferente do anterior.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Senha deve ser diferente da anterior.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Senha deve ser diferente da anterior.")
                    )
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseDto<ClienteModel>> atualizarCiente(
            @PathVariable(value = "id") UUID idCliente,
            @RequestBody ClienteAtualizarRequestDto clienteAtualizarRequestDto) {
        BaseDto baseDto = atualizarClienteService.atualizarCliente(idCliente, clienteAtualizarRequestDto);
        return ResponseEntity.status(baseDto.getResultado().getStatus()).body(baseDto);
    }
}
