package com.bootcamp.client.service.impl;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.model.ClientEntity;
import com.bootcamp.client.model.Constants;
import com.bootcamp.client.repository.ClientRepository;
import com.bootcamp.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Mono<ClientEntity> createClient(RequestBean bean, String typePerson) throws Exception {
        if(!Arrays.asList(Constants.typePerson).contains(typePerson))
            throw new Exception("No hay ese valor");

        if(!Arrays.asList(Constants.documentType).contains(bean.getDocumentType()))
            throw new Exception("No hay ese tipo de documento");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName(bean.getFirstName());
        clientEntity.setLastName(bean.getLastName());
        clientEntity.setCompanyName(bean.getCompanyName());
        clientEntity.setContactName(bean.getContactName());
        clientEntity.setDocumentNumber(bean.getDocumentNumber());
        clientEntity.setTypeClient(typePerson);
        clientEntity.setDocumentType(bean.getDocumentType());
        clientEntity.setIsActive(true);
        clientEntity.setCreatedAt(new Date());

        log.info("Guardando cliente...");
        Mono<ClientEntity> client = Mono.just(clientEntity);

        clientRepository.save(clientEntity).subscribe(clientEntity1 -> {
            log.info("Cliente guardado : {}", clientEntity1.toString() );
        });


        return Mono.just(clientEntity);
    }

    @Override
    public Flux<ClientEntity> getClient() {
        return clientRepository.findAll();
    }
}
