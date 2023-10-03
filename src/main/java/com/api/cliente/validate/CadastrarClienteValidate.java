package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
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
            erros.add(new BaseErrorDto("Nome.", "Campo obrigatório"));
        }
        if (inserirDadosClienteDto.getDataNascimento() == null || inserirDadosClienteDto.getDataNascimento().isEmpty()) {
            erros.add(new BaseErrorDto("Data de Nascimento.", "Campo obrigatório"));
        }
        if (inserirDadosClienteDto.getEmail() == null || inserirDadosClienteDto.getEmail().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", "Campo obrigatório"));
        }
        if (inserirDadosClienteDto.getCpf() == null || inserirDadosClienteDto.getCpf().isEmpty()) {
            erros.add(new BaseErrorDto("CPF.", "Campo obrigatório"));
        }
        if (inserirDadosClienteDto.getSenhaCatraca() == null || inserirDadosClienteDto.getSenhaCatraca().isEmpty()) {
            erros.add(new BaseErrorDto("Senha da catraca.", "Campo obrigatório"));
        }
        return erros;
    }

    public List<BaseErrorDto> validateCamposInvalidos(
            InserirDadosClienteDto inserirDadosClienteDto,
            List<BaseErrorDto> erros) {
        if ((
                (inserirDadosClienteDto.getNome().split("\\s+").length) <= 1)
                || (inserirDadosClienteDto.getNome().replaceAll("\\s", "").length() < 6)) {
            erros.add(new BaseErrorDto("Nome.", "Campo fora do padrão."));
        }
        if (!new DateUtils().dataIso(inserirDadosClienteDto.getDataNascimento())) {
            erros.add(new BaseErrorDto("Data de nascimento.", "Campo fora do padrão."));
        } else {
            if (new DateUtils().idadeMinima(inserirDadosClienteDto.getDataNascimento()) < 14) {
                erros.add(new BaseErrorDto("Data de nascimento.", "Campo fora do padrão."));
            }
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(inserirDadosClienteDto.getEmail()).matches()) {
            erros.add(new BaseErrorDto("E-mail.", "Campo fora do padrão."));
        }
        if (!Pattern.compile("^[0-9]{11}$").matcher(inserirDadosClienteDto.getCpf()).matches()) {
            erros.add(new BaseErrorDto("CPF.", "Campo fora do padrão."));
        }
        if (!Pattern.compile("^[0-9]{4}$").matcher(inserirDadosClienteDto.getSenhaCatraca()).matches()) {
            erros.add(new BaseErrorDto("Senha da catrata.", "Campo fora do padrão."));
        }
        if (inserirDadosClienteDto.getStatus() != null) {
            if (!(new String().valueOf(inserirDadosClienteDto.getStatus()).matches("^[0]$"))) {
                erros.add(new BaseErrorDto("Status.", "Campo fora do padrão."));
            }
        }
        return erros;
    }
}
