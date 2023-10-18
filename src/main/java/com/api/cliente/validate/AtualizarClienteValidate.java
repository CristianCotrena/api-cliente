package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.entity.dtos.ClienteRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AtualizarClienteValidate {

    public List<BaseErrorDto> validate(ClienteRequestDto clienteRequestDto) {
        List<BaseErrorDto> erros = validarCamposPermitidos(clienteRequestDto);
        return erros.size() > 0 ? erros : validateCamposNaoPermitidos(clienteRequestDto, erros);
    }

    public List<BaseErrorDto> validarCamposPermitidos(ClienteRequestDto clienteRequestDto) {
        List<BaseErrorDto> erros = new ArrayList<>();

        if (clienteRequestDto.getEmail() != null && !clienteRequestDto.getEmail().isEmpty()) {
            if (!(Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(clienteRequestDto.getEmail()).matches())) {
                erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (clienteRequestDto.getSenhaCatraca() != null && !clienteRequestDto.getSenhaCatraca().isEmpty()) {
            if (!Pattern.compile("^[0-9]{4}$").matcher(clienteRequestDto.getSenhaCatraca()).matches()) {
                erros.add(new BaseErrorDto("Senha da catrata.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        return erros;
    }

    public List<BaseErrorDto> validateCamposNaoPermitidos(
            ClienteRequestDto clienteRequestDto,
            List<BaseErrorDto> erros) {

        if (camposNaoPermitidos(clienteRequestDto)) {
            if (clienteRequestDto.getNome() != null && !clienteRequestDto.getNome().isEmpty()) {
                erros.add(new BaseErrorDto("Nome.", MensagensErros.NAO_PERMITIDO_ALTERAR));
            }
            if (clienteRequestDto.getDataNascimento() != null && !clienteRequestDto.getDataNascimento().isEmpty()) {
                erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.NAO_PERMITIDO_ALTERAR));
            }
            if (clienteRequestDto.getCpf() != null && !clienteRequestDto.getCpf().isEmpty()) {
                erros.add(new BaseErrorDto("CPF.", MensagensErros.NAO_PERMITIDO_ALTERAR));
            }
            if (clienteRequestDto.getStatus() != null) {
                erros.add(new BaseErrorDto("Status.", MensagensErros.NAO_PERMITIDO_ALTERAR));
            }
        }
        return erros;
    }

    public boolean camposNaoPermitidos(ClienteRequestDto clienteRequestDto) {
        return (clienteRequestDto.getNome() != null && !clienteRequestDto.getNome().isEmpty()) ||
                (clienteRequestDto.getDataNascimento() != null && !clienteRequestDto.getDataNascimento().isEmpty()) ||
                (clienteRequestDto.getCpf() != null && !clienteRequestDto.getCpf().isEmpty()) ||
                (clienteRequestDto.getStatus() != null);
    }
}
