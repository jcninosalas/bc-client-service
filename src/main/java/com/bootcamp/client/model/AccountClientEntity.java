package com.bootcamp.client.model;

import com.bootcamp.client.model.subentity.Account;
import com.bootcamp.client.model.subentity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

    @Getter
    @Setter
    @Document(collection = "accountClientPerson")
    @ToString
    @NoArgsConstructor
    public class AccountClientEntity
    {
        @Id
        private String _id;

        private Customer customer;
        private Account account;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        private Date createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        private Date modifiedAt;

        @Nullable
        private Integer limitTransaction;


    }
