package com.bootcamp.client.controller;

import com.bootcamp.client.bean.RequestClientAccountBean;
import com.bootcamp.client.service.AccountClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account/p-customer")
public class AccountClientController
{
    @Autowired
    private AccountClientService accountClientService;

    @PostMapping("/{documentNumber}")
    public Mono<ResponseEntity<Map<String, Object>>> createAccountClient(@Valid @RequestBody Mono<RequestClientAccountBean> bean, @PathVariable String documentNumber){

        return  bean.flatMap(requestClientAccountBean -> {
            Map<String, Object> response = new HashMap<>();

            return accountClientService.createClientAccount(requestClientAccountBean, documentNumber)
                .flatMap(accountClientEntity -> {
                    response.put("status", HttpStatus.OK.value());
                    response.put("message", "Se asigno al cliente");
                    response.put("body", accountClientEntity);

                    return Mono.just(ResponseEntity.ok().body(response));
                });
        }).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
