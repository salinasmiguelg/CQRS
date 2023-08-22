package com.tutorial.bankservice.queries.controller;

import com.tutorial.bankservice.commands.data.Account;
import com.tutorial.bankservice.queries.queries.FindAccountByIdQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping(value = "/cuenta")
public class ManageAccountController {

    private final QueryGateway queryGateway;

    public ManageAccountController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> getAccount(@RequestParam String id) {
        Account account = queryGateway.query(
                new FindAccountByIdQuery(id), Account.class
        ).join();

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }
}
