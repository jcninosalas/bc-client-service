package com.bootcamp.client.service;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.model.ClientEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService
{
    public Mono<ClientEntity> createClient(Mono<RequestBean> bean) throws Exception;
    public Flux<ClientEntity> getClient();
    public Mono<ClientEntity> getClient(String documentNumber);
    public Mono<ClientEntity> updateClient(RequestBean bean, String documentNumber);
    public Mono<ClientEntity> deleteClient(String documentNumber);
}
