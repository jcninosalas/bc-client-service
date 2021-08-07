package com.bootcamp.client.service.impl;

import com.bootcamp.client.bean.RequestBean;
import com.bootcamp.client.model.ClientEntity;
import com.bootcamp.client.bean.Constants;
import com.bootcamp.client.repository.ClientRepository;
import com.bootcamp.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Mono<ClientEntity> createClient(Mono<RequestBean> bean) {

        return bean.flatMap(requestBean -> {
            /*if(!Arrays.asList(Constants.documentType).contains(requestBean.getDocumentType()))
                throw new BusinessException(HttpStatus.BAD_REQUEST, "------");*/

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setFirstName(requestBean.getFirstName());
            clientEntity.setLastName(requestBean.getLastName());
            clientEntity.setDocumentNumber(requestBean.getDocumentNumber());
            clientEntity.setTypeClient(Constants.typePerson);
            clientEntity.setDocumentType(requestBean.getDocumentType());
            clientEntity.setIsActive(true);
            clientEntity.setCreatedAt(new Date());
            log.info("Guardando cliente...");

            return clientRepository.save(clientEntity);

        });
    }

    @Override
    public Flux<ClientEntity> getClient() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<ClientEntity> getClient(String documentNumber) {
        return clientRepository.findByDocumentNumberAndIsActive(documentNumber, true);
    }

    @Override
    public Mono<ClientEntity> updateClient(RequestBean bean, String documentNumber) {
        return clientRepository.findByDocumentNumber(documentNumber)
                .flatMap(clientEntity -> {
                    clientEntity.setFirstName(bean.getFirstName());
                    clientEntity.setLastName(bean.getLastName());
                    clientEntity.setModifiedAt(new Date());

                    return clientRepository.save(clientEntity);
                });
    }

    @Override
    public Mono<ClientEntity> deleteClient(String documentNumber) {
        return clientRepository.findByDocumentNumberAndIsActive(documentNumber, true)
                .flatMap(clientEntity -> {
                    clientEntity.setIsActive(false);

                    return clientRepository.save(clientEntity);
                });
    }


}
