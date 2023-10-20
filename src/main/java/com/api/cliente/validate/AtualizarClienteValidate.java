package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.entity.dtos.ClienteAtualizarRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AtualizarClienteValidate {

    public List<BaseErrorDto> validate(ClienteAtualizarRequestDto clienteAtualizarRequestDto) {
        List<BaseErrorDto> erros = validarCamposPermitidos(clienteAtualizarRequestDto);
        return erros;
    }

    public List<BaseErrorDto> validarCamposPermitidos(
            ClienteAtualizarRequestDto clienteAtualizarRequestDto) {
        List<BaseErrorDto> erros = new ArrayList<>();

        if (clienteAtualizarRequestDto.getEmail() != null && !clienteAtualizarRequestDto.getEmail().isEmpty()) {
            if (!(Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(clienteAtualizarRequestDto.getEmail()).matches())) {
                erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (clienteAtualizarRequestDto.getSenhaCatraca() != null && !clienteAtualizarRequestDto.getSenhaCatraca().isEmpty()) {
            if (!Pattern.compile("^[0-9]{4}$").matcher(clienteAtualizarRequestDto.getSenhaCatraca()).matches()) {
                erros.add(new BaseErrorDto("Senha da catrata.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        return erros;
    }
}
