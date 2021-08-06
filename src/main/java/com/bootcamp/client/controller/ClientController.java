package com.bootcamp.client.controller;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.model.ClientEntity;
import com.bootcamp.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ClientController
{
    @Value("${db.host: default}")
    String host;


    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public Flux<ClientEntity> getHello(){
        return clientService.getClient();
    }

    @PostMapping("/client/{typePerson}")
    public Mono<String> createClient(@RequestBody RequestBean bean, @PathVariable String typePerson) {
        try {
            clientService.createClient(bean, typePerson);
            return Mono.just("Se creó cliente");
        }
        catch (Exception e){
            System.out.println("Ocurrio un error" + e) ;
            return null;
        }
    }


}
