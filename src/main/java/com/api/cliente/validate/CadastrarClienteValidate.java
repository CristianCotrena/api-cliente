package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.core.data.DateUtils;
import com.api.cliente.entity.dtos.CadastrarClienteRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CadastrarClienteValidate {

    public List<BaseErrorDto> validate(CadastrarClienteRequestDto cadastrarClienteRequestDto) {
        List<BaseErrorDto> erros = validateCamposRequeridos(cadastrarClienteRequestDto);
        return erros.size() > 0 ? erros : validateCamposInvalidos(cadastrarClienteRequestDto, erros);
    }

    public List<BaseErrorDto> validateCamposRequeridos(CadastrarClienteRequestDto cadastrarClienteRequestDto) {
        List<BaseErrorDto> erros = new ArrayList<>();
        if (cadastrarClienteRequestDto.getNome() == null || cadastrarClienteRequestDto.getNome().isEmpty()) {
            erros.add(new BaseErrorDto("Nome.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (cadastrarClienteRequestDto.getDataNascimento() == null || cadastrarClienteRequestDto.getDataNascimento().isEmpty()) {
            erros.add(new BaseErrorDto("Data de Nascimento.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (cadastrarClienteRequestDto.getEmail() == null || cadastrarClienteRequestDto.getEmail().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (cadastrarClienteRequestDto.getCpf() == null || cadastrarClienteRequestDto.getCpf().isEmpty()) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (cadastrarClienteRequestDto.getSenhaCatraca() == null || cadastrarClienteRequestDto.getSenhaCatraca().isEmpty()) {
            erros.add(new BaseErrorDto("Senha da catraca.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        return erros;
    }

    public List<BaseErrorDto> validateCamposInvalidos(
            CadastrarClienteRequestDto cadastrarClienteRequestDto,
            List<BaseErrorDto> erros) {
        if ((
                (cadastrarClienteRequestDto.getNome().split("\\s+").length) <= 1)
                || (cadastrarClienteRequestDto.getNome().replaceAll("\\s", "").length() < 6)) {
            erros.add(new BaseErrorDto("Nome.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!new DateUtils().dataIso(cadastrarClienteRequestDto.getDataNascimento())) {
            erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        } else {
            if (new DateUtils().idadeMinima(cadastrarClienteRequestDto.getDataNascimento()) < 14) {
                erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(cadastrarClienteRequestDto.getEmail()).matches()) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!Pattern.compile("^[0-9]{11}$").matcher(cadastrarClienteRequestDto.getCpf()).matches()) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!Pattern.compile("^[0-9]{4}$").matcher(cadastrarClienteRequestDto.getSenhaCatraca()).matches()) {
            erros.add(new BaseErrorDto("Senha da catrata.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        return erros;
    }
}
