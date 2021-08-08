package com.bootcamp.client.repository;

import com.bootcamp.client.model.AccountClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountClientRepository extends ReactiveMongoRepository<AccountClientEntity, String>
{
    Mono<AccountClientEntity> findByCustomerDocumentNumberAndAccountType(String dodcumentNumber, String type);
    Mono<AccountClientEntity> findByAccountAccountNumber(String accountNumber);
    Mono<AccountClientEntity> findByCustomerDocumentNumber(String documentNumber);
}
