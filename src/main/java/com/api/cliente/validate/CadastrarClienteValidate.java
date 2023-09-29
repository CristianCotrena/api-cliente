package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.models.ClienteModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CriarClienteValidate {

    public List<BaseErrorDto> validate(ClienteModel clienteModel) {
        List<BaseErrorDto> erros = validateCamposRequeridos(clienteModel);
        return erros.size() > 0 ? erros : validateCamposInvalidos(clienteModel, erros);
    }

    public List<BaseErrorDto> validateCamposRequeridos(ClienteModel clienteModel) {
        List<BaseErrorDto> erros = new ArrayList<>();
        if (clienteModel.getNome() == null || clienteModel.getNome().isEmpty()) {
            erros.add(new BaseErrorDto("Nome.", "Campo obrigatório"));
        }
        if (clienteModel.getDataNascimento() == null || clienteModel.getDataNascimento().isEmpty()) {
            erros.add(new BaseErrorDto("Data de Nascimento.", "Campo obrigatório"));
        }
        if (clienteModel.getEmail() == null || clienteModel.getEmail().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", "Campo obrigatório"));
        }
        if (clienteModel.getCpf() == null || clienteModel.getCpf().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", "Campo obrigatório"));
        }
        if (clienteModel.getSenhaCatraca() == null || clienteModel.getSenhaCatraca().isEmpty()) {
            erros.add(new BaseErrorDto("E-mail.", "Campo obrigatório"));
        }
        return erros;
    }

    public List<BaseErrorDto> validateCamposInvalidos(ClienteModel clienteModel, List<BaseErrorDto> erros) {

        if (((clienteModel.getNome().split("\\s+").length) < 1) && ((clienteModel.getNome().replaceAll("\\s", "").length() >= 6))) {
            erros.add(new BaseErrorDto("Nome.", "Campo fora do padrão."));
        }
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(clienteModel.getEmail()).matches()) {
            erros.add(new BaseErrorDto("E-mail.", "Campo fora do padrão."));
        }
        if (!Pattern.compile("^[0-9]{11}$").matcher(clienteModel.getCpf()).matches()) {
            erros.add(new BaseErrorDto("CPF.", "Campo fora do padrão."));
        }
        if (!Pattern.compile("^[0-9]{4}$").matcher(clienteModel.getSenhaCatraca()).matches()) {
            erros.add(new BaseErrorDto("Senha.", "Campo fora do padrão."));
        }
        return erros;
    }
}
