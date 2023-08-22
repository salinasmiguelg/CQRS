package com.tutorial.bankservice.commands.events;

import com.tutorial.bankservice.commands.data.Account;
import com.tutorial.bankservice.commands.data.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("account")
public class AccountEventsHandler {
    private AccountRepository accountRepository;

    public AccountEventsHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Account account = new Account();
        account.setAccountId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getBalance());
        account.setStatus("CREATED");

        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        Account account = accountRepository.findById(accountActivatedEvent.getId()).orElse(null);

        if (account != null) {
            account.setStatus(accountActivatedEvent.getStatus());
            accountRepository.save(account);
        }
    }
    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent) {
        Account account = accountRepository
                .findById(accountCreditedEvent.getId()).orElse(null);

        if (account != null) {
            account.setBalance(account.getBalance()
                    .add(accountCreditedEvent.getAmount()));
        }
    }
    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent) {
        Account account = accountRepository
                .findById(accountDebitedEvent.getId()).orElse(null);

        if (account != null) {
            account.setBalance(account.getBalance()
                    .subtract(accountDebitedEvent.getAmount()));
        }
    }
}
