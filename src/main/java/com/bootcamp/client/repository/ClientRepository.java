package com.bootcamp.client.repository;

import com.bootcamp.client.model.ClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientRepository extends ReactiveMongoRepository<ClientEntity, String> {
}
