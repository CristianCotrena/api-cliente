package com.api.cliente.repositories.specifications.cliente;

import com.api.cliente.entity.models.ClienteModel;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecifications {

    public static Specification<ClienteModel> dataInicialMaiorOuIgual(String dataInicial) {
        return (root, query, criteriaBuilder) ->
                dataInicial == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("dataNascimento"), dataInicial);
    }

    public static Specification<ClienteModel> dataFinalMenorOuIgual(String dataFinal) {
        return (root, query, criteriaBuilder) ->
                dataFinal == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("dataNascimento"), dataFinal);
    }
}
