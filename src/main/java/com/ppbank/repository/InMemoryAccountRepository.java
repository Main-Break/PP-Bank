package com.ppbank.repository;

import com.ppbank.model.Account;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Simple in-memory implementation, useful for the base project and for tests.
 * Can be replaced with an implementation backed by a real database without
 * AccountService needing to change a single line.
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> accounts = new LinkedHashMap<>();

    @Override
    public void save(Account account) {
        accounts.put(account.getNumber(), account);
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        return Optional.ofNullable(accounts.get(number));
    }

    @Override
    public List<Account> findAll() {
        return List.copyOf(accounts.values());
    }
}
