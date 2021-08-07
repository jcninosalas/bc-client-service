package com.bootcamp.client.service.impl;

import com.bootcamp.client.bean.RequestClientAccountBean;
import com.bootcamp.client.model.AccountClientEntity;
import com.bootcamp.client.model.subentity.Account;
import com.bootcamp.client.model.subentity.Customer;
import com.bootcamp.client.repository.AccountClientRepository;
import com.bootcamp.client.repository.ClientRepository;
import com.bootcamp.client.service.AccountClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@Slf4j
public class AccountServiceImpl implements AccountClientService
{
    @Autowired
    AccountClientRepository accountClientRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Mono<AccountClientEntity> createClientAccount(RequestClientAccountBean accClientBean,
                                                         String documentNumber) {
        return clientRepository.findByDocumentNumber(documentNumber)
                .flatMap(clientEntity -> {
                    AccountClientEntity accountClientEntity = new AccountClientEntity();
                    Customer customer = new Customer(clientEntity.get_id(), clientEntity.getDocumentNumber());
                    Account account = new Account(accClientBean.getIdAccount(),
                            accClientBean.getAccountNumber(), accClientBean.getType());

                    accountClientEntity.setCustomer(customer);
                    accountClientEntity.setAccount(account);
                    accountClientEntity.setCreatedAt(new Date());

                    log.info("Guardando asignacion de cliente a cuenta : {}", accountClientEntity.toString() );

                    return accountClientRepository.save(accountClientEntity);
                });
    }
}
