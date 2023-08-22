package com.tutorial.bankservice.commands.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class DepositRestModel {

    private String accountId;
    private BigDecimal amount;
}
