package com.tutorial.bankservice.queries.projection;


import com.tutorial.bankservice.queries.queries.FindAccountByIdQuery;
import com.tutorial.bankservice.commands.data.Account;
import com.tutorial.bankservice.commands.data.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AccountProjection {

    private final AccountRepository accountRepository;

    public AccountProjection(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @QueryHandler
    public Account handle(FindAccountByIdQuery query) {
        log.info("Handling FindAccountByIdQuery...");
        Account account = accountRepository
                .findById(query.getAccountId()).orElse(null);

        return account;
    }
}
