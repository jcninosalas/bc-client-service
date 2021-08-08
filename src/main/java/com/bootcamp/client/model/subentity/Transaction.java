package com.bootcamp.client.model.subentity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Transaction
{
    private String type;
    private Double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;
}
