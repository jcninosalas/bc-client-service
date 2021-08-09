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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

                    List<Account> listAccount = new ArrayList<>();

                    Account account = new Account(accClientBean.getIdAccount(),
                            accClientBean.getAccountNumber(), accClientBean.getType(),
                            accClientBean.getLimitMovement(), accClientBean.getAmountTotal());

                    listAccount.add(account);

                    accountClientEntity.setCustomer(customer);
                    accountClientEntity.setAccount(listAccount);
                    accountClientEntity.setCreatedAt(new Date());

                    log.info("Guardando asignacion de cliente a cuenta : {}", accountClientEntity.toString() );

                    return accountClientRepository.findByCustomerDocumentNumber(
                            accountClientEntity.getCustomer().getDocumentNumber())
                            .flatMap(accountClientEntity1 -> {
                                accountClientEntity1.getAccount().add(account);
                                return accountClientRepository.findByCustomerDocumentNumberAndAccountType(
                                        accountClientEntity1.getCustomer().getDocumentNumber(),
                                        accClientBean.getType())
                                        .switchIfEmpty(accountClientRepository.save(accountClientEntity1));
                            })
                            .switchIfEmpty(accountClientRepository.save(accountClientEntity));
                });
    }

    @Override
    public Mono<AccountClientEntity> getAccountClient(String accountNumer, String documentNumber) {
        return accountClientRepository.findByAccountAccountNumberAndCustomerDocumentNumber(accountNumer, documentNumber);
    }
}
