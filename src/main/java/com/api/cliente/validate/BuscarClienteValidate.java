package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BuscarClienteValidate {

    public List<BaseErrorDto> validarParametros(String id, String email, String cpf) {

        List<BaseErrorDto> erros = new ArrayList<>();

        if (id != null && !id.isEmpty()) {
            if (!Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$").matcher(id).matches()) {
                erros.add(new BaseErrorDto("id", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (email != null && !email.isEmpty()) {
            if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()) {
                erros.add(new BaseErrorDto("email", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if (cpf != null && !cpf.isEmpty()) {
            if (!Pattern.compile("^[0-9]{11}$").matcher(cpf).matches()) {
                erros.add(new BaseErrorDto("cpf", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
        }
        if ((id == null || id.isEmpty()) && (email == null || email.isEmpty()) && (cpf == null || cpf.isEmpty())) {
            erros.add(new BaseErrorDto("id, email, cpf", MensagensErros.UM_CAMPO_EXIGIDO));
        }
        return erros;
    }
}
