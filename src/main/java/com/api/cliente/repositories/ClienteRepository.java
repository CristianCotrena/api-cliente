package com.api.cliente.repositories;

import com.api.cliente.entity.models.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    Optional<Boolean> existsByEmail(String email);
    Optional<Boolean> existsByCpf(String cpf);
    Optional<Boolean> existsBySenhaCatraca(String senhaCatraca);
    Optional<ClienteModel> findById(UUID idCliente);
}
