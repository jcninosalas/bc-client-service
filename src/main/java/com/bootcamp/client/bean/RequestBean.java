package com.bootcamp.client.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestBean
{
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String documentType;
    @NotNull
    private String documentNumber;
}
