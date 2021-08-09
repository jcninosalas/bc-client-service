package com.bootcamp.client.model.subentity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Account
{
    private String _id;
    private String accountNumber;
    private String type;
    private Integer limitMovement;
    private BigDecimal amountTotal;
}
