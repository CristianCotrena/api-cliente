package com.api.cliente.controllers.V1;

import com.api.cliente.base.dto.BaseDto;
import com.api.cliente.entity.dtos.AtualizarClienteRequestDto;
import com.api.cliente.entity.dtos.CadastrarClienteRequestDto;
import com.api.cliente.entity.models.ClienteModel;
import com.api.cliente.services.v1.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "API Cliente")
@RestController
@RequestMapping(value = "/v1/cliente")
public class ClienteController {

    CadastrarClienteService cadastrarClienteService;
    AtualizarClienteService atualizarClienteService;
    BuscarClienteService buscarClienteService;
    ListarClientesService listarClientesService;
    DeletarClienteService deletarClienteService;

    public ClienteController(
            CadastrarClienteService cadastrarClienteService,
            AtualizarClienteService atualizarClienteService,
            BuscarClienteService buscarClienteService,
            ListarClientesService listarClientesService,
            DeletarClienteService deletarClienteService) {
        this.cadastrarClienteService = cadastrarClienteService;
        this.atualizarClienteService = atualizarClienteService;
        this.buscarClienteService = buscarClienteService;
        this.listarClientesService = listarClientesService;
        this.deletarClienteService = deletarClienteService;
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
    public ResponseEntity<BaseDto<ClienteModel>> cadastrarCliente(@RequestBody CadastrarClienteRequestDto cadastrarClienteRequestDto) {
        BaseDto baseDto = cadastrarClienteService.cadastrarCliente(cadastrarClienteRequestDto);
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
            @RequestBody AtualizarClienteRequestDto atualizarClienteRequestDto) {
        BaseDto baseDto = atualizarClienteService.atualizarCliente(idCliente, atualizarClienteRequestDto);
        return ResponseEntity.status(baseDto.getResultado().getStatus()).body(baseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDto<ClienteModel>> buscarCliente(@PathVariable("id") UUID id) {
            BaseDto<ClienteModel> baseDto = buscarClienteService.buscarCliente(id);
            return ResponseEntity.ok(baseDto);
    }

    @Operation(summary = "Listar clientes.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Listagem concluída.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "Listagem concluída.")
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Dados não encontrados", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "E-mail já  cadastrado.")
                    )
            })
    })
    @GetMapping("/listar")
    public ResponseEntity<BaseDto<ClienteModel>> listarClientes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataFinal,
            @RequestParam(required = false) String pagina) {
        return listarClientesService.listarClientes(dataInicial, dataFinal, pagina);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseDto<Void>> deletarCliente(@PathVariable(value = "id")UUID id){
        return (ResponseEntity<BaseDto<Void>>) deletarClienteService.inativarCliente(id);

    }

}
