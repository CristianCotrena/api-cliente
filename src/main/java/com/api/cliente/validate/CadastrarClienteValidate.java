package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.core.data.DateUtils;
import com.api.cliente.entity.dtos.InserirDadosClienteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CadastrarClienteValidate {

    public List<BaseErrorDto> validate(InserirDadosClienteDto inserirDadosClienteDto) {
        List<BaseErrorDto> erros = validateCamposRequeridos(inserirDadosClienteDto);
        return erros.size() > 0 ? erros : validateCamposInvalidos(inserirDadosClienteDto, erros);
    }

    public List<BaseErrorDto> validateCamposRequeridos(InserirDadosClienteDto inserirDadosClienteDto) {
        List<BaseErrorDto> erros = new ArrayList<>();
        if (inserirDadosClienteDto.getNome() == null || inserirDadosClienteDto.getNome().isEmpty()) {
            erros.add(new BaseErrorDto("Nome.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (inserirDadosClienteDto.getDataNascimento() == null || inserirDadosClienteDto.getDataNascimento().isEmpty()) {
            erros.add(new BaseErrorDto("Data de Nascimento.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (inserirDadosClienteDto.getEmail() == null || inserirDadosClienteDto.getEmail().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (inserirDadosClienteDto.getCpf() == null || inserirDadosClienteDto.getCpf().isEmpty()) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        if (inserirDadosClienteDto.getSenhaCatraca() == null || inserirDadosClienteDto.getSenhaCatraca().isEmpty()) {
            erros.add(new BaseErrorDto("Senha da catraca.", MensagensErros.CAMPO_OBRIGATORIO));
        }
        return erros;
    }

    public List<BaseErrorDto> validateCamposInvalidos(
            InserirDadosClienteDto inserirDadosClienteDto,
            List<BaseErrorDto> erros) {
        if ((
                (inserirDadosClienteDto.getNome().split("\\s+").length) <= 1)
                || (inserirDadosClienteDto.getNome().replaceAll("\\s", "").length() < 6)) {
            erros.add(new BaseErrorDto("Nome.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!new DateUtils().dataIso(inserirDadosClienteDto.getDataNascimento())) {
            erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        } else {
            if (new DateUtils().idadeMinima(inserirDadosClienteDto.getDataNascimento()) < 14) {
                erros.add(new BaseErrorDto("Data de nascimento.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(inserirDadosClienteDto.getEmail()).matches()) {
            erros.add(new BaseErrorDto("E-mail.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!Pattern.compile("^[0-9]{11}$").matcher(inserirDadosClienteDto.getCpf()).matches()) {
            erros.add(new BaseErrorDto("CPF.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (!Pattern.compile("^[0-9]{4}$").matcher(inserirDadosClienteDto.getSenhaCatraca()).matches()) {
            erros.add(new BaseErrorDto("Senha da catrata.", MensagensErros.CAMPO_FORA_DO_PADRAO));
        }
        if (inserirDadosClienteDto.getStatus() != null) {
            if (!(new String().valueOf(inserirDadosClienteDto.getStatus()).matches("^[0]$"))) {
                erros.add(new BaseErrorDto("Status.", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        return erros;
    }
}
