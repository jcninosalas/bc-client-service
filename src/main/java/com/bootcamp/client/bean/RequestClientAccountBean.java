package com.bootcamp.client.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestClientAccountBean
{
    private String idAccount;
    private String accountNumber;
    private String type;
}
