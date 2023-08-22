package com.tutorial.bankservice.queries.queries;

import lombok.Data;


@Data
public class FindAccountByIdQuery {
    private String accountId;

    public FindAccountByIdQuery(String accountId) {
        this.accountId = accountId;
    }

}
