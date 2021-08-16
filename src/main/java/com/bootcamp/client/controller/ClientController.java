package com.bootcamp.client.controller;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.dto.BaseResponseDto;
import com.bootcamp.client.model.ClientEntity;
import com.bootcamp.client.service.ClientService;
import com.ctc.wstx.shaded.msv_core.util.Uri;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personal-customers")
@Slf4j
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public Flux<ClientEntity> getClients() {
        return clientService.getClient();
    }

    @PostMapping("")
    public Mono<ResponseEntity<BaseResponseDto<ClientEntity>>> createClient(@Valid @RequestBody Mono<RequestBean> bean) throws Exception {
        return clientService.createClient(bean)
                .flatMap(clientEntity -> {
                    BaseResponseDto<ClientEntity> response = new BaseResponseDto<ClientEntity>(HttpStatus.CREATED, "Client created", clientEntity);
                    return Mono.just(ResponseEntity.created(URI.create("/p-customers")).body(response));
                });
    }

    @GetMapping("")
    public Mono<ResponseEntity<BaseResponseDto<ClientEntity>>> getClient(@RequestParam String documentNumber) {
        return clientService.getClient(documentNumber)
                .flatMap(clientEntity -> {
                    BaseResponseDto<ClientEntity> response = new BaseResponseDto<ClientEntity>(HttpStatus.FOUND, "Client founded", clientEntity);
                    return Mono.just(ResponseEntity.status(HttpStatus.FOUND).body(response));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("")
    public Mono<ResponseEntity<BaseResponseDto<Void>>> deleteClient(@RequestParam String documentNumber) {
        return clientService.deleteClient(documentNumber)
                .map(clientEntity -> {
                    BaseResponseDto<Void> responseDto = new BaseResponseDto<Void>(HttpStatus.ACCEPTED, "Client deleted");
                    return ResponseEntity.accepted().body(responseDto);
                });
    }

    @PutMapping("")
    public Mono<ResponseEntity<BaseResponseDto<ClientEntity>>> updateClient(@Valid @RequestBody RequestBean bean,
                                                                            @RequestParam String documentNumber) {
        return clientService.updateClient(bean, documentNumber)
                .flatMap(clientEntity -> {
                    BaseResponseDto<ClientEntity> response = new BaseResponseDto<ClientEntity>(HttpStatus.ACCEPTED, "Client has been updated", clientEntity);
                    return Mono.just(ResponseEntity.accepted().body(response));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
