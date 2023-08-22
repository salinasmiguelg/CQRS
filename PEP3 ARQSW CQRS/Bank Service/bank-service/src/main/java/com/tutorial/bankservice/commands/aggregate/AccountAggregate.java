package com.tutorial.bankservice.commands.aggregate;

import com.tutorial.bankservice.commands.commands.CreateAccountCommand;
import com.tutorial.bankservice.commands.events.AccountCreatedEvent;
import com.tutorial.bankservice.commands.events.AccountDebitedEvent;
import com.tutorial.bankservice.commands.commands.DepositMoneyCommand;
import com.tutorial.bankservice.commands.commands.WithdrawMoneyCommand;
import com.tutorial.bankservice.commands.events.AccountActivatedEvent;
import com.tutorial.bankservice.commands.events.AccountCreditedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;


@Aggregate
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private String status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        log.info("CreateAccountCommand recibido.");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getBalance()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        log.info("Ha ocurrido una creación de cuenta.");
        this.accountId = accountCreatedEvent.getId();
        this.balance = accountCreatedEvent.getBalance();
        this.status = "CREATED";

        AggregateLifecycle.apply(new AccountActivatedEvent(this.accountId, "ACTIVATED"));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        log.info("Ha ocurrido una activación de cuenta.");
        this.status = accountActivatedEvent.getStatus();
    }

    @CommandHandler
    public void on(DepositMoneyCommand depositMoneyCommand) {
        log.info("DepositMoneyCommand recibido.");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                depositMoneyCommand.getId(),
                depositMoneyCommand.getAmount()));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent) {
        log.info("Ha ocurrido un depósito en una cuenta.");
        this.balance = this.balance.add(accountCreditedEvent.getAmount());
    }

    @CommandHandler
    public void on(WithdrawMoneyCommand withdrawMoneyCommand) {
        log.info("WithdrawMoneyCommand recibido.");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                withdrawMoneyCommand.getId(),
                withdrawMoneyCommand.getAmount()));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent) {
        log.info("Ha ocurrido un giro en una cuenta.");
        this.balance = this.balance.subtract(accountDebitedEvent.getAmount());
    }
}
