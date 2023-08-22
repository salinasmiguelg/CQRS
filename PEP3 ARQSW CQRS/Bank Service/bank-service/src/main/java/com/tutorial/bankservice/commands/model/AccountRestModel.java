package com.tutorial.bankservice.commands.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class AccountRestModel {
    private BigDecimal startingBalance;
}
