package com.api.cliente.repositories;

import com.api.cliente.entity.models.ClienteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    Optional<Boolean> existsByEmail(String email);
    Optional<Boolean> existsByCpf(String cpf);
    Optional<Boolean> existsBySenhaCatraca(String senhaCatraca);
    Optional<ClienteModel> findById(UUID id);

    Page<ClienteModel> findAll(Specification<ClienteModel> clienteModelSpecification, Pageable pagina);
}
