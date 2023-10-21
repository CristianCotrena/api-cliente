package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.core.data.DateUtils;
import com.api.cliente.entity.dtos.ClienteRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CadastrarClienteValidate {

    public List<BaseErrorDto> validate(ClienteRequestDto clienteRequestDto) {
        List<BaseErrorDto> erros = validateCamposRequeridos(clienteRequestDto);
        return erros.size() > 0 ? erros : validateCamposInvalidos(clienteRequestDto, erros);
    }

    public List<BaseErrorDto> validateCamposRequeridos(ClienteRequestDto clienteRequestDto) {
        List<BaseErrorDto> erros = new ArrayList<>();
        if (clienteRequestDto.getNome() == null || clienteRequestDto.getNome().isEmpty()) {
            erros.add(new BaseErrorDto("Nome.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (clienteRequestDto.getDataNascimento() == null || clienteRequestDto.getDataNascimento().isEmpty()) {
            erros.add(new BaseErrorDto("Data de Nascimento.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (clienteRequestDto.getEmail() == null || clienteRequestDto.getEmail().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (clienteRequestDto.getCpf() == null || clienteRequestDto.getCpf().isEmpty()) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (clienteRequestDto.getSenhaCatraca() == null || clienteRequestDto.getSenhaCatraca().isEmpty()) {
            erros.add(new BaseErrorDto("Senha da catraca.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        return erros;
    }

    public List<BaseErrorDto> validateCamposInvalidos(
            ClienteRequestDto clienteRequestDto,
            List<BaseErrorDto> erros) {
        if ((
                (clienteRequestDto.getNome().split("\\s+").length) <= 1)
                || (clienteRequestDto.getNome().replaceAll("\\s", "").length() < 6)) {
            erros.add(new BaseErrorDto("Nome.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!new DateUtils().dataIso(clienteRequestDto.getDataNascimento())) {
            erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        } else {
            if (new DateUtils().idadeMinima(clienteRequestDto.getDataNascimento()) < 14) {
                erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(clienteRequestDto.getEmail()).matches()) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!Pattern.compile("^[0-9]{11}$").matcher(clienteRequestDto.getCpf()).matches()) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!Pattern.compile("^[0-9]{4}$").matcher(clienteRequestDto.getSenhaCatraca()).matches()) {
            erros.add(new BaseErrorDto("Senha da catrata.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        return erros;
    }
}
