package com.tutorial.bankservice.commands.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class WithdrawRestModel {

    private String accountId;
    private BigDecimal amount;
}
