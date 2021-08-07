package com.bootcamp.client.controller;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.model.ClientEntity;
import com.bootcamp.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/p-customer")
@Slf4j
public class ClientController
{
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public Flux<ClientEntity> getClients(){
        return clientService.getClient();
    }

    @PostMapping("")
    public Mono<ResponseEntity<Map<String, Object>>> createClient(@Valid @RequestBody Mono<RequestBean> bean) throws Exception {
        Map<String, Object> response = new HashMap<>();

        return clientService.createClient(bean)
                    .flatMap(clientEntity -> {
                        response.put("status", HttpStatus.OK.value());
                        response.put("message", "Se creo el cliente");
                        response.put("body", clientEntity);

                        return Mono.just(ResponseEntity.ok().body(response));
                    });
    }

    @GetMapping("")
    public Mono<ResponseEntity<Map<String, Object>>> getClient(@RequestParam String documentNumber){
        Map<String, Object> response = new HashMap<>();
        return clientService.getClient(documentNumber)
                .flatMap(clientEntity -> {
                    response.put("status", HttpStatus.OK.value());
                    response.put("message", "Se obtiene la información del cliente");
                    response.put("body", clientEntity);

                    return Mono.just(ResponseEntity.ok().body(response));
                });
    }

    @DeleteMapping("")
    public Mono<ResponseEntity<Map<String, Object>>> deleteClient(@RequestParam String documentNumber){
        Map<String, Object> response = new HashMap();
        return clientService.deleteClient(documentNumber)
                .map(clientEntity -> {
                    response.put("status", HttpStatus.OK.value());
                    response.put("message", "Se eliminó el cliente");

                    return ResponseEntity.ok().body(response);
                });
    }

    @PutMapping("")
    public Mono<ResponseEntity<Map<String, Object>>> updateClient(@Valid @RequestBody RequestBean bean,
                                                                  @RequestParam String documentNumber){
        Map<String, Object> response = new HashMap<>();
        return clientService.updateClient(bean, documentNumber)
                .flatMap(clientEntity -> {
                    response.put("status", HttpStatus.OK);
                    response.put("message", "Cliente actualizado");
                    response.put("body", clientEntity);

                    return Mono.just(ResponseEntity.ok().body(response));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
