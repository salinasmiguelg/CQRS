package com.tutorial.bankservice.queries.projection;

import com.tutorial.bankservice.commands.events.AccountCreatedEvent;
import com.tutorial.bankservice.commands.events.AccountDebitedEvent;
import com.tutorial.bankservice.queries.queries.FindAccountByIdQuery;
import com.tutorial.bankservice.commands.events.AccountActivatedEvent;
import com.tutorial.bankservice.commands.events.AccountCreditedEvent;
import com.tutorial.bankservice.commands.data.Account;
import com.tutorial.bankservice.commands.data.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AccountProjection {

    private final AccountRepository accountRepository;

    public AccountProjection(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        log.info("Handling AccountCreatedEvent...");
        Account account = new Account();
        account.setAccountId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getBalance());
        account.setStatus("CREATED");

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        log.info("Handling AccountActivatedEvent...");
        Account account = accountRepository.findById(accountActivatedEvent.getId()).orElse(null);

        if (account != null) {
            account.setStatus(accountActivatedEvent.getStatus());
            accountRepository.save(account);
        }
    }
    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent) {
        log.info("Handling AccountCreditedEvent...");
        Account account = accountRepository
                .findById(accountCreditedEvent.getId()).orElse(null);

        if (account != null) {
            account.setBalance(account.getBalance()
                    .add(accountCreditedEvent.getAmount()));
        }
    }
    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent) {
        log.info("Handling AccountDebitedEvent...");
        Account account = accountRepository
                .findById(accountDebitedEvent.getId()).orElse(null);

        if (account != null) {
            account.setBalance(account.getBalance()
                    .subtract(accountDebitedEvent.getAmount()));
        }
    }

    @QueryHandler
    public Account handle(FindAccountByIdQuery query) {
        log.info("Handling FindAccountByIdQuery...");
        Account account = accountRepository
                .findById(query.getAccountId()).orElse(null);

        return account;
    }
}
