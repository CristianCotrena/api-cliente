package com.api.cliente.constants;

public @interface MensagensErros {

    String CAMPO_OBRIGATORIO = "Campo obrigatório.";
    String CAMPO_FORA_DO_PADRAO = "Campo fora do padrão.";
    String DADO_JA_CADASTRADO = "Já cadastrado.";
    String NAO_PERMITIDO_ALTERAR = "Não é permitido alterar esse campo.";
    String CAMPO_DIFERENTE_ANTERIOR = "Deve ser diferente do anterior.";
    String INEXISTENTE = "Dado inexistente.";
    String DADOS_NAO_ENCONTRADO = "Dados não encontrados.";
    String IDADE_MINIMA = "Deve ser maior que 14 anos.";
    String PAGINA_INVALIDA = "Página inválida.";
    String DUAS_DATAS = "Devem ser informada a data inicial e a data final.";
    String DATA_INICIAL_MENOR = "Data inicial deve ser menor que a data final.";
    String UM_CAMPO_EXIGIDO = "Deve informar ao menos um dos campos.";
    String PAGINA_OBRIGATORIA = "Página deve ser informada.";
}
