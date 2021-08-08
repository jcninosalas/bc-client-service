package com.bootcamp.client.service;

import com.bootcamp.client.bean.RequestClientAccountBean;
import com.bootcamp.client.model.AccountClientEntity;
import reactor.core.publisher.Mono;

public interface AccountClientService
{
    public Mono<AccountClientEntity> createClientAccount(RequestClientAccountBean accClientBean,
                                                         String documentNumber);
    public Mono<AccountClientEntity> getAccountClient(String accountNumer);
}
