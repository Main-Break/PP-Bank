package com.ppbank.service;

import com.ppbank.exception.AccountNotFoundException;
import com.ppbank.model.Account;
import com.ppbank.model.AccountType;
import com.ppbank.repository.AccountRepository;

/**
 * Orchestrates banking operations. Depends only on the AccountRepository
 * abstraction (received via constructor), never on a concrete implementation -
 * this allows swapping storage or using a fake repository in tests without
 * touching this class.
 */
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account openAccount(String number, String owner, AccountType type) {
        if (repository.findByNumber(number).isPresent()) {
            throw new IllegalArgumentException("An account with number " + number + " already exists");
        }
        Account account = new Account(number, owner, type);
        repository.save(account);
        return account;
    }

    public void deposit(String number, double amount) {
        Account account = getAccount(number);
        account.deposit(amount);
        repository.save(account);
    }

    public void withdraw(String number, double amount) {
        Account account = getAccount(number);
        account.withdraw(amount);
        repository.save(account);
    }

    public void transfer(String fromNumber, String toNumber, double amount) {
        Account from = getAccount(fromNumber);
        Account to = getAccount(toNumber);
        from.withdraw(amount);
        to.deposit(amount);
        repository.save(from);
        repository.save(to);
    }

    public double getBalance(String number) {
        return getAccount(number).getBalance();
    }

    private Account getAccount(String number) {
        return repository.findByNumber(number)
                .orElseThrow(() -> new AccountNotFoundException(number));
    }
}
