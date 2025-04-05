package com.api.cliente.validate;

import com.api.cliente.base.dto.BaseErrorDto;
import com.api.cliente.constants.MensagensErros;
import com.api.cliente.core.data.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ListarClientesValidate {

    public List<BaseErrorDto> validarParametros(String dataInicial, String dataFinal, String pagina) {

        List<BaseErrorDto> erros = new ArrayList<>();

        if ((dataInicial == null || dataInicial.isEmpty())
                && (dataFinal == null || dataFinal.isEmpty())
                && (pagina == null || pagina.isEmpty())) {
            erros.add(new BaseErrorDto("dataNascimento, pagina", MensagensErros.UM_CAMPO_EXIGIDO));
        }

        if ((dataInicial != null && !dataInicial.isEmpty()) && (dataFinal == null || dataFinal.isEmpty())) {
            erros.add(new BaseErrorDto("dataNascimento", MensagensErros.DUAS_DATAS));
        }
        if ((dataInicial == null || dataInicial.isEmpty()) && (dataFinal != null && !dataFinal.isEmpty())) {
            erros.add(new BaseErrorDto("dataNascimento", MensagensErros.DUAS_DATAS));
        }
        if ((dataInicial != null && !dataInicial.isEmpty()) && (dataFinal != null && !dataFinal.isEmpty())) {
            if (!new DateUtils().dataIso(dataInicial)) {
                erros.add(new BaseErrorDto("dataNascimento", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
            if (!new DateUtils().dataIso(dataFinal)) {
                erros.add(new BaseErrorDto("dataNascimento", MensagensErros.CAMPO_FORA_DO_PADRAO));
            }
            if (dataInicial.compareTo(dataFinal) >= 0) {
                erros.add(new BaseErrorDto("dataNascimento", MensagensErros.DATA_INICIAL_MENOR));
            }
        }
        if (pagina != null && !pagina.isEmpty()) {
            try {
                int numeroDaPagina = Integer.parseInt(pagina);
                if (numeroDaPagina < 0) {
                    erros.add(new BaseErrorDto("pagina", MensagensErros.PAGINA_INVALIDA));
                }
            } catch (Exception exception) {
                erros.add(new BaseErrorDto("pagina", MensagensErros.PAGINA_INVALIDA));
            }
        }
        if (pagina == null || pagina.isEmpty()) {
            erros.add(new BaseErrorDto("pagina", MensagensErros.PAGINA_OBRIGATORIA));
        }

        return erros;
    }
}
