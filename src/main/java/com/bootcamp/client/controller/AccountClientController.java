package com.bootcamp.client.controller;

import com.bootcamp.client.bean.RequestClientAccountBean;
import com.bootcamp.client.dto.BaseResponseDto;
import com.bootcamp.client.model.AccountClientEntity;
import com.bootcamp.client.service.AccountClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/p-customers/accounts")
public class AccountClientController
{
    @Autowired
    private AccountClientService accountClientService;

    @PostMapping("/{documentNumber}")
    public Mono<ResponseEntity<BaseResponseDto<AccountClientEntity>>> createAccountClient(@Valid @RequestBody Mono<RequestClientAccountBean> bean, @PathVariable String documentNumber){

        return  bean.flatMap(requestClientAccountBean -> {
            return accountClientService.createClientAccount(requestClientAccountBean, documentNumber)
                .flatMap(accountClientEntity -> {
                    BaseResponseDto<AccountClientEntity> response = new BaseResponseDto<>(HttpStatus.CREATED, "Account created for client with document number: " + documentNumber, accountClientEntity);
                    return Mono.just(ResponseEntity.created(URI.create("/p-customers/accounts")).body(response));
                });
        }).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{accountNumber}/{documentNumber}")
    public Mono<ResponseEntity<BaseResponseDto<AccountClientEntity>>> getAccountClient(@PathVariable String accountNumber,
                                                                      @PathVariable String documentNumber){
        return accountClientService.getAccountClient(accountNumber, documentNumber)
                .flatMap(accountClientEntity -> {
                    BaseResponseDto<AccountClientEntity> response = new BaseResponseDto<AccountClientEntity>(HttpStatus.FOUND, "Account founded", accountClientEntity);
                    return Mono.just(ResponseEntity.status(HttpStatus.FOUND).body(response));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
