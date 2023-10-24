package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.entity.dtos.AtualizarClienteRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AtualizarClienteValidate {

    public List<BaseErrorDto> validate(AtualizarClienteRequestDto atualizarClienteRequestDto) {
        List<BaseErrorDto> erros = validarCamposPermitidos(atualizarClienteRequestDto);
        return erros;
    }

    public List<BaseErrorDto> validarCamposPermitidos(
            AtualizarClienteRequestDto atualizarClienteRequestDto) {
        List<BaseErrorDto> erros = new ArrayList<>();

        if (atualizarClienteRequestDto.getEmail() != null && !atualizarClienteRequestDto.getEmail().isEmpty()) {
            if (!(Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(atualizarClienteRequestDto.getEmail()).matches())) {
                erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (atualizarClienteRequestDto.getSenhaCatraca() != null && !atualizarClienteRequestDto.getSenhaCatraca().isEmpty()) {
            if (!Pattern.compile("^[0-9]{4}$").matcher(atualizarClienteRequestDto.getSenhaCatraca()).matches()) {
                erros.add(new BaseErrorDto("Senha da catrata.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        return erros;
    }
}
