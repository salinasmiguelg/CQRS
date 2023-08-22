package com.tutorial.bankservice.commands.controller;

import com.tutorial.bankservice.commands.commands.CreateAccountCommand;
import com.tutorial.bankservice.commands.commands.DepositMoneyCommand;
import com.tutorial.bankservice.commands.commands.WithdrawMoneyCommand;
import com.tutorial.bankservice.commands.model.AccountRestModel;
import com.tutorial.bankservice.commands.model.DepositRestModel;
import com.tutorial.bankservice.commands.model.WithdrawRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/banco")
public class BankAccountController {


    private final CommandGateway commandGateway;

    public BankAccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(value = "/crear")
    public ResponseEntity<String> createAccount(@RequestBody AccountRestModel request) {
        try {
            CompletableFuture<String> response =
                    commandGateway.send(new CreateAccountCommand(
                            UUID.randomUUID().toString(),
                            request.getStartingBalance()));


            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/depositar")
    public ResponseEntity<String> deposit(@RequestBody DepositRestModel request) {
        try {
            commandGateway.send(new DepositMoneyCommand(
                    request.getAccountId(),
                    request.getAmount()
            ));

            return new ResponseEntity<>("Monto depositado de manera exitosa", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/retirar")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRestModel request) {
        try {
            commandGateway.send(new WithdrawMoneyCommand(
                    request.getAccountId(),
                    request.getAmount()
            ));

            return new ResponseEntity<>("Monto retirado de manera exitosa.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ha ocurrido un error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
