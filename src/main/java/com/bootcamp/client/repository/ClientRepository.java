package com.bootcamp.client.repository;

import com.bootcamp.client.model.ClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<ClientEntity, String> {

    Mono<ClientEntity> findByDocumentNumberAndIsActive(String documentNumber, Boolean isActive);
    Mono<ClientEntity> findByDocumentNumber(String documentNumber);
}
