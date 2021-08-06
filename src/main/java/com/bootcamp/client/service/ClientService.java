package com.bootcamp.client.service;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.model.ClientEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService
{
    public Mono<ClientEntity> createClient(RequestBean bean, String typePerson) throws Exception;
    public Flux<ClientEntity> getClient();
}
